package ru.game.tic_tac_toe_game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.game.tic_tac_toe_game.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board getByGameIdAndStep(long gameId, int step);
}
