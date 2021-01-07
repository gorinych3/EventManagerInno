package ru.inno.projects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.projects.models.Action;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.User;
import ru.inno.projects.services.EventService;
import ru.inno.projects.services.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@SpringBootApplication
public class Start implements CommandLineRunner {

    public static void main(String[] args) {
        log.info("Start!!!");
        SpringApplication.run(Start.class, args);
    }

    @Autowired
    EventService eventService;




    //@Transactional
    @Override
    public void run(String... args){
//        eventService.startAction(1);
//        Event event = eventService.getEventById(1);
//        System.out.println("Получили ивент!!!!");
//        System.out.println(event.getEventName());
//        System.out.println(event.getAction().getActionName());
//        System.out.println(event.getAction().getPlayActions().size());

        Action action = new Action();
        Event event = new Event();
        event.setEventName("testWithAction");
        event.setCreateDate(LocalDateTime.now());

        action.setActionName("test Action");
        action.setDescription("Пробуем добавить");
        action.setPlayersOnTeam(0);
        action.setTeams(0);


        Set<User> users = eventService.createUserList(5, event);
        for (User user : users){
            event.getUsers().add(user);
            user.getEvents().add(event);
        }
        action.setEvent(event);
        event.setAction(action);

        //eventService.addEvent(event, action);
        eventService.addEvent(event, users);
        //eventService.addEvent(event, new HashSet<>());


    }
}
