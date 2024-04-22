package org.example.persistence;

import org.example.model.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamRepository implements MemoryRepository<Team, String> {

    private final Map<String, Team> teams = new HashMap<>();

    @Override
    public Team save(Team team) {

        if (team == null) {
            return null;
        }

        return teams.put(extractId(team), team);
    }

    @Override
    public Team fetch(String id) {
        validId(id);
        return teams.get(id);
    }

    @Override
    public List<Team> findAll() {
        return teams.values()
                .stream()
                .toList();
    }

    private String extractId(Team team) {
        String id = team.getId();
        validId(id);
        return id;
    }

    private void validId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Match id cannot be null");
        }
    }
}
