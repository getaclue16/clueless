package getaclue.service;

import getaclue.domain.Guest;
import getaclue.domain.Location;
import getaclue.domain.Room;
import getaclue.domain.Weapon;

/**
 * Service for managing in-game actions.
 */
public interface ActionService {
    /**
     * Respond with the guest card.
     */
    int GUEST_RESPONSE = 1;
    /**
     * Respond with the weapon card.
     */
    int WEAPON_RESPONSE = 2;
    /**
     * Respond with the room card.
     */
    int ROOM_RESPONSE = 3;

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

    /**
     * Record a guess action.
     *
     * @param gameId
     *            the id of the game
     * @param guest
     *            the guest being guessed
     * @param weapon
     *            the weapon being guessed
     * @param username
     *            the user requesting the action
     * @throws GameNotFoundException
     *             no game with the given id was found
     * @throws InvalidGameStateException
     *             the request was invalid
     */
    void guess(long gameId, Guest guest, Weapon weapon, String username)
            throws GameNotFoundException, InvalidGameStateException;

    /**
     * Record a response to another user's guess.
     *
     * @param gameId
     *            the id of the game
     * @param response
     *            the response <br/>
     *            0 for no match <br/>
     *            1 to show the guest card <br/>
     *            2 to show the weapon card <br/>
     *            3 to show the room card
     * @param username
     *            the user making this response
     * @throws GameNotFoundException
     *             no game with the given id was found
     * @throws InvalidGameStateException
     *             the request was invalid
     * @throws ParameterException
     *             the request had an invalid parameter
     */
    void respond(long gameId, int response, String username)
            throws GameNotFoundException, InvalidGameStateException, ParameterException;

    /**
     * Record an accusation action.
     *
     * @param gameId
     *            the id of the game
     * @param guest
     *            the guest being guessed
     * @param weapon
     *            the weapon being guessed
     * @param room
     *            the room being guessed
     * @param username
     *            the user requesting the action
     * @return true if the accusation was correct, false otherwise
     * @throws GameNotFoundException
     *             no game with the given id was found
     * @throws InvalidGameStateException
     *             the request was invalid
     */
    boolean accuse(long gameId, Guest guest, Weapon weapon, Room room, String username)
            throws GameNotFoundException, InvalidGameStateException;

    /**
     * Record an end turn action.
     *
     * @param gameId
     *            the id of the game
     * @param username
     *            the user requesting the action
     * @throws GameNotFoundException
     *             no game with the given id was found
     * @throws InvalidGameStateException
     *             the request was invalid
     */
    void endturn(long gameId, String username)
            throws GameNotFoundException, InvalidGameStateException;

}
