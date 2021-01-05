package ru.inno.projects.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.projects.models.*;
import ru.inno.projects.repos.EventRepo;
import ru.inno.projects.repos.UserRepo;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;

    private final UserRepo userRepo;

    @Autowired
    public EventServiceImpl(EventRepo eventRepo, UserRepo userRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    @Transactional
    @Override
    public List<Event> getEventsByUser(User user) {
        log.info("start getEventsByUser in EventServiceImpl");
        return eventRepo.findEventsByUsers(user);
    }

    @Override
    public Event getEventById(long eventId) {
        return eventRepo.getOne(eventId);
    }

    @Override
    public Event getEventByEventName(String eventName) {
        return null;
    }

    @Override
    public boolean addEvent(Event event, Set<User> users) {
        event.setUsers(users);
        eventRepo.save(event);
        return true;
    }

    @Override
    public boolean addEvent(Event event, Set<User> users, Action action) {
        event.setUsers(users);
        event.setAction(action);
        eventRepo.save(event);
        return true;
    }

    @Transactional
    @Override
    public boolean createTeams(long eventId) {
        Event event = eventRepo.getOne(eventId);
        Action action = event.getAction();
        List<User> users = new ArrayList<>(event.getUsers());

        List<Team> teams;

        //если задано количество команд и не задано количество игроков в команде
        if (action.getTeams() != 0 && action.getPlayersOnTeam() == 0 && action.getTeams() < users.size()) {
            //необходимо разбить общее количество игроков на команды
            teams = new ArrayList<>(action.getTeams());
            Collections.shuffle(users);
            for (int i = 1; i <= action.getTeams(); i++){
                Team team = new Team();
                team.setTeamName("team" + i);
                Set<User> userSet = new HashSet<>();
                for (int j = 0; j < users.size(); j++){
                    if (users.size() % action.getTeams() == 0) {
                        userSet.add(users.get(j));
                    }
                }
                team.setTeamUsers(userSet);
                teams.add(team);
            }

        }

        return false;
    }

    @Transactional
    @Override
    public boolean createTeams(List<User> users) {
        System.out.println("Start createTeams !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        users = createUserList(new Event());
        saveUsers(users);
        Event event = new Event();
        event.setUsers(new HashSet<>(createUserList(new Event())));
        event.setEventName("test");
        event.setCreateDate(LocalDateTime.now());

//        System.out.println("попытка сохранить ивент");
//        eventRepo.saveAndFlush(event);
//        System.out.println("ивент сохранен");
        Set<Team> teams = new HashSet<>();
        Team team1 = new Team();
        Team team2 = new Team();
        team1.setTeamName("team1");
        team2.setTeamName("team2");

        List<User> userSet1 = users.subList(0, (users.size() / 2) - 1);
        List<User> userSet2 = users.subList(users.size() - users.size() / 2, users.size());

        team1.setTeamUsers(new HashSet<>(userSet1));
        team2.setTeamUsers(new HashSet<>(userSet2));

        teams.add(team1);
        teams.add(team2);

        event.setTeams(teams);

        eventRepo.save(event);

        return true;
    }

    @Override
    public boolean createRounds(String action) {
        return false;
    }

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


    private boolean saveUsers(List<User> users) {
        for (User user : users) {
            userRepo.saveAndFlush(user);
        }
        return true;
    }


//    public static void main(String [] args) {
//        Set<User> usersInit = new HashSet<>();
//        Event event = new Event();
//        List<Team> teams;
//        List<User> users = new ArrayList<>(usersInit);
//        int countTeams = 3;
//        for (int i = 0; i < 20; i++) {
//            users.add(i, new User("user" + i, "123" + i, true, "hjf@jgj" + i + ".com", "+711111111" + i,
//                    null, new HashSet<>(Collections.singletonList(event)), new HashSet<>(Collections.singletonList(Role.USER))));
//        }
//        teams = new ArrayList<>(countTeams);
//        int sizeTeam = users.size()/countTeams;
//        //Collections.shuffle(users);
//        for (int i = 0; i <= countTeams; i++) {
//            Team team = new Team();
//            team.setTeamName("team" + i);
//            Set<User> userSet = new HashSet<>();
//
//            for(int j = users.size() - sizeTeam * i; j > 0; j--){
//                userSet.add(users.get(j));
//            }
//
//            team.setTeamUsers(userSet);
//            teams.add(team);
//        }
//
//        for(Team team : teams){
//            System.out.println("==================================================================");
//            System.out.println(team.getTeamName());
//            System.out.println(team.getTeamUsers().size());
//            for (User user : team.getTeamUsers()){
//                System.out.println(user.getUsername());
//            }
//        }
//    }

    public static void main(String [] args) {
        Set<User> usersInit = new HashSet<>();
        Event event = new Event();
        List<Team> teams;
        List<User> users = new ArrayList<>(usersInit);

        for (int i = 0; i < 20; i++) {
            users.add(i, new User("user" + i, "123" + i, true, "hjf@jgj" + i + ".com", "+711111111" + i,
                    null, new HashSet<>(Collections.singletonList(event)), new HashSet<>(Collections.singletonList(Role.USER))));
        }
        int countTeams = usersInit.size();
        teams = new ArrayList<>(countTeams);
        int sizeTeam = users.size()/countTeams;
        //Collections.shuffle(users);
        for (int i = 0; i <= countTeams; i++) {
            Team team = new Team();
            team.setTeamName("team" + i);
            Set<User> userSet = new HashSet<>();
            int lastUser = 0;

            for(int j = users.size() - sizeTeam * i; j > 0; j--){
                userSet.add(users.get(j));
            }

            team.setTeamUsers(userSet);
            teams.add(team);
        }

        for(Team team : teams){
            System.out.println("==================================================================");
            System.out.println(team.getTeamName());
            System.out.println(team.getTeamUsers().size());
            for (User user : team.getTeamUsers()){
                System.out.println(user.getUsername());
            }
        }
    }
}
