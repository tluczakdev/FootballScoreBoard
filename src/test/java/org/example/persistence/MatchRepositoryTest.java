package org.example.persistence;

import org.example.model.Match;
import org.example.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchRepositoryTest {

    private MatchRepository matchRepository;

    @BeforeEach
    void init() {
        matchRepository = new MatchRepository();
    }

    @Test
    public void save() {
        //given
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");
        Match match = new Match(homeTeam, awayTeam);

        //when
        matchRepository.save(match);

        //then
        List<Match> matches = matchRepository.findAll();

        assertEquals(1, matches.size());
        assertEquals(match, matches.get(0));
    }

    @Test
    public void trySaveNull() {
        //when
        Match match = matchRepository.save(null);

        //then
        assertNull(match);
    }

    @Test
    public void fetch() {
        //given
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");
        Match match = new Match(homeTeam, awayTeam);
        matchRepository.save(match);

        //when
        Match result = matchRepository.fetch(match.getId());

        //then
        assertNotNull(result);
        assertEquals(match, result);
    }

    @Test
    public void fetchInvalidId() {
        //when
        Match result = matchRepository.fetch("-1");

        //then
        assertNull(result);
    }


    @Test
    public void fetchExceptionThrown() {
        //given
        String expectedMessage = "Match id cannot be null";

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            matchRepository.fetch(null);
        });

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findAllIfEmpty() {
        //when
        List<Match> matches = matchRepository.findAll();

        //then
        assertNotNull(matches);
        assertTrue(matches.isEmpty());
    }

    @Test
    public void findAll() {
        //given
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");
        Match firstMatch = new Match(homeTeam, awayTeam);
        Match secondMatch = new Match(homeTeam, awayTeam);

        //when
        matchRepository.save(firstMatch);
        matchRepository.save(secondMatch);

        //then
        List<Match> matches = matchRepository.findAll();

        assertNotNull(matches);
        assertEquals(2, matches.size());
        assertTrue(matches.contains(firstMatch));
        assertTrue(matches.contains(secondMatch));
    }
}