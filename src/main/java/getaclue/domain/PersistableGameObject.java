package getaclue.domain;

import javax.persistence.Transient;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a persistent object in the game.
 */
public abstract class PersistableGameObject extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 4699540189062634434L;

    @Override
    @JsonIgnore
    @Transient
    public final boolean isNew() {
        return getId() == null;
    }

}
