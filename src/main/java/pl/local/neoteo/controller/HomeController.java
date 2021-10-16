package pl.local.neoteo.controller;

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.local.neoteo.service.*;

import java.security.Principal;

@Controller
@RequestMapping({"/", "/home"})
public class HomeController {

    @RequestMapping("")
    public String home() {
        return "/home/index";
    }
}
