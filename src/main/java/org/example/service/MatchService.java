package org.example.service;

import org.example.model.Match;
import org.example.model.Team;
import org.example.model.TeamKind;
import org.example.persistence.MatchRepository;

import java.util.Comparator;
import java.util.List;

public class MatchService implements IMatchService {

    private final MatchRepository repository;

    public MatchService(MatchRepository repository) {
        this.repository = repository;
    }

    @Override
    public Match startMatch(Team homeTeam, Team awayTeam) {

        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("homeTeam and awayTeam cannot be null");
        }

        return repository.save(new Match(homeTeam, awayTeam));
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

        return repository.save(match);
    }

    @Override
    public void finishMatch(Match match) {

        if (match == null) {
            throw new IllegalArgumentException("match cannot be null");
        }

        match.endMatch();
        repository.save(match);
    }

    @Override
    public List<Match> getScoreBoard() {
        return repository.findAll()
                .stream()
                .filter(match -> match.getEndMatch() == null)
                .sorted(Comparator.comparing(Match::getStartMatch))
                .toList();
    }
}
