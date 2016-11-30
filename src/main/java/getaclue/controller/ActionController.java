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

import getaclue.domain.Guest;
import getaclue.domain.Location;
import getaclue.domain.Room;
import getaclue.domain.Weapon;
import getaclue.service.ActionService;
import getaclue.service.GameNotFoundException;
import getaclue.service.InvalidGameStateException;
import getaclue.service.ParameterException;

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

    /**
     * Make a guess during your turn.
     *
     * @param gameId
     *            the id of the game
     * @param guest
     *            the guest being guessed
     * @param weapon
     *            the weapon being guessed
     * @param principal
     *            the user making this request
     * @return a status message
     */
    @RequestMapping("/guess")
    public ResponseEntity<String> guess(@RequestParam("gameid") final long gameId,
            @RequestParam("guest") final Guest guest, @RequestParam("weapon") final Weapon weapon,
            final Principal principal) {
        try {
            actionService.guess(gameId, guest, weapon, principal.getName());
        } catch (GameNotFoundException e) {
            log.error("User tried to make a guess against invalid game id", e);
            return new ResponseEntity<>("Invalid game id", HttpStatus.BAD_REQUEST);
        } catch (InvalidGameStateException e) {
            log.warn("User could not perform guess", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * Respond to another player's guess.
     *
     * @param gameId
     *            the id of the game
     * @param response
     *            the response <br/>
     *            0 for no match <br/>
     *            1 to show the guest card <br/>
     *            2 to show the weapon card <br/>
     *            3 to show the room card
     * @param principal
     *            the user making this request
     * @return a status message
     */
    @RequestMapping("/respond")
    public ResponseEntity<String> respond(@RequestParam("gameid") final long gameId,
            @RequestParam final int response, final Principal principal) {
        try {
            actionService.respond(gameId, response, principal.getName());
        } catch (GameNotFoundException e) {
            log.error("User tried to respond against invalid game id", e);
            return new ResponseEntity<>("Invalid game id", HttpStatus.BAD_REQUEST);
        } catch (InvalidGameStateException e) {
            log.warn("User could not respond to guess", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ParameterException e) {
            log.warn("User provided an invalid parameter value", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * Make an accusation during your turn.
     *
     * @param gameId
     *            the id of the game
     * @param guest
     *            the guest being guessed
     * @param weapon
     *            the weapon being guessed
     * @param room
     *            the room being guessed
     * @param principal
     *            the user making this request
     * @return a status message
     */
    @RequestMapping("/accuse")
    public ResponseEntity<String> accuse(@RequestParam("gameid") final long gameId,
            @RequestParam("guest") final Guest guest, @RequestParam("weapon") final Weapon weapon,
            @RequestParam("room") final Room room, final Principal principal) {
        try {
            actionService.accuse(gameId, guest, weapon, room, principal.getName());
        } catch (GameNotFoundException e) {
            log.error("User tried to make an accusation against invalid game id", e);
            return new ResponseEntity<>("Invalid game id", HttpStatus.BAD_REQUEST);
        } catch (InvalidGameStateException e) {
            log.warn("User could not perform accusation", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * End your turn.
     *
     * @param gameId
     *            the id of the game
     * @param principal
     *            the user making this request
     * @return a status message
     */
    @RequestMapping("/endturn")
    public ResponseEntity<String> endturn(@RequestParam("gameid") final long gameId,
            final Principal principal) {
        try {
            actionService.endturn(gameId, principal.getName());
        } catch (GameNotFoundException e) {
            log.error("User tried to end turn against invalid game id", e);
            return new ResponseEntity<>("Invalid game id", HttpStatus.BAD_REQUEST);
        } catch (InvalidGameStateException e) {
            log.warn("User could not end turn", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
