package pl.local.mpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.local.mpark.entity.AppUser;
import pl.local.mpark.entity.AppUserRole;
import pl.local.mpark.helper.DatabaseResult;
import pl.local.mpark.repository.AppUserRoleRepository;

import java.util.List;

@Service
class AppUserRoleExtServiceImpl {

    private final AppUserRoleRepository appUserRoleRepository;

    public AppUserRoleExtServiceImpl(AppUserRoleRepository appUserRoleRepository) {
        this.appUserRoleRepository = appUserRoleRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    void save(AppUserRole appUserRole) {
        appUserRoleRepository.save(appUserRole);
    }

    @Transactional(rollbackFor = Exception.class)
    void delete(long id) {
        this.appUserRoleRepository.deleteById(id);
    }
}

@Service("appUserRoleService")
public class AppUserRoleServiceImpl implements AppUserRoleService {

    private final AppUserRoleExtServiceImpl appUserRoleExtService;
    private final AppUserRoleRepository appUserRoleRepository;

    @Autowired
    public AppUserRoleServiceImpl(AppUserRoleExtServiceImpl appUserRoleExtService, AppUserRoleRepository appUserRoleRepository) {
        this.appUserRoleExtService = appUserRoleExtService;
        this.appUserRoleRepository = appUserRoleRepository;
    }

    public DatabaseResult addAppUserRole(AppUserRole appUserRole) {
        try {
            this.appUserRoleExtService.save(appUserRole);
        }
        catch (Exception ex) {
            return DatabaseResult.AlreadyExist;
        }
        return DatabaseResult.Success;
    }

    public DatabaseResult deleteAppUserRole(long id) {
        try {
            this.appUserRoleExtService.delete(id);
        }
        catch (Exception ex) {
            return DatabaseResult.HasDependencies;
        }
        return DatabaseResult.Success;
    }

    @Transactional
    public List<AppUserRole> getAppUserRoles() {
        return appUserRoleRepository.findAll();
    }

    @Transactional
    public AppUserRole getAppUserRole(long id) {
        var appUserRole = appUserRoleRepository.findById(id);
        return appUserRole.orElse(null);
    }

    @Transactional
    public AppUserRole getAppUserRole(String role) {
        var appUserRole = appUserRoleRepository.findByRole(role);
        return appUserRole;
    }
}
