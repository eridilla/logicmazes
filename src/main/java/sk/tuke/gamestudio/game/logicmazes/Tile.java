package sk.tuke.gamestudio.game.logicmazes;

public abstract class Tile {
    TileType type;

    public Tile(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return this.type;
    }
}
