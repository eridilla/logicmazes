import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File level = new File("levels/abc");

        Maze maze = new Maze(level);

        maze.drawMap();
        System.out.println(maze.getPlayer().getPosX());
        System.out.println(maze.getPlayer().getPosY());


    }
}
