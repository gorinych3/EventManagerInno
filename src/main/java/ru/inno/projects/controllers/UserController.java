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
import ru.inno.projects.models.Role;
import ru.inno.projects.models.User;
import ru.inno.projects.services.EventService;
import ru.inno.projects.services.UserService;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public UserController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String list(Model model) {
        log.info("Start method list from UserController");
        model.addAttribute("users", userService.getAllUsers());
        return "list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        log.info("Start method userEditForm from UserController");
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String email,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {
        log.info("Start method userSave from UserController");
        user.setEmail(email);

        userService.updateUserRoles(user, form);

        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        log.info("Start method getProfile from UserController");
        model.addAttribute(user);

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal User user, @Valid User userToSave, BindingResult bindingResult, Model model) {

        log.info("Start method updateProfile from UserController");

        boolean hasAnError = false;
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            errors.remove("usernameError");
            if (!errors.isEmpty()) {
                model.mergeAttributes(errors);
                hasAnError = true;
            }
        }

        if (userToSave.getPassword() != null && !userToSave.getPassword().equals(userToSave.getPassword2())) {
            model.addAttribute("password2Error", "Повтор пароля не совпадает.");
            hasAnError = true;
        }

        if (hasAnError) {
            return "profile";
        }

        userService.updateUser(user, userToSave.getUsername(), userToSave.getPassword(), userToSave.getPhoneNumber(), userToSave.getEmail());

        return "redirect:/user/profile";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/event/{eventId}")
    public String showEventsUsers(@PathVariable("eventId") long eventId, Model model) {
        log.info("Start method list from showEventsUsers");
        Event event = eventService.getEventById(eventId);
        model.addAttribute("users", userService.getAllUsersByEvent(event));
        return "usersFromEvent";
    }
}
