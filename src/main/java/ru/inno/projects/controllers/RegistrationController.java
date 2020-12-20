package ru.inno.projects.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.inno.projects.models.User;
import ru.inno.projects.services.UserService;


import javax.validation.Valid;
import java.util.Map;

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
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        log.info("Start method addUser from RegistrationController");

        boolean hasAnError = false;
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            hasAnError = true;
        }

        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("password2Error", "Повтор пароля не совпадает.");
            hasAnError = true;
        }

        if (userService.isUserExists(user)) {
            model.addAttribute("usernameError", "Такой пользователь уже существует.");
            hasAnError = true;
        }

        if (hasAnError){
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("errorMessage", "Что-то пошло не так во время регистрации.");
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
