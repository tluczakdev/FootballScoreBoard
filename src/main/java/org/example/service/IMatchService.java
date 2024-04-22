package org.example.service;

import org.example.model.Match;
import org.example.model.Team;
import org.example.model.TeamKind;

public interface IMatchService {

    Match startMatch(Team homeTeam, Team awayTeam);

    Match increaseScore(Match match, TeamKind teamKind);

    void finishMatch(Match match);
}
