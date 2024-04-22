package org.example.service;

import org.example.model.Team;
import org.example.persistence.TeamRepository;

public class TeamService implements ITeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team createTeam(String name) {
        return teamRepository.save(new Team(name));
    }
}
