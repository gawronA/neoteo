package pl.local.mpark.service;

import org.springframework.stereotype.Service;
import pl.local.mpark.entity.AppUser;
import pl.local.mpark.entity.Booking;
import pl.local.mpark.entity.Payment;
import pl.local.mpark.entity.Subscription;
import pl.local.mpark.helper.DatabaseResult;

import java.util.Date;

@Service
public interface AccountService {
    DatabaseResult addAccount(AppUser user);
    DatabaseResult updateAccount(AppUser user);
    DatabaseResult changePassword(AppUser user, String password);
    DatabaseResult changeSubscription(AppUser user, Subscription subscription);
    DatabaseResult addBooking(AppUser user, Booking booking);
    DatabaseResult confirmBooking(Booking booking);
    double getBalance(AppUser user);
    int getPendingPaymentsCount(AppUser user);
    DatabaseResult makeParkingPayment(AppUser user, Date fromDate, Date toDate);
}
