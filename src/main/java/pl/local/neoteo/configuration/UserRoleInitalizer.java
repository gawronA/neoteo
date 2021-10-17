package pl.local.neoteo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.local.neoteo.entity.UserRole;
import pl.local.neoteo.service.UserRoleService;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Configuration
public class UserRoleInitalizer {

    @Resource
    UserRoleService userRoleService;

    @Bean
    void initializeUserRoles() {
        UserRole roleTenant = new UserRole();
        roleTenant.setRole("ROLE_TENANT");
        UserRole roleManager = new UserRole();
        roleManager.setRole("ROLE_MANAGER");
        UserRole roleAdmin = new UserRole();
        roleAdmin.setRole("ROLE_ADMIN");

        var roles = userRoleService.getUserRoles();
        if(roles.isEmpty()){
            userRoleService.addUserRole(roleTenant);
            userRoleService.addUserRole(roleManager);
            userRoleService.addUserRole(roleAdmin);
        }
        else{
            var roleList = roles.stream().filter(r -> r.getRole().equals(roleTenant.getRole())).collect(Collectors.toList());
            if(roleList.isEmpty()) userRoleService.addUserRole(roleTenant);
            roleList = roles.stream().filter(r->r.getRole().equals((roleManager.getRole()))).collect(Collectors.toList());
            if(roleList.isEmpty()) userRoleService.addUserRole(roleManager);
            roleList = roles.stream().filter(r->r.getRole().equals((roleAdmin.getRole()))).collect(Collectors.toList());
            if(roleList.isEmpty()) userRoleService.addUserRole(roleAdmin);
        }
    }
}
