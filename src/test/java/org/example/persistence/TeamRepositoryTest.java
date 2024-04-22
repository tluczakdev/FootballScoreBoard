package org.example.persistence;

import org.example.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamRepositoryTest {

    private TeamRepository teamRepository;

    @BeforeEach
    void init() {
        teamRepository = new TeamRepository();
    }

    @Test
    public void save() {
        //given
        Team homeTeam = new Team("home");

        //when
        teamRepository.save(homeTeam);

        //then
        List<Team> teams = teamRepository.findAll();

        assertEquals(1, teams.size());
        assertEquals(homeTeam, teams.get(0));
    }

    @Test
    public void trySaveNull() {
        //when
        Team team = teamRepository.save(null);

        //then
        assertNull(team);
    }

    @Test
    public void fetch() {
        //given
        Team homeTeam = new Team("home");
        teamRepository.save(homeTeam);

        //when
        Team result = teamRepository.fetch(homeTeam.getId());

        //then
        assertNotNull(result);
        assertEquals(homeTeam, result);
    }

    @Test
    public void fetchInvalidId() {
        //when
        Team result = teamRepository.fetch("-1");

        //then
        assertNull(result);
    }


    @Test
    public void fetchExceptionThrown() {
        //given
        String expectedMessage = "Team id cannot be null";

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            teamRepository.fetch(null);
        });

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findAllIfEmpty() {
        //when
        List<Team> teams = teamRepository.findAll();

        //then
        assertNotNull(teams);
        assertTrue(teams.isEmpty());
    }

    @Test
    public void findAll() {
        //given
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");

        //when
        teamRepository.save(homeTeam);
        teamRepository.save(awayTeam);

        //then
        List<Team> teams = teamRepository.findAll();

        assertEquals(2, teams.size());
        assertTrue(teams.contains(homeTeam));
        assertTrue(teams.contains(awayTeam));
    }
}