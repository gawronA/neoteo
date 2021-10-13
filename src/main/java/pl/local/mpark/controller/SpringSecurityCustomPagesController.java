package pl.local.mpark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.local.mpark.entity.AppUser;
import pl.local.mpark.helper.AppUserValidator;
import pl.local.mpark.helper.DatabaseResult;
import pl.local.mpark.helper.PasswordValidator;
import pl.local.mpark.service.AccountService;
import pl.local.mpark.service.SubscriptionService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
