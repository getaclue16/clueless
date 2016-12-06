package getaclue.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import getaclue.dao.GameRepository;
import getaclue.domain.Action;
import getaclue.domain.Card;
import getaclue.domain.EndTurn;
import getaclue.domain.Game;
import getaclue.domain.Game.State;
import getaclue.domain.Guess;
import getaclue.domain.Guest;
import getaclue.domain.Location;
import getaclue.domain.Move;
import getaclue.domain.NonPlayer;
import getaclue.domain.Player;
import getaclue.domain.Response;
import getaclue.domain.Room;
import getaclue.domain.Solution;
import getaclue.domain.Turn;
import getaclue.domain.Weapon;

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
        // Make sure the game is in progress
        if (game.getState() != State.IN_PROGRESS) {
            throw new InvalidGameStateException("This game is not in progress");
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
            throw new InvalidGameStateException(String.format("That is not a valid move"));
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

    @Override
    public void guess(final long gameId, final Guest guest, final Weapon weapon,
            final String username) throws GameNotFoundException, InvalidGameStateException {
        // Get the game
        Game game = gameRepository.findOne(gameId);
        if (game == null) {
            throw new GameNotFoundException();
        }
        // Make sure the game is in progress
        if (game.getState() != State.IN_PROGRESS) {
            throw new InvalidGameStateException("This game is not in progress");
        }
        // Make sure it's this user's turn
        Player player = game.getActivePlayer();
        if (!username.equals(player.getUsername())) {
            throw new InvalidGameStateException("It is not your turn");
        }
        // The user must move first or have been moved by another player
        Turn turn = game.getTurns().get(game.getTurns().size() - 1);
        if (turn.getActions().size() == 0) {
            if (!player.isForcedToMove()) {
                throw new InvalidGameStateException("You must move before making a suggestion");
            }
            player.setForcedToMove(false);
        }
        // Has a guess already been made?
        if (turn.getActions().size() > 1) {
            throw new InvalidGameStateException("You have already made a suggestion this turn");
        }
        // Is the user in a room?
        if (player.getCurrentLocation().isHall()) {
            throw new InvalidGameStateException("You must be in a room to make a suggestion");
        }
        Solution solution = new Solution(guest, weapon, player.getCurrentLocation().toRoom());
        Guess guess = new Guess(ZonedDateTime.now(), solution);
        turn.addAction(guess);

        // Move the accused guest to the location
        boolean found = false;
        for (Player accused : game.getPlayers()) {
            if (accused.getGuest() == guest) {
                found = true;
                accused.setCurrentLocation(player.getCurrentLocation());
                accused.setForcedToMove(true);
            }
        }
        if (!found) {
            for (NonPlayer nonPlayer : game.getNonPlayers()) {
                if (nonPlayer.getGuest() == guest) {
                    nonPlayer.setCurrentLocation(player.getCurrentLocation());
                    break;
                }
            }
        }

        // Move the weapon to the location
        game.getWeaponLocations().put(weapon, player.getCurrentLocation().toRoom());

        // Record the action
        gameRepository.save(game);

    }

    @Override
    public void respond(final long gameId, final int response, final String username)
            throws GameNotFoundException, InvalidGameStateException, ParameterException {
        // Get the game
        Game game = gameRepository.findOne(gameId);
        if (game == null) {
            throw new GameNotFoundException();
        }
        // Make sure the game is in progress
        if (game.getState() != State.IN_PROGRESS) {
            throw new InvalidGameStateException("This game is not in progress");
        }
        // Make sure a guess was made
        Turn turn = game.getTurns().get(game.getTurns().size() - 1);
        if (turn.getActions().size() < 1) {
            throw new InvalidGameStateException("No guess was made yet");
        }
        Action action = turn.getActions().get(turn.getActions().size() - 1);
        Player previousPlayer;
        Guess guess = null;
        if (action instanceof Guess) {
            previousPlayer = turn.getPlayer();
            guess = (Guess) action;
        } else if (action instanceof Response) {
            if (((Response) action).getCardShown() != null) {
                throw new InvalidGameStateException("A card was already shown for this guess");
            }
            previousPlayer = ((Response) action).getPlayer();
            int actionIndex = turn.getActions().size() - 2;
            while (guess == null) {
                if (turn.getActions().get(actionIndex) instanceof Guess) {
                    guess = (Guess) turn.getActions().get(actionIndex);
                } else {
                    actionIndex--;
                }
            }
        } else {
            throw new InvalidGameStateException("No guess was made yet");
        }

        // Make sure it's this user's turn to respond
        int nextIndex = game.getPlayers().indexOf(previousPlayer) + 1;
        if (nextIndex == game.getPlayers().size()) {
            nextIndex = 0;
        }
        Player player = game.getPlayers().get(nextIndex);
        if (!player.getUsername().equals(username)) {
            throw new InvalidGameStateException("It is not your turn to respond");
        }

        Response responseAction = new Response();
        responseAction.setTimestamp(ZonedDateTime.now());
        responseAction.setPlayer(player);

        // What type of response
        if (response == 0) {
            // Make sure the user doesn't have any of the guessed cards
            for (Card card : player.getCards()) {
                if (card.getName().equals(guess.getSolution().getGuest().getName())
                        || card.getName().equals(guess.getSolution().getRoom().getName())
                        || card.getName().equals(guess.getSolution().getWeapon().getName())) {
                    throw new ParameterException("You have a matching card and must show it");
                }
            }
        } else {
            // Make sure this user has the card
            String responseName;
            if (response == GUEST_RESPONSE) { // Guest
                responseName = guess.getSolution().getGuest().getName();
            } else if (response == WEAPON_RESPONSE) { // Weapon
                responseName = guess.getSolution().getWeapon().getName();
            } else if (response == ROOM_RESPONSE) { // Room
                responseName = guess.getSolution().getRoom().getName();
            } else {
                throw new ParameterException("Invalid response value");
            }
            boolean found = false;
            for (Card card : player.getCards()) {
                if (card.getName().equals(responseName)) {
                    responseAction.setCardShown(card);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new ParameterException("You do not have the " + responseName + " card");
            }
        }

        // Record the action
        turn.addAction(responseAction);
        gameRepository.save(game);
    }

    @Override
    public boolean accuse(final long gameId, final Guest guest, final Weapon weapon,
            final Room room, final String username)
            throws GameNotFoundException, InvalidGameStateException {
        // Get the game
        Game game = gameRepository.findOne(gameId);
        if (game == null) {
            throw new GameNotFoundException();
        }
        // Make sure the game is in progress
        if (game.getState() != State.IN_PROGRESS) {
            throw new InvalidGameStateException("This game is not in progress");
        }
        // Make sure it's this user's turn
        Player player = game.getActivePlayer();
        if (!username.equals(player.getUsername())) {
            throw new InvalidGameStateException("It is not your turn");
        }

        // Check the solution
        if (game.getSolution().getGuest() == guest && game.getSolution().getWeapon() == weapon
                && game.getSolution().getRoom() == room) {
            game.setState(State.COMPLETE);
            gameRepository.save(game);
            return true;
        }
        game.getActivePlayer().setFailedAccusation(true);
        startNextTurn(game);
        gameRepository.save(game);
        return false;
    }

    @Override
    public void endturn(final long gameId, final String username)
            throws GameNotFoundException, InvalidGameStateException {
        // Get the game
        Game game = gameRepository.findOne(gameId);
        if (game == null) {
            throw new GameNotFoundException();
        }
        // Make sure the game is in progress
        if (game.getState() != State.IN_PROGRESS) {
            throw new InvalidGameStateException("This game is not in progress");
        }
        // Make sure it's this user's turn
        Player player = game.getActivePlayer();
        if (!username.equals(player.getUsername())) {
            throw new InvalidGameStateException("It is not your turn");
        }

        // Has the user taken any action?
        Turn turn = game.getTurns().get(game.getTurns().size() - 1);
        if (turn.getActions().size() < 1) {
            // If the user was forced to move here, they must at least guess
            // before ending their turn
            if (turn.getPlayer().isForcedToMove()) {
                throw new InvalidGameStateException(
                        "You must make a guess or move before ending your turn");
            }
            // TODO If a move is possible, the user must take it
        }

        // Add the end turn action
        EndTurn endTurn = new EndTurn();
        endTurn.setTimestamp(ZonedDateTime.now());
        turn.addAction(endTurn);

        // Start the next user's turn
        startNextTurn(game);

        gameRepository.save(game);
    }

    private void startNextTurn(final Game game) {
        Player nextPlayer = getNextActivePlayer(game.getPlayers(),
                game.getPlayers().indexOf(game.getActivePlayer()));
        Turn nextTurn = new Turn(nextPlayer);
        game.getTurns().add(nextTurn);
        game.setActivePlayer(nextPlayer);
    }

    private Player getNextActivePlayer(final List<Player> players, final int currentPlayerIndex) {
        int next = currentPlayerIndex + 1;
        if (next == players.size()) {
            next = 0;
        }
        if (players.get(next).isFailedAccusation()) {
            return getNextActivePlayer(players, next);
        }
        return players.get(next);
    }

}
