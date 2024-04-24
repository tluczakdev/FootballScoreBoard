package org.footballscoreboard;

import org.footballscoreboard.scoreboard.ScoreBoard;
import org.footballscoreboard.scoreboard.Game;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        ScoreBoard scoreBoard = new ScoreBoard();

        Game polandUSA = scoreBoard.startGame("Poland", "USA");
        scoreBoard.updateScore(polandUSA, 0, 1);

        Game mexicoUruquay = scoreBoard.startGame("Mexico", "Uruguay");
        scoreBoard.updateScore(mexicoUruquay, 3, 1);

        Game brazilChile = scoreBoard.startGame("Brazil", "Chile");
        scoreBoard.updateScore(brazilChile, 2, 1);

        Game franceGermany = scoreBoard.startGame("France", "Germany");

        printSummaryGames(scoreBoard);

        // finished match
        scoreBoard.finishGame(franceGermany);

        printSummaryGames(scoreBoard);

    }

    private static void printSummaryGames(ScoreBoard scoreBoard) {
        var summaryGames = scoreBoard.getSummaryGames();
        System.out.println("=".repeat(50));
        System.out.println("Summary games: " + LocalDateTime.now());
        summaryGames.forEach(Main::printGame);
        System.out.println("=".repeat(50));
    }

    private static void printGame(Game game) {
        System.out.printf("%s %d - %s %d%n",
                game.getHomeTeam(), game.getHomeScore(),
                game.getAwayTeam(), game.getAwayScore());
    }
}