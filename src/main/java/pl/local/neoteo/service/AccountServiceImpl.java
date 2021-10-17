package pl.local.neoteo.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.Property;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.entity.UserRole;
import pl.local.neoteo.helper.DatabaseResult;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final MailService mailService;

    public AccountServiceImpl(UserService userService,
                              UserRoleService userRoleService,
                              MailService mailService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.mailService = mailService;
    }

    @Override
    public DatabaseResult createAccount(User user) {
        UserRole userRole = this.userRoleService.getUserRole("ROLE_TENANT");
        if(userRole == null) return DatabaseResult.Error;
        user.setUserRoles(new HashSet<>());
        user.getUserRoles().add(userRole);
        user.setPassword(hashPassword(user.getPassword()));
        user.setActivationToken(generateActivationToken(64));

        var result = this.userService.addUser(user);
        if(result != DatabaseResult.Success) return result;

        var res = mailService.send(
                "244009@edu.p.lodz.pl",
                user.getEmail(),
                "NeoTeo accound activation",
                "<h3>Hello " +
                        user.getFirstName() +
                        " " +
                        user.getLastName() +
                        "</h3>" +
                        "<p>Click <a href=\"http://localhost:8081/account/activate?token=" +
                        user.getActivationToken() +
                        "\">here</a> to activate your account</p>");
        if(res) return DatabaseResult.Success;
        else return DatabaseResult.Error;
    }

    @Override
    public DatabaseResult updateAccount(User user) {
        User dbUser = this.userService.getUser(user.getId());
        if(dbUser == null) return DatabaseResult.Error;

        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setEmail(user.getEmail());
        dbUser.setPhone(user.getPhone());
        dbUser.setPesel(user.getPesel());
        dbUser.setActive(user.isActive());

        return this.userService.updateUser(dbUser);
    }

    @Override
    public DatabaseResult changePassword(User user, String password) {
        password = hashPassword(password);
        user.setPassword(password);
        return updateAccount(user);
    }

    public DatabaseResult activateAccount(String token) {
        User dbUser = this.userService.getUserByActivationToken(token);
        if(dbUser == null) return DatabaseResult.Error;

        if(dbUser.isActive()) return DatabaseResult.Error;
        dbUser.setActive(true);
        return updateAccount(dbUser);
    }

    private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private String generateActivationToken(int len) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
