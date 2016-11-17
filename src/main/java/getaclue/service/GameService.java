package getaclue.service;

import java.util.Collection;

import getaclue.domain.Game;
import getaclue.service.GameServiceImpl.GameNotFoundException;
import getaclue.service.GameServiceImpl.InvalidGameStateException;

/**
 * Service for managing games.
 */
public interface GameService {

    /**
     * Create a new game.
     *
     * @param gameName
     *            the name of the game (optional)
     * @param creator
     *            the creator of the game
     * @return the new game
     */
    Game newGame(String gameName, String creator);

    /**
     * Get a collection of all open games.
     *
     * @return a collection of all open games
     */
    Collection<Game> getNewGames();

    /**
     * Join an open game.
     *
     * @param gameId
     *            the id of the game to join
     * @param username
     *            the user requesting to join
     * @return the game that has been joined
     * @throws GameNotFoundException
     *             no game with the given id exists
     * @throws InvalidGameStateException
     *             the game is full or has already started
     */
    Game joinGame(long gameId, String username)
            throws GameNotFoundException, InvalidGameStateException;

}
