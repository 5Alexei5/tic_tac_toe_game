package ru.game.tic_tac_toe_game.strategy;

import ru.game.tic_tac_toe_game.entity.Board;
import ru.game.tic_tac_toe_game.entity.Move;

public interface Strategy {

    Move getMove(Board board);
}
