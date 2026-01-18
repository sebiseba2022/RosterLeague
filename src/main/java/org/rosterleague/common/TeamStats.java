package org.rosterleague.common;

import java.io.Serializable;

public class TeamStats implements Serializable {
    private final String teamName;
    private int played;
    private int points;

    public TeamStats(String teamName) {
        this.teamName = teamName;
        this.played = 0;
        this.points = 0;
    }

    public void addMatch(int pointsObtained) {
        this.played++;
        this.points += pointsObtained;
    }

    public String getTeamName() { return teamName; }
    public int getPlayed() { return played; }
    public int getPoints() { return points; }
}