package org.footballscoreboard.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

    @Disabled
    @Test
    public void startMatchExceptionThrowIfProblemWithAddToMemory() throws Exception {
        // given
        String homeTeam = "Poland";
        String awayTeam = "USA";
        String expectedMessage = "Problem with add match in memory";

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


}