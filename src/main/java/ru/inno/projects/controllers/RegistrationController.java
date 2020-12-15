package ru.inno.projects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.inno.projects.models.User;
import ru.inno.projects.services.UserService;

@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        System.out.println("Вызов registration контроллера");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Model model, User user) {
        if (!userService.addUser(user)) {
            model.addAttribute("message", "User exist!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivate = userService.activateUser(code);
        String message;

        if(isActivate){
            message = "Поздравлем, регистрация прошла успешно!";
        } else {
            message = "Код активации не найден, попробуйте еще раз";
        }

        model.addAttribute("message", message);
        return "login";
    }
}
