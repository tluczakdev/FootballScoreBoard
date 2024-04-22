package org.example.service;

import org.example.model.Match;
import org.example.model.Team;
import org.example.model.TeamKind;

public class MatchService implements IMatchService {

    @Override
    public Match startMatch(Team homeTeam, Team awayTeam) {

        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("homeTeam and awayTeam cannot be null");
        }

        return new Match(homeTeam, awayTeam);
    }

    @Override
    public Match increaseScore(Match match, TeamKind teamKind) {

        if (match == null) {
            throw new IllegalArgumentException("match cannot be null");
        }

        if(teamKind == null) {
            throw new IllegalArgumentException("teamKind cannot be null");
        }

        switch (teamKind) {
            case HOME:
                match.increaseHomeScore();
                break;
            case AWAY:
                match.increaseAwayScore();
                break;
            default:
                throw new IllegalArgumentException("team kind not supported");
        }

        return match;
    }

    @Override
    public void finishMatch(Match match) {

        if (match == null) {
            throw new IllegalArgumentException("match cannot be null");
        }

        match.endMatch();
    }
}
