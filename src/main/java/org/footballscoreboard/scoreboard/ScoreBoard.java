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

        if (matches.contains(match))
            throw new IllegalArgumentException("Can't start new match, because it already exists");

        boolean isAdded = matches.add(match);
        if (!isAdded) throw new RuntimeException("Internal error. Problem with start match");

        return match;
    }

    public void finishMatch(Match match) {

        if (match == null) {
            throw new IllegalArgumentException("Match cannot be null");
        }

        if (!matches.contains(match)) return;

        boolean isRemoved = matches.remove(match);
        if (!isRemoved) throw new RuntimeException("Internal error. Problem with finish match");
    }

    public void updateScore(Match match, int homeScore, int awayScore) {

        if (match == null) {
            throw new IllegalArgumentException("Match cannot be null");
        }

        validScore(homeScore, awayScore);

        Match toUpdateMatch = matches.stream()
                .filter(candidate -> equalsMatch(candidate, match))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Match does not exist"));

        toUpdateMatch.setHomeScore(homeScore);
        toUpdateMatch.setAwayScore(awayScore);
    }

    public LinkedHashSet<Match> getSummaryGames() {
        return (LinkedHashSet<Match>) matches.clone();
    }

    private static boolean equalsMatch(Match candidate, Match match) {
        return candidate.getHomeTeam().equals(match.getHomeTeam())
                && candidate.getAwayTeam().equals(match.getAwayTeam());
    }

    private void validTeamName(String homeTeam, String awayTeam) {

        if (homeTeam == null || homeTeam.isBlank()) {
            throw new IllegalArgumentException("Parameter 'homeTeam' and/or 'awayTeam' cannot be null and empty");
        }

        if (awayTeam == null || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Parameter 'homeTeam' and/or 'awayTeam' cannot be null and empty");
        }

        if (homeTeam.toLowerCase().trim().equals(awayTeam.toLowerCase().trim())) {
            throw new IllegalArgumentException(
                    format("Parameter 'homeTeam' = [%s] and 'awayTeam' [%s] must be different", homeTeam, awayTeam));
        }
    }

    private void validScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) throw new IllegalArgumentException("Score id must be greater than zero");
    }
}