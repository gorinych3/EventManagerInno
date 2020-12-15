package ru.inno.projects.services;

public interface EventMailServise {

    void send(String emailTo, String subject, String message);
}
