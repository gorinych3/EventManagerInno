package ru.inno.projects.services;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.Invitation;
import ru.inno.projects.models.User;
import ru.inno.projects.repos.InvitationRepo;
import ru.inno.projects.repos.UserRepo;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Thread.sleep;

@Getter
@Setter
@Slf4j
@Component
public class SenderMail2User implements Runnable {

    private final UserRepo userRepo;
    private final InvitationRepo invitationRepo;
    private final EventMailService mailservice;
    private Event event;
    private User invitorUser;
    private List<String> emails;

    @Autowired
    public SenderMail2User(UserRepo userRepo, InvitationRepo invitationRepo, EventMailService mailService) {
        this.userRepo = userRepo;
        this.invitationRepo = invitationRepo;
        this.mailservice = mailService;
    }

    @Override
    public void run() {
        for (String userEmail : emails) {
            saveData(userEmail);
        }
        for (String email : emails) {
            send(email, event);
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                log.warn("InterruptedException");
                Thread.currentThread().interrupt();
            }
        }
        emails.clear();

    }

    private void saveData(String userEmail) {
        Invitation invitation = new Invitation();

        User userToInvite = userRepo.findUserByEmail(userEmail);
        if (userToInvite == null) {
            log.info("User with the email does not exist");
            invitation.setEmailInvitation(userEmail);
            Invitation invitationByEmailInvitationAndEvent = invitationRepo.findInvitationByEmailInvitationAndEvent(userEmail, event);
            if (invitationByEmailInvitationAndEvent != null) {
                log.info("This user already invited to the event");
            }
        } else {
            invitation.setInvitedUser(userToInvite);
            Invitation invitationByInvitedUserAndEvent = invitationRepo.findInvitationByInvitedUserAndEvent(userToInvite, event);
            if (invitationByInvitedUserAndEvent != null) {
                log.info("This user already invited to the event");
            }
        }


        invitation.setEvent(event);
        invitation.setInvitorUser(invitorUser);
        invitation.setAccepted(false);
        invitation.setInformed(false);
        invitation.setCreationDate(LocalDateTime.now());

        invitationRepo.save(invitation);
    }

    public void send(String email, Event event) {

        try {
            Invitation invitation = invitationRepo.findInvitationByEmailInvitationAndEvent(email, event);

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

                mailservice.send(invitedUser.getEmail(), "Приглашение на ивент", message);
            } else if (!invitation.getEmailInvitation().isEmpty()) {
                final String message = String.format(
                        "Привет! \n" +
                                "Добро пожаловать в Event Manager. %s тебя пригласили на ивент под названием: %s, " +
                                "для участия в ивенте перейди по ссылке: " +
                                "http://localhost:8080/registration/invitation/%s",
                        invitorUser.getUsername(),
                        event.getEventName(),
                        invitation.getEmailInvitation()
                );

                mailservice.send(invitation.getEmailInvitation(), "Приглашение на ивент", message);
            }
        }catch (Exception e){
            log.error("Не получилось доставить приглашение на почту клиента");
        }
    }
}
