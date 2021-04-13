package sk.tuke.gamestudio.game.logicmazes;

public class Player extends Tile {
    private int points;
    private int posX;
    private int posY;
    private int moves;

    public Player() {
        super(TileType.PLAYER);
        this.points = 0;
        this.posX = 0;
        this.posY = 0;
        this.moves = 20;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public void addPoint() {
        this.points++;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
