package org.tennisgame.infra.out;

import org.tennisgame.domain.model.*;
import org.tennisgame.domain.port.out.ScoreOutputPort;

public class ConsoleScoreAdapter implements ScoreOutputPort {
    @Override
    public void display(Score score) {
        System.out.println(getScoreToShow(score));
    }

    private String getScoreToShow(Score score) {
        return switch (score) {
            case PointScore p -> "Player A : " + p.scoreA() + " / Player B : " + p.scoreB();
            case Deuce d -> "Deuce";
            case Advantage a -> "Advantage Player " + a.player();
            case GameWon g -> "Player " + g.winner() + " wins the game";
        };
    }
}
