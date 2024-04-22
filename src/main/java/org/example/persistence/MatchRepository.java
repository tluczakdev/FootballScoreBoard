package org.example.persistence;

import org.example.model.Match;

import java.util.*;

public class MatchRepository implements MemoryRepository<Match, String> {

    private final Map<String, Match> matches = new HashMap<>();

    @Override
    public Match save(Match match) {

        if (match == null) {
            return null;
        }

        return matches.put(extractId(match), match);
    }


    @Override
    public Match fetch(String id) {
        validId(id);
        return matches.get(id);
    }

    @Override
    public List<Match> findAll() {
        return matches.values()
                .stream()
                .toList();
    }

    private String extractId(Match match) {
        String id = match.getId();
        validId(id);
        return id;
    }

    private void validId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Match id cannot be null");
        }
    }
}
