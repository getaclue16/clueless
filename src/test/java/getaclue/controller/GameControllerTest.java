package getaclue.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import getaclue.domain.Game;
import getaclue.domain.Game.State;
import getaclue.domain.Guest;
import getaclue.service.GameService;
import getaclue.service.GameServiceImpl.GameNotFoundException;
import getaclue.service.GameServiceImpl.InvalidGameStateException;

/**
 * Test class for {@link GameController}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private GameService gameService;

    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String player1 = "player1";

    /**
     * Setup method to run before each test.
     */
    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    /**
     * Test method for
     * {@link GameController#createGame(String, java.security.Principal)}.
     *
     * @throws Exception
     */
    @Test
    @WithMockUser
    public void testCreateGame() throws Exception {
        String gameName = "newGame";
        MvcResult result = mvc
                .perform(get("/game/create").param("name", gameName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        String content = result.getResponse().getContentAsString();
        assertFalse(content.contains("solution"));
        Game game = objectMapper.readerFor(Game.class).readValue(content);
        assertEquals(gameName, game.getName());
        assertNull(game.getSolution());
        assertTrue(game.getState().equals(State.NEW));

        mvc.perform(get("/game/create").param("name", gameName).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    /**
     * Test method for {@link GameController#openGames()}.
     *
     * @throws Exception
     */
    @Test
    @WithMockUser
    public void testOpenGames() throws Exception {
        // Make an open game
        Long openGameId = gameService.newGame(null, player1).getId();

        // Make a full game
        Long fullGameId = getFullGame().getId();

        // Make a game that is started
        Long startedGameId = getStartedGame().getId();

        // Get the list of open games
        MvcResult result = mvc.perform(get("/game/open").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        List<Game> games = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Game>>() {
                });
        assertTrue(games.size() >= 1);

        // Make sure we only get the new, not full game
        boolean found = false;
        for (Game game : games) {
            assertFalse(fullGameId.equals(game.getId()));
            assertFalse(startedGameId.equals(game.getId()));
            if (openGameId.equals(game.getId())) {
                found = true;
            }
        }
        assertTrue(found);
    }

    /**
     * Test method for
     * {@link GameController#joinGame(long, java.security.Principal)}.
     *
     * @throws Exception
     */
    @Test
    @WithMockUser
    public void testJoinGame() throws Exception {
        Game game = gameService.newGame(null, player1);

        // Join the game
        MvcResult result = mvc.perform(get("/game/join").param("gameid", game.getId().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        Game joinedGame = objectMapper.readerFor(Game.class)
                .readValue(result.getResponse().getContentAsString());
        assertEquals(game.getId(), joinedGame.getId());
        assertEquals(2, joinedGame.getPlayers().size());
        assertEquals(player1, joinedGame.getPlayers().get(0).getUsername());
        assertEquals("user", joinedGame.getPlayers().get(1).getUsername());

        // Attempt to join the same game again
        mvc.perform(get("/game/join").param("gameid", game.getId().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
                .andExpect(content().string("Unable to join game"));

        // Attempt to join a full game
        game = getFullGame();
        mvc.perform(get("/game/join").param("gameid", game.getId().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
                .andExpect(content().string("Unable to join game"));

        // Attempt to join invalid game id
        mvc.perform(get("/game/join").param("gameid", "999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(content().string("Invalid game id"));

        // Attempt to join a game that has already started
        game = getStartedGame();
        mvc.perform(get("/game/join").param("gameid", game.getId().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
                .andExpect(content().string("Unable to join game"));
    }

    /**
     * Test method for
     * {@link GameController#startGame(long, java.security.Principal)}.
     *
     * @throws Exception
     */
    @Test
    @WithMockUser
    public void testStartGame() throws Exception {
        // Try to start the game with only one player
        Game game = gameService.newGame(null, "user");
        mvc.perform(get("/game/start").param("gameid", game.getId().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
                .andExpect(content().string("Unable to start game"));

        // Add a second player and successfully start the game
        gameService.joinGame(game.getId(), player1);
        MvcResult result = mvc.perform(get("/game/start").param("gameid", game.getId().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        Game startedGame = objectMapper.readerFor(Game.class)
                .readValue(result.getResponse().getContentAsString());
        assertEquals(game.getId(), startedGame.getId());
        // The game was started
        assertEquals(State.IN_PROGRESS, startedGame.getState());
        // The players were assigned guests
        assertEquals(2, startedGame.getPlayers().size());
        assertEquals(Guest.SCARLET, startedGame.getPlayers().get(0).getGuest());
        assertNotNull(startedGame.getPlayers().get(1).getGuest());
        assertFalse(startedGame.getPlayers().get(1).getGuest().equals(Guest.SCARLET));
        // The players were delt cards
        assertEquals(11, startedGame.getPlayers().get(0).getCards().size());
        assertEquals(10, startedGame.getPlayers().get(1).getCards().size());

        // Try to start the game again, even though it is already started
        mvc.perform(get("/game/start").param("gameid", game.getId().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
                .andExpect(content().string("Unable to start game"));

        // Try to start an invalid game id
        mvc.perform(get("/game/start").param("gameid", "999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(content().string("Invalid game id"));

        // Try to start a game created by someone else
        mvc.perform(get("/game/start").param("gameid", getFullGame().getId().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
                .andExpect(content().string("Unable to start game"));

    }

    private Game getFullGame() throws GameNotFoundException, InvalidGameStateException {
        Game game = gameService.newGame(null, player1);
        gameService.joinGame(game.getId(), "player2");
        gameService.joinGame(game.getId(), "player3");
        gameService.joinGame(game.getId(), "player4");
        gameService.joinGame(game.getId(), "player5");
        gameService.joinGame(game.getId(), "player6");
        return game;
    }

    private Game getStartedGame() throws GameNotFoundException, InvalidGameStateException {
        Game game = gameService.newGame(null, player1);
        gameService.joinGame(game.getId(), "player2");
        gameService.startGame(game.getId(), player1);
        return game;
    }

}
