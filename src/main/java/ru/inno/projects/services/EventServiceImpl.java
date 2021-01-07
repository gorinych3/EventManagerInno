package ru.inno.projects.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.projects.models.*;
import ru.inno.projects.repos.ActionRepo;
import ru.inno.projects.repos.EventRepo;
import ru.inno.projects.repos.UserRepo;

import java.util.*;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;

    private final UserRepo userRepo;

    private final ActionRepo actionRepo;

    @Autowired
    public EventServiceImpl(EventRepo eventRepo, UserRepo userRepo, ActionRepo actionRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.actionRepo = actionRepo;
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

    @Transactional
    @Override
    public boolean addEvent(Event event, Set<User> users) {
        //users = new HashSet<>(userRepo.findAll());

//        for (User user : users){
//            System.out.println(user.getUsername() + "    " + user.getUserId());
//            event.getUsers().add(user);
//            user.getEvents().add(event);
//        }
        System.out.println("Пытаемся сохранить ивент!!!!!!!!!!!!!!");
        eventRepo.save(event);
        return true;
    }

    @Transactional
    @Override
    public boolean addEvent(Event event, Action action) {
        List<User> users = userRepo.findAll();
        System.out.println(users);
        event.setUsers(new HashSet<>(users));
        event = eventRepo.save(event);
        //action = actionRepo.save(action);
        //event.setAction(action);
        //eventRepo.save(event);
        return true;
    }

    @Transactional
    @Override
    public boolean startAction(long eventId) {
        //1 получить ивент. Нужно понять, что нам может передать клиент, скорее всего либо имя ивента, либо Id
        Event event = eventRepo.getOne(eventId);
        //2 получить action из ивента и определить логику реализации экшена
        //Action action = event.getAction();
        Action action = new Action();
        action.setActionName("testAction");
        action.setDescription("Пробуем для начала создать вручную");
        //3 реализовать экшен в зависимости от содержания полей
        List<Team> teams = new ArrayList<>();
        Set<User> users = event.getUsers();
        Set<PlayAction> playActionSet = new HashSet<>();
        //если нет заданного количества команд или количества игроков в командах, то это цепь (лига, санта)
        if(action.getTeams() == 0 && action.getPlayersOnTeam() == 0) {
            for (User user : users) {
                Team team = new Team();
                team.setTeamName(user.getUsername());
                team.setUsers(new HashSet<>(Collections.singletonList(user)));
                teams.add(team);
            }
            Collections.shuffle(teams);



            PlayAction playActionfirstLast = new PlayAction();
            playActionfirstLast.setMaster(teams.get(teams.size() - 1));
            playActionfirstLast.setSlave(teams.get(0));

            playActionSet.add(playActionfirstLast);
            for (int i = 1; i <= teams.size(); i++) {
                PlayAction playAction = new PlayAction();
                if (i != teams.size()) {
                    playAction.setMaster(teams.get(i - 1));
                    playAction.setSlave(teams.get(i));
                    playActionSet.add(playAction);
                }
            }
        }
        //4 занести данные в таблицы
        action.setPlayActions(playActionSet);
        event.setAction(action);
        actionRepo.save(action);
        eventRepo.save(event);
        //5 вернуть какое то отображение клиенту
        return false;
    }

    @Override
    public Set<User> createUserList(int countUser, Event event) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < countUser; i++) {
            users.add(i, new User("user" + i, "123" + i, true, "hjf@jgj" + i + ".com", "+711111111" + i,
                    null, new HashSet<>(Collections.singletonList(Role.USER))));
        }
        return new HashSet<>(users);
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

    private boolean saveUsers(List<User> users) {
        for (User user : users) {
            userRepo.saveAndFlush(user);
        }
        return true;
    }

//    public static void main(String[] args) {
//        Set<User> usersInit = new HashSet<>();
//        Event event = new Event();
//        List<Team> teams = new ArrayList<>();
//        List<User> users = new ArrayList<>(usersInit);
//
//        for (int i = 0; i < 19; i++) {
//            users.add(i, new User("user" + i, "123" + i, true, "hjf@jgj" + i + ".com", "+711111111" + i,
//                    null, new HashSet<>(Collections.singletonList(event)), new HashSet<>(Collections.singletonList(Role.USER))));
//        }
//        for (User user : users) {
//            Team team = new Team();
//            team.setTeamName(user.getUsername());
//            team.setTeamUsers(new HashSet<>(Collections.singletonList(user)));
//            teams.add(team);
//        }
//        Collections.shuffle(teams);
//
//        Set<PlayAction> playActionSet = new HashSet<>();
//
//        PlayAction playActionfirstLast = new PlayAction();
//        playActionfirstLast.setMaster(teams.get(teams.size()-1));
//        playActionfirstLast.setSlave(teams.get(0));
//
//        playActionSet.add(playActionfirstLast);
//        for (int i = 1; i <= teams.size(); i++) {
//            PlayAction playAction = new PlayAction();
//            if(i != teams.size()) {
//                playAction.setMaster(teams.get(i-1));
//                playAction.setSlave(teams.get(i));
//                playActionSet.add(playAction);
//            }
//        }
//
//
//        for (Team team : teams) {
//            System.out.println("==================================================================");
//            System.out.println(team.getTeamName());
//        }
//
//        for (PlayAction playAction : playActionSet) {
//            System.out.println("==================================================================");
//            System.out.println(playAction.getMaster() + " -> " + playAction.getSlave());
//        }
//    }


    @Override
    public Event save(Event event) {
        return eventRepo.save(event);
    }
}
