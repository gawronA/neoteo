package pl.local.neoteo.service;

import pl.local.neoteo.entity.User;
import pl.local.neoteo.entity.UserRole;
import pl.local.neoteo.helper.DatabaseResult;

import java.util.List;

public interface UserService {
    public DatabaseResult addUser(User user);
    public DatabaseResult updateUser(User user);
    public DatabaseResult deleteUser(long id);
    public User getUser(long id);
    public User getUser(String email);
    public List<User> getUsers();
}
