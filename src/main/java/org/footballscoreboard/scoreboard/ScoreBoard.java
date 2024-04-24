package org.footballscoreboard.scoreboard;

import java.util.LinkedHashSet;

import static java.lang.String.format;

public class ScoreBoard {

    private final LinkedHashSet<Game> games;

    public ScoreBoard() {
        this.games = new LinkedHashSet<>();
    }

    public Game startGame(String homeTeam, String awayTeam) {

        validTeamName(homeTeam, awayTeam);

        Game game = new Game(homeTeam, awayTeam);

        if (games.contains(game))
            throw new IllegalArgumentException("Can't start new game, because it already exists");

        games.add(game);

        return game;
    }

    public void finishGame(Game game) {

        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }

        if (!games.contains(game)) return;

        games.remove(game);
    }

    public void updateScore(Game game, int homeScore, int awayScore) {

        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }

        validScore(homeScore, awayScore);

        Game toUpdateGame = games.stream()
                .filter(candidate -> equalsMatch(candidate, game))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Game does not exist"));

        toUpdateGame.setHomeScore(homeScore);
        toUpdateGame.setAwayScore(awayScore);
    }

    public LinkedHashSet<Game> getSummaryGames() {
        return (LinkedHashSet<Game>) games.clone();
    }

    private static boolean equalsMatch(Game candidate, Game game) {
        return candidate.getHomeTeam().equals(game.getHomeTeam())
                && candidate.getAwayTeam().equals(game.getAwayTeam());
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