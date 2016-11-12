package getaclue.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for {@link CluelessUser}.
 */
public class CluelessUserTest {

    /**
     * Test method for {@link CluelessUser#equals(Object)}.
     */
    @Test
    public void testEqualsObject() {
        CluelessUser user1 = new CluelessUser();
        CluelessUser user2 = new CluelessUser();

        assertFalse(user1.equals(null));
        assertFalse(user1.equals(new String()));

        assertTrue(user1.equals(user2));

        user2.setUsername("username");
        assertFalse(user1.equals(user2));
        user1.setUsername("otherUsername");
        assertFalse(user1.equals(user2));
        user1.setUsername(user2.getUsername());
        assertTrue(user1.equals(user2));

        user2.setPassword("password");
        assertFalse(user1.equals(user2));
        user1.setPassword("otherPassword");
        assertFalse(user1.equals(user2));
        user1.setPassword(user2.getPassword());
        assertTrue(user1.equals(user2));
    }

}
