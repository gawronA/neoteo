package pl.local.neoteo.controller;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.data.util.Pair;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.local.neoteo.entity.Property;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.entity.UserRole;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.helper.PasswordValidator;
import pl.local.neoteo.helper.UserValidator;
import pl.local.neoteo.service.*;

import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import com.sendgrid.*;

@Controller
@RequestMapping("account")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;
    //private final PdfService pdfService;
    private final ReCaptchaService reCaptchaService;
    private final UserValidator userValidator = new UserValidator();
    private final PasswordValidator passwordValidator = new PasswordValidator();


    public AccountController(AccountService accountService,
                             UserService userService,
                             ReCaptchaService reCaptchaService,
                             MailService mailService) {
        this.userService = userService;
        this.accountService = accountService;
        this.reCaptchaService = reCaptchaService;
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("isError", false);
        //model.addAttribute("subscriptions", this.subscriptionService.getSubscriptions());
        return "/account/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user,
                           @RequestParam("repeatPassword") String repeatPassword,
                           Model model, BindingResult bindingResult, HttpServletRequest request) {
        userValidator.validate(user, bindingResult);
        passwordValidator.validate(Pair.of(user.getPassword(), repeatPassword), bindingResult);
        if(bindingResult.hasErrors() || !reCaptchaService.verify(request.getParameter("g-recaptcha-response"))) {
            user.setPassword("");
            model.addAttribute("user", user);
            model.addAttribute("isError", true);
            return "/account/register";
        }
        else {
            var result = this.accountService.createAccount(user);
            if(result != DatabaseResult.Success) {
                return "redirect:register?errorMsg=" + URLEncoder.encode("message.registerFailed", StandardCharsets.UTF_8);
            }
            else {
                return "redirect:/login?successMsg=" + URLEncoder.encode("message.registerSuccessful", StandardCharsets.UTF_8);
            }
        }
    }

    @RequestMapping(value="activate", method = RequestMethod.GET)
    public String activate(@RequestParam("token") String token) {
        var result = this.accountService.activateAccount(token);
        if(result != DatabaseResult.Success) return "redirect:/login?errorMsg=" + URLEncoder.encode("message.tokenInvalid", StandardCharsets.UTF_8);
        return "redirect:/login?successMsg=" + URLEncoder.encode("message.tokenValid", StandardCharsets.UTF_8);
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "details", method = RequestMethod.GET)
    public String details(Model model, Principal principal) {
        User user = this.userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "/account/details";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "details", method = RequestMethod.POST)
    public String details(@ModelAttribute("user") User user, BindingResult bindingResult, HttpServletRequest request, Principal principal) throws ServletException {
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()) return "redirect:details?errorMsg=" + URLEncoder.encode("message.invalidData", StandardCharsets.UTF_8);
        //user.setEmail(user.getEmail());

        var result = this.accountService.updateAccount(user);
        if(result != DatabaseResult.Success) return "redirect:details?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
        if(!user.getEmail().equals(principal.getName())) request.logout();
        return "redirect:details?successMsg=" + URLEncoder.encode("message.detailsChanged", StandardCharsets.UTF_8);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public String changePassword(@RequestParam("password") String password, @RequestParam("repeatPassword") String repeatPassword,
                                 Principal principal) {
        Errors bindingResult = new BindException(new User(), "password");
        passwordValidator.validate(Pair.of(password, repeatPassword), bindingResult);
        if(bindingResult.hasErrors()) return "redirect:details?errorMsg=" + URLEncoder.encode("validation.passwordShouldMatch", StandardCharsets.UTF_8);
        User user = userService.getUserByEmail(principal.getName());
        var result = this.accountService.changePassword(user, password);
        if(result != DatabaseResult.Success) return "redirect:details?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
        return "redirect:details?successMsg=" + URLEncoder.encode("message.passwordChanged", StandardCharsets.UTF_8);
    }
/*
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "changeSubscription", method = RequestMethod.GET)
    public String changeSubscriptionGet(@RequestParam("id") long id, Model model, Principal principal) {
        AppUser user = this.appUserService.getUser(principal.getName());
        if(user.getSubscription().getId() == id) return "redirect:/prices?infoMsg=" + URLEncoder.encode("message.subscriptionAlreadyOwned", StandardCharsets.UTF_8);
        model.addAttribute("subscription", this.subscriptionService.getSubscription(id));
        return "/account/confirmSubscriptionChange";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "changeSubscription", method = RequestMethod.POST)
    public String changeSubscriptionPost(@RequestParam("id") long id, Model model, Principal principal) {
        AppUser user = this.appUserService.getUser(principal.getName());
        if(user.getSubscription().getId() == id) return "redirect:/prices?infoMsg=" + URLEncoder.encode("message.subscriptionAlreadyOwned", StandardCharsets.UTF_8);
        var result = this.accountService.changeSubscription(user, this.subscriptionService.getSubscription(id));
        if(result != DatabaseResult.Success) return "redirect:/prices?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);;
        return "redirect:/?successMsg=" + URLEncoder.encode("message.subscriptionChangeSuccessful", StandardCharsets.UTF_8);

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "pay", method = RequestMethod.POST)
    public String pay(@RequestParam(value = "id", required = false) long[] ids) {
        if(ids == null) return "redirect:/payments?infoMsg=" + URLEncoder.encode("message.noPendingPayments", StandardCharsets.UTF_8);
        for(long id : ids) {
            Payment payment = this.paymentService.getPayment(id);
            payment.setPaid(true);
            if(this.paymentService.updatePayment(payment) != DatabaseResult.Success) return "redirect:/payments?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
        }
        return "redirect:/payments?successMsg=" + URLEncoder.encode("message.paymentSuccessful", StandardCharsets.UTF_8);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "book", method = RequestMethod.POST)
    public String book(@Valid @ModelAttribute("booking") Booking booking, BindingResult bindingResult,
                       Principal principal) {
        if(bindingResult.hasErrors()) return "redirect:/bookings?errorMsg=" + URLEncoder.encode("message.invalidData", StandardCharsets.UTF_8);

        AppUser user = this.appUserService.getUser(principal.getName());
        DatabaseResult result = DatabaseResult.Error;
        if(booking.getId() != 0) {
            result = this.bookingService.updateBooking(booking);
        }
        else {
            result = this.accountService.addBooking(user, booking);
        }

        if(result != DatabaseResult.Success) return "redirect:/bookings?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
        return "redirect:/bookings?successMsg=" + URLEncoder.encode("message.bookingSuccessful", StandardCharsets.UTF_8);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @RequestMapping(value = "confirmBooking", method = RequestMethod.POST)
    public String confirmBooking(@RequestParam("id") long id) {
        Booking booking = this.bookingService.getBooking(id);
        if(booking.getAppUser() == null) return "redirect:/bookings?errorMsg=" + URLEncoder.encode("message.noUserAssigned", StandardCharsets.UTF_8);
        if(booking.getToTime().before(booking.getFromTime())) return "redirect:/bookings?errorMsg=" + URLEncoder.encode("validation.bookingDatesInvalid", StandardCharsets.UTF_8);
        if(booking.isAccepted()) return "redirect:/bookings?errorMsg=" + URLEncoder.encode("validation.bookingAlreadyConfirmed", StandardCharsets.UTF_8);
        if(this.accountService.confirmBooking(booking) != DatabaseResult.Success) return "redirect:/bookings?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
        return "redirect:/bookings?successMsg=" + URLEncoder.encode("message.bookingConfirmationSuccessful", StandardCharsets.UTF_8);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "printPayments", method = RequestMethod.GET)
    public void printPayments(HttpServletResponse response, Principal principal) {
        pdfService.generatePaymentsPdf(appUserService.getUser(principal.getName()), response);
    }*/
}
