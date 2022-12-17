package ru.game.tic_tac_toe_game.controllres;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.game.tic_tac_toe_game.entity.Game;
import ru.game.tic_tac_toe_game.entity.Move;
import ru.game.tic_tac_toe_game.exceptions.CancelMoveException;
import ru.game.tic_tac_toe_game.exceptions.GameNotFoundException;
import ru.game.tic_tac_toe_game.exceptions.MoveException;
import ru.game.tic_tac_toe_game.services.GameService;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
@Scope("session")
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<Game> getCurrentGame(HttpSession session) {
        Game game = gameService.getGameBySessionId(session.getId());
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Game> newGame(@RequestBody(required = false) String first, HttpSession session) {

        Game game = gameService.createNewGame(first, session.getId());
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @PutMapping()
    public Game move(@RequestBody Move move, HttpSession session) {

        return gameService.move(move, session.getId());
    }

    @PutMapping("/cancel")
    public Game cancelMove(HttpSession session) {

        return gameService.cancelMove(session.getId());
    }

    @DeleteMapping("/{id}")
    public void removeGame(@PathVariable Long id) {
        gameService.remove(id);
    }

    @ExceptionHandler({GameNotFoundException.class, MoveException.class, CancelMoveException.class})
    public ResponseEntity<?> notFound(Exception exception) {

        if (exception.getClass() == GameNotFoundException.class ||
            exception.getClass() == CancelMoveException.class) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
}
