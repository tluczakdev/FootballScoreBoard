package org.example.service;

import org.example.model.Team;
import org.example.persistence.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamServiceTest {

    private TeamService teamService;

    @BeforeEach
    public void init() {
        this.teamService = new TeamService(new TeamRepository());
    }

    @Test
    public void createTeam() {
        String name = "testTeamName";
        Team team = teamService.createTeam(name);

        assertNotNull(team);
        assertNotNull(team.getId());
        assertNotNull(team.getName());
        assertEquals(team.getName(), name);
    }
}