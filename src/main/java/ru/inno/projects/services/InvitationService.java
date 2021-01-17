package ru.inno.projects.services;

import ru.inno.projects.models.Event;
import ru.inno.projects.models.Invitation;
import ru.inno.projects.models.User;

import java.util.List;

public interface InvitationService {

    List<Invitation> getAllInvitations();

    List<Invitation> getInvitationsByInvitedUser(User user);

    List<Invitation> getInvitationsByInvitorUser(User user);

    List<Invitation> getInvitationsByEvent(Event event);

    boolean sendInvitation(Event event, User invitorUser, String email);

    boolean sendInvitation(Event event, User invitorUser, List<String> emails);

    int updateInvitationsOnAddingNewUser(User user);

    Invitation markInvitationRead(Invitation invitation);

    Invitation markInvitationAccepted(Invitation invitation);

    Invitation markInvitationUnAccepted(Invitation invitation);

    int getAmountOfNotReadInvitations(User user);

    int getAmountOfNotReadInvitationsForCurrentUser();

    void removeInvitation(Invitation invitation);

    Invitation getInvitationByEmailInvitationAndEvent(String email, Event event);

    Invitation getInvitationByInvitedUserAndEvent(User user, Event event);
}
