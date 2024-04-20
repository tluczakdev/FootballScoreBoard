package org.example.model;

import java.util.UUID;

public class Team {

    private final String id = UUID.randomUUID().toString();
    private String name;

    private Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
