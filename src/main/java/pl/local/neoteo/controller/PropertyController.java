package pl.local.neoteo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.local.neoteo.entity.Property;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/property")
@PreAuthorize("isAuthenticated()")
public class PropertyController {

    private final UserService userService;

    public PropertyController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping("")
    public String property(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("property", user.getProperty());
        return "/property/index_user";
    }
}
