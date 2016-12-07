package getaclue.domain;

/**
 * A room on the game board.
 */
public enum Room {
    /**
     * The Study.
     */
    STUDY("Study", 0, 0),
    /**
     * The Hall.
     */
    HALL("Hall", 2, 0),
    /**
     * The Lounge.
     */
    LOUNGE("Lounge", 4, 0),
    /**
     * The Library.
     */
    LIBRARY("Library", 0, 2),
    /**
     * The Billiard Room.
     */
    BILLIARDROOM("Billiard Room", 2, 2),
    /**
     * The Dining Room.
     */
    DININGROOM("Dining Room", 4, 2),
    /**
     * The Conservatory.
     */
    CONSERVATORY("Conservatory", 0, 4),
    /**
     * The Ballroom.
     */
    BALLROOM("Ballroom", 2, 4),
    /**
     * The Kitchen.
     */
    KITCHEN("Kitchen", 4, 4);

    private final String name;
    private final int x;
    private final int y;

    Room(final String name, final int x, final int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * Get the name of the room.
     *
     * @return the name of the room
     */
    public String getName() {
        return name;
    }

    /**
     * Get the x value of this room.
     *
     * @return the x value of this room
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y value of this room.
     *
     * @return the y value of this room
     */
    public int getY() {
        return y;
    }

}
