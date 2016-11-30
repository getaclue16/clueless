package getaclue.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Used to display guests that are not assigned to any player.
 */
@Entity
public final class NonPlayer extends PersistableGameObject {

    private static final long serialVersionUID = -7307940723887053422L;

    @NotNull
    private Guest guest;
    private Location currentLocation;

    /**
     * @return the guest
     */
    public Guest getGuest() {
        return guest;
    }

    /**
     * Set the guest. This also sets the non-player's location as the guest's
     * assigned starting location.
     *
     * @param guest
     *            the guest to set
     */
    public void setGuest(final Guest guest) {
        this.guest = guest;
        if (guest != null) {
            this.currentLocation = guest.getStartLocation();
        }
    }

    /**
     * @return the currentLocation
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * @param currentLocation
     *            the currentLocation to set
     */
    public void setCurrentLocation(final Location currentLocation) {
        this.currentLocation = currentLocation;
    }

}
