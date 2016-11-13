package getaclue.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import getaclue.domain.CluelessUser;

/**
 * Test class for {@link HomeController}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HomeControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

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
     * {@link HomeController#newUserForm(org.springframework.ui.Model)}.
     *
     * @throws Exception
     */
    @Test
    public void testNewUserForm() throws Exception {
        mvc.perform(get("/newuser")).andExpect(status().isOk()).andExpect(view().name("newuser"))
                .andExpect(model().attribute("newuser", isA(CluelessUser.class)));
    }

    /**
     * Test method for
     * {@link HomeController#newUserSubmit(CluelessUser, org.springframework.validation.BindingResult, org.springframework.ui.Model)}.
     *
     * @throws Exception
     */
    @Test
    public void testNewUserSubmit() throws Exception {
        String username = "SampleUser";
        String password = "SamplePassword";

        // Valid new user
        mvc.perform(formLogin().user(username).password(password)).andExpect(unauthenticated());
        mvc.perform(post("/newuser").with(csrf()).param("username", username).param("password",
                password)).andExpect(status().isFound()).andExpect(redirectedUrl("/"));
        mvc.perform(formLogin().user(username).password(password)).andExpect(authenticated());

        // Binding error
        mvc.perform(post("/newuser").with(csrf()).param("password", password))
                .andExpect(status().isOk()).andExpect(view().name("newuser"))
                .andExpect(model().attribute("newuser", is(new CluelessUser(null, password))));

        // Duplicate username
        String newpassword = "NewPassword";
        mvc.perform(post("/newuser").with(csrf()).param("username", username).param("password",
                newpassword)).andExpect(status().isOk()).andExpect(view().name("newuser"))
                .andExpect(
                        model().attribute("newuser", is(new CluelessUser(username, newpassword))));
        mvc.perform(formLogin().user(username).password(password)).andExpect(authenticated());

        // Invalid csrf
        mvc.perform(post("/newuser").with(csrf().useInvalidToken()).param("username", username)
                .param("password", password)).andExpect(status().isForbidden());
    }

}
