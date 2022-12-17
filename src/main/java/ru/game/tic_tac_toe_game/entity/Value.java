package ru.game.tic_tac_toe_game.entity;

import lombok.Getter;

@Getter
public enum Value {

    X( 10),
    O( 200),
    EMPTY( 0);

    private final int number;

    Value(int number) {
        this.number = number;
    }
}
