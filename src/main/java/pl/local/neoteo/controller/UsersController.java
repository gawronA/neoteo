package pl.local.neoteo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.helper.UserValidator;
import pl.local.neoteo.service.AccountService;
import pl.local.neoteo.service.UserRoleService;
import pl.local.neoteo.service.UserService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UsersController {

    private final AccountService accountService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserValidator userValidator = new UserValidator();

    public UsersController(AccountService accountService, UserService userService, UserRoleService userRoleService) {
        this.accountService = accountService;
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @RequestMapping("")
    public String users(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "/users";
    }

    @RequestMapping("{id}")
    public String getUser(@PathVariable("id") long id, Model model) {
        if(id == 0) {
            model.addAttribute("user", new User());
        }
        else {
            model.addAttribute("user", userService.getUser(id));
        }
        model.addAttribute("userRoleList", userRoleService.getUserRoles());
        model.addAttribute("userRoleList", userRoleService.getUserRoles());
        return "/properties/user";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()) return "redirect:users?errorMsg=" + URLEncoder.encode("message.invalidData", StandardCharsets.UTF_8);

        DatabaseResult result = DatabaseResult.Error;
        if(user.getId() == 0) {
            result = this.accountService.createAccount(user);
        }
        else {
            result = this.accountService.updateAccount(user);
        }
        if(result == DatabaseResult.Success) return "redirect:users?successMsg=" + URLEncoder.encode("message.createUserSuccessful", StandardCharsets.UTF_8);
        else if(result == DatabaseResult.AlreadyExist) return "redirect:users?errorMsg=" + URLEncoder.encode("message.userAlreadyExist", StandardCharsets.UTF_8);
        return "redirect:users?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("id") long id) {
        var result = this.userService.deleteUser(id);
        if(result == DatabaseResult.Success) return "redirect:/users?successMsg=" + URLEncoder.encode("message.userDeleteSuccessful", StandardCharsets.UTF_8);
        else if(result == DatabaseResult.HasDependencies) return "redirect:/users?errorMsg=" + URLEncoder.encode("message.hasDependencies", StandardCharsets.UTF_8);
        return "redirect:/users?errorMsg=" + URLEncoder.encode("message.error", StandardCharsets.UTF_8);

    }
}
