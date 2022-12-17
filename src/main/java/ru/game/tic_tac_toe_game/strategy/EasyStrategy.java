package ru.game.tic_tac_toe_game.strategy;

import ru.game.tic_tac_toe_game.entity.Board;
import ru.game.tic_tac_toe_game.entity.Move;
import ru.game.tic_tac_toe_game.entity.Value;

public class EasyStrategy implements Strategy {

    @Override
    public Move getMove(Board board) {
        Value[][] values = board.getBoard();
        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values[y].length; x++) {
                if (values[y][x] == Value.EMPTY) {
                    return new Move(y, x);
                }
            }
        }
        return null;
    }
}
