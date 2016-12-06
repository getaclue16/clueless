package getaclue.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import getaclue.dao.GameRepository;
import getaclue.domain.Card;
import getaclue.domain.Game;
import getaclue.domain.Game.State;
import getaclue.domain.Guest;
import getaclue.domain.NonPlayer;
import getaclue.domain.Player;
import getaclue.domain.Solution;
import getaclue.domain.Turn;

/**
 * Implementation of the game service.
 */
@Service
@SuppressWarnings("checkstyle:designforextension")
public class GameServiceImpl implements GameService {

    private static final int MAX_PLAYERS = 6;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game newGame(final String gameName, final String creator) {
        Game game = new Game();
        game.setName(gameName);
        game.setSolution(Solution.generateSolution());

        Player player = new Player(creator);
        game.setPlayers(Collections.singletonList(player));

        gameRepository.save(game);

        return game;
    }

    @Override
    public Collection<Game> getNewGames() {
        Collection<Game> games = gameRepository.findByState(State.NEW);

        // Only return games that are not full!
        Iterator<Game> iterator = games.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getPlayers().size() >= MAX_PLAYERS) {
                iterator.remove();
            }
        }

        return games;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Game joinGame(final long gameId, final String username)
            throws GameNotFoundException, InvalidGameStateException {
        // Make sure the game exists
        Game game = gameRepository.findOne(gameId);
        if (game == null) {
            throw new GameNotFoundException();
        }
        // Make sure the game has not started yet
        if (game.getState() != State.NEW) {
            throw new InvalidGameStateException("The selected game has already started");
        }
        // Make sure the game isn't already full
        if (game.getPlayers().size() >= MAX_PLAYERS) {
            throw new InvalidGameStateException("The selected game is full");
        }
        // Make sure the user isn't already in the game
        for (Player player : game.getPlayers()) {
            if (player.getUsername().equals(username)) {
                throw new InvalidGameStateException("You have already joined this game");
            }
        }
        game.getPlayers().add(new Player(username));
        gameRepository.save(game);
        return game;
    }

    @Override
    @Transactional
    public Game startGame(final long gameId, final String username)
            throws InvalidGameStateException, GameNotFoundException {
        // Make sure the game exists
        Game game = gameRepository.findOne(gameId);
        if (game == null) {
            throw new GameNotFoundException();
        }
        // Make sure the game hasn't already started
        if (game.getState() != State.NEW) {
            throw new InvalidGameStateException("Game has already started");
        }
        // Make sure the user is the creator of the game
        if (!username.equals(game.getPlayers().get(0).getUsername())) {
            throw new InvalidGameStateException("Only the game creator can start the game");
        }
        // Make sure there are at least two players in the game
        if (game.getPlayers().size() < 2) {
            throw new InvalidGameStateException("Not enough players to start the game");
        }

        // Assign a guest to each player
        List<Player> players = game.getPlayers();
        Collections.shuffle(players);
        List<Guest> guests = new ArrayList<>(Arrays.asList(Guest.values()));
        // First assign Miss Scarlet as she is required
        players.get(0).setGuest(Guest.SCARLET);
        guests.remove(0);
        // Next assign the rest
        Collections.shuffle(guests);
        for (int i = 1; i < players.size(); i++) {
            players.get(i).setGuest(guests.remove(0));
        }

        // Remaining guests are recorded as non-players for display on the game
        // board
        List<NonPlayer> nonplayers = game.getNonPlayers();
        while (guests.size() > 0) {
            NonPlayer nonplayer = new NonPlayer();
            nonplayer.setGuest(guests.remove(0));
            nonplayers.add(nonplayer);
        }

        // Deal the cards
        List<Card> deck = Card.newDeck();
        Collections.shuffle(deck);
        for (int i = 0; i < deck.size(); i++) {
            players.get(i % players.size()).getCards().add(deck.get(i));
        }

        // Set the active player and start their turn
        game.setActivePlayer(players.get(0));
        game.getTurns().add(new Turn(players.get(0)));

        // Start the game
        game.setState(State.IN_PROGRESS);

        // Save the state and return the game
        gameRepository.save(game);
        return game;
    }

}
