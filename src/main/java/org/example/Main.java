package org.example;

import org.example.model.Match;
import org.example.model.Team;
import org.example.persistence.MatchRepository;
import org.example.service.MatchService;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.model.TeamKind.AWAY;
import static org.example.model.TeamKind.HOME;

public class Main {
    public static void main(String[] args) {

        MatchService matchService = new MatchService(new MatchRepository());

        // Poland 3 - 2 USA
        Team poland = new Team("Poland");
        Team usa = new Team("USA");
        Match polandUsa = new Match(poland, usa);
        for (int i = 0; i < 3; i++) {
            matchService.increaseScore(polandUsa, HOME);
        }
        for (int i = 0; i < 2; i++) {
            matchService.increaseScore(polandUsa, AWAY);
        }

        // Italy 6 - 8 France
        Team italy = new Team("Italy");
        Team france = new Team("France");
        Match italyFrance = new Match(italy, france);
        for (int i = 0; i < 6; i++) {
            matchService.increaseScore(italyFrance, HOME);
        }
        for (int i = 0; i < 2; i++) {
            matchService.increaseScore(italyFrance, AWAY);
        }

        // Germany 1 - 2 Uruguay
        Team germany = new Team("Germany");
        Team uruguay = new Team("Uruguay");
        Match germanyUruguay = new Match(germany, uruguay);
        for (int i = 0; i < 1; i++) {
            matchService.increaseScore(germanyUruguay, HOME);
        }
        for (int i = 0; i < 2; i++) {
            matchService.increaseScore(germanyUruguay, AWAY);
        }

        // India 0 - 1 China
        Team india = new Team("India");
        Team china = new Team("China");
        Match indiaChina = new Match(india, china);
        for (int i = 0; i < 1; i++) {
            matchService.increaseScore(indiaChina, AWAY);
        }

        printScoreBoard(matchService);

        // Finish match India - China
        matchService.finishMatch(indiaChina);

        printScoreBoard(matchService);
    }

    private static void printScoreBoard(MatchService matchService) {
        System.out.println("Score Board on Time: " + LocalDateTime.now());
        List<Match> matches = matchService.getScoreBoard();
        matches.forEach(Main::print);
        System.out.println("=".repeat(100));
    }

    private static void print(Match match) {
        System.out.println(String.format(
                "%s %d - %d %s",
                match.getHomeTeam().getName(), match.getHomeScore(),
                match.getAwayScore(), match.getAwayTeam().getName()));
    }
}