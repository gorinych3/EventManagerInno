package ru.inno.projects.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.Invitation;
import ru.inno.projects.models.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    boolean addUser(User user);

    boolean isUserExists(User user);

    User getUserByLogin(String login);

    User getUserByName(String name);

    User getUserById(BigDecimal userId);

    Set<User> getAllUsersByEvent(Event event);

    boolean updateUserRoles(User user, Map<String, String> form);

    boolean updateUser(User user, String password, String phoneNumber, String email);

    boolean deleteUser(User user);

    boolean deleteUserById(BigDecimal userId);

    boolean activateUser(String code);

    User getUserByEmail(String email);

    List<User> getAllByInvitationsInvited(Invitation invitation);

    List<User> getAllByInvitationsInvitor(Invitation invitation);
}
