package getaclue.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * A location on the game board.
 */
@Entity
public final class Location extends PersistableGameObject {

    private static final long serialVersionUID = -8218819142266632293L;

    @NotNull
    private int x;
    @NotNull
    private int y;

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

}
