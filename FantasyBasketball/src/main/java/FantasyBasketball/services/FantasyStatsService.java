package FantasyBasketball.services;

import FantasyBasketball.exceptions.ResourceException;
import FantasyBasketball.exceptions.ResourceNotFoundException;
import FantasyBasketball.models.FantasyStats;
import FantasyBasketball.repositories.fantasyStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FantasyStatsService {

    @Autowired
    fantasyStatsRepository statsRepo;

    // find by stats_id
    public List<FantasyStats> getByID(Integer statsID) throws ResourceNotFoundException {
        Optional<FantasyStats> result = statsRepo.findById(statsID);
        if (result.isPresent()) {
            FantasyStats statsResult = result.get();
            return List.of(statsResult);
        } else {
            throw new ResourceNotFoundException("Fantasy Stats couldn't be found by ID in DB");
        }
    }

    // get operation
    public List<FantasyStats> getStatsByTemplate(
            Integer stats_id,
            Integer player_id,
            Integer schedule_id,
            Integer client_id,
            Integer league_id,
            Integer threeP,
            Integer twoP,
            Integer freeThrows,
            Integer rebounds,
            Integer assists,
            Integer blocks,
            Integer steals,
            Integer turnovers,
            Integer totPoints) {
        return statsRepo.findByTemplate(
                stats_id,
                player_id,
                schedule_id,
                client_id,
                league_id,
                threeP,
                twoP,
                freeThrows,
                rebounds,
                assists,
                blocks,
                steals,
                turnovers,
                totPoints);
    }

    // delete operations
    public List<FantasyStats> deleteStats(Integer player_id, Integer schedule_id, Integer league_id, Integer client_id) throws ResourceNotFoundException, ResourceException {
        if (player_id == null && schedule_id == null && league_id == null){
            throw new ResourceException("Provide at least player_id, schedule_id, or league_id");
        }
        List<FantasyStats> stats_to_delete = statsRepo.findByIDs(player_id, schedule_id, league_id, client_id);
        if (stats_to_delete.size() == 0) {
            throw new ResourceNotFoundException("Fantasy Stats not found in DB, cannot delete");
        }

        for (FantasyStats stat_to_delete:stats_to_delete) {
            deleteStatsByID(stat_to_delete.getStats_id());
        }

        return stats_to_delete;
    }
    // delete stats using stat_id
    public void deleteStatsByID(Integer stats_id) throws ResourceNotFoundException {
        if(statsRepo.existsById(stats_id)) {
            statsRepo.deleteById(stats_id);
        } else {
            throw new ResourceNotFoundException("Fantasy Stats not found in DB, cannot delete");
        }
    }

    // Post operation
    public List<FantasyStats> postStats(FantasyStats stats) {
        stats.setStats_id(0);
        FantasyStats result = statsRepo.save(stats);

        return List.of(result);
    }
    // Post operation using fantasystat list
    public List<FantasyStats> postStatSheet(List<FantasyStats> statSheet) {
        return (List<FantasyStats>) statsRepo.saveAll(statSheet);
    }

    // Put Operation
    public List<FantasyStats> updateStats(FantasyStats stats) throws ResourceNotFoundException {
        if(statsRepo.existsById(stats.getStats_id())) {
            FantasyStats result = statsRepo.save(stats);
            return List.of(result);
        } else {
            throw new ResourceNotFoundException("Fantasy Stats not found in DB. Cannot update");
        }
    }
    // Checking stats_id is valid
    public void checkPostInputs(FantasyStats stats) throws ResourceException {
        if (stats.getStats_id() != null) {
            throw new ResourceException("Do not provide stats_id.");
        }
    }

    public List<FantasyStats> getStatsBySchedule(Set<Integer> ScheduleList){
        return statsRepo.findFantasyStatsByScheduleList(ScheduleList);
    }

    public HashMap<Integer, Integer> generatePlayerStat(Set<Integer> gameList) {

        List<FantasyStats> playerStats = statsRepo.findFantasyStatsByScheduleList(gameList);

        HashMap<Integer, Integer> playerStatsMap = new HashMap<>();
        for (FantasyStats stat: playerStats) {
            playerStatsMap.put(stat.getPlayer_id(), stat.getTot_points());
        }
        return playerStatsMap;

    }

}
