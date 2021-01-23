package ru.inno.projects.controllers;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.inno.projects.models.Action;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.Invitation;
import ru.inno.projects.models.User;
import ru.inno.projects.services.ActionService;
import ru.inno.projects.services.EventService;
import ru.inno.projects.services.InvitationService;
import ru.inno.projects.services.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final InvitationService invitationService;
    private final ActionService actionService;

    @Autowired
    public EventController(EventService eventService,
                           UserService userService,
                           InvitationService invitationService,
                           ActionService actionService) {
        this.eventService = eventService;
        this.userService = userService;
        this.invitationService = invitationService;
        this.actionService = actionService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public String eventList(@AuthenticationPrincipal User user, Model model) {
        log.info("Start method eventList from EventController");
        model.addAttribute("events", eventService.getEventsByUser(user));
        return "eventList";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user")
    public String eventUsersList(@AuthenticationPrincipal User user, Model model) {
        log.info("Start method eventUsersList from EventController");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("events", eventService.getEventsByOwner(user));
        return "currentUserEvents";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("{event}")
    public String showEvent(@PathVariable Event event, @AuthenticationPrincipal User user,
                            @RequestParam(value = "err", required = false) Integer err, Model model) {
        log.info("SHOW CURRENT EVENT {}", event.getEventId());

        model.addAttribute("event", event);
        model.addAttribute("user", user);
        model.addAttribute("isResult", eventService.getBoolResultAction(event));

        List<Invitation> invitationsByEvent = invitationService.getInvitationsByEvent(event);
        List<User> userList = new ArrayList<>(userService.getAllUsersByEvent(event));
        model.addAttribute("event", event);
        model.addAttribute("usersCount", userList.isEmpty() ? 0 : userList.size());
        model.addAttribute("invitations", invitationsByEvent);

        if (err != null) {
            if (err == 1) {
                model.addAttribute("errorMessage", "Вы уже добавили этого пользователя");
            } else if (err == 2) {
                model.addAttribute("errorMessage", "Что-то пошло не так при отсылке приглашения");
            }
        }

        return "eventPage";
    }

    @PostMapping("/send_invitation")
    public String sendInvitation(@RequestParam Map<String, String> form,
                                 @AuthenticationPrincipal User user,
                                 @RequestParam("eventId") Event event,
                                 Model model) {

        log.info("SEND INVITATION TO CURRENT USER");

        String email = form.get("email");

        User invitedUser = userService.getUserByEmail(email);

        Invitation invitationByEmailInvitationAndEvent = null;
        Invitation invitationByInvitedUserAndEvent = null;

        if (invitedUser != null) {
            invitationByInvitedUserAndEvent = invitationService.getInvitationByInvitedUserAndEvent(invitedUser, event);
        } else {
            invitationByEmailInvitationAndEvent = invitationService.getInvitationByEmailInvitationAndEvent(email, event);
        }


        String errCode = "";
        if (invitationByEmailInvitationAndEvent != null || invitationByInvitedUserAndEvent != null) {
            errCode = "?err=1";
        } else if (!invitationService.sendInvitation(event, user, email)) {
            errCode = "?err=2";
        }

        return "redirect:/event/" + event.getEventId() + errCode;
    }

    @PostMapping("/remove_invitation")
    public String removeInvitation(@AuthenticationPrincipal User user,
                                   @RequestParam("invitationId") Invitation invitation,
                                   @RequestParam("eventId") Event event) {
        log.info("Start method removeInvitation");
        if (user.getUserId().equals(invitation.getInvitorUser().getUserId())) {
            invitationService.removeInvitation(invitation);
        }
        return "redirect:/event/" + event.getEventId();
    }

    @GetMapping("/create")
    public String formEvent(@AuthenticationPrincipal User user) {
        log.info("GO TO CREATE EVENT PAGE");
        return "eventRegistration";
    }

    /**
     * Очень жирный контроллер, необходимо перенести всю логику в сервис класс
     */
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create")
    public String formEvent(@AuthenticationPrincipal User user,
                            @RequestParam(value = "eventId", required = false) Event event,
                            @RequestParam(value = "eventName", required = false) String name,
                            @RequestParam(value = "eventDate", required = false) String eventDate,
                            @RequestParam(value = "eventTossDate", required = false) String eventTossDate,
                            @RequestParam(value = "membersJSON", required = false) String membersJSON,
                            @RequestParam(value = "teams", required = false, defaultValue = "0") Integer teams,
                            @RequestParam(value = "playersOnTeam", required = false, defaultValue = "0") Integer playersOnTeam) {
        log.info("CREATE NEW EVENT");
        Event newEvent = new Event(name, LocalDateTime.now());
                            @RequestParam(value = "teams", required = false) Integer teams,
                            @RequestParam(value = "playersOnTeam", required = false) Integer playersOnTeam) {
        log.info("CREATE NEW or UPDATE EVENT");
        Event eventToSave;
        if (event == null) {
            eventToSave = new Event(name, LocalDateTime.now());
        } else {
            eventToSave = event;
            eventToSave.setEventName(name);
        }

        Gson json = new Gson();
        List<String> array = json.fromJson(membersJSON, new TypeToken<List<String>>() {
        }.getType());
        eventToSave.setEventDate(eventDate == null || eventDate.isEmpty() ?
                null : LocalDateTime.parse(eventDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        eventToSave.setEventTossDate(eventTossDate == null || eventTossDate.isEmpty() ?
                null : LocalDate.parse(eventTossDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        eventToSave.setOwnerUser(user);
        Event savedEvent = eventService.save(eventToSave, teams, playersOnTeam);

        if (event == null) {
            invitationService.sendInvitation(savedEvent, user, array);
        }

        return "redirect:/event/" + savedEvent.getEventId();
    }

    @PostMapping("/start_event")
    public String startEvent(@AuthenticationPrincipal User user,
                             @RequestParam(value = "eventId") long eventId, Model model) {
        log.info("START ACTION");
        Event resultEvent = eventService.startAction(eventId);
        Action action = actionService.getActionById(resultEvent.getAction().getActionId());

        model.addAttribute("user", user);
        model.addAttribute("event", resultEvent);
        model.addAttribute("action", resultEvent.getAction());
        model.addAttribute("playActions", resultEvent.getAction().getPlayActions());
        model.addAttribute("santa", action.getTeams() == 0 && action.getPlayersOnTeam() == 0 ? 1 : 0);
        return "eventResultPage";
    }

    @PostMapping("/resultAction")
    public String showResultAction(@AuthenticationPrincipal User user,
                                   @RequestParam(value = "eventId") long eventId, Model model) {
        log.info("SHOW RESULT ACTION");
        Event resultEvent = eventService.getEventById(eventId);
        Action action = actionService.getActionById(resultEvent.getAction().getActionId());

        model.addAttribute("user", user);
        model.addAttribute("event", resultEvent);
        model.addAttribute("action", action);
        model.addAttribute("playActions", actionService.getAllPlayActionsByAction(action));
        model.addAttribute("santa", action.getTeams() == 0 && action.getPlayersOnTeam() == 0 ? 1 : 0);
        return "eventResultPage";
    }

    @PostMapping("/remove_event")
    public String removeEvent(@AuthenticationPrincipal User user,
                              @RequestParam("eventId") Event event, Model model) {

        invitationService.removeInvitationsByEvent(event);
        if(event.getAction() != null) actionService.removeAction(event.getAction());
        eventService.removeEvent(event);

        return "redirect:/event/user";
    }
}
