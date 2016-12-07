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

    @Override
    public String getDescription() {
        // Moved from a room to a hall
        if (toLocation.isHall()) {
            Room room = fromLocation.toRoom();
            // The first turn will not start in a room
            if (room == null) {
                return "Moved from the starting location to a hallway";
            }
            return String.format("Moved from the %s to a hallway", room.getName());
        }
        // Moved from a hall to a room
        if (fromLocation.isHall()) {
            return String.format("Moved from a hallway to the %s", toLocation.toRoom().getName());
        }
        // Moved through a shortcut
        return String.format("Took a shortcut from the %s to the %s",
                fromLocation.toRoom().getName(), toLocation.toRoom().getName());
    }

}
