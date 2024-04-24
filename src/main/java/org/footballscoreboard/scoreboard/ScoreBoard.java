package org.footballscoreboard.scoreboard;

import java.util.LinkedHashSet;

import static java.lang.String.format;

public class ScoreBoard {

    private final LinkedHashSet<Match> matches;

    public ScoreBoard() {
        this.matches = new LinkedHashSet<>();
    }

    public Match startMatch(String homeTeam, String awayTeam) {

        validTeamName(homeTeam, awayTeam);

        Match match = new Match(homeTeam, awayTeam);
        boolean isAdded = matches.add(match);
        if (!isAdded) {
            throw new RuntimeException("Problem with add match in memory");
        }
        return match;
    }

    private void validTeamName(String homeTeam, String awayTeam) {
        if (homeTeam == null || homeTeam.isBlank()) {
            throw new IllegalArgumentException("Parameter 'homeTeam' and/or 'awayTeam' cannot be null and empty");
        }

        if (awayTeam == null || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Parameter 'homeTeam' and/or 'awayTeam' cannot be null and empty");
        }

        if(homeTeam.toLowerCase().trim().equals(awayTeam.toLowerCase().trim())) {
            throw new IllegalArgumentException(
                    format("Parameter 'homeTeam' = [%s] and 'awayTeam' [%s] must be different", homeTeam, awayTeam));
        }
    }

    public LinkedHashSet<Match> getMatches() {
        return (LinkedHashSet<Match>) matches.clone();
    }
}