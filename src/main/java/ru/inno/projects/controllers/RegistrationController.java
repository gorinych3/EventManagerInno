package ru.inno.projects.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.inno.projects.models.User;
import ru.inno.projects.services.UserService;

@Slf4j
@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        log.info("Start method registration from RegistrationController");
        System.out.println("Вызов registration контроллера");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Model model, User user) {
        log.info("Start method addUser from RegistrationController");
        if (!userService.addUser(user)) {
            model.addAttribute("message", "User exist!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        log.info("Start method activate from RegistrationController");
        boolean isActivate = userService.activateUser(code);
        String message;

        if(isActivate){
            log.info("Registration was successful");
            message = "Поздравляем, регистрация прошла успешно!";
        } else {
            log.info("Registration failed, activation code not found");
            message = "Код активации не найден, попробуйте еще раз";
        }

        model.addAttribute("message", message);
        return "login";
    }
}
