package getaclue.domain;

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
