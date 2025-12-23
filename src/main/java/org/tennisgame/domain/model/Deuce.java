package org.tennisgame.domain.model;

public record Deuce() implements Score {

    @Override
    public Score nextState(char winner) {
        return new Advantage(winner);
    }

}
