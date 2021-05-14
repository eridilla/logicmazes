package sk.tuke.gamestudio.game.logicmazes;

import sk.tuke.gamestudio.entity.Score;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Maze map;
    private GameState gameState;
    private Player player;
    private List<String> levels;

    public Game() {
        this.map = null;
        this.gameState = GameState.MENU;
        this.player = new Player();

        this.levels = new ArrayList<>();

        File folder = new File("src/main/resources/levels");

        if (folder.listFiles() == null) {
            System.out.println("The levels folder is empty. Exiting...");
            gameState = GameState.EXIT;
            return;
        }

        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                levels.add(fileEntry.getName());
            }
        }
    }

    public Game(File level) {
        this.map = null;
        this.gameState = GameState.MENU;
        this.player = new Player();

        this.levels = new ArrayList<>();

        File folder = new File("src/main/resources/levels");

        if (folder.listFiles() == null) {
            System.out.println("The levels folder is empty. Exiting...");
            gameState = GameState.EXIT;
            return;
        }

        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                levels.add(fileEntry.getName());
            }
        }

        createGame(level);
    }

    public Player getPlayer() {
        return player;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<String> getLevels() {
        return this.levels;
    }

    public Maze getMap() {
        return map;
    }

    public void levelSelect() {
        gameState = GameState.LEVELSELECT;

        int n = 1;

        for (String lvlName : levels) {
            System.out.println(n + ". " + lvlName);
            n++;
        }

        System.out.print("Select level: ");
        InputHandler handler = new InputHandler(this);

        InputReturn selectRet = InputReturn.FAILURE;

        do {
            selectRet = handler.nextInput();

            if (selectRet == InputReturn.FAILURE) {
                System.out.print("Invalid input, please try again: ");
            }
        } while (selectRet != InputReturn.SUCCESS);
    }

    public void createGame(File level) {
        this.map = new Maze(level, this.player);
    }

    public void startGame() {
        gameState = GameState.PLAYING;
    }

    public void checkWin() {
        if (player.getPoints() == map.getMaxPoints()) {
            gameState = GameState.WIN;
//            player.setPoints(0);
        }
    }

    public void checkLose() {
        if (player.getMoves() == 0) {
            gameState = GameState.LOSE;
//            player.setPoints(0);
        }
    }

    public void printLeaderboard(List<Score> leaderboard) {
        System.out.println();
        System.out.println("Game\t\t\tName\tScore\tPlayed On");

        for (Score scoreEntry : leaderboard) {
            System.out.println(scoreEntry.getGame() + "\t" + scoreEntry.getPlayer() + "\t" + scoreEntry.getPoints() + "\t\t" + scoreEntry.getPlayedOn());
        }
    }
}
