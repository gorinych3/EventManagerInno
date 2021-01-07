package ru.inno.projects.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.inno.projects.models.User;
import ru.inno.projects.services.EventService;
import ru.inno.projects.services.InvitationService;
import ru.inno.projects.services.UserService;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/invitations")
public class InvitationController {

    private final EventService eventService;
    private final UserService userService;
    private final InvitationService invitationService;


    @Autowired
    public InvitationController(EventService eventService, UserService userService, InvitationService invitationService) {
        this.eventService = eventService;
        this.userService = userService;
        this.invitationService = invitationService;
    }

//    @PreAuthorize("hasAuthority('USER')")
//    @GetMapping
//    public String eventList(Model model) {
//        log.info("Start method eventList from EventController");
//        model.addAttribute("events", eventService.getAllEvents());
//        return "eventList";
//    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user")
    public String invitationUserList(@AuthenticationPrincipal User user, Model model) {
        log.info("Start method invitationUserList from InvitationController");
        model.addAttribute("invitationsInvited", invitationService.getInvitationsByInvitedUser(user));
        model.addAttribute("invitationsInvitor", invitationService.getInvitationsByInvitorUser(user));

//        model.addAttribute("username", user.getUsername());

        return "currentUserInvitations";
    }



}
