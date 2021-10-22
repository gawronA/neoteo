package pl.local.neoteo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.local.neoteo.entity.Property;
import pl.local.neoteo.entity.Record;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.entity.UtilityType;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.service.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private final UserService userService;
    private final PropertyService propertyService;
    private final UtilityTypeService utilityTypeService;
    private final RecordService recordService;
    private final PdfService pdfService;

    public PropertyController(UserService userService, PropertyService propertyService, UtilityTypeService utilityTypeService, RecordService recordService, PdfService pdfService) {
        this.userService = userService;
        this.propertyService = propertyService;
        this.utilityTypeService = utilityTypeService;
        this.recordService = recordService;
        this.pdfService = pdfService;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("properties")
    public String properties(Model model, SecurityContextHolderAwareRequestWrapper wrapper, Principal principal) {
        if(wrapper.isUserInRole("ROLE_TENANT"))
        {
            User user = userService.getUserByEmail(principal.getName());
            Property property = user.getProperty();
            if(property != null)
                property.setRecords(property.getRecords().stream().sorted(Comparator.comparing(Record::getDate).reversed()).collect(Collectors.toCollection(LinkedHashSet::new)));
            model.addAttribute("property", property);
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
            //model.addAttribute("records", null);
        }
        else {
            Property property = propertyService.getProperty(id);
            property.setRecords(property.getRecords().stream().sorted(Comparator.comparing(Record::getDate).reversed()).collect(Collectors.toCollection(LinkedHashSet::new)));
            model.addAttribute("property", property);
        }
        model.addAttribute("utilityTypes", utilityTypeService.getUtilityTypes());
        return "/properties/property";
    }

    @PreAuthorize("isAuthenticated()")
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
            Property dbProperty = propertyService.getProperty(property.getId());
            property.setUser(dbProperty.getUser());
            property.setRecords(dbProperty.getRecords());
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
        if(result != DatabaseResult.Success) return "redirect:utilities?errorMsg=" + URLEncoder.encode("message.deleteUtilityFailed", StandardCharsets.UTF_8);
        else return "redirect:utilities?successMsg=" + URLEncoder.encode("message.deleteUtilitySuccess", StandardCharsets.UTF_8);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("report")
    public String getReport(Principal principal, HttpServletResponse response) {
        User user = userService.getUserByEmail(principal.getName());
        try {
            OutputStream o = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=neoteo_raport_" + user.getFirstName() + "_" + user.getLastName());
            pdfService.generateReport(user, o);
            return "redirect:/properties";
        } catch (Exception ex) {
            return "redirect:/properties";
        }

    }
}
