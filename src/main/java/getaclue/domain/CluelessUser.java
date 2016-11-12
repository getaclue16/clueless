package getaclue.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * A user.
 */
public class CluelessUser {
    @NotNull
    @Pattern(regexp = "[\\w ]+")
    private String username;

    @NotNull
    @Pattern(regexp = ".{8,}")
    private String password;

    /**
     * Default constructor.
     */
    public CluelessUser() {
    }

    /**
     * Constructor with parameters.
     *
     * @param username
     *            the username to set
     * @param password
     *            the password to set
     */
    public CluelessUser(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return the username
     */
    public final String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public final void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public final String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        if (password == null) {
            result = prime * result;
        } else {
            result = prime * result + password.hashCode();
        }
        if (username == null) {
            result = prime * result;
        } else {
            result = prime * result + username.hashCode();
        }
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CluelessUser other = (CluelessUser) obj;
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        return true;
    }
}
