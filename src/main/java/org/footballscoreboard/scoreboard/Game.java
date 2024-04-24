package org.footballscoreboard.scoreboard;

import java.util.Objects;

public class Game {

    private final String homeTeam;
    private final String awayTeam;
    private int homeScore = 0;
    private int awayScore = 0;

    Game(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game game)) return false;
        return homeScore == game.homeScore
                && awayScore == game.awayScore
                && Objects.equals(homeTeam, game.homeTeam)
                && Objects.equals(awayTeam, game.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }
}
