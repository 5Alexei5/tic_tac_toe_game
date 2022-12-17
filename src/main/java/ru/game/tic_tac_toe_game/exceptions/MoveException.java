package ru.game.tic_tac_toe_game.exceptions;

public class MoveException extends RuntimeException {

    public MoveException() {
    }

    public MoveException(Throwable cause) {
        super(cause);
    }

    public MoveException(String message) {
        super(message);
    }
}
