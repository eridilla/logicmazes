public abstract class Tile {
    TileType type;

    public Tile(TileType type) {
        this.type = type;
    }

    TileType getType() {
        return this.type;
    }
}
