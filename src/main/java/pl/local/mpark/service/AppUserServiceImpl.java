package pl.local.mpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.local.mpark.entity.AppUser;
import pl.local.mpark.entity.AppUserRole;
import pl.local.mpark.helper.DatabaseResult;
import pl.local.mpark.repository.AppUserRepository;
import pl.local.mpark.repository.AppUserRoleRepository;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

@Service
class AppUserExtServiceImpl {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserExtServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    void save(AppUser appUser) {
        this.appUserRepository.save(appUser);
    }

    @Transactional(rollbackFor = Exception.class)
    void delete(long id) {
        this.appUserRepository.deleteById(id);
    }
}

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserRoleRepository appUserRoleRepository;
    private final AppUserExtServiceImpl appUserExtService;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, AppUserRoleRepository appUserRoleRepository, AppUserExtServiceImpl appUserExtService) {
        this.appUserRepository = appUserRepository;
        this.appUserRoleRepository = appUserRoleRepository;
        this.appUserExtService = appUserExtService;
    }

    @Override
    public DatabaseResult addUser(AppUser user) {
//        user.setPassword(hashPassword(user.getPassword()));
        try {
            appUserExtService.save(user);
        }
        catch (Exception ex) {
            return DatabaseResult.AlreadyExist;
        }
        return DatabaseResult.Success;
    }

    @Override
    public DatabaseResult editUser(AppUser user) {
        return addUser(user);
//        try {
//            appUserExtService.save(user);
//        }
//        catch (Exception ex) {
//            return DatabaseResult.AlreadyExist;
//        }
//        return DatabaseResult.Success;
    }

    @Override
    public DatabaseResult deleteUser(long id) {
        try {
            this.appUserExtService.delete(id);
        }
        catch (Exception ex) {
            return DatabaseResult.HasDependencies;
        }
        return DatabaseResult.Success;
    }

    @Override
    @Transactional
    public AppUser getUser(long id) {
        return appUserRepository.findById(id);
    }

    @Override
    @Transactional
    public AppUser getUser(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }

    private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
