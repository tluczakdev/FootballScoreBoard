package org.footballscoreboard;

import org.footballscoreboard.scoreboard.ScoreBoard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final String POLAND = "Poland";
    private static final String USA = "USA";
    private static final String MEXICO = "Mexico";
    private static final String URUGUAY = "Uruguay";
    private static final String BRAZIL = "Brazil";
    private static final String CHILE = "Chile";
    private static final long SLEEP = 3;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {

        ScoreBoard scoreBoard = new ScoreBoard();

        // Poland - USA
        printComment("Start Poland vs USA");
        scoreBoard.startGame(POLAND, USA); // start Match Poland - USA
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("USA Gol");
        scoreBoard.updateScore(POLAND, USA, 0, 1); // Poland - USA 0 : 1 -- increase score
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("USA correct Gol");
        scoreBoard.updateScore(POLAND, USA, 0, 0); // Poland - USA 0 : 0 -- correct score
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("USA Gol");
        scoreBoard.updateScore(POLAND, USA, 0, 1); // Poland - USA 0 : 1 -- increase score
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("USA Gol");
        scoreBoard.updateScore(POLAND, USA, 0, 2); // Poland - USA 0 : 2 -- increase score
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("USA Gol");
        scoreBoard.updateScore(POLAND, USA, 0, 3); // Poland - USA 0 : 3 -- increase score
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Poland Gol");
        scoreBoard.updateScore(POLAND, USA, 1, 3); // Poland - USA 1 : 3 -- increase score
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Poland Gol");
        scoreBoard.updateScore(POLAND, USA, 2, 3); // Poland - USA 2 : 3 -- increase score
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Poland Gol");
        scoreBoard.updateScore(POLAND, USA, 3, 3); // Poland - USA 3 : 3 -- increase score
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Start Mexico vs Uruguay");
        scoreBoard.startGame(MEXICO, URUGUAY); // start Match Mexico - Uruguay
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Poland Gol");
        scoreBoard.updateScore(POLAND, USA, 4, 1); // Poland - USA 4 : 3 -- increase score
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Mexico Gol");
        scoreBoard.updateScore(MEXICO, URUGUAY, 1, 0); // Mexico - Uruguay 1 : 0 -- increase score
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Mexico Gol");
        scoreBoard.updateScore(MEXICO, URUGUAY, 2, 0); // Mexico - Uruguay 2 : 0 -- increase score
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Finish Poland vs USA");
        scoreBoard.finishGame(POLAND, USA); // finish match Poland - USA
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Start Brazil vs Chile");
        scoreBoard.startGame(BRAZIL, CHILE); // start Match Brazil - Chile
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Brazil Gol");
        scoreBoard.updateScore(BRAZIL, CHILE, 1, 0); // Brazil - Chile 1 : 0 -- increase score
        printSummaryGames(scoreBoard);

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Brazil Gol");
        scoreBoard.updateScore(BRAZIL, CHILE, 2, 0); // Brazil - Chile 2 : 0 -- increase score
        printSummaryGames(scoreBoard);  // print Score Board

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Finish Mexico vs Uruguay");
        scoreBoard.finishGame(MEXICO, URUGUAY);
        printSummaryGames(scoreBoard);

        TimeUnit.SECONDS.sleep(SLEEP);
        printComment("Finish Brazil vs Chile");
        scoreBoard.finishGame(BRAZIL, CHILE);
        printSummaryGames(scoreBoard);  // print Score Board
    }

    private static void printComment(String comment) {
        System.out.println("\n ### " + comment + " ### \n");
    }

    private static void printSummaryGames(ScoreBoard scoreBoard) {
        var summaryGames = scoreBoard.getSummaryGames();
        System.out.println("=".repeat(50));
        System.out.println("Summary games: " + LocalDateTime.now().format(DATE_TIME_FORMATTER));
        summaryGames.forEach(Main::printGame);
        System.out.println("=".repeat(50));
    }

    private static void printGame(ScoreBoard.ScoreBoardItem scoreBoardItem) {
        System.out.printf("%s %d - %s %d%n",
                scoreBoardItem.getHomeTeam(), scoreBoardItem.getHomeScore(),
                scoreBoardItem.getAwayTeam(), scoreBoardItem.getAwayScore());
    }
}