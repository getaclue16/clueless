package getaclue.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * A card in the game.
 */
@Entity
public final class Card extends PersistableGameObject {
    private static final long serialVersionUID = -2305395887705384349L;

    private static final List<Card> PROTO_DECK = new ArrayList<>();

    // Initialize the prototype deck
    static {
        for (Guest guest : Guest.values()) {
            PROTO_DECK.add(new Card(guest.getName()));
        }
        for (Room room : Room.values()) {
            PROTO_DECK.add(new Card(room.getName()));
        }
        for (Weapon weapon : Weapon.values()) {
            PROTO_DECK.add(new Card(weapon.getName()));
        }
    }

    /**
     * Get a new deck of cards.
     *
     * @return a list of all cards
     */
    public static ArrayList<Card> newDeck() {
        return new ArrayList<Card>(PROTO_DECK);
    }

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
