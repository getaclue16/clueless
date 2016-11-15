package getaclue.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import getaclue.dao.GameRepository;
import getaclue.domain.Game;
import getaclue.domain.Game.State;
import getaclue.domain.Solution;

/**
 * REST controller for game actions.
 */
@RestController
@RequestMapping("/game")
public final class GameController {

    @Autowired
    private GameRepository gameRepository;

    /**
     * Create a new game.
     *
     * @param name
     *            the name of the new game (optional)
     * @return the new game
     */
    @RequestMapping("/create")
    public Game createGame(@RequestParam(value = "name", required = false) final String name) {
        Game game = new Game();
        game.setName(name);
        game.setSolution(Solution.generateSolution());
        gameRepository.save(game);
        return game;
    }

    /**
     * Get a list of all open games.
     *
     * @return a list of all open games
     */
    @RequestMapping("/open")
    public Collection<Game> openGames() {
        return gameRepository.findByState(State.NEW);
    }

}
