package pl.local.neoteo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.local.neoteo.entity.AppUser;
import pl.local.neoteo.helper.AppUserValidator;
import pl.local.neoteo.helper.PasswordValidator;
import pl.local.neoteo.service.AccountService;
import pl.local.neoteo.service.SubscriptionService;

@Controller
public class SpringSecurityCustomPagesController {

    private final AppUserValidator appUserValidator = new AppUserValidator();
    private final PasswordValidator passwordValidator = new PasswordValidator();
    private final SubscriptionService subscriptionService;
    private final AccountService accountService;

    public SpringSecurityCustomPagesController(SubscriptionService subscriptionService, AccountService accountService) {
        this.subscriptionService = subscriptionService;
        this.accountService = accountService;
    }

    @RequestMapping("login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if(error != null) model.addAttribute("error", "message.invalidUsernameOrPassword");
        if(logout != null) model.addAttribute("msg", "message.loginSuccessful");

        return "/security/login";
    }

    @RequestMapping("/forbidden")
    public String forbidden() {
        return "/security/forbidden";
    }
}
