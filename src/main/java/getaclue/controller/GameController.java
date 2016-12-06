package getaclue.controller;

import java.security.Principal;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import getaclue.domain.Game;
import getaclue.service.GameNotFoundException;
import getaclue.service.GameService;
import getaclue.service.InvalidGameStateException;

/**
 * REST controller for game setup.
 */
@Controller
@RequestMapping("/game")
public final class GameController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GameService gameService;

    /**
     * Get the game board.
     *
     * @param gameId
     *            the id of the game to return
     * @param model
     *            the model
     * @param principal
     *            the user who requested the game
     * @return the base game board
     * @throws GameNotFoundException
     *             an invalid game id was provided
     * @throws InvalidGameStateException
     *             the player is not a member of this game
     */
    @GetMapping()
    public String gameBoard(@RequestParam(value = "gameid") final long gameId, final Model model,
            final Principal principal) throws GameNotFoundException, InvalidGameStateException {
        Game game = gameService.getGame(gameId, principal.getName());
        model.addAttribute("game", game);
        return "game";
    }

    /**
     * Create a new game.
     *
     * @param name
     *            the name of the new game
     * @param principal
     *            the user who requested to create the game
     * @return the new game
     */
    @PostMapping("/create")
    public ResponseEntity<Game> createGame(@RequestParam final String name,
            final Principal principal) {
        Game game = gameService.newGame(name, principal.getName());
        return new ResponseEntity<Game>(game, HttpStatus.CREATED);
    }

    /**
     * Get a list of all open games.
     *
     * @return a list of all open games
     */
    @GetMapping("/open")
    @ResponseBody
    public Collection<Game> openGames() {
        return gameService.getNewGames();
    }

    /**
     * Join an open game.
     *
     * @param gameId
     *            the id of the game to join
     * @param principal
     *            the principal of the user requesting to join
     * @return the game that has been joined or an error message if the action
     *         failed
     */
    @PostMapping("/join")
    public ResponseEntity<?> joinGame(@RequestParam(value = "gameid") final long gameId,
            final Principal principal) {
        Game game;
        try {
            game = gameService.joinGame(gameId, principal.getName());
        } catch (GameNotFoundException e) {
            log.error("User requested to join invalid game id", e);
            return new ResponseEntity<String>("Invalid game id", HttpStatus.BAD_REQUEST);
        } catch (InvalidGameStateException e) {
            log.warn("User could not join game", e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

    /**
     * Start a game.
     *
     * @param gameId
     *            the id of the game to start
     * @param principal
     *            the principal of the user requesting to start
     * @return the game that has been started or an error message if the action
     *         failed
     */
    @PostMapping("/start")
    public ResponseEntity<?> startGame(@RequestParam(value = "gameid") final long gameId,
            final Principal principal) {
        Game game;
        try {
            game = gameService.startGame(gameId, principal.getName());
        } catch (GameNotFoundException e) {
            log.error("User requested to start invalid game id", e);
            return new ResponseEntity<String>("Invalid game id", HttpStatus.BAD_REQUEST);
        } catch (InvalidGameStateException e) {
            log.warn("User could not start game", e);
            return new ResponseEntity<String>("Unable to start game", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

}
