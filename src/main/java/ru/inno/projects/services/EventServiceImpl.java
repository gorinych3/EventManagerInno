package ru.inno.projects.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.projects.models.*;
import ru.inno.projects.repos.ActionRepo;
import ru.inno.projects.repos.EventRepo;
import ru.inno.projects.repos.PlayActionRepo;
import ru.inno.projects.repos.UserRepo;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@EnableScheduling
@Service
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;
    private final UserRepo userRepo;
    private final ActionRepo actionRepo;
    private final PlayActionRepo playActionRepo;

    @Autowired
    public EventServiceImpl(EventRepo eventRepo, UserRepo userRepo, ActionRepo actionRepo, PlayActionRepo playActionRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.actionRepo = actionRepo;
        this.playActionRepo = playActionRepo;
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
    public List<Event> getEventsByOwner(User user) {
        log.info("start getEventsByOwner in EventServiceImpl");
        return eventRepo.findEventsByOwnerUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Event getEventById(long eventId) {
        return eventRepo.findEventByEventId(eventId);
    }

    @Transactional
    @Override
    public Event save(Event event, Integer teams, Integer playersOnTeam) {
        Action action = new Action();
        action.setActionName("unknown type");
        if (teams != null && teams != 0 && (playersOnTeam == null || playersOnTeam == 0)) {
            action.setTeams(teams);
            action.setActionName("Team");
            action.setDescription("Разбиваем группу участников на определенное количество команд");
        }
        if (playersOnTeam != null && playersOnTeam != 0 && (teams == null || teams == 0)) {
            action.setPlayersOnTeam(playersOnTeam);
            action.setActionName("Player");
            action.setDescription("Разбиваем группу участников на команды с определенным " +
                    "количеством игроков в каждой команде");
        }
        if ((playersOnTeam == null || playersOnTeam == 0) && (teams == null || teams == 0)) {
            action.setActionName("Santa");
            action.setDescription("Каждый игрок в паре с другим игором, пары не повторяются");
        }
        action.setEvent(event);
        event.setAction(action);
        return eventRepo.save(event);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean getBoolResultAction(Event event) {
        return !playActionRepo.findAllPlayActionsByAction(event.getAction()).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Event startAction(long eventId) {
        log.info("startAction method");
        Event event = eventRepo.getOne(eventId);
        Action action = event.getAction();
        if (action != null) {
            Set<PlayAction> playActionSet = new HashSet<>(playActionRepo.findAllPlayActionsByAction(action));
            for (PlayAction playAction : playActionSet) {
                playActionRepo.delete(playAction);
            }
            playActionSet.clear();
            Set<User> users = event.getUsers();

            if (!users.isEmpty()) {
                //если нет заданного количества команд или количества игроков в командах, то это цепь (лига, санта)
                log.info("get teams = " + action.getTeams());
                log.info("get getPlayersOnTeam = " + action.getPlayersOnTeam());
                if (action.getTeams() == 0 && action.getPlayersOnTeam() == 0) {
                    playActionSet = santaRealization(event.getAction(), users);
                } else {
                    playActionSet = actionRealization(event.getAction(), users);
                }

                action.setPlayActions(playActionSet);
                event.setAction(action);
                action.setEvent(event);
                action = actionRepo.save(action);
                event = eventRepo.save(event);
            }
            Set<PlayAction> playActions = new HashSet<>(playActionRepo.findAllPlayActionsByAction(action));
            Set<User> userSet = new HashSet<>(userRepo.findAllByEvents(event));
            event.setUsers(userSet);
            action.setPlayActions(playActions);
            event.setAction(action);
        }
        return event;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Set<PlayAction> santaRealization(Action action, Set<User> userSet) {

        List<Team> teams = new ArrayList<>();
        List<User> users = new ArrayList<>(userSet);
        Collections.shuffle(users);
        Set<PlayAction> playActionSet = new HashSet<>();

        for (User user : users) {
            Team team = new Team();
            team.setTeamName(user.getUsername());
            team.setUsers(new HashSet<>(Collections.singletonList(user)));
            teams.add(team);
        }

        return getPlayActions(action, teams, playActionSet);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Set<PlayAction> actionRealization(Action action, Set<User> userSet) {
        List<Team> teams = new ArrayList<>();
        List<User> users = new ArrayList<>(userSet);
        Collections.shuffle(users);
        Set<PlayAction> playActionSet = new HashSet<>();
        int countTeams = action.getTeams();
        int countPlayers = action.getPlayersOnTeam();

        if (countTeams == 0) {
            countTeams = users.size() / countPlayers;
        } else {
            countPlayers = users.size() / countTeams;
        }


        for (int i = 0; i < countTeams; i++) {
            List<User> userTeam = new ArrayList<>(users.subList(0, countPlayers));
            Team team = new Team();
            team.setUsers(new HashSet<>(userTeam));
            team.setTeamName("Team " + userTeam.get(0).getUsername());
            teams.add(team);
            users.removeAll(userTeam);
        }

        for (int i = 0; i < users.size(); i++) {
            teams.get(i).getUsers().add(users.get(i));
        }

        return getPlayActions(action, teams, playActionSet);
    }

    private Set<PlayAction> getPlayActions(Action action, List<Team> teams, Set<PlayAction> playActionSet) {
        PlayAction playActionfirstLast = new PlayAction();
        playActionfirstLast.setMaster(teams.get(teams.size() - 1));
        playActionfirstLast.setSlave(teams.get(0));
        playActionfirstLast.setAction(action);

        playActionSet.add(playActionfirstLast);
        for (int i = 1; i <= teams.size(); i++) {
            PlayAction playAction = new PlayAction();
            if (i != teams.size()) {
                playAction.setMaster(teams.get(i - 1));
                playAction.setSlave(teams.get(i));
                playActionSet.add(playAction);
                playAction.setAction(action);
            }
        }

        return playActionSet;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    // каждую ночь: cron = "0 0 0 * * ?",  один раз в 2 минуты: cron = "0 */2 * ? * *"
    // для тестов fixedRate = 60000, initialDelay = 180000
    public void checkEventExpire() {
        log.info("Start scheduled");
        List<Event> events = eventRepo.findEventsByEventTossDate(LocalDate.now());
        for (Event event : events) {
            Action action = event.getAction();
            if (action != null) {
                List<PlayAction> playActions = playActionRepo.findAllPlayActionsByAction(action);
                if (playActions == null || playActions.isEmpty()) {
                    startAction(event.getEventId());
                }
            }
        }
    }


    /**
     * Метод исключительно для тестирования!!!! Удалить после всех тестов.
     *
     * @param countUser
     * @param event
     * @return
     */
    @Override
    public Set<User> createUserList(int countUser, Event event) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < countUser; i++) {
            users.add(i, new User("user" + i, "123" + i, true, "hjf@jgj" + i + ".com", "+711111111" + i,
                    null, new HashSet<>(Collections.singletonList(Role.USER))));
        }
        return new HashSet<>(users);
    }


}
