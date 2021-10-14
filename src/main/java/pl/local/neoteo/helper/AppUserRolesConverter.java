package pl.local.neoteo.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.local.neoteo.entity.AppUserRole;
import pl.local.neoteo.service.UserRoleService;

import java.util.HashSet;
import java.util.Set;


public class AppUserRolesConverter implements Converter<String, Set<AppUserRole>> {

    private final UserRoleService appUserRoleService;

    @Autowired
    public AppUserRolesConverter(UserRoleService appUserRoleService) {
        this.appUserRoleService = appUserRoleService;
    }
    @Override
    public Set<AppUserRole> convert(String roleId) {
        AppUserRole role = appUserRoleService.getAppUserRole(Long.parseLong(roleId));
        if(role == null) return null;
        Set<AppUserRole> roles = new HashSet<AppUserRole>();
        roles.add(role);
        return roles;
    }
}
