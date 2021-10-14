package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.local.neoteo.entity.AppUser;
import pl.local.neoteo.entity.Booking;
import pl.local.neoteo.entity.Payment;
import pl.local.neoteo.entity.Subscription;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.repository.BookingRepository;
import pl.local.neoteo.repository.SubscriptionRepository;

import java.util.List;

@Service
class BookingExtServiceImpl {
    private final BookingRepository bookingRepository;

    public BookingExtServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    void save(Booking booking) {
        this.bookingRepository.save(booking);
    }

    @Transactional(rollbackFor = Exception.class)
    void delete(long id) {
        this.bookingRepository.deleteById(id);
    }
}

@Service("bookingService")
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingExtServiceImpl bookingExtService;
    private final AppUserService appUserService;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingExtServiceImpl bookingExtService, AppUserService appUserService) {
        this.bookingRepository = bookingRepository;
        this.bookingExtService = bookingExtService;
        this.appUserService = appUserService;
    }

    public DatabaseResult addBooking(Booking booking) {
        try {
            this.bookingExtService.save(booking);
        }
        catch (Exception ex) {
            return DatabaseResult.AlreadyExist;
        }
        return DatabaseResult.Success;
    }

    @Override
    @Transactional
    public DatabaseResult updateBooking(Booking booking) {
        var dbBookingFind = this.bookingRepository.findById(booking.getId());
        Booking dbBooking = dbBookingFind.orElse(null);
        if(dbBooking == null) return DatabaseResult.Error;

        dbBooking.setFromTime(booking.getFromTime());
        dbBooking.setToTime(booking.getToTime());
        dbBooking.setAccepted(booking.isAccepted());

        return addBooking(dbBooking);
    }

    public DatabaseResult deleteBooking(long id) {
        Booking booking = getBooking(id);
        if(booking == null) return DatabaseResult.Error;

        AppUser bookingUser = booking.getAppUser();
        booking.setAppUser(null);
        this.appUserService.editUser(bookingUser);

        try {
            this.bookingExtService.delete(id);
        }
        catch (Exception ex) {
            return DatabaseResult.HasDependencies;
        }
        return DatabaseResult.Success;
    }

    @Transactional
    public Booking getBooking(long id) {
        var booking = this.bookingRepository.findById(id);
        return booking.orElse(null);
    }
    @Transactional
    public List<Booking> getBookings() {
        return this.bookingRepository.findAll();
    }

    @Transactional
    public List<Booking> getFreeBookings() {
        var bookings = this.bookingRepository.findByAppUser(null);
        return bookings;
    }
}
