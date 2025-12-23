package org.tennisgame.domain.model;

public record Advantage(char player) implements Score {
    @Override
    public Score nextState(char winner) {
        return player == winner
                ? new GameWon(winner)
                : new Deuce();
    }

}
