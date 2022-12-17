package ru.game.tic_tac_toe_game.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.game.tic_tac_toe_game.exceptions.MoveException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "com.vladmihalcea.hibernate.type.json.JsonStringType")
    private Value[][] board;

    private int step;

    private long gameId;

    public Board(Board board) {
        this.board = Arrays.stream(board.getBoard())
                .map(array -> Arrays.copyOf(array, array.length))
                .collect(Collectors.toList()).toArray(new Value[board.getBoard().length][]);
        step = board.getStep();
        gameId = board.getGameId();
    }

    public void makeMove(Move move, Value value) {
        if (board[move.getY()][move.getX()] == Value.EMPTY) {
            board[move.getY()][move.getX()] = value;
        } else {
            throw new MoveException("Illegal move");
        }
    }

    public void initialize() {
        board = new Value[3][3];
        for (Value[] values : board) {
            Arrays.fill(values, Value.EMPTY);
        }
    }
}
