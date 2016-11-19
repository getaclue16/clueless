package getaclue.service;

/**
 * Exception thrown when the user requests an action against a game that is in
 * the wrong state. For example, the user tries to join a game that is already
 * in progress.
 */
public final class InvalidGameStateException extends Exception {
    private static final long serialVersionUID = -3341631815800097440L;

    /**
     * Construct a new exception with the given message.
     *
     * @param message
     *            the message
     */
    public InvalidGameStateException(final String message) {
        super(message);
    }
}
