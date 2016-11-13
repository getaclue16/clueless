package getaclue.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * A turn in the game.
 */
@Entity
public final class Turn extends AbstractPersistable<Long> {

    private static final long serialVersionUID = -66086761512365387L;

    @NotNull
    private Player player;
    @OneToMany
    private List<Action> actions;

    /**
     * Create a turn.
     */
    public Turn() {
    }

    /**
     * Create a turn.
     *
     * @param player
     *            the play whose turn this is
     */
    public Turn(final Player player) {
        this.player = player;
    }

    /**
     * @return the actions
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * @param actions
     *            the actions to set
     */
    public void setActions(final List<Action> actions) {
        this.actions = actions;
    }

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

}
