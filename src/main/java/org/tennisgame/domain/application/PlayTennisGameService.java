package org.tennisgame.domain.application;

import lombok.RequiredArgsConstructor;
import org.tennisgame.domain.model.*;
import org.tennisgame.domain.port.out.ScoreOutputPort;
import org.tennisgame.domain.port.in.PlayTennisGameUseCase;

@RequiredArgsConstructor
public class PlayTennisGameService implements PlayTennisGameUseCase {
    private final ScoreOutputPort outputPort;

    @Override
    public void play(String sequence) {
        Score score = new PointScore(0, 0);
        for (char winner : sequence.toCharArray()) {
            score = score.nextState(winner);
            outputPort.display(score);
            if (score instanceof GameWon) {
                break;
            }
        }
    }
}
