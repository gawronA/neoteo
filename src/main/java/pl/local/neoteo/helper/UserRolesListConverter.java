package pl.local.neoteo.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.local.neoteo.entity.UserRole;
import pl.local.neoteo.service.UserRoleService;

import java.util.HashSet;
import java.util.Set;


public class UserRolesListConverter implements Converter<String[], Set<UserRole>> {

    private final UserRoleService userRoleService;

    @Autowired
    public UserRolesListConverter(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
    @Override
    public Set<UserRole> convert(String[] roleIds) {
        Set<UserRole> roles = new HashSet<UserRole>();
        for(String roleId : roleIds) {
            roles.add(userRoleService.getUserRole(Long.parseLong(roleId)));
        }
        return roles;
    }
}
