package getaclue.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * The solution to the game.
 */
@Embeddable
public final class Solution {

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

    /**
     * Generate a random solution.
     *
     * @return a random solution
     */
    public static Solution generateSolution() {
        List<Guest> guests = Arrays.asList(Guest.values());
        List<Weapon> weapons = Arrays.asList(Weapon.values());
        List<Room> rooms = Arrays.asList(Room.values());

        Collections.shuffle(guests);
        Collections.shuffle(weapons);
        Collections.shuffle(rooms);

        Solution solution = new Solution(guests.get(0), weapons.get(0), rooms.get(0));
        return solution;
    }

}
