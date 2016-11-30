package getaclue.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A location on the game board.
 */
@Entity
public final class Location extends PersistableGameObject {

    private static final long serialVersionUID = -8218819142266632293L;

    /**
     * The maximum value for x or y.
     */
    public static final int GRID_MAX = 4;

    private static final int GRID_DIAG = GRID_MAX * GRID_MAX * 2;

    @NotNull
    private int x = -1;
    @NotNull
    private int y = -1;

    /**
     * Create a location.
     */
    public Location() {
    }

    /**
     * Create a location.
     *
     * @param x
     *            the x value of the location
     * @param y
     *            the y value of the location
     */
    public Location(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x value of this location.
     *
     * @return the x value of this location
     */
    public int getX() {
        return x;
    }

    /**
     * Set the x value of this location.
     *
     * @param x
     *            the x value of this location
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * Get the y value of this location.
     *
     * @return the y value of this location
     */
    public int getY() {
        return y;
    }

    /**
     * Set the y value of this location.
     *
     * @param y
     *            the y value of this location
     */
    public void setY(final int y) {
        this.y = y;
    }

    /**
     * Check if it is valid to move from one location to another.
     *
     * @param from
     *            the location being moved from
     * @param to
     *            the location being moved to
     * @return true if this is a valid move, false in any other case
     */
    public static boolean isValidMove(final Location from, final Location to) {
        // Cannot move from/to nowhere
        if (from == null || to == null) {
            return false;
        }
        // Cannot move off the game board
        if (!to.isOnGameBoard()) {
            return false;
        }
        // Vertical move
        if (from.x == to.x) {
            return (Math.abs(from.y - to.y) == 1);
        }
        // Horizontal move
        if (from.y == to.y) {
            return (Math.abs(from.x - to.x) == 1);
        }
        // Shortcut
        if (from.isOnGameBoard()) {
            return (Math.pow(from.x - to.x, 2) + Math.pow(from.y - to.y, 2)) == GRID_DIAG;
        }
        return false;
    }

    /**
     * Check if this location is a valid location on the game board.
     *
     * @return true if this location is within the bounds of the game board
     */
    private boolean isOnGameBoard() {
        // Outside the boundary
        if (x < 0 || y < 0 || x > GRID_MAX || y > GRID_MAX) {
            return false;
        }
        // Voids between halls
        if (x % 2 == 1 && y % 2 == 1) {
            return false;
        }
        return true;
    }

    /**
     * Check if this location is a hallway.
     *
     * @return true if this location is a hallway
     */
    @JsonIgnore
    public boolean isHall() {
        if (!isOnGameBoard()) {
            return false;
        }
        return (x + y) % 2 == 1;
    }

    /**
     * Return the room that is at this location.
     *
     * @return the room at this location or null
     */
    public Room toRoom() {
        for (Room room : Room.values()) {
            if (room.getX() == x && room.getY() == y) {
                return room;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

    /**
     * Check if this location is the same as another location.
     *
     * @param other
     *            the location to compare to
     * @return true if the x and y coordinates of the two locations are the same
     */
    public boolean coincidesWith(final Location other) {
        if (other == null) {
            return false;
        }
        return other.x == x && other.y == y;
    }

}
