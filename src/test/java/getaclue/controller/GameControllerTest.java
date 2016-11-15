package getaclue.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

/**
 * Test class for {@link GameController}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {

    @Autowired
    private WebApplicationContext wac;

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
                .andDo(print()).andExpect(status().isOk()).andReturn();

        Game game = objectMapper.readerFor(Game.class)
                .readValue(result.getResponse().getContentAsString());
        assertEquals(gameName, game.getName());
        assertNotNull(game.getSolution());
        assertNotNull(game.getSolution().getGuest());
        assertNotNull(game.getSolution().getRoom());
        assertNotNull(game.getSolution().getWeapon());
        assertTrue(game.getState().equals(State.NEW));

        mvc.perform(get("/game/create").param("name", gameName).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    /**
     * Test method for {@link GameController#openGames()}.
     *
     * @throws Exception
     */
    @Test
    @WithMockUser
    public void testOpenGames() throws Exception {
        mvc.perform(get("/game/create"));
        MvcResult result = mvc.perform(get("/game/open").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        List<Game> games = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Game>>() {
                });
        assertTrue(games.size() >= 1);
    }

}
