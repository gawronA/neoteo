package pl.local.mpark.service;

import org.springframework.stereotype.Service;
import pl.local.mpark.entity.Booking;
import pl.local.mpark.entity.Subscription;
import pl.local.mpark.helper.DatabaseResult;

import java.util.List;

@Service("bookingService")
public interface BookingService {
    public DatabaseResult addBooking(Booking booking);
    public DatabaseResult updateBooking(Booking booking);
    public DatabaseResult deleteBooking(long id);
    public Booking getBooking(long id);
    public List<Booking> getBookings();
    public List<Booking> getFreeBookings();
}
