package pl.local.neoteo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.local.neoteo.entity.User;
import pl.local.neoteo.entity.UserRole;
import pl.local.neoteo.helper.DatabaseResult;
import pl.local.neoteo.repository.UserRepository;
import pl.local.neoteo.repository.UserRoleRepository;

import java.util.List;

@Service
class UserExtServiceImpl {

    private final UserRepository userRepository;

    @Autowired
    public UserExtServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    void save(User User) {
        this.userRepository.save(User);
    }

    @Transactional(rollbackFor = Exception.class)
    void delete(long id) {
        this.userRepository.deleteById(id);
    }
}

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserExtServiceImpl userExtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, UserExtServiceImpl userExtService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userExtService = userExtService;
    }

    @Override
    public DatabaseResult addUser(User user) {
        user.setId(0);
        try {
            userExtService.save(user);
        }
        catch (Exception ex) {
            return DatabaseResult.AlreadyExist;
        }
        return DatabaseResult.Success;
    }

    @Override
    public DatabaseResult updateUser(User user) {
        try {
            userExtService.save(user);
        }
        catch (Exception ex) {
            return DatabaseResult.AlreadyExist;
        }
        return DatabaseResult.Success;
    }

    @Override
    public DatabaseResult deleteUser(long id) {
        try {
            this.userExtService.delete(id);
        }
        catch (Exception ex) {
            return DatabaseResult.HasDependencies;
        }
        return DatabaseResult.Success;
    }

    @Override
    @Transactional
    public User getUser(long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User getUserByActivationToken(String token) {
        return userRepository.findByActivationToken(token);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /*private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }*/
}
