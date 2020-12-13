package ru.inno.projects.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.inno.projects.models.Role;
import ru.inno.projects.models.User;
import ru.inno.projects.repos.UserRepo;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final EventMailServise mailServise;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, EventMailServise mailServise) {
        this.userRepo = userRepo;
        this.mailServise = mailServise;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public boolean addUser(User user) {

        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (!Objects.equals(userFromDb, null)) {
            return false;
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);

        final String message = String.format(
                "Привет, %s! \n" +
                        "Добро пожаловать в Event Manager. Для подтверждения регистрации перейдите по ссылке: " +
                        "http://localhost:8080/activate/%s",
                user.getUsername(),
                user.getActivationCode()
        );

        if (!user.getEmail().isEmpty()) {
            mailServise.send(user.getEmail(), "Activation code", message);
        }

        return true;
    }

    @Override
    public User getUserByLogin(String login) {
        return null;
    }

    @Override
    public User getUserByName(String name) {
        return null;
    }

    @Override
    public User getUserById(BigDecimal userId) {
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUserById(BigDecimal userId) {
        return false;
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (Objects.equals(user, null)) {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(userName);
        return user != null ? user : new User();
    }
}
