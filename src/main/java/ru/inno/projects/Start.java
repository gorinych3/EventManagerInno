package ru.inno.projects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.inno.projects.services.EventService;

import java.util.ArrayList;

@Slf4j
@SpringBootApplication
public class Start implements CommandLineRunner {

    public static void main(String[] args) {
        log.info("Start!!!");
        SpringApplication.run(Start.class, args);
    }

    @Autowired
    EventService eventService;

    @Override
    public void run(String... args){
        eventService.createTeams(new ArrayList<>());
    }
}
