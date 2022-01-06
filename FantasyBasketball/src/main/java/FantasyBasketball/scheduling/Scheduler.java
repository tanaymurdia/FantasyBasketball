package FantasyBasketball.scheduling;

import FantasyBasketball.exceptions.ResourceNotFoundException;
import FantasyBasketball.repositories.clientRepository;
import FantasyBasketball.repositories.fantasyGameRepository;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.repositories.fantasyStatsRepository;
import FantasyBasketball.services.FantasyGameService;
import FantasyBasketball.services.FantasyStatsService;
import FantasyBasketball.services.FantasyTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Scheduler {


    @Autowired
    FantasyTeamService teamService;

    @Autowired
    FantasyGameService gameService;

    @Autowired
    fantasyGameRepository gameRepo;

    @Autowired
    FantasyStatsService statService;

    @Autowired
    fantasyPlayerRepository playerRepo;

    @Autowired
    fantasyStatsRepository statsRepo;

    @Autowired
    clientRepository clientRepo;


    @Scheduled(cron = "*/10 * * * * *")
    @Async
    //@Scheduled(cron = "0 0 */1 * * *")
    public void hourlySchedule() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        System.out.println("Java cron job expression:: " + strDate);

        System.out.println("\t\t\tRun Daily function being called.");
        HourlyUpdate hourly = new HourlyUpdate();
        try {
            hourly.runHourly(playerRepo, gameRepo, statsRepo, clientRepo, now);
        } catch (ResourceNotFoundException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    //@Scheduled(cron = "*/10 * * * * *")
    @Scheduled(cron = "0 0 0 * * SUN")
    @Async
    public void weeklySchedule() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        System.out.println("\t\t\tJava cron job expression:: " + strDate);

        System.out.println("\t\t\tRun Weekly function being called.");
        WeeklyUpdate weekly = new WeeklyUpdate();
        try {
            weekly.runWeekly(teamService, gameService, statService);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }
}
