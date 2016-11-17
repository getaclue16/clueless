package getaclue.domain;

/**
 * A weapon in the game.
 */
public enum Weapon {
    /**
     * The rope.
     */
    ROPE("rope"),
    /**
     * The lead pipe.
     */
    PIPE("lead pipe"),
    /**
     * The knife.
     */
    KNIFE("knife"),
    /**
     * The wrench.
     */
    WRENCH("wrench"),
    /**
     * The candlestick.
     */
    CANDLESTICK("candlestick"),
    /**
     * The revolver.
     */
    REVOLVER("revolver");

    private final String name;

    Weapon(final String name) {
        this.name = name;
    }

    /**
     * Get the name of the weapon.
     *
     * @return the name of the weapon
     */
    public String getName() {
        return name;
    }

}
