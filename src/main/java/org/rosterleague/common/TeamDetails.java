package org.rosterleague.common;

import java.io.Serial;
import java.io.Serializable;


public class TeamDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = -1618941013515364318L;
    private final String id;
    private final String name;
    private final String city;

    public TeamDetails(String id, String name, String city) {

        this.id = id;
        this.name = name;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + city;
    }

}
