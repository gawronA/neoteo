package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.helper.DatabaseResult;

import java.util.Date;

@Service
public interface AccountService {
    DatabaseResult createAccount(User user);
    DatabaseResult updateAccount(User user);
    DatabaseResult updateAccount(User user, String password);
    DatabaseResult changePassword(User user, String password);
    DatabaseResult activateAccount(String token);
}
