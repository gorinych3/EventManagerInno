package ru.inno.projects.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.User;
import ru.inno.projects.services.EventService;
import ru.inno.projects.services.InvitationService;
import ru.inno.projects.services.UserService;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final InvitationService invitationService;

    @Autowired
    public EventController(EventService eventService, UserService userService, InvitationService invitationService) {
        this.eventService = eventService;
        this.userService = userService;
        this.invitationService = invitationService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public String eventList(Model model) {
        log.info("Start method eventList from EventController");
        model.addAttribute("events", eventService.getAllEvents());
        return "eventList";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user")
    public String eventUsersList(@AuthenticationPrincipal User user, Model model) {
        log.info("Start method eventUsersList from EventController");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("events", eventService.getEventsByUser(user));
        return "currentUserEvents";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("{event}")
    public String showEvent(@PathVariable Event event, @AuthenticationPrincipal User user, Model model) {
        log.info("Start method showEvent");
        model.addAttribute("event", event);
        return "eventPage";
    }

    @PostMapping("/send_invitation")
    public String sendInvitation(@RequestParam Map<String, String> form, @AuthenticationPrincipal User user, @RequestParam("eventId") Event event, Model model) {

        log.info("Start method sendInvitation");

        String email = form.get("email");

        if (!invitationService.sendInvitation(event, user, email)) {
            model.addAttribute("errorMessage", "Что-то пошло не так при отсылке приглашения");
            model.addAttribute("event", event);
            return "eventPage";
        }

        return "redirect:/invitations/user";
    }

}
