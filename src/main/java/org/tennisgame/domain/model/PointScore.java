package org.tennisgame.domain.model;

public record PointScore(int scoreA, int scoreB) implements Score {
    @Override
    public Score nextState(char winner) {
        int a = scoreA;
        int b = scoreB;

        if (winner == 'A') {
            a = nextPoint(scoreA);
        } else if (winner == 'B') {
            b = nextPoint(scoreB);
        } else {
            throw new IllegalArgumentException("Invalid player: " + winner);
        }

        if (a == 40 && b == 40) {
            return new Deuce();
        }

        if (a > 40) {
            return new GameWon('A');
        }

        if (b > 40) {
            return new GameWon('B');
        }

        return new PointScore(a, b);
    }



    private int nextPoint(int score) {
        return switch (score) {
            case 0  -> 15;
            case 15 -> 30;
            case 30 -> 40;
            default ->  50;
        };
    }
}
