package ru.inno.projects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.inno.projects.models.User;
import ru.inno.projects.repos.UserRepo;

@Controller
public class MainController {

    private final UserRepo userRepo;

    @Autowired
    public MainController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String main(Model model) {
        model.addAttribute("name", "user");
        return "main";
    }

    @GetMapping("/list")
    public String list(Model model) {
        Iterable<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "list";
    }

}
