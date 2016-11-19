package getaclue.service;

/**
 * Exception thrown when the user requests an action against a game that doesn't
 * exist in the repository.
 */
public final class GameNotFoundException extends Exception {
    private static final long serialVersionUID = 4739089592582336211L;

    /**
     * Construct a new exception.
     */
    public GameNotFoundException() {
    }

}
