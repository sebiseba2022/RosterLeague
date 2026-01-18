package org.rosterleague.common;

import java.io.Serial;
import java.io.Serializable;


public class LeagueDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 290368886584321980L;
    private final String id;
    private final String name;
    private final String sport;

    public LeagueDetails(String id, String name, String sport) {

        this.id = id;
        this.name = name;
        this.sport = sport;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSport() {
        return sport;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + sport;
    }

}
