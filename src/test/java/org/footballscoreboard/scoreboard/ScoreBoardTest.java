package org.footballscoreboard.scoreboard;

import org.footballscoreboard.scoreboard.ScoreBoard.ScoreBoardItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

    private ScoreBoard scoreBoard;

    private final String POLAND = "Poland";
    private final String USA = "Usa";
    private final String MEXICO = "Mexico";
    private final String URUGUAY = "Uruguay";
    private final String BRAZIL = "Brazil";
    private final String CHILE = "Chile";

    @BeforeEach
    void init() {
        this.scoreBoard = new ScoreBoard();
    }

    @ParameterizedTest
    @MethodSource("isInvalidTeamNames")
    public void cannotBeStartGameIfAnyParametersIsNullEmptyOrNotDifferent(String homeTeam, String awayTeam) {
        // when
        startGame(homeTeam, awayTeam);

        // then
        assertTrue(scoreBoard.getSummaryGames().isEmpty());
    }

    @Test
    public void startedGameMustHaveSetTeamNamesAndInitialScoreOnZeroZero() {
        // when
        scoreBoard.startGame(POLAND, USA);

        // then
        var resultSet = scoreBoard.getSummaryGames();
        assertEquals(resultSet.size(), 1);

        var result = getFirstScoreBoardItem(resultSet);
        assertEquals(result.getHomeTeam(), POLAND);
        assertEquals(result.getHomeScore(), 0);
        assertEquals(result.getAwayTeam(), USA);
        assertEquals(result.getAwayScore(), 0);
    }

    @Test
    public void cannotBeStartGameIfExistsGameForTheSameTeams() {
        // given
        startGame(POLAND, USA);

        // when
        startGame(POLAND, USA);

        // then
        var resultSet = scoreBoard.getSummaryGames();
        assertEquals(resultSet.size(), 1);
        var result = getFirstScoreBoardItem(resultSet);
        assertEquals(result.getHomeTeam(), POLAND);
        assertEquals(result.getAwayTeam(), USA);
    }

    @Test
    public void canBeFinishStartedGame() {
        // given
        startGame(POLAND, USA);
        startGame(MEXICO, URUGUAY);

        // when
        scoreBoard.finishGame(POLAND, USA);

        // then
        var resultSet = scoreBoard.getSummaryGames();
        assertEquals(resultSet.size(), 1);
        var result = getFirstScoreBoardItem(resultSet);
        assertEquals(result.getHomeTeam(), MEXICO);
        assertEquals(result.getAwayTeam(), URUGUAY);
    }

    @ParameterizedTest
    @MethodSource("isInvalidTeamNames")
    public void canBeReturnFalseDuringUpdatingScoreIfTeamNameParametersIsNullEmptyOrNotDifferent(
            String homeTeam, String awayTeam) {

        // when
        var result = scoreBoard.updateScore(homeTeam, awayTeam, 1, 1);

        // then
        assertFalse(result);
    }

    @Test
    public void canBeReturnFalseDuringUpdatingScoreIfMatchNotExists() {
        // given
        startGame(POLAND, USA);

        // when
        var result = scoreBoard.updateScore(MEXICO, URUGUAY, 1, 0);

        // then
        assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("invalidScore")
    public void canBeReturnFalseDuringUpdatingScoreIfScoreIsLessThanZero(int homeScore, int awayScore) {
        // given
        startGame(POLAND, USA);

        // when
        var result = scoreBoard.updateScore(POLAND, USA, homeScore, awayScore);

        // then
        assertFalse(result);
    }

    @Test
    public void canBeReturnTrueDuringUpdatingScoreIfScoreIsMoreThanZero() {
        // given
        startGame(POLAND, USA);
        int newHomeScore = 1;
        int newAwayScore = 1;

        startGame(MEXICO, URUGUAY);

        // when
        scoreBoard.updateScore(POLAND, USA, newHomeScore, newAwayScore);

        //then
        var resultSet = scoreBoard.getSummaryGames();
        assertEquals(2, resultSet.size());

        ScoreBoardItem[] resultArrays = new ScoreBoardItem[resultSet.size()];
        resultSet.toArray(resultArrays);

        for (ScoreBoardItem item : resultArrays) {
            if (item.getHomeTeam().equals(POLAND)
                    && item.getAwayTeam().equals(USA)) {
                assertEquals(item.getHomeScore(), newHomeScore);
                assertEquals(item.getAwayScore(), newAwayScore);
            } else {
                assertEquals(item.getHomeScore(), 0);
                assertEquals(item.getAwayScore(), 0);
            }
        }
    }

    @Test
    public void canBeReturnCollectionActiveMatches() {
        // given
        // active match
        startGame(POLAND, USA);
        startGame(MEXICO, URUGUAY);

        // finished match
        startGame(BRAZIL, CHILE);
        scoreBoard.finishGame(BRAZIL, CHILE);

        // when
        var summary = scoreBoard.getSummaryGames();

        //then
        assertNotNull(summary);
        assertEquals(summary.size(), 2);
        for (ScoreBoardItem item : summary) {
            var condition = item.getHomeTeam().equals(BRAZIL)
                    && item.getAwayTeam().equals(CHILE);
            assertFalse(condition);
        }
    }

    private ScoreBoardItem getFirstScoreBoardItem(Set<? extends ScoreBoardItem> resultSet) {
        var oResult = resultSet.stream().findFirst();
        assertTrue(oResult.isPresent());
        return oResult.get();
    }

    void startGame(String homeTeam, String awayTeam) {
        scoreBoard.startGame(homeTeam, awayTeam);
    }

    private static Stream<Arguments> isInvalidTeamNames() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, ""),
                Arguments.of("", null),
                Arguments.of("", ""),
                Arguments.of(" ", " "),
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