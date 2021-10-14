package pl.local.neoteo.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.local.neoteo.entity.UserRole;
import pl.local.neoteo.service.UserRoleService;

import java.util.HashSet;
import java.util.Set;


public class UserRolesConverter implements Converter<String, Set<UserRole>> {

    private final UserRoleService userRoleService;

    @Autowired
    public UserRolesConverter(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
    @Override
    public Set<UserRole> convert(String roleId) {
        UserRole role = userRoleService.getUserRole(Long.parseLong(roleId));
        if(role == null) return null;
        Set<UserRole> roles = new HashSet<UserRole>();
        roles.add(role);
        return roles;
    }
}
