package ru.inno.projects.controllers;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.inno.projects.models.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.inno.projects.models.Member;
import ru.inno.projects.models.User;
import ru.inno.projects.repos.MemberRepo;
import ru.inno.projects.services.EventService;
import ru.inno.projects.services.InvitationService;
import ru.inno.projects.services.UserService;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final InvitationService invitationService;
    MemberRepo memberRepo;

    @Autowired
    public EventController(EventService eventService, UserService userService, InvitationService invitationService, MemberRepo memberRepo) {
        this.eventService = eventService;
        this.userService = userService;
        this.invitationService = invitationService;
        this.memberRepo = memberRepo;
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

    @GetMapping("/create")
    public String formEvent(@AuthenticationPrincipal User user) {
        log.info("Start method sendInvitation");
        return "eventRegistration";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create")
    public String formEvent(@AuthenticationPrincipal User user,
                            @RequestParam(value = "eventName", required = false)String name,
                            @RequestParam(value = "createDate", required = false) String createDate,
                            @RequestParam(value = "membersJSON", required = false) String membersJSON,
                            Model model) throws ParseException {
        log.info("Start method sendInvitation");
        Event newEvent = new Event(name, LocalDateTime.parse(createDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        Gson json = new Gson();
        List<Member> array = json.fromJson(membersJSON, new TypeToken<List<Member>>() {}.getType());
        newEvent.setMembers(array);
        Event savedEvent = eventService.save(newEvent);
        array.forEach((m) ->
        {
            m.setEvent(savedEvent);
            memberRepo.save(m);
        });
        model.addAttribute("event",savedEvent);
        return "eventPage";
    }
}
