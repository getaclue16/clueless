package getaclue.domain;

/**
 * A guest in the game.
 */
public enum Guest {
    /**
     * Miss Scarlet.
     */
    SCARLET("Miss Scarlet", new Location(3, -1)),
    /**
     * Professor Plum.
     */
    PLUM("Prof. Plum", new Location(-1, 1)),
    /**
     * Mrs. Peacock.
     */
    PEACOCK("Mrs. Peacock", new Location(-1, 3)),
    /**
     * Mr. Green.
     */
    GREEN("Mr. Green", new Location(1, 5)),
    /**
     * Mrs. White.
     */
    WHITE("Mrs. White", new Location(3, 5)),
    /**
     * Colonel Mustard.
     */
    MUSTARD("Col. Mustard", new Location(5, 1));

    private final String name;
    private final Location startLocation;

    Guest(final String name, final Location startLocation) {
        this.name = name;
        this.startLocation = startLocation;
    }

    /**
     * Get the guest's name.
     *
     * @return the guest's name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the guest's starting location.
     *
     * @return the guest's starting location
     */
    public Location getStartLocation() {
        return startLocation;
    }

}
