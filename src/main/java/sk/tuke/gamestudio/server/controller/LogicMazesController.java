package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.UserTable;
import sk.tuke.gamestudio.game.logicmazes.Game;
import sk.tuke.gamestudio.game.logicmazes.GameState;
import sk.tuke.gamestudio.game.logicmazes.InputHandler;
import sk.tuke.gamestudio.game.logicmazes.Tile;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/logicmazes")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class LogicMazesController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    private Game game;
    private InputHandler inputHandler;
    private String[][] rocks;
    private String lastMove;
    private String lastFacing;

    @RequestMapping
    public String logicmazes() {
        return "logicmazesMainMenu";
    }

    @RequestMapping("/levelselect")
    public String selectLevel() {
        game = new Game();
        inputHandler = new InputHandler(game);
        game.setGameState(GameState.LEVELSELECT);
        return "logicmazesLevelSelect";
    }

    @RequestMapping("/game")
    public String playGame(@RequestParam(required = false) String level, @RequestParam(required = false) String move) {
        if (game.getGameState() == GameState.LEVELSELECT && level != null) {
            inputHandler.nextInputWebApp(level);
            rocks = new String[game.getMap().getMazeHeight()][game.getMap().getMazeWidth()];
        } else if (game.getGameState() == GameState.PLAYING && move != null) {
            lastMove = move;
            inputHandler.nextInputWebApp(move);
        }

//        game.checkWin();
//        game.checkLose();
//
//        if (game.getGameState() == GameState.WIN || game.getGameState() == GameState.LOSE) {
//            return "logicmazesEnd";
//        }

        return "logicmazesGame";
    }

    @RequestMapping("/topscores")
    public String getLeaderboards(@RequestParam(required = false) String result, @RequestParam(required = false) String player, Model scoreModel) {
        if (result != null && player != null) {
            Score scoreObj = new Score("logicmazes", player, game.getPlayer().getMoves(), LocalDate.now());
            scoreService.addScore(scoreObj);
        }

        fillModel(scoreModel);
        return "logicmazesLeaderboards";
    }

    @RequestMapping("/ratings")
    public String getRatings(@RequestParam(required = false) String rating, @RequestParam(required = false) String player, Model ratingModel) {
        if (rating != null && player != null) {
            Rating ratingObj = new Rating("logicmazes", player, Integer.parseInt(rating));
            ratingService.setRating(ratingObj);
        }

        fillModel(ratingModel);
        return "logicmazesRatings";
    }

    @RequestMapping("/comments")
    public String getComments(@RequestParam(required = false) String playerName, @RequestParam(required = false) String commentText, Model commentsModel) {
        if (playerName != null && commentText != null) {
            Comment commentObj = new Comment(playerName, commentText, LocalDate.now(), "logicmazes");
            commentService.addComment(commentObj);
        }

        fillModel(commentsModel);
        return "logicmazesComments";
    }

    public String getHtmlLevelList() {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul id='menuList'>\n");
        int counter = 1;

        for (String lvlName : game.getLevels()) {
            sb.append("<li>");
            sb.append(String.format("<a href='/logicmazes/game?level=%d'><button class='menuButton'>%d. %s</button></a>", counter, counter, lvlName));
            sb.append("</li>\n");
            counter++;
        }

        sb.append("<li>");
        sb.append("<a href='/logicmazes'><button class='menuButton backButton'><< Back to menu</button></a>");
        sb.append("</li>\n");

        sb.append("</ul>\n");

        return sb.toString();
    }

    public String getHtmlField() {
        List<List<Tile>> maze = game.getMap().getMap();
        StringBuilder sb = new StringBuilder();
        sb.append("<table id='gameField'>\n");

        int i = 0;
        int j = 0;

        for (List<Tile> tiles : maze) {
            sb.append("<tr>\n");
            for (Tile tile : tiles) {
                switch (tile.getType()) {
                    case WALL:
                        if (this.rocks[i][j] == null) {
                            String randRock = getRandomRock();
                            this.rocks[i][j] = randRock;
                            sb.append(String.format("<td><img src='/img/%s.png'/></td>\n", randRock));
                        } else {
                            sb.append(String.format("<td><img src='/img/%s.png'/></td>\n", this.rocks[i][j]));
                        }
                        break;
                    case OPEN:
                        sb.append("<td><img src='/img/dirtTile.png'/></td>\n");
                        break;
                    case POINT:
                        sb.append("<td><img src='/img/gem.png'/></td>\n");
                        break;
                    case PLAYER:
                        if (lastMove != null && lastMove.equals("E")) {
                            sb.append("<td><img src='/img/playerR.png'/></td>\n");
                            lastFacing = "playerR";
                        } else if (lastMove != null && lastMove.equals("W")){
                            sb.append("<td><img src='/img/playerL.png'/></td>\n");
                            lastFacing = "playerL";
                        } else if (lastFacing != null){
                            sb.append(String.format("<td><img src='/img/%s.png'/></td>\n", lastFacing));
                        } else {
                            sb.append("<td><img src='/img/playerR.png'/></td>\n");
                        }
                        break;
                    default:
                        break;
                }

                j++;
            }

            sb.append("</tr>\n");
            j = 0;
            i++;
        }

        sb.append("</table>\n");
        return sb.toString();
    }

    public String getMoves() {
        return String.format("Moves left: %d", game.getMap().getPlayer().getMoves());
    }

    public String getPoints() {
        return String.format("Points: %d", game.getMap().getPlayer().getPoints());
    }

    public String getWinLose() {
        if (game.getGameState() == GameState.WIN) {
            return "You Win!";
        } else {
            return "You Lose!";
        }
    }

    public String getRandomRock() {
        Random rand = new Random();

        int randNum = rand.nextInt((4 - 1) + 1) + 1;

        switch (randNum) {
            case 1:
                return "rock1";
            case 2:
                return "rock2";
            case 3:
                return "rock3";
            case 4:
                return "rock4";
            default:
                throw new IllegalStateException("Generated invalid number");
        }
    }

//    public String getRatingByName() {
//        StringBuilder sb = new StringBuilder();
//
//        if (ratingService.getRating("logicmazes", "gggg") <= 0) {
//            sb.append("<i>Your rating:</i>\n");
//            sb.append("<i>You haven't submitted a rating yet...</i>\n");
//        } else {
//            sb.append("<i>Your rating: ");
//            sb.append(ratingService.getRating("logicmazes", "gggg"));
//            sb.append("/5</i>\n");
//        }
//
//        return sb.toString();
//    }

    public String getGameState() {
        game.checkWin();
        game.checkLose();

        if (game.getGameState() == GameState.WIN) {
            return "1";
        } else if (game.getGameState() == GameState.LOSE) {
            return "-1";
        } else {
            return "0";
        }
    }

    private void fillModel(Model model) {
        model.addAttribute("scores", scoreService.getTopScores("logicmazes"));
        model.addAttribute("rating", ratingService.getAverageRating("logicmazes"));
        model.addAttribute("comments", commentService.getComments("logicmazes"));
    }
}
