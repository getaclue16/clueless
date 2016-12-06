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

    /**
     * Start a game.
     *
     * @param gameId
     *            the id of the game to start
     * @param username
     *            the user requesting to start the game
     * @return the game that has been started
     * @throws GameNotFoundException
     *             no game with the given id exists
     * @throws InvalidGameStateException
     *             the game could not be started
     */
    Game startGame(long gameId, String username)
            throws GameNotFoundException, InvalidGameStateException;

    /**
     * Retrieve a game.
     *
     * @param gameId
     *            the id of the game to retrieve
     * @param username
     *            the user requesting the game
     * @return the game
     * @throws GameNotFoundException
     *             no game with the given id exists
     * @throws InvalidGameStateException
     *             this player is not a member of this game
     */
    Game getGame(long gameId, String username)
            throws GameNotFoundException, InvalidGameStateException;

}
