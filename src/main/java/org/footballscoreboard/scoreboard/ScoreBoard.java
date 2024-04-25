package org.footballscoreboard.scoreboard;

import java.util.*;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.stream.Collectors.toUnmodifiableSet;

public class ScoreBoard {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private final LinkedHashSet<Game> games;

    public ScoreBoard() {
        this.games = new LinkedHashSet<>();
    }

    public void startGame(String homeTeam, String awayTeam) {

        if (isInvalidTeamName(homeTeam, awayTeam)) return;

        Game game = new Game(homeTeam, awayTeam);

        boolean isAdded = games.add(game);
        if (!isAdded) {
            logger.warning(format("Can't start new game [%s vs %s], because it already exists", homeTeam, awayTeam));
        }
    }

    public void finishGame(String homeTeam, String awayTeam) {

        if (isInvalidTeamName(homeTeam, awayTeam)) return;

        Optional<Game> oGame = games.stream()
                .filter(candidate -> equalsMatch(candidate, homeTeam, awayTeam))
                .findFirst();

        oGame.ifPresent(games::remove);
    }

    public boolean updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {

        if (isInvalidTeamName(homeTeam, awayTeam)) return false;

        if (isInvalidScoreValue(homeScore, awayScore)) return false;

        Optional<Game> oGame = games.stream()
                .filter(candidate -> equalsMatch(candidate, homeTeam, awayTeam))
                .findFirst();

        if (oGame.isPresent()) {
            Game toUpdateGame = oGame.get();
            toUpdateGame.setHomeScore(homeScore);
            toUpdateGame.setAwayScore(awayScore);
            return true;
        }

        return false;
    }

    public Set<? extends ScoreBoardItem> getSummaryGames() {
        return games.stream()
                .map(ScoreBoardItem::of)
                .collect(toUnmodifiableSet());
    }


    private static boolean equalsMatch(Game game, String homeTeam, String awayTeam) {
        return game.getHomeTeam().equals(homeTeam)
                && game.getAwayTeam().equals(awayTeam);
    }

    private boolean isInvalidTeamName(String homeTeam, String awayTeam) {

        if (isNullOrBlank(homeTeam)) {
            logger.warning("Parameter 'homeTeam' can't be null or empty");
            return true;
        }

        if (isNullOrBlank(awayTeam)) {
            logger.warning("Parameter 'awayTeam' can't be null or empty");
            return true;
        }

        if (homeTeam.toLowerCase().trim().equals(awayTeam.toLowerCase().trim())) {
            logger.warning(
                    format("Parameter 'homeTeam' = [%s] and 'awayTeam' [%s] must be different", homeTeam, awayTeam));
            return true;
        }

        return false;
    }

    private boolean isInvalidScoreValue(int homeScore, int awayScore) {

        if (homeScore < 0) {
            logger.warning(format("Parameters homeScore[%d] must be greater than zero", homeScore));
            return true;
        }

        if (awayScore < 0) {
            logger.warning(format("Parameters awayScore[%d] must be greater than zero", awayScore));
            return true;
        }
        return false;
    }

    private boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public static class ScoreBoardItem {
        private final String homeTeam;
        private final int homeScore;
        private final String awayTeam;
        private final int awayScore;

        ScoreBoardItem(String homeTeam, int homeScore, String awayTeam, int awayScore) {
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            this.homeScore = homeScore;
            this.awayScore = awayScore;
        }

        static ScoreBoardItem of(Game game) {
            return new ScoreBoardItem(
                    game.getHomeTeam(),
                    game.getHomeScore(),
                    game.getAwayTeam(),
                    game.getAwayScore()
            );
        }

        public String getHomeTeam() {
            return homeTeam;
        }

        public int getHomeScore() {
            return homeScore;
        }

        public String getAwayTeam() {
            return awayTeam;
        }

        public int getAwayScore() {
            return awayScore;
        }
    }
}
