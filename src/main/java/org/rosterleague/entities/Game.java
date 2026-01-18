package org.rosterleague.entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "PERSISTENCE_ROSTER_GAME")
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Team homeTeam;

    @ManyToOne
    private Team awayTeam;

    private Integer homeScore;
    private Integer awayScore;

    @ManyToOne
    private League league;

    public Game() {}

    public Game(League league, Team home, Team away, int hScore, int aScore) {
        this.league = league;
        this.homeTeam = home;
        this.awayTeam = away;
        this.homeScore = hScore;
        this.awayScore = aScore;
    }

    public Team getHomeTeam() { return homeTeam; }
    public Team getAwayTeam() { return awayTeam; }
    public Integer getHomeScore() { return homeScore; }
    public Integer getAwayScore() { return awayScore; }
    public League getLeague() { return league; }
}