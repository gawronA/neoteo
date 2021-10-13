package pl.local.mpark.service;

import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.local.mpark.entity.*;
import pl.local.mpark.helper.DatabaseResult;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Period;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    private final PaymentService paymentService;
    private final SubscriptionService subscriptionService;
    private final AppUserService appUserService;
    private final BookingService bookingService;
    private final AppUserRoleService appUserRoleService;

    public AccountServiceImpl(PaymentService paymentService, SubscriptionService subscriptionService, AppUserService appUserService, BookingService bookingService, AppUserRoleService appUserRoleService) {
        this.paymentService = paymentService;
        this.subscriptionService = subscriptionService;
        this.appUserService = appUserService;
        this.bookingService = bookingService;
        this.appUserRoleService = appUserRoleService;
    }

    @Override
    public DatabaseResult addAccount(AppUser user) {
        AppUserRole userRole = this.appUserRoleService.getAppUserRole("ROLE_USER");
        if(userRole == null) return DatabaseResult.Error;
        user.setAppUserRoles(new HashSet<>());
        user.getAppUserRoles().add(userRole);
        user.setPayments(new HashSet<>());
        user.setUsername(user.getEmail());
        user.setPassword(hashPassword(user.getPassword()));
        user.setActive(true);

        UserCard card = new UserCard();
        card.setNumber(RandomStringUtils.random(14, false, true));
        card.setAppUser(user);
        user.setCard(card);

        user.getPayments().add(makePayment("Subskrybcja " + user.getSubscription().getName(), user.getSubscription().getBasePrice(), user));

        //if(this.paymentService.addPayment(subscriptionPayment) != DatabaseResult.Success) return DatabaseResult.Error;
        return this.appUserService.addUser(user);
    }

    @Override
    public DatabaseResult updateAccount(AppUser user) {
        AppUser dbUser = this.appUserService.getUser(user.getId());
        if(dbUser == null) return DatabaseResult.Error;

        dbUser.setActive(user.isActive());
        if(user.getPassword() != null && !user.getPassword().isEmpty()) dbUser.setPassword(user.getPassword());
        if(user.getFirstName() != null && !user.getFirstName().isEmpty()) dbUser.setFirstName(user.getFirstName());
        if(user.getLastName() != null && !user.getLastName().isEmpty()) dbUser.setLastName(user.getLastName());
        if(user.getEmail() != null && !user.getEmail().isEmpty()) {
            dbUser.setEmail(user.getEmail());
            dbUser.setUsername(user.getEmail());
        }
        if(user.getTelephoneNumber() != null && !user.getTelephoneNumber().isEmpty()) dbUser.setTelephoneNumber(user.getTelephoneNumber());
        if(user.getAppUserRoles() != null && !user.getAppUserRoles().isEmpty()) dbUser.setAppUserRoles(user.getAppUserRoles());
        if(user.getCard() != null) dbUser.setCard(user.getCard());
        if(user.getSubscription() != null) dbUser.setSubscription(user.getSubscription());
        if(user.getPayments() != null && !user.getPayments().isEmpty()) {
            for(Payment payment : user.getPayments()) {
                payment.setAppUser(dbUser);
            }
            dbUser.setPayments(user.getPayments());
        }
        else if(user.getPayments() == null) {
            for(Payment payment : dbUser.getPayments()) {
                payment.setAppUser(null);
            }
        }
        if(user.getBookings() != null && !user.getBookings().isEmpty()) {
            for(Booking booking : user.getBookings()) {
                booking.setAppUser(dbUser);
            }
            dbUser.setBookings(user.getBookings());
        }
        else if(user.getBookings() == null) {
            for(Booking booking : dbUser.getBookings()) {
                booking.setAppUser(null);
            }
        }

        return this.appUserService.editUser(dbUser);
    }

    @Override
    public DatabaseResult changePassword(AppUser user, String password) {
        password = hashPassword(password);
        user.setPassword(password);
        return updateAccount(user);
    }

    @Override
    public DatabaseResult changeSubscription(AppUser user, Subscription subscription) {
        user.setSubscription(subscription);

        user.getPayments().add(makePayment("Subskrybcja " + user.getSubscription().getName(), user.getSubscription().getBasePrice(), user));

        return updateAccount(user);
    }

    @Override
    public DatabaseResult addBooking(AppUser user, Booking booking) {
        booking.setAppUser(user);
        user.getBookings().add(booking);

        return updateAccount(user);
    }

    @Override
    public DatabaseResult confirmBooking(Booking booking) {
        AppUser user = booking.getAppUser();
        booking.setAccepted(true);

        double amount = calculatePrice(booking.getFromTime(), booking.getToTime(), user.getSubscription());
        amount += user.getSubscription().getBookingPrice();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        user.getPayments().add(makePayment("Rezerwacja " +
                                                dateFormat.format(booking.getFromTime()) +
                                                " - "+
                                                dateFormat.format(booking.getToTime()), amount, user));

        return updateAccount(user);
    }

    @Override
    public double getBalance(AppUser user) {
        var payments = getPendingPayments(user);
        double balance = 0;
        for(Payment payment : payments) {
            balance += payment.getAmount();
        }
        return balance;
    }

    @Override
    public int getPendingPaymentsCount(AppUser user) {
        return getPendingPayments(user).size();
    }

    @Override
    public DatabaseResult makeParkingPayment(AppUser user, Date fromDate, Date toDate) {
        double parkingPrice = calculatePrice(fromDate, toDate, user.getSubscription());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Payment parkingPayment = makePayment("Opłata parkingowa " + dateFormat.format(fromDate) +
                                                    " - " + dateFormat.format(toDate),
                parkingPrice, user);

        user.getPayments().add(parkingPayment);
        return updateAccount(user);
    }

    private List<Payment> getPendingPayments(AppUser user) {
        var payments = user.getPayments().stream().filter(p-> !p.isPaid());
        return payments.collect(Collectors.toList());
    }

    private Payment makePayment(String name, double amount, AppUser user) {
        Payment payment = new Payment();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        payment.setAmount(amount);
        payment.setName(name + " | " + dateFormat.format(new Date()));
        payment.setAppUser(user);
        return payment;
    }

    private double calculatePrice(Date from, Date to, Subscription subscription) {
        double timeInMiliseconds = to.getTime() - from.getTime();
        double timeInHours = timeInMiliseconds / 1000 / 60 / 60;
        double price = timeInHours * subscription.getHourPrice();
        return price;
    }

    private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

}
