package getaclue.service;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import getaclue.dao.GameRepository;
import getaclue.domain.Game;
import getaclue.domain.Location;
import getaclue.domain.Move;
import getaclue.domain.Player;
import getaclue.domain.Turn;

/**
 * Implementation of the action service.
 */
@Service
@SuppressWarnings("checkstyle:designforextension")
public class ActionServiceImpl implements ActionService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public void move(final long gameId, final Location moveto, final String username)
            throws GameNotFoundException, InvalidGameStateException {
        // Get the game
        Game game = gameRepository.findOne(gameId);
        if (game == null) {
            throw new GameNotFoundException();
        }
        // Make sure it's this user's turn
        Player player = game.getActivePlayer();
        if (!username.equals(player.getUsername())) {
            throw new InvalidGameStateException("It is not your turn");
        }
        // The move must be the first action of the turn
        Turn turn = game.getTurns().get(game.getTurns().size() - 1);
        if (turn.getActions().size() > 0) {
            throw new InvalidGameStateException("You have already moved or made a guess this turn");
        }
        // Make sure the move is legal
        if (!Location.isValidMove(player.getCurrentLocation(), moveto)) {
            throw new InvalidGameStateException(String.format("It is invalid to move from %s to %s",
                    player.getCurrentLocation(), moveto));
        }
        // If the target is a hall, make sure it is empty
        if (moveto.isHall()) {
            for (Player otherPlayer : game.getPlayers()) {
                if (moveto.coincidesWith(otherPlayer.getCurrentLocation())) {
                    throw new InvalidGameStateException("The hall is already occupied");
                }
            }
        }
        // Record the move
        Move move = new Move();
        move.setTimestamp(ZonedDateTime.now());
        move.setFromLocation(player.getCurrentLocation());
        move.setToLocation(moveto);
        turn.addAction(move);
        player.setForcedToMove(false);
        gameRepository.save(game);
    }

}
