package getaclue.domain;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The complete representation of an active or completed game.
 */
@Entity
public final class Game extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 6196171277887994233L;

    @NotNull
    private String name;
    @Embedded
    private Solution solution;
    private Player activePlayer;
    @OneToMany
    private List<Turn> turns;
    @OneToMany
    private List<Player> players;

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

    @Override
    @JsonIgnore
    @Transient
    public boolean isNew() {
        return getId() == null;
    }

}
