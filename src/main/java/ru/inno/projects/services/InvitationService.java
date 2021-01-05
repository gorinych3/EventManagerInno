package ru.inno.projects.services;

import ru.inno.projects.models.Event;
import ru.inno.projects.models.Invitation;
import ru.inno.projects.models.User;

import java.util.List;

public interface InvitationService {

    List<Invitation> getAllInvitations();

    List<Invitation> getInvitationsByInvitedUser(User user);

    List<Invitation> getInvitationsByInvitorUser(User user);

    List<Invitation> getAllByEvent(Event event);

    boolean sendInvitation(Event event, User invitorUser, String email);
}
