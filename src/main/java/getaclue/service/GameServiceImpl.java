package getaclue.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import getaclue.dao.GameRepository;
import getaclue.domain.Game;
import getaclue.domain.Game.State;
import getaclue.domain.Player;
import getaclue.domain.Solution;

/**
 * Implementation of the game service.
 */
@Service
public final class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    /** {@inheritDoc} */
    public Game newGame(final String gameName, final String creator) {
        Game game = new Game();
        game.setName(gameName);
        game.setSolution(Solution.generateSolution());

        Player player = new Player(creator);
        game.setPlayers(Collections.singletonList(player));

        gameRepository.save(game);

        return game;
    }

    /** {@inheritDoc} */
    public Collection<Game> getNewGames() {
        return gameRepository.findByState(State.NEW);
    }

}
