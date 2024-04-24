package org.example.model;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;

public class Match {

    private final String id = UUID.randomUUID().toString();

    private int homeScore = 0;
    private int awayScore = 0;

    private Match() {
    }

    public String getId() {
        return id;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }
}
