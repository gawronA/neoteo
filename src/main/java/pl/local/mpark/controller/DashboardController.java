package pl.local.mpark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.local.mpark.entity.*;
import pl.local.mpark.helper.AppUserValidator;
import pl.local.mpark.helper.DatabaseResult;
import pl.local.mpark.service.*;

import javax.faces.annotation.RequestMap;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class DashboardController {
    private final AppUserService appUserService;
    private final PaymentService paymentService;
    private final SubscriptionService subscriptionService;
    private final BookingService bookingService;
    private final AppUserRoleService appUserRoleService;
    private final AccountService accountService;

    private final AppUserValidator appUserValidator = new AppUserValidator();

    public DashboardController(AppUserService appUserService, PaymentService paymentService, SubscriptionService subscriptionService, BookingService bookingService, AppUserRoleService appUserRoleService, AccountService accountService) {
        this.appUserService = appUserService;
        this.paymentService = paymentService;
        this.subscriptionService = subscriptionService;
        this.bookingService = bookingService;
        this.appUserRoleService = appUserRoleService;
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public String user(@PathVariable("id") long id, Model model) {
        if(id == 0) {
            model.addAttribute("appUser", new AppUser());
        }
        else {
            model.addAttribute("appUser", this.appUserService.getUser(id));
        }
        model.addAttribute("freePayments", this.paymentService.getFreePayments());
        model.addAttribute("subscriptions", this.subscriptionService.getSubscriptions());
        model.addAttribute("freeBookings", this.bookingService.getFreeBookings());
        model.addAttribute("appUserRoleList", this.appUserRoleService.getAppUserRoles());
        return "/dashboard/user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String users(Model model) {
        model.addAttribute("appUsers", appUserService.getUsers());
        return "/dashboard/users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "users", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("appUser") AppUser user, BindingResult bindingResult) {
        appUserValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()) return "redirect:users?errorMsg=" + URLEncoder.encode("message.invalidData", StandardCharsets.UTF_8);

        DatabaseResult result = DatabaseResult.Error;
        if(user.getId() == 0) {
            result = this.accountService.addAccount(user);
        }
        else {
            result = this.accountService.updateAccount(user);
        }
        //var result = this.appUserService.addUser(user);
        if(result == DatabaseResult.Success) return "redirect:users?successMsg=" + URLEncoder.encode("message.createUserSuccessful", StandardCharsets.UTF_8);
        else if(result == DatabaseResult.AlreadyExist) return "redirect:users?errorMsg=" + URLEncoder.encode("message.userAlreadyExist", StandardCharsets.UTF_8);
        return "redirect:users?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "deleteUser", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("id") long id) {
        var result = this.appUserService.deleteUser(id);
        if(result == DatabaseResult.Success) return "redirect:users?successMsg=" + URLEncoder.encode("message.userDeleteSuccessful", StandardCharsets.UTF_8);
        else if(result == DatabaseResult.HasDependencies) return "redirect:users?errorMsg=" + URLEncoder.encode("message.hasDependencies", StandardCharsets.UTF_8);
        return "redirect:users?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);

    }


    ////////////////////////////////
    /////PAYMENTS
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "payment/{id}", method = RequestMethod.GET)
    public String payment(@PathVariable("id") long id, Model model) {
        if(id == 0) {
            model.addAttribute("payment", new Payment());
        }
        else {
            model.addAttribute("payment", this.paymentService.getPayment(id));
        }
        return "/dashboard/payment";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "payments", method = RequestMethod.GET)
    public String payments(Model model, SecurityContextHolderAwareRequestWrapper wrapper, Principal principal) {
        if(wrapper.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("payments", this.paymentService.getPayments());
        }
        else {
            model.addAttribute("payments", this.appUserService.getUser(principal.getName()).getPayments());
        }
        return "/dashboard/payments";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "payments", method = RequestMethod.POST)
    public String addPayment(@Valid @ModelAttribute Payment payment, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "redirect:payments?errorMsg=" + URLEncoder.encode("message.invalidData", StandardCharsets.UTF_8);

        DatabaseResult result = DatabaseResult.Error;
        if(payment.getId() == 0) {
            result = this.paymentService.addPayment(payment);
        }
        else {
            result = this.paymentService.updatePayment(payment);
        }
        if(result == DatabaseResult.Success) return "redirect:payments?successMsg=" + URLEncoder.encode("message.paymentCreatedSuccessfully", StandardCharsets.UTF_8);
        else if(result == DatabaseResult.AlreadyExist) return "redirect:payments?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
        return "redirect:payments?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "deletePayment", method = RequestMethod.POST)
    public String deletePayment(@RequestParam("id") long id) {
        var result = this.paymentService.deletePayment(id);
        if(result == DatabaseResult.Success) return "redirect:payments?successMsg=" + URLEncoder.encode("message.paymentDeletedSuccessfully", StandardCharsets.UTF_8);
        else if(result == DatabaseResult.HasDependencies) return "redirect:payments?errorMsg=" + URLEncoder.encode("message.hasDependencies", StandardCharsets.UTF_8);
        return "redirect:payments?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);

    }

    ////////////////////////////////
    /////SUBSCRIPTIONS
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "subscription/{id}", method = RequestMethod.GET)
    public String subscription(@PathVariable("id") long id, Model model) {
        if(id == 0) {
            model.addAttribute("subscription", new Subscription());
        }
        else {
            model.addAttribute("subscription", this.subscriptionService.getSubscription(id));
        }
        return "/dashboard/subscription";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "subscriptions", method = RequestMethod.GET)
    public String subscriptions(Model model, SecurityContextHolderAwareRequestWrapper wrapper, Principal principal) {
        if(wrapper.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("subscriptions", subscriptionService.getSubscriptions());
        }
        else {
            List<Subscription> subscriptionList = new ArrayList<>();
            subscriptionList.add(appUserService.getUser(principal.getName()).getSubscription());
            model.addAttribute("subscriptions", subscriptionList);
        }
        return "/dashboard/subscriptions";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "subscriptions", method = RequestMethod.POST)
    public String addSubscription(@Valid @ModelAttribute(value = "subscription") Subscription subscription, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "redirect:subscriptions?errorMsg=" + URLEncoder.encode("message.invalidData", StandardCharsets.UTF_8);
        var result = this.subscriptionService.addSubscription(subscription);
        if(result == DatabaseResult.Success) return "redirect:subscriptions?successMsg=" + URLEncoder.encode("message.subscriptionCreatedSuccessfully", StandardCharsets.UTF_8);
        else if(result == DatabaseResult.AlreadyExist) return "redirect:subscriptions?errorMsg=" + URLEncoder.encode("message.subscriptionAlreadyExist", StandardCharsets.UTF_8);
        return "redirect:subscriptions?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "deleteSubscription", method = RequestMethod.POST)
    public String deleteSubscription(@RequestParam("id") long id) {
        var result = this.subscriptionService.deleteSubscription(id);
        if(result == DatabaseResult.Success) return "redirect:subscriptions?successMsg=" + URLEncoder.encode("message.subscriptionDeletedSuccessfully", StandardCharsets.UTF_8);
        else if(result == DatabaseResult.HasDependencies) return "redirect:subscriptions?errorMsg=" + URLEncoder.encode("message.hasDependencies", StandardCharsets.UTF_8);
        return "redirect:subscriptions?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
    }

    ////////////////////////////////
    /////Bookings
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_USER')")
    @RequestMapping(value = "booking/{id}", method = RequestMethod.GET)
    public String booking(@PathVariable("id") long id, Model model) {
        if(id == 0) {
            model.addAttribute("booking", new Booking());
        }
        else {
            model.addAttribute("booking", this.bookingService.getBooking(id));
        }
        return "/dashboard/booking";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_USER')")
    @RequestMapping(value = "bookings", method = RequestMethod.GET)
    public String bookings(Model model, SecurityContextHolderAwareRequestWrapper wrapper, Principal principal) {
        if(wrapper.isUserInRole("ROLE_ADMIN") || wrapper.isUserInRole("ROLE_EMPLOYEE")) {
            model.addAttribute("bookings", bookingService.getBookings());
        }
        else {
            model.addAttribute("bookings", appUserService.getUser(principal.getName()).getBookings());
        }
        return "/dashboard/bookings";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @RequestMapping(value = "bookings", method = RequestMethod.POST)
    public String addBooking(@Valid @ModelAttribute(value = "booking") Booking booking, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "redirect:bookings?errorMsg=" + URLEncoder.encode("message.invalidData", StandardCharsets.UTF_8);

        DatabaseResult result = DatabaseResult.Error;
        if(booking.getId() == 0) {
            result = this.bookingService.addBooking(booking);
        }
        else {
            result = this.bookingService.updateBooking(booking);
        }
        if(result == DatabaseResult.Success) return "redirect:bookings?successMsg=" + URLEncoder.encode("message.bookingCreatedSuccessfully", StandardCharsets.UTF_8);
        else if(result == DatabaseResult.AlreadyExist) return "redirect:bookings?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
        return "redirect:bookings?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_USER')")
    @RequestMapping(value = "deleteBooking", method = RequestMethod.POST)
    public String deleteBooking(@RequestParam("id") long id) {
        var result = this.bookingService.deleteBooking(id);
        if(result == DatabaseResult.Success) return "redirect:bookings?successMsg=" + URLEncoder.encode("message.bookingDeletedSuccessfully", StandardCharsets.UTF_8);
        else if(result == DatabaseResult.HasDependencies) return "redirect:bookings?errorMsg=" + URLEncoder.encode("message.hasDependencies", StandardCharsets.UTF_8);
        return "redirect:bookings?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
    }

    ////////////////////////////////
    /////AppUserRoles
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "role/{id}", method = RequestMethod.GET)
    public String role(@PathVariable("id") long id, Model model) {
        if(id == 0) {
            model.addAttribute("appUserRole", new AppUserRole());
        }
        else {
            model.addAttribute("appUserRole", this.appUserRoleService.getAppUserRole(id));
        }
        return "/dashboard/role";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "roles", method = RequestMethod.GET)
    public String roles(Model model) {
        model.addAttribute("appUserRoles", appUserRoleService.getAppUserRoles());
        return "/dashboard/roles";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "roles", method = RequestMethod.POST)
    public String addRole(@Valid @ModelAttribute("appUserRole") AppUserRole appUserRole, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "redirect:roles?errorMsg=" + URLEncoder.encode("message.invalidData", StandardCharsets.UTF_8);
        var result = this.appUserRoleService.addAppUserRole(appUserRole);
        if(result == DatabaseResult.Success) return "redirect:roles?successMsg=" + URLEncoder.encode("message.roleCreatedSuccessfully", StandardCharsets.UTF_8);
        else if(result == DatabaseResult.AlreadyExist) return "redirect:roles?errorMsg=" + URLEncoder.encode("message.roleAlreadyExist", StandardCharsets.UTF_8);
        return "redirect:roles?errorMsg=" + URLEncoder.encode("Inny błąd", StandardCharsets.UTF_8);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "deleteRole", method = RequestMethod.POST)
    public String deleteRole(@RequestParam("id") long id) {
        var result = this.appUserRoleService.deleteAppUserRole(id);
        if(result == DatabaseResult.Success) return "redirect:roles?successMsg=" + URLEncoder.encode("message.roleDeletedSuccessfully", StandardCharsets.UTF_8);
        else if(result == DatabaseResult.HasDependencies) return "redirect:roles?errorMsg=" + URLEncoder.encode("message.hasDependencies", StandardCharsets.UTF_8);
        return "redirect:roles?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
    }
}
