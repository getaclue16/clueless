package getaclue.service;

import java.util.Collection;

import getaclue.domain.Game;

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

}
