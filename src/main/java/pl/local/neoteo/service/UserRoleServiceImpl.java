package pl.local.neoteo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.entity.UserRole;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.repository.UserRoleRepository;
import pl.local.neoteo.repository.UserRoleRepository;

import java.util.List;

@Service
class UserRoleExtServiceImpl {

    private final UserRoleRepository userRoleRepository;

    public UserRoleExtServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    void save(UserRole UserRole) {
        userRoleRepository.save(UserRole);
    }

    @Transactional(rollbackFor = Exception.class)
    void delete(long id) {
        this.userRoleRepository.deleteById(id);
    }
}

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleExtServiceImpl userRoleExtService;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleExtServiceImpl userRoleExtService, UserRoleRepository userRoleRepository) {
        this.userRoleExtService = userRoleExtService;
        this.userRoleRepository = userRoleRepository;
    }

    public DatabaseResult addUserRole(UserRole UserRole) {
        try {
            this.userRoleExtService.save(UserRole);
        }
        catch (Exception ex) {
            return DatabaseResult.AlreadyExist;
        }
        return DatabaseResult.Success;
    }

    public DatabaseResult deleteUserRole(long id) {
        try {
            this.userRoleExtService.delete(id);
        }
        catch (Exception ex) {
            return DatabaseResult.HasDependencies;
        }
        return DatabaseResult.Success;
    }

    @Transactional
    public List<UserRole> getUserRoles() {
        return userRoleRepository.findAll();
    }

    @Transactional
    public UserRole getUserRole(long id) {
        var userRole = userRoleRepository.findById(id);
        return userRole.orElse(null);
    }

    @Transactional
    public UserRole getUserRole(String role) {
        var userRole = userRoleRepository.findByRole(role);
        return userRole;
    }
}
