package ru.inno.projects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.projects.models.Action;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.PlayAction;
import ru.inno.projects.models.User;
import ru.inno.projects.services.ActionService;
import ru.inno.projects.services.EventService;
import ru.inno.projects.services.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Autowired
    ActionService actionService;

    //@Transactional
    @Override
    public void run(String... args){
        //eventService.startAction(1);
//        Event event = eventService.getEventById(1);
//        System.out.println("Получили ивент!!!!");
//        System.out.println(event.getEventName());
//        System.out.println(event.getAction().getActionName());
//        System.out.println(event.getAction().getPlayActions().size());

        //-------------------------------------------------------------------------------

        Event event = new Event();
        event.setEventName("testWithAction5");
        event.setCreateDate(LocalDateTime.now());

        //eventService.save(event, null, null);


        //eventService.addEvent(event, action);
        //eventService.addEvent(event, users);
        //eventService.addEvent(event, new HashSet<>());


        //-------------------------------------------------------------------------------------------
        /*
        List<Event> eventList = eventService.getAllEvents();

        for(Event event1 : eventList){
            System.out.println(event1.getEventName());
            if(event1.getAction() != null) {
                Action findedAction = actionService.getActionByEvent(event1);

                System.out.println("=====================================================================");
                System.out.println("Наименование экшена " + findedAction.getActionName());
                System.out.println();
                Set<PlayAction> playActionSet = actionService.getAllPlayActionsByAction(findedAction);
                System.out.println("Успешно получили список playActionSet");
                System.out.println();
                for (PlayAction playAction : playActionSet){
                    System.out.println("ПОПЫТКА ПОЛУЧИТЬ ТИМЫ СО СВЯЗЬЮ ЕГЕРЯ");
                    System.out.println(playAction.getMaster().getTeamName());
                    for (User user : playAction.getMaster().getUsers()){
                        System.out.println("\t" + user.getUsername());
                    }
                    System.out.println(playAction.getSlave().getTeamName());
                    for (User user : playAction.getSlave().getUsers()) {
                        System.out.println("\t" + user.getUsername());
                    }
                }

            }else System.out.println("Нет экшена");
        }
         */
    }
}
