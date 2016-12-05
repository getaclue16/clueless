package getaclue.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import getaclue.domain.CluelessUser;

/**
 * The controller for the home page and login pages.
 */
@Controller
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserDetailsManager userDetailsManager;

    /**
     * Autowired constructor.
     *
     * @param userDetailsManager
     *            the UserDetailsManager implementation
     */
    @Autowired
    public HomeController(final UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    /**
     * Display the new user form.
     *
     * @param model
     *            the model
     * @return the new user page
     */
    @GetMapping("/newuser")
    public final String newUserForm(final Model model) {
        model.addAttribute("newuser", new CluelessUser());
        return "newuser";
    }

    /**
     * Create a new user.
     *
     * @param newuser
     *            the new user
     * @param bindingResult
     *            validation result
     * @param model
     *            the model
     * @return the home page if the new user was created, otherwise the new user
     *         form
     */
    @PostMapping("/newuser")
    public final String newUserSubmit(@ModelAttribute("newuser") @Valid final CluelessUser newuser,
            final BindingResult bindingResult, final Model model) {
        // If binding errors occurred, return the user to the form
        if (bindingResult.hasErrors()) {
            log.debug(bindingResult.getErrorCount() + " errors found");
            return "newuser";
        }

        // If the username already exists, return the user to the form
        if (userDetailsManager.userExists(newuser.getUsername())) {
            log.debug("User already exists: " + newuser.getUsername());
            FieldError fieldError = new FieldError("newuser", "username", newuser.getUsername(),
                    false, null, null, "Username is not available");
            bindingResult.addError(fieldError);
            return "newuser";
        }

        // Create the new user
        log.info("Creating new user: " + newuser.getUsername());
        userDetailsManager.createUser(new User(newuser.getUsername(), newuser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("user"))));

        // Return the user to the home page
        return "redirect:/";
    }

    /**
     * Get the game board.
     *
     * @return the base game board
     */
    @GetMapping("/game")
    public final String gameBoard() {
        return "game";
    }

}
