package getaclue.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * The solution to the game.
 */
@Entity
public final class Solution extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 1396957259572492576L;

    @NotNull
    private Guest guest;
    @NotNull
    private Weapon weapon;
    @NotNull
    private Room room;

    /**
     * Create a solution.
     */
    public Solution() {
    }

    /**
     * Create a Solution.
     *
     * @param guest
     *            the Guest
     * @param weapon
     *            the Weapon
     * @param room
     *            the Room
     */
    public Solution(final Guest guest, final Weapon weapon, final Room room) {
        this.guest = guest;
        this.weapon = weapon;
        this.room = room;
    }

    /**
     * Get the guest.
     *
     * @return the guest
     */
    public Guest getGuest() {
        return guest;
    }

    /**
     * Set the guest.
     *
     * @param guest
     *            the guest to set
     */
    public void setGuest(final Guest guest) {
        this.guest = guest;
    }

    /**
     * Get the weapon.
     *
     * @return the weapon
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Set the weapon.
     *
     * @param weapon
     *            the weapon to set
     */
    public void setWeapon(final Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Get the room.
     *
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Set the room.
     *
     * @param room
     *            the room to set
     */
    public void setRoom(final Room room) {
        this.room = room;
    }

}
