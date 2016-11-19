package getaclue.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import getaclue.domain.Game;
import getaclue.service.GameNotFoundException;
import getaclue.service.GameService;
import getaclue.service.InvalidGameStateException;

/**
 * Test class for {@link ActionController}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ActionControllerTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    private final String username = "user";
    private final String action = "/game/action";

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
     * {@link ActionController#move(long, getaclue.domain.Location, java.security.Principal)}.
     *
     * @throws Exception
     */
    @Test
    public void testMove() throws Exception {
        Game game = getGame();

        // Invalid game id
        mvc.perform(get(action + "/move").with(user(game.getActivePlayer().getUsername()))
                .param("gameid", "999").param("x", "0").param("y", "0")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

        // Not this player's turn
        mvc.perform(get(action + "/move").with(user(game.getPlayers().get(1).getUsername()))
                .param("gameid", game.getId().toString()).param("x", "0").param("y", "0")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());

        // Illegal move off game board
        mvc.perform(get(action + "/move").with(user(game.getActivePlayer().getUsername()))
                .param("gameid", game.getId().toString()).param("x", "3").param("y", "-1")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());

        // Illegal move to void
        mvc.perform(get(action + "/move").with(user(game.getActivePlayer().getUsername()))
                .param("gameid", game.getId().toString()).param("x", "1").param("y", "1")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());

        // Illegal move multiple spaces away
        mvc.perform(get(action + "/move").with(user(game.getActivePlayer().getUsername()))
                .param("gameid", game.getId().toString()).param("x", "0").param("y", "0")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());

        // Valid request
        mvc.perform(get(action + "/move").with(user(game.getActivePlayer().getUsername()))
                .param("gameid", game.getId().toString()).param("x", "3").param("y", "0")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        // Try to move again during the same turn
        mvc.perform(get(action + "/move").with(user(game.getActivePlayer().getUsername()))
                .param("gameid", game.getId().toString()).param("x", "4").param("y", "0")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
    }

    private Game getGame() throws GameNotFoundException, InvalidGameStateException {
        Long gameid = gameService.newGame(null, username).getId();
        gameService.joinGame(gameid, "player2");
        Game game = gameService.startGame(gameid, username);
        return game;
    }

}
