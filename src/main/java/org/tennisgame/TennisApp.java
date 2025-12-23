package org.tennisgame;

import org.tennisgame.domain.application.PlayTennisGameService;
import org.tennisgame.infra.out.ConsoleScoreAdapter;

public class TennisApp {

    public static void main(String[] args) {

        var presenter = new ConsoleScoreAdapter();

        var gameService = new PlayTennisGameService(presenter);

        String input = "ABABAA";
        System.out.println("--- Starting Tennis Game with sequence: " + input + " ---");
        gameService.play(input);

    }
}