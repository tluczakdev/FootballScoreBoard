package org.footballscoreboard.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ScoreBoardTest {

    private ScoreBoard scoreBoard;

    @BeforeEach
    void init() {
        this.scoreBoard = new ScoreBoard();
    }

    @ParameterizedTest
    @MethodSource("nullOrEmptyTeamNames")
    public void startGameExceptionThrowIfParametersIsNullOrEmpty(String homeTeam, String awayTeam) {
        // given
        String expectedMessage = "Parameter 'homeTeam' and/or 'awayTeam' cannot be null and empty";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.startGame(homeTeam, awayTeam);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @MethodSource("invalidTeamNames")
    public void startGameExceptionThrowIfParametersAreNotDifferent(String homeTeam, String awayTeam) {
        // given
        String expectedMessage = String.format("Parameter 'homeTeam' = [%s] and 'awayTeam' [%s] must be different", homeTeam, awayTeam);

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.startGame(homeTeam, awayTeam);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void startGameCorrectParameters() {
        // given
        String homeTeam = "Poland";
        String awayTeam = "USA";

        // when
        Game game = scoreBoard.startGame(homeTeam, awayTeam);

        // then
        assertNotNull(game);
        assertEquals(homeTeam, game.getHomeTeam());
        assertEquals(awayTeam, game.getAwayTeam());
    }

    @Test
    public void startGameValidInitialScore() {
        // given
        String homeTeam = "Poland";
        String awayTeam = "USA";

        // when
        Game game = scoreBoard.startGame(homeTeam, awayTeam);

        // then
        assertNotNull(game);
        assertEquals(game.getHomeScore(), 0);
        assertEquals(game.getAwayScore(), 0);
    }

    @Test
    public void startMatchAddGameToCollection() {
        // given
        String homeTeam = "Poland";
        String awayTeam = "USA";

        // when
        Game game = scoreBoard.startGame(homeTeam, awayTeam);

        // then
        assertNotNull(game);
        assertEquals(scoreBoard.getSummaryGames().size(), 1);
        assertTrue(scoreBoard.getSummaryGames().contains(game));
    }

    @Test
    public void startMatchExceptionThrowIfGameExist() {
        // given
        String homeTeam = "Poland";
        String awayTeam = "USA";
        scoreBoard.startGame(homeTeam, awayTeam);

        String expectedMessage = "Can't start new game, because it already exists";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.startGame(homeTeam, awayTeam);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Disabled
    @Test
    public void startGameExceptionThrowIfProblemWithAddToMemory() throws Exception {
        // given
        String homeTeam = "Poland";
        String awayTeam = "USA";
        String expectedMessage = "Internal error. Problem with start game";

        LinkedHashSet<Game> mockGames = mock(LinkedHashSet.class);

        ScoreBoard mockScoreBoard = mock(ScoreBoard.class);

        Field field = mockScoreBoard.getClass().getDeclaredField("games");
        field.setAccessible(true);
        field.set(mockScoreBoard, mockGames);

        when(mockGames.add(any(Game.class))).thenReturn(false);

        // when
        Exception exception = assertThrows(RuntimeException.class, () -> {
            mockScoreBoard.startGame(homeTeam, awayTeam);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @NullSource
    public void finishMatchExceptionThrowIfGameIsNull(Game game) {
        // given
        String expectedMessage = "Game cannot be null";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.finishGame(game);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void finishGameCorrectParameters() {
        // given
        Game firstGame = scoreBoard.startGame("Poland", "USA");
        Game secondGame = scoreBoard.startGame("Mexico", "Uruguay");
        Game notExistsGame = new Game("Brazil", "Chile");

        // when
        scoreBoard.finishGame(secondGame);
        scoreBoard.finishGame(notExistsGame);

        // then
        assertEquals(scoreBoard.getSummaryGames().size(), 1);
        assertTrue(scoreBoard.getSummaryGames().contains(firstGame));
    }

    @Disabled
    @Test
    public void finishGameExceptionThrowIfProblemWithRemoveFromMemory() throws Exception {
        // given
        String expectedMessage = "Internal error. Problem with finish game";

        LinkedHashSet<Game> mockGames = mock(LinkedHashSet.class);

        ScoreBoard mockScoreBoard = mock(ScoreBoard.class);

        Field field = mockScoreBoard.getClass().getDeclaredField("games");
        field.setAccessible(true);
        field.set(mockScoreBoard, mockGames);

        when(mockGames.remove(any(Game.class))).thenReturn(false);

        // when
        Exception exception = assertThrows(RuntimeException.class, () -> {
            mockScoreBoard.finishGame(any(Game.class));
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @NullSource
    public void updateScoreExceptionThrowIfMatchIsNull(Game game) {
        // given
        String expectedMessage = "Game cannot be null";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateScore(game, 1, 1);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateScoreExceptionThrowIfMatchNotExists() {
        // given
        Game game = new Game("Poland", "USA");
        String exceptedMessage = "Game does not exist";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateScore(game, 1, 1);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(exceptedMessage));
    }

    @ParameterizedTest
    @MethodSource("invalidScore")
    public void updateScoreExceptionThrowIfScoreIdLessThanZero(int homeScore, int awayScore) {
        // given
        Game game = new Game("Poland", "USA");
        String exceptedMessage = "Score id must be greater than zero";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateScore(game, homeScore, awayScore);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(exceptedMessage));
    }

    @Test
    public void updateScoreCorrectParameters() {
        // given
        Game game = scoreBoard.startGame("Poland", "USA");
        Game toUpdatGame = scoreBoard.startGame("Brazil", "Chile");
        int newHomeScore = 1;
        int newAwayScore = 1;

        // when
        scoreBoard.updateScore(toUpdatGame, newHomeScore, newAwayScore);

        //then
        LinkedHashSet<Game> games = scoreBoard.getSummaryGames();
        assertEquals(2, games.size());
        assertTrue(games.contains(game));

        Game[] arrayGames = new Game[games.size()];
        games.toArray(arrayGames);

        Game updatedGame = arrayGames[1];
        assertEquals(updatedGame.getHomeTeam(), toUpdatGame.getHomeTeam());
        assertEquals(updatedGame.getHomeScore(), newHomeScore);
        assertEquals(updatedGame.getAwayTeam(), toUpdatGame.getAwayTeam());
        assertEquals(updatedGame.getAwayScore(), toUpdatGame.getAwayScore());
    }

    @Test
    public void getSummaryGames() {
        // given
        // active match
        Game polandUSA = scoreBoard.startGame("Poland", "USA");
        scoreBoard.updateScore(polandUSA, 0, 1);

        Game mexicoUruquay = scoreBoard.startGame("Mexico", "Uruguay");
        scoreBoard.updateScore(mexicoUruquay, 3, 1);

        Game brazilChile = scoreBoard.startGame("Brazil", "Chile");
        scoreBoard.updateScore(brazilChile, 2, 1);

        // finished match
        Game franceGermany = scoreBoard.startGame("France", "Germany");
        scoreBoard.finishGame(franceGermany);

        // when
        var summary = scoreBoard.getSummaryGames();

        //then
        assertNotNull(summary);
        assertEquals(summary.size(), 3);
        assertTrue(summary.contains(polandUSA));
        assertTrue(summary.contains(mexicoUruquay));
        assertTrue(summary.contains(brazilChile));
    }

    private static Stream<Arguments> nullOrEmptyTeamNames() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, ""),
                Arguments.of("", null),
                Arguments.of("", ""),
                Arguments.of(" ", " ")
        );
    }

    private static Stream<Arguments> invalidTeamNames() {
        return Stream.of(
                Arguments.of("poland", "poland"),
                Arguments.of("Poland", "Poland"),
                Arguments.of("poland", "Poland"),
                Arguments.of(" poland", "poland"),
                Arguments.of("poland ", "poland")
        );
    }

    private static Stream<Arguments> invalidScore() {
        return Stream.of(
                Arguments.of(-1, 1),
                Arguments.of(1, -1),
                Arguments.of(-1, -1)
        );
    }

}