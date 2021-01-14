package ru.inno.projects.services;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.Role;
import ru.inno.projects.models.Team;
import ru.inno.projects.models.User;
import ru.inno.projects.repos.EventRepo;
import ru.inno.projects.repos.UserRepo;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class EventServiceImplTest {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventService eventService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

//    @Ignore
//    @Transactional
//    @Test
//    void createTeams() {
//        List<User> users = new ArrayList<>();
//
//        if (saveUsers(createUserList(new Event()))) {
//            Event event = new Event();
//            event.setUsers(new HashSet<>(createUserList(new Event())));
//            event.setEventName("test");
//            event.setCreateDate(LocalDateTime.now());
//
//            System.out.println("попытка сохранить ивент");
//            eventRepo.save(event);
//            System.out.println("ивент сохранен");
//
//            users = createUserList(event);
//        }
//
//        assertTrue(eventService.createTeams(users));
//
//
//    }

    private List<User> createUserList(Event event) {
        List<User> users = new ArrayList<>();
        users.add(new User("user", "123", true, "hjf@jgj.com", "+71111111111",
                null, new HashSet<>(Collections.singletonList(event)), new HashSet<>(Collections.singletonList(Role.USER))));
        users.add(new User("user1", "123", true, "hjf1@jgj.com", "+71111111111",
                null, new HashSet<>(Collections.singletonList(event)), new HashSet<>(Collections.singletonList(Role.USER))));
        users.add(new User("user2", "123", true, "hjf2@jgj.com", "+71111111111",
                null, new HashSet<>(Collections.singletonList(event)), new HashSet<>(Collections.singletonList(Role.USER))));
        users.add(new User("user3", "123", true, "hjf3@jgj.com", "+71111111111",
                null, new HashSet<>(Collections.singletonList(event)), new HashSet<>(Collections.singletonList(Role.USER))));
        users.add(new User("user4", "123", true, "hjf4@jgj.com", "+71111111111",
                null, new HashSet<>(Collections.singletonList(event)), new HashSet<>(Collections.singletonList(Role.USER))));
        return users;
    }


    private boolean saveUsers(List<User> users){
        for (User user : users){
            userRepo.save(user);
        }
        return true;
    }
}