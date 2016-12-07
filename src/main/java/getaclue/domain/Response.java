package getaclue.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A response to a guess.
 */
@Entity
public final class Response extends Action {

    private static final long serialVersionUID = 4769697783164392715L;

    @ManyToOne
    @JoinColumn
    private Player player;
    @Embedded
    @JsonIgnore
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

    @Override
    public String getDescription() {
        if (cardShown == null) {
            return String.format("%s did not show any cards", player.getGuest().getName());
        }
        return String.format("%s showed a card", player.getGuest().getName());
    }

}
