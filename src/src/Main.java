import java.io.File;

public class Main {
    public static void main(String[] args) {
        File level = new File("levels/abc");

        Maze maze = new Maze(level);

        maze.drawMap();
        System.out.println(maze.getPlayer().getPosX());
        System.out.println(maze.getPlayer().getPosY());

    }
}
