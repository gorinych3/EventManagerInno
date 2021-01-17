package ru.inno.projects.services;

public interface EventMailService {

    void send(String emailTo, String subject, String message);
}
