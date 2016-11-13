package getaclue.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * A player in a game.
 */
@Entity
public final class Player extends AbstractPersistable<Long> {

    private static final long serialVersionUID = -7546020484507317449L;

    @NotNull
    private String username;
    private Guest guest;
    @OneToMany
    private List<Card> cards;
    private Location currentLocation;
    private boolean forcedToMove = false;
    private boolean failedAccusation = false;

    /**
     * Create a player.
     */
    public Player() {
    }

    /**
     * Create a player.
     *
     * @param username
     *            the username of the player
     */
    public Player(final String username) {
        this.username = username;
    }

    /**
     * @return the guest
     */
    public Guest getGuest() {
        return guest;
    }

    /**
     * @param guest
     *            the guest to set
     */
    public void setGuest(final Guest guest) {
        this.guest = guest;
    }

    /**
     * @return the cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * @param cards
     *            the cards to set
     */
    public void setCards(final List<Card> cards) {
        this.cards = cards;
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

    /**
     * @return the forcedToMove
     */
    public boolean isForcedToMove() {
        return forcedToMove;
    }

    /**
     * @param forcedToMove
     *            the forcedToMove to set
     */
    public void setForcedToMove(final boolean forcedToMove) {
        this.forcedToMove = forcedToMove;
    }

    /**
     * @return the failedAccusation
     */
    public boolean isFailedAccusation() {
        return failedAccusation;
    }

    /**
     * @param failedAccusation
     *            the failedAccusation to set
     */
    public void setFailedAccusation(final boolean failedAccusation) {
        this.failedAccusation = failedAccusation;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

}
