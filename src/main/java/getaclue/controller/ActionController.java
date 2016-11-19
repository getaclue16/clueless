package getaclue.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import getaclue.domain.Location;
import getaclue.service.ActionService;
import getaclue.service.GameNotFoundException;
import getaclue.service.InvalidGameStateException;

/**
 * REST controller for in-game actions.
 */
@RestController
@RequestMapping("/game/action")
public final class ActionController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ActionService actionService;

    /**
     * Move to a new location on the game board.
     *
     * @param gameId
     *            the id of the game
     * @param moveto
     *            the location to move to
     * @param principal
     *            the user making this request
     * @return a status message
     */
    @RequestMapping("/move")
    public ResponseEntity<String> move(@RequestParam("gameid") final long gameId,
            final Location moveto, final Principal principal) {
        try {
            actionService.move(gameId, moveto, principal.getName());
        } catch (GameNotFoundException e) {
            log.error("User tried to perform move action against invalid game id", e);
            return new ResponseEntity<>("Invalid game id", HttpStatus.BAD_REQUEST);
        } catch (InvalidGameStateException e) {
            log.warn("User could not perform move", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
