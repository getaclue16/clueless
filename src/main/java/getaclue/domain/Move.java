package getaclue.domain;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * An action where a player moved from one location on the game board to
 * another.
 */
@Entity
public final class Move extends Action {

    private static final long serialVersionUID = 7091907635396218367L;

    @NotNull
    private Location fromLocation;
    @NotNull
    private Location toLocation;

    /**
     * Default constructor.
     */
    public Move() {
    }

    /**
     * @param timestamp
     *            the date and time this action occurred
     * @param fromLocation
     *            the location where the player moved from
     * @param toLocation
     *            the location where the player moved to
     */
    public Move(final ZonedDateTime timestamp, final Location fromLocation,
            final Location toLocation) {
        super(timestamp);
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    /**
     * @return the fromLocation
     */
    public Location getFromLocation() {
        return fromLocation;
    }

    /**
     * @param fromLocation
     *            the fromLocation to set
     */
    public void setFromLocation(final Location fromLocation) {
        this.fromLocation = fromLocation;
    }

    /**
     * @return the toLocation
     */
    public Location getToLocation() {
        return toLocation;
    }

    /**
     * @param toLocation
     *            the toLocation to set
     */
    public void setToLocation(final Location toLocation) {
        this.toLocation = toLocation;
    }

}
