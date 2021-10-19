package pl.local.neoteo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.local.neoteo.entity.Property;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.entity.UtilityType;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.service.PropertyService;
import pl.local.neoteo.service.UserService;
import pl.local.neoteo.service.UtilityTypeService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private final UserService userService;
    private final PropertyService propertyService;
    private final UtilityTypeService utilityTypeService;

    public PropertyController(UserService userService, PropertyService propertyService, UtilityTypeService utilityTypeService) {
        this.userService = userService;
        this.propertyService = propertyService;
        this.utilityTypeService = utilityTypeService;
    }

    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "properties/{id}", method = RequestMethod.GET)
    public String getProperty(@PathVariable("id")long id, Model model) {
        if(id == 0) {
            model.addAttribute("property", new Property());
        }
        else {
            model.addAttribute("property", propertyService.getProperty(id));
        }
        model.addAttribute("utilityTypes", utilityTypeService.getUtilityTypes());
        return "/properties/property";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "properties", method = RequestMethod.POST)
    public String addProperty(@ModelAttribute("property") Property property, @RequestParam("utilityTypes") Set<UtilityType> utilityTypes, Principal principal) {
        property.setUtilityTypes(utilityTypes);
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



    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @RequestMapping("utilities")
    public String utilities(Model model)
    {
        model.addAttribute("utilityTypes", utilityTypeService.getUtilityTypes());
        return "/properties/utilities";
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @RequestMapping(value = "utilities/{id}", method = RequestMethod.GET)
    public String getUtility(@PathVariable("id")long id, Model model) {
        if(id == 0) {
            model.addAttribute("utilityType", new UtilityType());
        }
        else {
            model.addAttribute("utilityType", utilityTypeService.getUtilityType(id));
        }
        return "/properties/utility";
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @RequestMapping(value = "utilities", method = RequestMethod.POST)
    public String addUtility(@ModelAttribute("utilityType") UtilityType utilityType) {
        if(utilityType.getId() == 0)
        {
            var result = utilityTypeService.addUtilityType(utilityType);
            if(result != DatabaseResult.Success) return "redirect:utilities?errorMsg=" + URLEncoder.encode("message.createUtilityFailed", StandardCharsets.UTF_8);
            else return "redirect:utilities?successMsg=" + URLEncoder.encode("message.createUtilitySuccess", StandardCharsets.UTF_8);
        }
        else
        {
            var result = utilityTypeService.updateUtilityType(utilityType);
            if(result != DatabaseResult.Success) return "redirect:utilities?errorMsg=" + URLEncoder.encode("message.updateUtilityFailed", StandardCharsets.UTF_8);
            else return "redirect:utilities?successMsg=" + URLEncoder.encode("message.updateUtilitySuccess", StandardCharsets.UTF_8);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @RequestMapping(value = "deleteUtilities", method = RequestMethod.POST)
    public String deleteUtility(@RequestParam("id") long id) {
        var result = utilityTypeService.deleteUtilityType(id);
        if(result != DatabaseResult.Success) return "redirect:utilities?errorMsg=" + URLEncoder.encode("message.deleteUtilitySuccess", StandardCharsets.UTF_8);
        else return "redirect:utilities?successMsg=" + URLEncoder.encode("message.deleteUtilitySuccess", StandardCharsets.UTF_8);
    }
}
