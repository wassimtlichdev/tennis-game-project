package org.tennisgame.domain.model;

public record GameWon(char winner) implements Score {

    @Override
    public Score nextState(char winner) {
            return this;
    }

}
