package getaclue.domain;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * A user's guess of the solution.
 */
@Entity
public final class Guess extends Action {

    private static final long serialVersionUID = -8501372924755248933L;

    @NotNull
    private Solution solution;

    /**
     * Create a guess.
     */
    public Guess() {
    }

    /**
     * Create a guess.
     *
     * @param timestamp
     *            the date and time this action occurred
     * @param solution
     *            the solution being guessed
     */
    public Guess(final ZonedDateTime timestamp, final Solution solution) {
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
        return String.format("Made a suggestion: %s", solution.toString());
    }

}
