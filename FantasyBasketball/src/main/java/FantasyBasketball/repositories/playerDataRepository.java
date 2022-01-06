package FantasyBasketball.repositories;

import FantasyBasketball.models.FantasyPlayer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
    CrudRepository already has the following functions:
        (in the form): function_name(parameter:parameter_type):return_type
        save(entity:S):S
        saveAll(entities:Iterable<S>):Iterable<S>
        findById(id:ID):Optional<T>
        existsById(id:ID):boolean
        findAll():Iterable<T>
        findAllById(ids:Iterable<ID>)Iterable<T>
        count():long
        deleteById(idLID):void
        delete(entity:T):void
        deleteAllById(ids:Iterable<? extends ID>):void
        deleteAll(entities:Iterable<? extends T>):void
        deleteAll():void

    All functions below extend the CrudRepository
 */

@Repository
public interface playerDataRepository extends CrudRepository<FantasyPlayer, Integer> {

    @Query(value = "select * from fantasy_player where ((:player_id is NULL or player_id = :player_id) and\n" +
            "                          (:position is NULL or position LIKE %:position%) and\n" +
            "                          (:first_name is NULL or first_name LIKE %:first_name%) and\n" +
            "                          (:last_name is NULL or last_name LIKE %:last_name%) and\n" +
            "                          (:nba_team is NULL or nba_team LIKE %:nba_team% ) and\n" +
            "                          (:ball_api_id is NULL or ball_api_id=:ball_api_id))", nativeQuery = true)
    List<FantasyPlayer> findByTemplate(@Param("player_id") Integer player_id,
                                       @Param("position") String position,
                                       @Param("first_name") String first_name,
                                       @Param("last_name") String last_name,
                                       @Param("nba_team") String nba_team,
                                       @Param("ball_api_id") Integer ball_api_id);

    @Transactional
    @Modifying
    @Query(value = "insert into player_data values(:player_id, :first_name, :last_name, :position, :nba_team, :ball_api_id, :total_points)",
            nativeQuery = true)
    void insertDbPlayer(@Param("player_id") Integer player_id,
                             @Param("position") String position,
                             @Param("first_name") String first_name,
                             @Param("last_name") String last_name,
                             @Param("nba_team") String nba_team,
                             @Param("ball_api_id") Integer ball_api_id,
                             @Param("total_points") Integer total_points);

}