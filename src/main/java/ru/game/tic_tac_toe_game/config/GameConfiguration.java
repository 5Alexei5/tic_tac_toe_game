package ru.game.tic_tac_toe_game.config;

import lombok.Getter;
import ru.game.tic_tac_toe_game.entity.Value;
import ru.game.tic_tac_toe_game.strategy.Strategy;

@Getter
public class GameConfiguration {

    private final Value figureUser;
    private final Value figureMachine;
    private final Strategy strategy;

    public GameConfiguration(Strategy strategy, String figureUser) {
        this.figureUser = Value.valueOf(figureUser);
        figureMachine = this.figureUser == Value.X ? Value.O : Value.X;
        this.strategy = strategy;

    }
}
