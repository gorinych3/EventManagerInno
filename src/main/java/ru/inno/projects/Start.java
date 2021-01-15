package ru.inno.projects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Start implements CommandLineRunner {

    public static void main(String[] args) {
        log.info("Start!!!");
        SpringApplication.run(Start.class, args);
    }

    @Override
    public void run(String... args) {
    }
}
