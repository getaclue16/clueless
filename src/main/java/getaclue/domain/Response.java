package getaclue.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * A response to a guess.
 */
@Entity
public final class Response extends Action {

    private static final long serialVersionUID = 4769697783164392715L;

    @NotNull
    private Player player;
    private Card cardShown;

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player
     *            the player to set
     */
    public void setPlayer(final Player player) {
        this.player = player;
    }

    /**
     * @return the cardShown
     */
    public Card getCardShown() {
        return cardShown;
    }

    /**
     * @param cardShown
     *            the cardShown to set
     */
    public void setCardShown(final Card cardShown) {
        this.cardShown = cardShown;
    }

}