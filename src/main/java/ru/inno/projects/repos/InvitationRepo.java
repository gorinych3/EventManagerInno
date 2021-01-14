package ru.inno.projects.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.Invitation;
import ru.inno.projects.models.User;

import java.util.List;

@Repository
public interface InvitationRepo extends JpaRepository<Invitation, Long> {

    List<Invitation> findInvitationsByInvitedUser(User user);

    List<Invitation> findInvitationsByInvitorUser(User user);

    List<Invitation> findInvitationsByEmailInvitation(String email);

    Invitation findInvitationByInvitedUserAndEvent(User user, Event event);

    Invitation findInvitationByEmailInvitationAndEvent(String email, Event event);

    List<Invitation> findInvitationsByInformedIsFalseAndInvitedUser(User user);

    List<Invitation> findInvitationsByEvent(Event event);

    List<Invitation> findAllByEvent(Event event);

}
