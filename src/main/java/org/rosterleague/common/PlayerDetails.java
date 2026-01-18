package org.rosterleague.common;

import java.io.Serial;
import java.io.Serializable;

public class PlayerDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = -5352446961599198526L;

    private final String id;
    private final String name;
    private final String position;
    private final double salary;

    public PlayerDetails(String id, String name, String position,
            double salary) {

        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + position + " " + salary;
    }

}
