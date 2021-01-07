package ru.inno.projects.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.Invitation;
import ru.inno.projects.models.User;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findUserByEmail(String email);

    User findByActivationCode(String code);

    List<User> findAllByEvents(Event event);

//    List<User> findAllByInvitationsUserInvited(Invitation invitation);
//
//    List<User> findAllByInvitationsUserInvitor(Invitation invitation);
}
