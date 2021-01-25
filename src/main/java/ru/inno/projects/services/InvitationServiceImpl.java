package ru.inno.projects.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.Invitation;
import ru.inno.projects.models.User;
import ru.inno.projects.repos.EventRepo;
import ru.inno.projects.repos.InvitationRepo;
import ru.inno.projects.repos.UserRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@Transactional
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepo invitationRepo;
    private final UserRepo userRepo;
    private final EventRepo eventRepo;
    private final EventMailService mailServise;
    private final SenderMail2User senderMail2User;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    public InvitationServiceImpl(InvitationRepo invitationRepo,
                                 UserRepo userRepo,
                                 EventRepo eventRepo,
                                 EventMailService mailServise,
                                 SenderMail2User senderMail2User) {
        this.invitationRepo = invitationRepo;
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.mailServise = mailServise;
        this.senderMail2User = senderMail2User;
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


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Invitation getInvitationByInvitedUserAndEvent(User user, Event event) {
        return invitationRepo.findInvitationByInvitedUserAndEvent(user, event);
    }


    @Override
    public Invitation getInvitationByEmailInvitationAndEvent(String email, Event event) {
        return invitationRepo.findInvitationByEmailInvitationAndEvent(email, event);
    }

    @Override
    public boolean sendInvitation(Event event, User invitorUser, String email) {
        String message = "";
        String sendEmail = "";
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
                message = String.format(
                    "Привет! \n" +
                            "%s тебя пригласили на ивент под названием: %s. \n" +
                            "Для просмотра ивента перейди по ссылке: " +
                            "http://localhost:8080/event/%s \n" +
                            "Для участия в ивенте подтверди приглашение по ссылке: " +
                            "http://localhost:8080/invitations/user",
                    invitorUser.getUsername(),
                    event.getEventName(),
                    event.getEventId()
            );
            sendEmail = invitedUser.getEmail();

        } else if (!invitation.getEmailInvitation().isEmpty()) {
            message = String.format(
                    "Привет! \n" +
                            "Добро пожаловать в Event Manager. %s тебя пригласили на ивент под названием: %s, " +
                            "для участия в ивенте перейди по ссылке: " +
                            "http://localhost:8080/registration/invitation/%s",
                    invitorUser.getUsername(),
                    event.getEventName(),
                    invitation.getEmailInvitation()
            );
            sendEmail = invitation.getEmailInvitation();
        }
        if (invitedUser != null || !invitation.getEmailInvitation().isEmpty()) {
            try {
                mailServise.send(sendEmail, "Приглашение на ивент", message);
            }catch (Exception e){
                log.error("Не удалось отправить приглашение на почту участнику");
            }
        }
        return true;
    }

    @Override
    public boolean sendInvitation(Event event, User user, List<String> emails) {
        log.info("Запуск нового потока для отправки пачки приглашений");
        senderMail2User.setEmails(emails);
        senderMail2User.setEvent(event);
        senderMail2User.setInvitorUser(user);
        executorService.submit(senderMail2User);
        log.info("Все письма отправлены, останавливаем поток");
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
