package FantasyBasketball.controllers;

import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.services.FantasyPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static FantasyBasketball.controllers.ControllerUtils.*;

@Controller
public class FantasyPlayerController {

    @Autowired
    FantasyPlayerService fantasyPlayerService;

    private static final Logger log = LoggerFactory.getLogger(FantasyPlayerController.class);

    private final HttpServletRequest request;

    @Autowired
    public FantasyPlayerController(HttpServletRequest request) { this.request = request; }


    @RequestMapping(value = "/fantasyPlayers", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayersByTemplate(@RequestParam(value = "player_id", required = false) Integer player_id,
                                                  @RequestParam(value = "team_id", required = false) Integer team_id,
                                                  @RequestParam(value = "position", required = false) String position,
                                                  @RequestParam(value = "first_name", required = false) String first_name,
                                                  @RequestParam(value = "last_name", required = false) String last_name,
                                                  @RequestParam(value = "nba_team", required = false) String nba_team,
                                                  @RequestParam(value = "league_id", required = false) Integer league_id,
                                                  @RequestParam(value = "ball_api_id", required = false) Integer ball_api_id) {

        try {

            // log GET request and set client id
            logGetRequest(request, log);
            Integer client_id = getClientId(request);

            // get by template given by request
            List<FantasyPlayer> result = fantasyPlayerService.getPlayerByTemplate(player_id,
                    client_id,
                    team_id,
                    position,
                    first_name,
                    last_name,
                    nba_team,
                    league_id,
                    ball_api_id);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception on GET: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //    Added Update Players
    //    Not sure if this function is needed for implementation
    //


    // create fantasy player disabled, should be triggered through league creation
//    @RequestMapping(value = "/fantasyPlayers", method = RequestMethod.POST)
//    public ResponseEntity<?> createPlayers(@RequestBody FantasyPlayer player) {
//        try {
//
//            // log post request and get client id
//            logPostRequest(request, log, player.toString());
//            Integer client_id = getClientId(request);
//
//            // Set player client id and do regular post
//            player.setClientID(client_id);
//            List<FantasyPlayer> result = FantasyPlayerService.postFantasyPlayer(player);
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (DataIntegrityViolationException e) {
//            log.error("Exception on POST: ", e);
//            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
//        } catch (Exception e) {
//            // all other exceptions
//            log.error("Exception on POST: ", e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    // put method disabled, players should only need team_id updated
    //          we can do this through /fantasyLeague/draft/draftPlayers
//    @RequestMapping(value = "/fantasyPlayers", method = RequestMethod.PUT)
//    public ResponseEntity<?> updatePlayers(@RequestBody FantasyPlayer player) {
//        try {
//
//            // log put request and get client id
//            logPutRequest(request, log, player.toString());
//            Integer client_id = getClientId(request);
//
//            // Regular put
//            player.setClientID(client_id);
//            List<FantasyPlayer> result = FantasyPlayerService.updateFantasyPlayer(player);
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (DataIntegrityViolationException e) {
//            log.error("Exception on PUT: " + e.getMessage());
//            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
//        } catch (Exception e) {
//            // other exceptions
//            log.error("Exception on PUT: " + e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    // deletion of players should not be handled by clients, we will delete through league deletion,
    //      league will cascade on delete all fantasyPlayers
//    @RequestMapping(value = "/fantasyPlayers", method = RequestMethod.DELETE)
//    public ResponseEntity<?> deletePlayer(@RequestParam(value = "player_id") Integer player_id,
//                                          @RequestParam(value = "client_id") Integer client_id,
//                                          @RequestParam(value = "league_id") Integer league_id) {
//
//        try {
//            log.info("DELETE: " + request.getRequestURL() + "?" + request.getQueryString());
//
//            FantasyPlayerService.deleteFantasyPlayer(player_id, client_id, league_id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (ResourceNotFoundException e) {
//            log.error("Exception on DELETE: ", e);
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            log.error("Exception on DELETE: ", e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
}
