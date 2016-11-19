package getaclue.service;

import getaclue.domain.Location;

/**
 * Service for managing in-game actions.
 */
public interface ActionService {

    /**
     * Record a move action.
     *
     * @param gameId
     *            the id of the game
     * @param moveto
     *            the location being moved to
     * @param username
     *            the user requesting the action
     * @throws GameNotFoundException
     *             no game with the given id was found
     * @throws InvalidGameStateException
     *             the request was invalid
     */
    void move(long gameId, Location moveto, String username)
            throws GameNotFoundException, InvalidGameStateException;

}
