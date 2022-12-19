package ru.game.tic_tac_toe_game.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.game.tic_tac_toe_game.config.GameConfiguration;
import ru.game.tic_tac_toe_game.entity.Board;
import ru.game.tic_tac_toe_game.entity.Game;
import ru.game.tic_tac_toe_game.entity.Move;
import ru.game.tic_tac_toe_game.entity.Winner;
import ru.game.tic_tac_toe_game.exceptions.CancelMoveException;
import ru.game.tic_tac_toe_game.exceptions.GameNotFoundException;
import ru.game.tic_tac_toe_game.exceptions.MoveException;
import ru.game.tic_tac_toe_game.repository.BoardRepository;
import ru.game.tic_tac_toe_game.repository.GameRepository;
import ru.game.tic_tac_toe_game.strategy.Strategy;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class GameService {

    private static final String USER = "USER";

    private final GameRepository gameRepository;
    private final BoardRepository boardRepository;
    private final GameConfiguration configuration;
    private final Strategy gameStrategy;


    public Game createNewGame(String first, String sessionId) {

        Board board = new Board();
        board.initialize();

        Game game = gameRepository.getBySessionId(sessionId);
        if (game != null) {
            gameRepository.delete(game);
        }

        game = Game.builder()
                    .board(board)
                    .sessionId(sessionId)
                    .build();

        if (!USER.equals(first)) {
            Move move = gameStrategy.getMove(board);
            board.makeMove(move, configuration.getFigureMachine());
        }

        gameRepository.save(game);
        board.setGameId(game.getId());

        return game;
    }

    public void remove(Long id) {
        Optional<Game> game = gameRepository.findById(id);
        gameRepository.delete(game.orElseThrow(GameNotFoundException::new));
    }

    public Game move(Move move, String sessionId) {

        Game game = gameRepository.getBySessionId(sessionId);

        if (game.isCompleted()) {
            throw new MoveException("Game is completed");
        }
        Board board = new Board(game.getBoard());
        game.setBoard(board);
        board.makeMove(move, configuration.getFigureUser());

        if (game.isWinner()) {
            game.setWinner(Winner.USER);
            board.setStep(board.getStep() + 1);
        } else
        if (!game.isCompleted()) {
            board.makeMove(gameStrategy.getMove(board), configuration.getFigureMachine());
            board.setStep(board.getStep() + 1);
            if (game.isWinner()) {
                game.setWinner(Winner.MACHINA);
            }
        }

        gameRepository.save(game);

        return game;
    }

    public Game cancelMove(String sessionId) {

        Game game = gameRepository.getBySessionId(sessionId);
        if (game.isCompleted()) {
            game.setCompleted(false);
            game.setWinner(null);
        }
        int step = game.getBoard().getStep() - 1;
        Board board = boardRepository.getByGameIdAndStep(game.getId(), step);
        if (board == null) {
            throw new CancelMoveException();
        }

        game.setBoard(board);
        gameRepository.save(game);

        return game;
    }

    @Transactional(readOnly = true)
    public Game getGameBySessionId(String sessionId) {

        Game game = gameRepository.getBySessionId(sessionId);
        if (game == null) {
            throw new GameNotFoundException();
        }
        return game;
    }
}
