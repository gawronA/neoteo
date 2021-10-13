package pl.local.mpark.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.local.mpark.entity.Booking;
import pl.local.mpark.service.BookingService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookingConverter implements Converter<String, Set<Booking>> {

    private final BookingService bookingService;

    @Autowired
    public BookingConverter(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public Set<Booking> convert(String s) {
        Booking booking = this.bookingService.getBooking(Long.parseLong(s));
        if(booking == null) return null;
        Set<Booking> bookings = new HashSet<Booking>();
        bookings.add(booking);
        return bookings;
    }
}
