package getaclue.domain;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * An action taken during a turn in the game.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Action extends AbstractPersistable<Long> {

    private static final long serialVersionUID = -3260240631142109482L;

    @ManyToOne
    private Turn turn;

    @NotNull
    private ZonedDateTime timestamp;

    /**
     * Create an action.
     */
    public Action() {
    }

    /**
     * Create an action with the specified timestamp.
     *
     * @param timestamp
     *            the date and time this action occurred
     */
    public Action(final ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
    }

    /**
     * @return the turn
     */
    public final Turn getTurn() {
        return turn;
    }

    /**
     * @param turn
     *            the turn to set
     */
    public final void setTurn(final Turn turn) {
        this.turn = turn;
    }

    /**
     * @return the timestamp
     */
    public final ZonedDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     *            the timestamp to set
     */
    public final void setTimestamp(final ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
