package event.manager.controllers;

import event.manager.models.User;
import event.manager.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main {

    @Autowired
    private UserRepo userRepo;

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

    @GetMapping("/login")
    public String enter() {
        return "login";
    }
}
