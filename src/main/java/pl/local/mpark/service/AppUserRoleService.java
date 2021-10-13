package pl.local.mpark.service;

import org.springframework.stereotype.Service;
import pl.local.mpark.entity.AppUserRole;
import pl.local.mpark.helper.DatabaseResult;

import java.util.List;

@Service("appUserRoleService")
public interface AppUserRoleService {
    public DatabaseResult addAppUserRole(AppUserRole appUserRole);
    public DatabaseResult deleteAppUserRole(long id);
    public List<AppUserRole> getAppUserRoles();
    public AppUserRole getAppUserRole(long id);
    public AppUserRole getAppUserRole(String role);
}
