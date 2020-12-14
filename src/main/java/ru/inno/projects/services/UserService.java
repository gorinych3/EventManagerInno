package ru.inno.projects.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.inno.projects.models.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    boolean addUser(User user);

    User getUserByLogin(String login);

    User getUserByName(String name);

    User getUserById(BigDecimal userId);

    boolean updateUser(User user);

    boolean deleteUser(User user);

    boolean deleteUserById(BigDecimal userId);

    boolean activateUser(String code);
}
