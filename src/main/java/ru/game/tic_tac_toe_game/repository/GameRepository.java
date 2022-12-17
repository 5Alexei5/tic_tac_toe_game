package ru.game.tic_tac_toe_game.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.game.tic_tac_toe_game.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

    Game getBySessionId(String sessionId);
}
