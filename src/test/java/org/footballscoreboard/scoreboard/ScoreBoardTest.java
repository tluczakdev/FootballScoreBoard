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
    public void startMatchExceptionThrowIfParametersIsNullOrEmpty(String homeTeam, String awayTeam) {
        // given
        String expectedMessage = "Parameter 'homeTeam' and/or 'awayTeam' cannot be null and empty";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.startMatch(homeTeam, awayTeam);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @MethodSource("invalidTeamNames")
    public void startMatchExceptionThrowIfParametersAreNotDifferent(String homeTeam, String awayTeam) {
        // given
        String expectedMessage = String.format("Parameter 'homeTeam' = [%s] and 'awayTeam' [%s] must be different", homeTeam, awayTeam);

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.startMatch(homeTeam, awayTeam);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void startMatchCorrectParameters() {
        // given
        String homeTeam = "Poland";
        String awayTeam = "USA";

        // when
        Match match = scoreBoard.startMatch(homeTeam, awayTeam);

        // then
        assertNotNull(match);
        assertEquals(homeTeam, match.getHomeTeam());
        assertEquals(awayTeam, match.getAwayTeam());
    }

    @Test
    public void startMatchValidInitialScore() {
        // given
        String homeTeam = "Poland";
        String awayTeam = "USA";

        // when
        Match match = scoreBoard.startMatch(homeTeam, awayTeam);

        // then
        assertNotNull(match);
        assertEquals(match.getHomeScore(), 0);
        assertEquals(match.getAwayScore(), 0);
    }

    @Test
    public void startMatchAddMatchToCollection() {
        // given
        String homeTeam = "Poland";
        String awayTeam = "USA";

        // when
        Match match = scoreBoard.startMatch(homeTeam, awayTeam);

        // then
        assertNotNull(match);
        assertEquals(scoreBoard.getMatches().size(), 1);
        assertTrue(scoreBoard.getMatches().contains(match));
    }

    @Test
    public void startMatchExceptionThrowIfMatchExist() {
        // given
        String homeTeam = "Poland";
        String awayTeam = "USA";
        scoreBoard.startMatch(homeTeam, awayTeam);

        String expectedMessage = "Can't start new match, because it already exists";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.startMatch(homeTeam, awayTeam);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Disabled
    @Test
    public void startMatchExceptionThrowIfProblemWithAddToMemory() throws Exception {
        // given
        String homeTeam = "Poland";
        String awayTeam = "USA";
        String expectedMessage = "Internal error. Problem with start match";

        LinkedHashSet<Match> mockMatches = mock(LinkedHashSet.class);

        ScoreBoard mockScoreBoard = mock(ScoreBoard.class);

        Field field = mockScoreBoard.getClass().getDeclaredField("matches");
        field.setAccessible(true);
        field.set(mockScoreBoard, mockMatches);

        when(mockMatches.add(any(Match.class))).thenReturn(false);

        // when
        Exception exception = assertThrows(RuntimeException.class, () -> {
            mockScoreBoard.startMatch(homeTeam, awayTeam);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @NullSource
    public void finishMatchExceptionThrowIfMatchIsNull(Match match) {
        // given
        String expectedMessage = "Match cannot be null";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.finishMatch(match);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void finishMatchCorrectParameters() {
        // given
        Match firstMatch = scoreBoard.startMatch("Poland", "USA");
        Match secondMatch = scoreBoard.startMatch("Mexico", "Uruguay");
        Match notExistsMatch = new Match("Brazil", "Chile");

        // when
        scoreBoard.finishMatch(secondMatch);
        scoreBoard.finishMatch(notExistsMatch);

        // then
        assertEquals(scoreBoard.getMatches().size(), 1);
        assertTrue(scoreBoard.getMatches().contains(firstMatch));
    }

    @Disabled
    @Test
    public void finishMatchExceptionThrowIfProblemWithRemoveFromMemory() throws Exception {
        // given
        String expectedMessage = "Internal error. Problem with finish match";

        LinkedHashSet<Match> mockMatches = mock(LinkedHashSet.class);

        ScoreBoard mockScoreBoard = mock(ScoreBoard.class);

        Field field = mockScoreBoard.getClass().getDeclaredField("matches");
        field.setAccessible(true);
        field.set(mockScoreBoard, mockMatches);

        when(mockMatches.remove(any(Match.class))).thenReturn(false);

        // when
        Exception exception = assertThrows(RuntimeException.class, () -> {
            mockScoreBoard.finishMatch(any(Match.class));
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @NullSource
    public void updateScoreExceptionThrowIfMatchIsNull(Match match) {
        // given
        String expectedMessage = "Match cannot be null";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateScore(match, 1, 1);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateScoreExceptionThrowIfMatchNotExists() {
        // given
        Match match = new Match("Poland", "USA");
        String exceptedMessage = "Match does not exist";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateScore(match, 1, 1);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(exceptedMessage));
    }

    @ParameterizedTest
    @MethodSource("invalidScore")
    public void updateScoreExceptionThrowIfScoreIdLessThanZero(int homeScore, int awayScore) {
        // given
        Match match = new Match("Poland", "USA");
        String exceptedMessage = "Score id must be greater than zero";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateScore(match, homeScore, awayScore);
        });

        // then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(exceptedMessage));
    }

    @Test
    public void updateScoreCorrectParameters() {
        // given
        Match match = scoreBoard.startMatch("Poland", "USA");
        Match toUpdatMatch = scoreBoard.startMatch("Brazil", "Chile");
        int newHomeScore = 1;
        int newAwayScore = 1;

        // when
        scoreBoard.updateScore(toUpdatMatch, newHomeScore, newAwayScore);

        //then
        LinkedHashSet<Match> matches = scoreBoard.getMatches();
        assertEquals(2, matches.size());
        assertTrue(matches.contains(match));

        Match[] arrayMatches = new Match[matches.size()];
        matches.toArray(arrayMatches);

        Match updatedMatch = arrayMatches[1];
        assertEquals(updatedMatch.getHomeTeam(), toUpdatMatch.getHomeTeam());
        assertEquals(updatedMatch.getHomeScore(), newHomeScore);
        assertEquals(updatedMatch.getAwayTeam(), toUpdatMatch.getAwayTeam());
        assertEquals(updatedMatch.getAwayScore(), toUpdatMatch.getAwayScore());
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