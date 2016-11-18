package getaclue.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The complete representation of an active or completed game.
 */
@Entity
public final class Game extends PersistableGameObject {

    private static final long serialVersionUID = 6196171277887994233L;

    private String name;
    @Embedded
    @JsonIgnore
    private Solution solution;
    @ManyToOne
    @JoinColumn
    private Player activePlayer;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Turn> turns;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> players;
    private State state = State.NEW;

    /**
     * Create a game.
     */
    public Game() {
    }

    /**
     * @return the solution
     */
    public Solution getSolution() {
        return solution;
    }

    /**
     * @param solution
     *            the solution to set
     */
    public void setSolution(final Solution solution) {
        this.solution = solution;
    }

    /**
     * @return the activePlayer
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * @param activePlayer
     *            the activePlayer to set
     */
    public void setActivePlayer(final Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * @return the turns
     */
    public List<Turn> getTurns() {
        return turns;
    }

    /**
     * @param turns
     *            the turns to set
     */
    public void setTurns(final List<Turn> turns) {
        this.turns = turns;
    }

    /**
     * @return the players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @param players
     *            the players to set
     */
    public void setPlayers(final List<Player> players) {
        this.players = players;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(final State state) {
        this.state = state;
    }

    /**
     * The state of a game.
     */
    public enum State {
        /**
         * Game is new and has not started, users can join.
         */
        NEW,
        /**
         * Game has started, no new users can join.
         */
        IN_PROGRESS,
        /**
         * The game is complete, no further actions can be taken.
         */
        COMPLETE;
    }

}
