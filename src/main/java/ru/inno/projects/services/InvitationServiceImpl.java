package ru.inno.projects.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.Invitation;
import ru.inno.projects.models.User;
import ru.inno.projects.repos.EventRepo;
import ru.inno.projects.repos.InvitationRepo;
import ru.inno.projects.repos.UserRepo;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepo invitationRepo;
    private final UserRepo userRepo;
    private final EventRepo eventRepo;


    @Autowired
    public InvitationServiceImpl(InvitationRepo invitationRepo, UserRepo userRepo, EventRepo eventRepo) {
        this.invitationRepo = invitationRepo;
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
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
    public List<Invitation> getInvitationsByEvent(Event event) {
        return invitationRepo.findInvitationsByEvent(event);
    }

    @Override
    public boolean sendInvitation(Event event, User invitorUser, String email) {
        Invitation invitation = new Invitation();

        User userToInvite = userRepo.findUserByEmail(email);
        if (userToInvite == null) {
            log.info("User with the email does not exist");
            invitation.setEmailInvitation(email);
            Invitation invitationByEmailInvitationAndEvent = invitationRepo.findInvitationByEmailInvitationAndEvent(email, event);
            if (invitationByEmailInvitationAndEvent != null) {
                log.info("This user already invited to the event");
                return false;
            }
        } else {
            invitation.setInvitedUser(userToInvite);
            Invitation invitationByInvitedUserAndEvent = invitationRepo.findInvitationByInvitedUserAndEvent(userToInvite, event);
            if (invitationByInvitedUserAndEvent != null) {
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

    @Override
    public int updateInvitationsOnAddingNewUser(User user) {
        List<Invitation> invitationsToUpdate = invitationRepo.findInvitationsByEmailInvitation(user.getEmail());
        for (Invitation invitation :
                invitationsToUpdate) {
            invitation.setInvitedUser(user);
            invitationRepo.save(invitation);
        }

        return invitationsToUpdate.size();
    }

    @Override
    public Invitation markInvitationRead(Invitation invitation) {
        invitation.setInformed(true);
        return invitationRepo.save(invitation);
    }


    @Transactional
    @Override
    public Invitation markInvitationAccepted(Invitation invitation) {
        log.info("markInvitationAccepted method");
        invitation.setAccepted(true);
        Event event = eventRepo.getOne(invitation.getEvent().getEventId());
        event.setEventName("SANTA NEW");
        User user = userRepo.getOne(invitation.getInvitedUser().getUserId());
        user.getEvents().add(event);
        userRepo.save(user);
        event.getUsers().add(user);
        eventRepo.save(event);

        return invitationRepo.save(invitation);
    }

    @Override
    public Invitation markInvitationUnAccepted(Invitation invitation) {
        invitation.setAccepted(false);
        return invitationRepo.save(invitation);
    }

    @Override
    public int getAmountOfNotReadInvitations(User user) {
        return invitationRepo.findInvitationsByInformedIsFalseAndInvitedUser(user).size();
    }

    @Override
    public int getAmountOfNotReadInvitationsForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            User user = (User) auth.getPrincipal();
            return getAmountOfNotReadInvitations(user);
        }
        return 0;
    }


}
