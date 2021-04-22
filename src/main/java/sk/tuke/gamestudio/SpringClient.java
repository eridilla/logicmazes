package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.game.logicmazes.ConsoleUI;
import sk.tuke.gamestudio.game.logicmazes.Game;
import sk.tuke.gamestudio.game.logicmazes.InputHandler;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@Configuration
public class SpringClient {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI ui) {
        return args -> ui.play();
    }

    @Bean
    public ConsoleUI consoleUI(InputHandler handler) {
        return new ConsoleUI(handler);
    }

    @Bean
    public InputHandler inputHandler(Game game) {
        return new InputHandler(game);
    }

    @Bean
    public Game game() {
        return new Game();
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }
}