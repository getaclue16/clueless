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
import getaclue.service.GameService;

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
        String player1 = "player1";
        // Make an open game
        Long openGameId = gameService.newGame(null, player1).getId();

        // Make a full game
        Long fullGameId = gameService.newGame(null, player1).getId();
        gameService.joinGame(fullGameId, "player2");
        gameService.joinGame(fullGameId, "player3");
        gameService.joinGame(fullGameId, "player4");
        gameService.joinGame(fullGameId, "player5");
        gameService.joinGame(fullGameId, "player6");

        // TODO: Make a game that is started
        // Long startedGameId = gameService.newGame(null, player1).getId();
        // gameService.joinGame(startedGameId, "player2");
        // gameService.startGame(startedGameId);

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
            // assertFalse(startedGameId.equals(game.getId()));
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
        String player1 = "player1";
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
        game = gameService.newGame(null, player1);
        gameService.joinGame(game.getId(), "player2");
        gameService.joinGame(game.getId(), "player3");
        gameService.joinGame(game.getId(), "player4");
        gameService.joinGame(game.getId(), "player5");
        gameService.joinGame(game.getId(), "player6");
        mvc.perform(get("/game/join").param("gameid", game.getId().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
                .andExpect(content().string("Unable to join game"));

        // Attempt to join invalid game id
        mvc.perform(get("/game/join").param("gameid", "999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(content().string("Invalid game id"));

        // TODO: Attempt to join a game that has already started
        // Can't do this until we add the ability to start a game
    }

}
