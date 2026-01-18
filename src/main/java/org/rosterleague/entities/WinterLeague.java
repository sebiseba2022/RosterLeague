package org.rosterleague.entities;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Entity;
import org.rosterleague.common.IncorrectSportException;

@Entity
public class WinterLeague extends League implements Serializable {
    @Serial
    private static final long serialVersionUID = 8942582153559426625L;
    
    /** Creates a new instance of WinterLeague */
    public WinterLeague() {
    }
    
    public WinterLeague(String id, String name, String sport) throws
            IncorrectSportException {
        this.id = id;
        this.name = name;
        if (sport.equalsIgnoreCase("hockey") ||
                sport.equalsIgnoreCase("skiing") ||
                sport.equalsIgnoreCase("snowboarding")) {
            this.sport = sport;
        } else {
            throw new IncorrectSportException("Sport is not a winter sport.");
        }
    }
    
}
