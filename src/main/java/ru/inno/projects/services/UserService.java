package ru.inno.projects.services;


import ru.inno.projects.models.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User addUser(User user);

    User getUserByLogin(String login);

    User getUserByName(String name);

    User getUserById(BigDecimal userId);

    boolean updateUser(User user);

    boolean deleteUser(User user);

    boolean deleteUserById(BigDecimal userId);

}
