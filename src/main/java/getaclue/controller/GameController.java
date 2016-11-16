package getaclue.controller;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import getaclue.domain.Game;
import getaclue.service.GameService;

/**
 * REST controller for game actions.
 */
@RestController
@RequestMapping("/game")
public final class GameController {

    @Autowired
    private GameService gameService;

    /**
     * Create a new game.
     *
     * @param name
     *            the name of the new game (optional)
     * @param principal
     *            the user who requested to create the game
     * @return the new game
     */
    @RequestMapping("/create")
    public Game createGame(@RequestParam(value = "name", required = false) final String name,
            final Principal principal) {
        Game game = gameService.newGame(name, principal.getName());
        return game;
    }

    /**
     * Get a list of all open games.
     *
     * @return a list of all open games
     */
    @RequestMapping("/open")
    public Collection<Game> openGames() {
        return gameService.getNewGames();
    }

}
