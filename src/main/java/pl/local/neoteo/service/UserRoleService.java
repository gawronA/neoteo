package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.UserRole;
import pl.local.neoteo.helper.DatabaseResult;

import java.util.List;

@Service
public interface UserRoleService {
    public DatabaseResult addUserRole(UserRole appUserRole);
    public DatabaseResult deleteUserRole(long id);
    public List<UserRole> getUserRoles();
    public UserRole getUserRole(long id);
    public UserRole getUserRole(String role);
}
