package getaclue.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the user requests an action against a game that doesn't
 * exist in the repository.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class GameNotFoundException extends Exception {
    private static final long serialVersionUID = 4739089592582336211L;

    /**
     * Construct a new exception.
     */
    public GameNotFoundException() {
    }

}
