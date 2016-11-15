package getaclue.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import getaclue.domain.Game;
import getaclue.domain.Game.State;

/**
 * The persistence interface for Game objects.
 */
public interface GameRepository extends CrudRepository<Game, Long> {

    /**
     * Find a game by name.
     *
     * @param name
     *            the name of the game to find
     * @return the game
     */
    Game findByName(String name);

    /**
     * Find all games with the given state.
     *
     * @param state
     *            the state of the games to return
     * @return a collection of games
     */
    Collection<Game> findByState(State state);

}
