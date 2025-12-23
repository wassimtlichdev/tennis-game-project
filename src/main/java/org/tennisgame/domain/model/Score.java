package org.tennisgame.domain.model;


public sealed interface Score permits PointScore, Deuce, Advantage, GameWon {
    Score nextState(char winner);
}

