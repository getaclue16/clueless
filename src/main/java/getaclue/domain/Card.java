package getaclue.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * A card in the game.
 */
@Entity
public final class Card extends PersistableGameObject {

    private static final long serialVersionUID = -2305395887705384349L;

    @NotNull
    private String name;

    /**
     * Create a card.
     */
    public Card() {
    }

    /**
     * Create a card.
     *
     * @param name
     *            the name of the card
     */
    public Card(final String name) {
        this.setName(name);
    }

    /**
     * Get the name of the card.
     *
     * @return the name of the card
     */
    String getName() {
        return name;
    }

    /**
     * Set the name of the card.
     *
     * @param name
     *            the name of the card
     */
    public void setName(final String name) {
        this.name = name;
    }

}
