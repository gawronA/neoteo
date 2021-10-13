package pl.local.mpark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.local.mpark.entity.AppUser;
import pl.local.mpark.service.*;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/", "/home"})
public class HomeController {

    private final SubscriptionService subscriptionService;
    private final AppUserService appUserService;
    private final AccountService accountService;
    private final PaymentService paymentService;
    private final BookingService bookingService;

    public HomeController(SubscriptionService subscriptionService, AppUserService appUserService, AccountService accountService, PaymentService paymentService, BookingService bookingService) {
        this.subscriptionService = subscriptionService;
        this.appUserService = appUserService;
        this.accountService = accountService;
        this.paymentService = paymentService;
        this.bookingService = bookingService;
    }

    @RequestMapping("")
    public String home(Model model, SecurityContextHolderAwareRequestWrapper wrapper, Principal principal) {
        if(wrapper.isUserInRole("ROLE_ADMIN")) {

            model.addAttribute("name", appUserService.getUser(principal.getName()).getFirstName());
            model.addAttribute("usersCount", appUserService.getUsers().size());
            model.addAttribute("inactiveUsersCount", (int)appUserService.getUsers().stream().filter(p -> !p.isActive()).count());
            model.addAttribute("totalBalance", paymentService.getTotalBalance());
            model.addAttribute("totalPaidBalance", paymentService.getTotalPaidBalance());
            model.addAttribute("bookingsCount", bookingService.getBookings().size());
            model.addAttribute("pendingBookingsCount", bookingService.getBookings().stream().filter(p->!p.isAccepted()).count());
            return "/dashboard/index_admin";
        }
        else if(wrapper.isUserInRole("ROLE_EMPLOYEE")) {
            model.addAttribute("name", appUserService.getUser(principal.getName()).getFirstName());
            model.addAttribute("bookingsCount", bookingService.getBookings().size());
            model.addAttribute("pendingBookingsCount", bookingService.getBookings().stream().filter(p->!p.isAccepted()).count());
            return "/dashboard/index_employee";
        }
        else if(wrapper.isUserInRole("ROLE_USER")) {
            AppUser user = appUserService.getUser(principal.getName());
            model.addAttribute("user", user);
            model.addAttribute("balance", accountService.getBalance(user));
            model.addAttribute("pendingPaymentsCount", accountService.getPendingPaymentsCount(user));
            return "/dashboard/index_user";
        }
        else return "/home/index";

    }

    @RequestMapping("prices")
    public String prices(Model model) {
        model.addAttribute("subscriptions", this.subscriptionService.getSubscriptions());
        return "/home/prices/index";
    }
}
