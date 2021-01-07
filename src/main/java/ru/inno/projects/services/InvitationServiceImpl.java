package ru.inno.projects.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.Invitation;
import ru.inno.projects.models.User;
import ru.inno.projects.repos.InvitationRepo;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class InvitationServiceImpl implements InvitationService {

    private InvitationRepo invitationRepo;
    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public InvitationServiceImpl(InvitationRepo invitationRepo, UserService userService, EventService eventService) {

        this.invitationRepo = invitationRepo;
        this.userService = userService;
        this.eventService = eventService;
    }

    @Override
    public List<Invitation> getAllInvitations() {
        return invitationRepo.findAll();
    }

    @Override
    public List<Invitation> getInvitationsByInvitedUser(User user) {
        return invitationRepo.findInvitationsByInvitedUser(user);
    }

    @Override
    public List<Invitation> getInvitationsByInvitorUser(User user) {
        return invitationRepo.findInvitationsByInvitorUser(user);
    }

    @Override
    public List<Invitation> getAllByEvent(Event event) {
        return null;
    }

    @Override
    public boolean sendInvitation(Event event, User invitorUser, String email) {
        Invitation invitation = new Invitation();

        User userToInvite = userService.getUserByEmail(email);
        if(userToInvite == null){
            // throw exception
            log.info("User with the email does not exist");

        } else {
            invitation.setInvitedUser(userToInvite);
            Invitation invitationByInvitedUserAndEvent = invitationRepo.findInvitationByInvitedUserAndEvent(userToInvite, event);
            if(invitationByInvitedUserAndEvent != null) {
                log.info("This user already invited to the event");
                return false;
            }
        }



        invitation.setEvent(event);
        invitation.setInvitorUser(invitorUser);
        invitation.setAccepted(false);
        invitation.setInformed(false);
        invitation.setCreationDate(LocalDateTime.now());

        invitationRepo.save(invitation);

        return true;
    }
}
