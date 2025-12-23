package org.tennisgame.domain.port.out;

import org.tennisgame.domain.model.Score;

public interface ScoreOutputPort {
    void display(Score score);
}
