package getaclue.domain;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * A user's accusation.
 */
@Entity
public final class Accuse extends Action {

    private static final long serialVersionUID = 125757148573193130L;

    @NotNull
    private Solution solution;

    /**
     * Create a guess.
     */
    public Accuse() {
    }

    /**
     * Create an accusation.
     *
     * @param timestamp
     *            the date and time this action occurred
     * @param solution
     *            the accused solution
     */
    public Accuse(final ZonedDateTime timestamp, final Solution solution) {
        super(timestamp);
        this.solution = solution;
    }

    /**
     * @return the solution
     */
    public Solution getSolution() {
        return solution;
    }

    /**
     * @param solution
     *            the solution to set
     */
    public void setSolution(final Solution solution) {
        this.solution = solution;
    }

    @Override
    public String getDescription() {
        return String.format("Made an accusation: %s", solution.toString());
    }

}
