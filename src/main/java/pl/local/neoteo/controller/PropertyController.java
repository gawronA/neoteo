package pl.local.neoteo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.local.neoteo.entity.Property;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.service.PropertyService;
import pl.local.neoteo.service.UserService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Controller
@RequestMapping("/properties")
@PreAuthorize("isAuthenticated()")
public class PropertyController {

    private final UserService userService;
    private final PropertyService propertyService;

    public PropertyController(UserService userService, PropertyService propertyService) {
        this.userService = userService;
        this.propertyService = propertyService;
    }

    @RequestMapping("properties")
    public String properties(Model model, SecurityContextHolderAwareRequestWrapper wrapper, Principal principal) {
        if(wrapper.isUserInRole("ROLE_TENANT"))
        {
            User user = userService.getUserByEmail(principal.getName());
            model.addAttribute("property", user.getProperty());
            return "/properties/index_tenant";
        }
        else
        {
            model.addAttribute("properties", propertyService.getProperties());
            return "/properties/index_manager";
        }
    }

    @RequestMapping(value = "properties/{id}", method = RequestMethod.GET)
    public String getProperty(@PathVariable("id")long id, Model model) {
        if(id == 0) {
            model.addAttribute("property", new Property());
        }
        else {
            model.addAttribute("property", propertyService.getProperty(id));
        }
        return "/properties/property";
    }

    /*@RequestMapping(value = "properties/{id}", method = RequestMethod.POST)
    public String editProperty(@PathVariable("id")long id, Model model) {
        if(id == 0) {
            model.addAttribute("property", new Property());
        }
        else {
            model.addAttribute("property", propertyService.getProperty(id));
        }
        return "/properties/property";
    }*/

    @RequestMapping(value = "properties", method = RequestMethod.POST)
    public String addProperty(@ModelAttribute("property") Property property, Principal principal) {
        if(property.getId() == 0)
        {
            var result = propertyService.createProperty(property, principal.getName());
            if(result != DatabaseResult.Success) return "redirect:properties?errorMsg=" + URLEncoder.encode("message.createPropertyFailed", StandardCharsets.UTF_8);
            else return "redirect:properties?successMsg=" + URLEncoder.encode("message.createPropertySuccess", StandardCharsets.UTF_8);
        }
        else
        {
            User user = propertyService.getProperty(property.getId()).getUser();
            property.setUser(user);
            var result = propertyService.updateProperty(property);
            if(result != DatabaseResult.Success) return "redirect:properties?errorMsg=" + URLEncoder.encode("message.updatePropertyFailed", StandardCharsets.UTF_8);
            else return "redirect:properties?successMsg=" + URLEncoder.encode("message.updatePropertySuccess", StandardCharsets.UTF_8);
        }
    }
}
