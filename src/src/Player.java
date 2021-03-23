public class Player extends Tile {
    private int points;
    private int posX;
    private int posY;

    public Player(int posX, int posY) {
        super(TileType.PLAYER);
        this.points = 0;
        this.posX = posX;
        this.posY = posY;
    }

    public int getPoints() {
        return points;
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
