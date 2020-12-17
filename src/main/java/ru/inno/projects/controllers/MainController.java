package ru.inno.projects.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.inno.projects.repos.UserRepo;

@Slf4j
@Controller
@RequestMapping("/")
public class MainController {

    private final UserRepo userRepo;

    @Autowired
    public MainController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String main() {
        log.info("Start method main from MainController");
        return "main";
    }

}
