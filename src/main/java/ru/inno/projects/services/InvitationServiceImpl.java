package ru.inno.projects.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
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
@Transactional
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepo invitationRepo;
    private final UserRepo userRepo;
    private final EventRepo eventRepo;
    private final JavaMailSender mailSender;
    private final EventMailServise mailServise;

    @Autowired
    public InvitationServiceImpl(InvitationRepo invitationRepo, UserRepo userRepo, EventRepo eventRepo, JavaMailSender mailSender, EventMailServise mailServise) {
        this.invitationRepo = invitationRepo;
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.mailSender = mailSender;
        this.mailServise = mailServise;
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

        User invitedUser = userRepo.findUserByEmail(email);

        if (invitedUser != null) {
            final String message = String.format(
                    "Привет! \n" +
                            "%s тебя пригласили на ивент под названием: %s. \n" +
                            "для просмотра ивента перейди по ссылке: " +
                            "http://localhost:8080/event/%s \n" +
                            "для участия в ивенте подтверди приглашение по ссылке: " +
                            "http://localhost:8080/invitations/user",
                    invitorUser.getUsername(),
                    event.getEventName(),
                    event.getEventId()
            );

            mailServise.send(invitedUser.getEmail(), "Приглашение на ивент", message);
        }

        else if (!invitation.getEmailInvitation().isEmpty()) {
            final String message = String.format(
                    "Привет! \n" +
                            "Добро пожаловать в Event Manager. %s тебя пригласили на ивент под названием: %s, " +
                            "для участия в ивенте перейди по ссылке: " +
                            "http://localhost:8080/registration/invitation/%s",
                    invitorUser.getUsername(),
                    event.getEventName(),
                    invitation.getEmailInvitation()
            );

            mailServise.send(invitation.getEmailInvitation(), "Приглашение на ивент", message);
        }
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

    @Override
    public void removeInvitation(Invitation invitation) {
        invitationRepo.delete(invitation);
    }

    @Override
    public void removeInvitationsByEvent(Event event) {
        invitationRepo.removeInvitationsByEvent(event);
    }


}
