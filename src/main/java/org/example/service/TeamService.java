package org.example.service;

import org.example.model.Team;

public class TeamService implements ITeamService {

    @Override
    public Team createTeam(String name) {
        return new Team(name);
    }
}
