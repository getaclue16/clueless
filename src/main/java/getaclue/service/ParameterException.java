package getaclue.service;

/**
 * Exception thrown when invalid parameter values are provided.
 */
public class ParameterException extends Exception {
    private static final long serialVersionUID = -1560568109909050878L;

    /**
     * Construct a new exception with the given message.
     *
     * @param message
     *            the message
     */
    public ParameterException(final String message) {
        super(message);
    }

}
