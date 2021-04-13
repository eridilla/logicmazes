package sk.tuke.gamestudio.game.logicmazes;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();
        InputHandler handler = new InputHandler(game);
        ScoreServiceJDBC scoreJDBC = new ScoreServiceJDBC();
        List<Score> leaderboard = scoreJDBC.getTopScores("Logical Mazes");

        do {
            System.out.println("Welcome to Logical Mazes!");
            System.out.println("1. Play!");
            System.out.println("2. Leaderboards");
            System.out.println("3. Ratings");
            System.out.println("4. Comments");
            System.out.println();
            System.out.print("Select option or quit (1...4, [Q]UIT): ");

            handler.nextInput();

            if (game.getGameState() == GameState.EXIT) {
                break;
            }

            if (game.getGameState() == GameState.PLAYING) {
                game.getMap().getPlayer().setMoves(20);
                game.getMap().drawMap();
            }

            while (game.getGameState() == GameState.PLAYING) {
                System.out.println("Make your move ([N]orth, [E]ast, [S]outh, [W]est or [Q]uit): ");
                InputReturn selectRet = InputReturn.FAILURE;

                do {
                    selectRet = handler.nextInput();

                    if (selectRet == InputReturn.FAILURE) {
                        System.out.print("Invalid input, please try again: ");
                    }
                } while (selectRet != InputReturn.SUCCESS);

                game.checkLose();
                game.checkWin();

                if (game.getGameState() == GameState.LOSE) {
                    System.out.println("Sorry, you ran out of moves and you lose!");
                    System.out.print("Back to menu? (Y/N): ");
                    handler.nextInput();
                    System.out.println();
                }

                if (game.getGameState() == GameState.WIN) {
                    System.out.println("Congratulations, you have completed this level!");
                    System.out.print("Enter your name for the leaderboard: ");

                    Scanner scanner = new Scanner(System.in);
                    String name = scanner.nextLine();

                    Date date = new Date();
                    Score score = new Score("Logical Mazes", name, game.getMap().getPlayer().getMoves(), date);
                    scoreJDBC.addScore(score);

                    game.printLeaderboard(leaderboard);

                    System.out.println();
                    System.out.print("Back to menu? (Y/N): ");
                    selectRet = InputReturn.FAILURE;

                    do {
                        selectRet = handler.nextInput();

                        if (selectRet == InputReturn.FAILURE) {
                            System.out.print("Invalid input, please try again: ");
                        }
                    } while (selectRet != InputReturn.SUCCESS);

                    break;
                }
            }
        } while (game.getGameState() != GameState.EXIT);

        System.out.println("Exiting game...");
    }
}
