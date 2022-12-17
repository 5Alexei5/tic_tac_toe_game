package ru.game.tic_tac_toe_game.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Board board;

    private boolean completed;

    @Enumerated(EnumType.STRING)
    private Winner winner;

    private String sessionId;


    public boolean isWinner() {

        Value[][] values = board.getBoard();
        int valueX = Value.X.getNumber() * 3;
        int valueO = Value.O.getNumber() * 3;

        int diagonal1 = 0;
        int diagonal2 = 0;

        for (int i = 0; i < values.length; i++) {
            diagonal1 += values[i][i].getNumber();
            diagonal2 += values[i][2 - i].getNumber();
        }

        if (diagonal1 == valueX || diagonal1 == valueO || diagonal2 == valueX || diagonal2 == valueO) {
            completed = true;
            return true;
        }

        boolean hasEmpty = false;
        int lineY, lineX;

        for (int y = 0; y < values.length; y++) {
            lineY = 0;
            lineX = 0;
            for (int x = 0; x < values[y].length; x++) {

                if (values[y][x] == Value.EMPTY) {
                    hasEmpty = true;
                }

                lineX += values[y][x].getNumber();
                lineY += values[x][y].getNumber();
            }

            if (lineX == valueX || lineX == valueO || lineY == valueX || lineY == valueO) {
                completed = true;
                return true;
            }
        }

        if (!hasEmpty) {
            winner = Winner.STANDOFF;
            completed = true;
        }

        return false;
    }
}
