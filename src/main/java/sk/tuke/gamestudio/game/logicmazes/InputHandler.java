package sk.tuke.gamestudio.game.logicmazes;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.*;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class InputHandler {
    private Game game;

//    @Autowired
//    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    public InputHandler(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
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
//                        ScoreServiceJDBC scoreJDBC = new ScoreServiceJDBC();
//                        List<Score> leaderboard = scoreJDBC.getTopScores("Logical Mazes");
//                        List<Score> leaderboard = scoreService.getTopScores("Logical Mazes");
//                        game.printLeaderboard(leaderboard);

                        System.out.println("Work in progress...");
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
                    case 3:
//                        RatingServiceJDBC ratingJDBC = new RatingServiceJDBC();

                        int avgRating = ratingService.getAverageRating("Logic Mazes");

                        if (avgRating == -1) {
                            System.out.println("No ratings yet...");
                        } else {
                            System.out.println("Average rating for Logic Mazes: " + avgRating + "/5");
                        }

                        System.out.println();
                        System.out.print("Submit rating? (Y/N): ");
                        input = scanner.nextLine().toUpperCase();

                        if (input.equals("Y") || input.equals("YES")) {
                            System.out.print("Rate my game from 1 (bad) to 5 (great): ");
                            int rating = scanner.nextInt();

                            while (rating < 1 || rating > 5) {
                                System.out.print("Invalid input, please try again: ");
                                rating = scanner.nextInt();
                            }

                            System.out.print("Enter your name: ");
                            input = scanner.nextLine();
                            input = scanner.nextLine();

                            Rating ratingObj = new Rating("Logic Mazes", input, rating);

                            ratingService.setRating(ratingObj);

                            System.out.println("Rating submitted successfully! Returning to menu...");
                            System.out.println();

                            return InputReturn.SUCCESS;
                        }

                        if (input.equals("N") || input.equals("NO")) {
                            game.setGameState(GameState.MENU);
                            System.out.println();
                            return InputReturn.SUCCESS;
                        }

                    case 4:
//                        CommentServiceJDBC commentJDBC = new CommentServiceJDBC();
                        List<Comment> comments = commentService.getComments("Logic Mazes");

                        if (comments.size() == 0) {
                            System.out.println("No comments submitted...");
                        } else {
                            System.out.println("Name\tComment");

                            for (Comment comment : comments) {
                                System.out.println(comment.getName() + "\t" + comment.getComment());
                            }
                        }

                        System.out.println();

                        System.out.print("Submit comment? (Y/N): ");
                        input = scanner.nextLine().toUpperCase();

                        if (input.equals("Y") || input.equals("YES")) {
                            System.out.print("Enter comment: ");
                            String comment = scanner.nextLine();

                            System.out.print("Enter your name: ");
                            input = scanner.nextLine();
                            Date date = new Date();

                            Comment commentObj = new Comment(input, comment, date, "Logic Mazes");

                            commentService.addComment(commentObj);

                            System.out.println("Comment submitted successfully! Returning to menu...");
                            System.out.println();

                            return InputReturn.SUCCESS;
                        }

                        if (input.equals("N") || input.equals("NO")) {
                            game.setGameState(GameState.MENU);
                            System.out.println();
                            return InputReturn.SUCCESS;
                        }

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

                File level = new File("src/main/resources/levels/" + game.getLevels().get(n - 1));

                game.createGame(level);
                game.startGame();
            } catch (NumberFormatException nfe) {
                return InputReturn.FAILURE;
            }
        }


        return InputReturn.SUCCESS;
    }
}
