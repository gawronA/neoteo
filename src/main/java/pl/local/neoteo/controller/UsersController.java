package pl.local.neoteo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.service.AccountService;
import pl.local.neoteo.service.UserRoleService;
import pl.local.neoteo.service.UserService;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UsersController {

    @Autowired()
    private final UserService userService;
    private final UserRoleService userRoleService;

    public UsersController(UserService userService, UserRoleService userRoleService) {
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
            model.addAttribute("userRoleList", userRoleService.getUserRoles());
        }
        else {
            model.addAttribute("user", userService.getUser(id));
            model.addAttribute("userRoleList", userRoleService.getUserRoles());
        }
        return "/properties/user";
    }
}
