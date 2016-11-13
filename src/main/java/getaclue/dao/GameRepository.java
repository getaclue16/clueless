package getaclue.dao;

import org.springframework.data.repository.CrudRepository;

import getaclue.domain.Game;

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

}
