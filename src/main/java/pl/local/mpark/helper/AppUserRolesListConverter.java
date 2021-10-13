package pl.local.mpark.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.local.mpark.entity.AppUserRole;
import pl.local.mpark.service.AppUserRoleService;

import java.util.HashSet;
import java.util.Set;


public class AppUserRolesListConverter implements Converter<String[], Set<AppUserRole>> {

    private final AppUserRoleService appUserRoleService;

    @Autowired
    public AppUserRolesListConverter(AppUserRoleService appUserRoleService) {
        this.appUserRoleService = appUserRoleService;
    }
    @Override
    public Set<AppUserRole> convert(String[] roleIds) {
        Set<AppUserRole> roles = new HashSet<AppUserRole>();
        for(String roleId : roleIds) {
            roles.add(appUserRoleService.getAppUserRole(Long.parseLong(roleId)));
        }
        return roles;
    }
}
