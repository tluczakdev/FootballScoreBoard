package org.example.service;

import org.example.model.Match;
import org.example.model.Team;
import org.example.persistence.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.example.model.TeamKind.AWAY;
import static org.example.model.TeamKind.HOME;
import static org.junit.jupiter.api.Assertions.*;

class MatchServiceTest {

    private MatchService matchService;

    @BeforeEach
    public void init() {
        this.matchService = new MatchService(new MatchRepository());
    }

    @Test
    public void startMatchValidCorrectData() {
        //given
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");

        //when
        Match match = matchService.startMatch(homeTeam, awayTeam);

        //then
        assertNotNull(match);

        assertNotNull(match.getId());

        assertNotNull(match.getStartMatch());
        assertTrue(match.getStartMatch().isBefore(LocalDateTime.now()));

        assertNull(match.getEndMatch());

        assertEquals(match.getAwayScore(), 0);
        assertEquals(match.getHomeScore(), 0);

        assertNotNull(match.getHomeTeam());
        assertNotNull(match.getAwayTeam());
    }

    @Test
    public void startMatchValidDifferentTeams() {
        //given
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");

        //when
        Match match = matchService.startMatch(homeTeam, awayTeam);

        //then
        assertNotEquals(match.getHomeTeam(), match.getAwayTeam());
    }

    @ParameterizedTest
    @MethodSource("incorrectPairTeamsInMatch")
    public void startMatchExceptionThrown(Team homeTeam, Team awayTeam) {
        //given
        String expectedMessage = "homeTeam and awayTeam cannot be null";

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            matchService.startMatch(homeTeam, awayTeam);
        });

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void increaseScoreCorrectData() {
        //given
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");
        Match match = matchService.startMatch(homeTeam, awayTeam);

        int homeScore = match.getHomeScore();
        int expectedHomeScore = homeScore + 1;
        int awayScore = match.getAwayScore();
        int expectedAwayScore = awayScore + 1;

        //when
        matchService.increaseScore(match, HOME);
        matchService.increaseScore(match, AWAY);

        //then
        assertEquals(expectedHomeScore, match.getHomeScore());
        assertEquals(expectedAwayScore, match.getAwayScore());
    }

    @Test
    public void increaseScoreExceptionThrownIfMatchIsNull() {
        //given
        String expectedMessage = "match cannot be null";

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            matchService.increaseScore(null, HOME);
        });

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void finishMatchExceptionThrownIfTeamKindIsNull() {
        //given
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");
        Match match = matchService.startMatch(homeTeam, awayTeam);

        String expectedMessage = "teamKind cannot be null";

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            matchService.increaseScore(match, null);
        });

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void finishMatchExceptionThrownIfTeamKindIsInvalid() {
        //given
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");
        Match match = matchService.startMatch(homeTeam, awayTeam);

        String expectedMessage = "team kind not supported";

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            matchService.increaseScore(match, null); // TODO fix me!!
        });

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void finishMatchCorrectData() {
        //given
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");
        Match match = matchService.startMatch(homeTeam, awayTeam);

        //when
        matchService.finishMatch(match);

        //then
        assertNotNull(match.getEndMatch());
        assertTrue(match.getStartMatch().isBefore(match.getEndMatch()));
    }

    @Test
    public void finishMatchExceptionThrownIfMatchIsNull() {
        //given
        String expectedMessage = "match cannot be null";

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            matchService.finishMatch(null);
        });

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getScoreBoard(){
        //given
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");
        Match firstMatch = matchService.startMatch(homeTeam, awayTeam);
        Match secondMatch = matchService.startMatch(homeTeam, awayTeam);

        //when
        List<Match> scoreBoard = matchService.getScoreBoard();

        //then
        assertNotNull(scoreBoard);
        assertEquals(2, scoreBoard.size());
        assertEquals(firstMatch, scoreBoard.get(0));
        assertEquals(secondMatch, scoreBoard.get(1));
    }

    private static Stream<Arguments> incorrectPairTeamsInMatch() {
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(homeTeam, null),
                Arguments.of(null, awayTeam)
        );
    }
}