package pl.local.mpark.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.local.mpark.entity.AppUser;
import pl.local.mpark.entity.AppUserRole;
import pl.local.mpark.helper.DatabaseResult;

import java.util.List;

public interface AppUserService {
    public DatabaseResult addUser(AppUser user);
    public DatabaseResult editUser(AppUser user);
    public DatabaseResult deleteUser(long id);
    public AppUser getUser(long id);
    public AppUser getUser(String username);
    public List<AppUser> getUsers();
}
