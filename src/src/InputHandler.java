import gamestudio.entity.Score;
import gamestudio.service.ScoreServiceJDBC;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class InputHandler {
    private Game game;

    public InputHandler(Game game) {
        this.game = game;
    }

    public InputReturn nextInput() {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine().toUpperCase();

        if (input.equals("Q") || input.equals("QUIT") || input.equals("EXIT")) {
            game.setGameState(GameState.EXIT);
            return InputReturn.SUCCESS;
        }

        if (game.getGameState() == GameState.WIN || game.getGameState() == GameState.LOSE) {
            if (input.equals("Y") || input.equals("YES")) {
                game.setGameState(GameState.MENU);
                System.out.println();
                return InputReturn.SUCCESS;
            }

            if (input.equals("N") || input.equals("NO")) {
                game.setGameState(GameState.EXIT);
                System.out.println();
                return InputReturn.SUCCESS;
            }
        }

        if (game.getGameState() == GameState.MENU) {
            System.out.println();

            try {
                int n = Integer.parseInt(input);

                switch (n) {
                    case 1:
                        game.levelSelect();
                        break;
                    case 2:
                        ScoreServiceJDBC scoreJDBC = new ScoreServiceJDBC();
                        List<Score> leaderboard = scoreJDBC.getTopScores("Logical Mazes");
                        game.printLeaderboard(leaderboard);

                        System.out.println();
                        System.out.print("Back to menu? (Y/N): ");

                        input = scanner.nextLine().toUpperCase();

                        if (input.equals("Y") || input.equals("YES")) {
                            game.setGameState(GameState.MENU);
                            System.out.println();
                            return InputReturn.SUCCESS;
                        }

                        if (input.equals("N") || input.equals("NO")) {
                            game.setGameState(GameState.EXIT);
                            System.out.println();
                            return InputReturn.SUCCESS;
                        }
                        break;
                    default:
                        return InputReturn.FAILURE;
                }

            } catch (NumberFormatException nfe) {
                return InputReturn.FAILURE;
            }
        }

        if (game.getGameState() == GameState.PLAYING) {
            if (input.equals("NORTH") || input.equals("N")) {
                game.getMap().movePlayer(Direction.NORTH);
                return InputReturn.SUCCESS;
            }

            if (input.equals("EAST") || input.equals("E")) {
                game.getMap().movePlayer(Direction.EAST);
                return InputReturn.SUCCESS;
            }

            if (input.equals("SOUTH") || input.equals("S")) {
                game.getMap().movePlayer(Direction.SOUTH);
                return InputReturn.SUCCESS;
            }

            if (input.equals("WEST") || input.equals("W")) {
                game.getMap().movePlayer(Direction.WEST);
                return InputReturn.SUCCESS;
            } else {
                return InputReturn.FAILURE;
            }
        }

        if (game.getGameState() == GameState.LEVELSELECT) {
            try {
                int n = Integer.parseInt(input);

                if (n < 1 || n > game.getLevels().size()) {
                    return InputReturn.FAILURE;
                }

                File level = new File("levels/" + game.getLevels().get(n-1));

                game.createGame(level);
                game.startGame();
            } catch (NumberFormatException nfe) {
                return InputReturn.FAILURE;
            }
        }


        return InputReturn.SUCCESS;
    }
}
