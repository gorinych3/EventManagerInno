package ru.inno.projects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.inno.projects.models.Role;
import ru.inno.projects.models.User;
import ru.inno.projects.repos.UserRepo;

import java.util.Collections;
import java.util.Objects;

@Controller
public class RegistrationController {

    private UserRepo userRepo;

    @Autowired
    public RegistrationController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/registration")
    public String registration() {
        System.out.println("Вызов registration контроллера");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Model model, User user) {
        User actualUser = userRepo.findByUsername(user.getUsername());
        if (!Objects.equals(actualUser, null)) {
            model.addAttribute("message", "User exist!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
}
