package ru.game.tic_tac_toe_game.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.game.tic_tac_toe_game.strategy.EasyStrategy;
import ru.game.tic_tac_toe_game.strategy.Strategy;

@Getter
@Setter
@Configuration
@NoArgsConstructor
@ConfigurationProperties(prefix = "game")
public class GameProperties {

    private String level = "easy";

    private String figureUser = "X";



    @Bean
    public GameConfiguration gameConfiguration() {
        return new GameConfiguration(gameStrategy(), figureUser);
    }

    @Bean
    public Strategy gameStrategy() {
        if (level.equals("easy")) {
            return new EasyStrategy();
        }
        return new EasyStrategy();
    }

}
