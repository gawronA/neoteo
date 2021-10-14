package pl.local.neoteo.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.entity.UserRole;
import pl.local.neoteo.helper.DatabaseResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    private final UserService userService;
    private final UserRoleService userRoleService;

    public AccountServiceImpl(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public DatabaseResult createAccount(User user) {
        UserRole userRole = this.userRoleService.getUserRole("ROLE_TENANT");
        if(userRole == null) return DatabaseResult.Error;
        user.setUserRoles(new HashSet<>());
        user.getUserRoles().add(userRole);
        user.setPassword(hashPassword(user.getPassword()));

        return this.userService.addUser(user);
    }

    @Override
    public DatabaseResult updateAccount(User user) {
        User dbUser = this.userService.getUser(user.getId());
        if(dbUser == null) return DatabaseResult.Error;

        dbUser.setActive(user.isActive());
        if(user.getPassword() != null && !user.getPassword().isEmpty()) dbUser.setPassword(user.getPassword());
        if(user.getFirstName() != null && !user.getFirstName().isEmpty()) dbUser.setFirstName(user.getFirstName());
        if(user.getLastName() != null && !user.getLastName().isEmpty()) dbUser.setLastName(user.getLastName());
        if(user.getEmail() != null && !user.getEmail().isEmpty()) dbUser.setEmail(user.getEmail());
        if(user.getPhone() != null && !user.getPhone().isEmpty()) dbUser.setPhone(user.getPhone());
        if(user.getUserRoles() != null && !user.getUserRoles().isEmpty()) dbUser.setUserRoles(user.getUserRoles());

        return this.userService.editUser(dbUser);
    }

    @Override
    public DatabaseResult changePassword(User user, String password) {
        password = hashPassword(password);
        user.setPassword(password);
        return updateAccount(user);
    }

    private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

}
