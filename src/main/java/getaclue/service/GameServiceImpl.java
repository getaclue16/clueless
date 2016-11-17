package getaclue.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import getaclue.dao.GameRepository;
import getaclue.domain.Game;
import getaclue.domain.Game.State;
import getaclue.domain.Player;
import getaclue.domain.Solution;

/**
 * Implementation of the game service.
 */
@Service
@SuppressWarnings("checkstyle:designforextension")
public class GameServiceImpl implements GameService {

    private static final int MAX_PLAYERS = 6;

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
        Collection<Game> games = gameRepository.findByState(State.NEW);

        // Only return games that are not full!
        Iterator<Game> iterator = games.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getPlayers().size() >= MAX_PLAYERS) {
                iterator.remove();
            }
        }

        return games;
    }

    /** {@inheritDoc} */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Game joinGame(final long gameId, final String username)
            throws GameNotFoundException, InvalidGameStateException {
        // Make sure the game exists
        Game game = gameRepository.findOne(gameId);
        if (game == null) {
            throw new GameNotFoundException("Game not found");
        }
        // Make sure the game has not started yet
        if (game.getState() != State.NEW) {
            throw new InvalidGameStateException("Game has already started");
        }
        // Make sure the game isn't already full
        if (game.getPlayers().size() >= MAX_PLAYERS) {
            throw new InvalidGameStateException("Game is full");
        }
        // Make sure the user isn't already in the game
        for (Player player : game.getPlayers()) {
            if (player.getUsername().equals(username)) {
                throw new InvalidGameStateException("User already joined game");
            }
        }
        game.getPlayers().add(new Player(username));
        gameRepository.save(game);
        return game;
    }

    /**
     * Exception thrown when the user requests an action against a game that
     * doesn't exist in the repository.
     */
    public final class GameNotFoundException extends Exception {
        /**
         * Construct a new exception with the given message.
         *
         * @param message
         *            the message
         */
        public GameNotFoundException(final String message) {
            super(message);
        }

        private static final long serialVersionUID = 6746861657499037448L;
    }

    /**
     * Exception thrown when the user requests an action against a game that is
     * in the wrong state. For example, the user tries to join a game that is
     * already in progress.
     */
    public final class InvalidGameStateException extends Exception {
        /**
         * Construct a new exception with the given message.
         *
         * @param message
         *            the message
         */
        public InvalidGameStateException(final String message) {
            super(message);
        }

        private static final long serialVersionUID = -3802312486434398731L;
    }

}
