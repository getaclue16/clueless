package getaclue.domain;

import javax.persistence.Entity;

/**
 * An action marking the end of a turn.
 */
@Entity
public final class EndTurn extends Action {

    private static final long serialVersionUID = -6605505147180705436L;

    @Override
    public String getDescription() {
        return "End turn";
    }

}
