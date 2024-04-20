package org.example.model;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;

public class Match {

    private final String id = UUID.randomUUID().toString();
    private final LocalDateTime startMatch = now();
    private LocalDateTime endMatch;

    private int homeScore = 0;
    private int awayScore = 0;

    private Team homeTeam;
    private Team awayTeam;

    private Match() {
    }

    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getStartMatch() {
        return startMatch;
    }

    public LocalDateTime getEndMatch() {
        return endMatch;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void increaseHomeScore(){
        this.homeScore += 1;
    }

    public void increaseAwayScore(){
        this.awayScore += 1;
    }

    public void endMatch(){
        this.endMatch = now();
    }
}
