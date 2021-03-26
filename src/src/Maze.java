import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Maze {
    private int mazeWidth;
    private int mazeHeight;
    private List<List<Tile>> map;
    private File level;
    private Player player;
    private int maxPoints;

    public Maze(File level, Player player) {
        this.mazeWidth = 0;
        this.mazeHeight = 0;
        this.map = new ArrayList<>();
        this.level = level;
        this.player = player;
        this.maxPoints = 0;

        this.createMap();
    }

    public Player getPlayer() {
        return player;
    }

    public List<List<Tile>> getMap() {
        return map;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    private void createMap() {
        try {
            Scanner lvlReader = new Scanner(this.level);

            mazeWidth = lvlReader.nextInt();
            mazeHeight = lvlReader.nextInt();

            lvlReader.nextLine();

            for (int i = 0; i < mazeHeight; i++) {
                this.map.add(new ArrayList<>());
                String row = lvlReader.nextLine();

                for (int j = 0; j < mazeWidth; j++) {
                    switch (row.charAt(j)) {
                        case 'X':
                            this.map.get(i).add(new Wall());
                            break;
                        case 'O':
                            this.map.get(i).add(new OpenSpace());
                            break;
                        case 'P':
                            this.map.get(i).add(new Point());
                            maxPoints++;
                            break;
                        case 'V':
//                            this.player = new Player(j, i);
                            this.map.get(i).add(this.player);
                            this.player.setPosX(j);
                            this.player.setPosY(i);
                            break;
                        default:
                    }
                }
            }

            lvlReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occured while reading level file.");
            e.printStackTrace();

        }
    }

    public void drawMap() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }

        System.out.println("Moves left: " + player.getMoves());
        System.out.print("+");

        for (int i = 0; i < mazeWidth; i++) {
            System.out.print("-");
        }

        System.out.println("+");


        for (int i = 0; i < mazeHeight; i++) {
            System.out.print("|");

            for (int j = 0; j < mazeWidth; j++) {
                switch (map.get(i).get(j).type) {
                    case WALL:
                        System.out.print("X");
                        break;
                    case OPEN:
                        System.out.print(" ");
                        break;
                    case POINT:
                        System.out.print("O");
                        break;
                    case PLAYER:
                        System.out.print("V");
                        break;
                    default:
                        break;
                }
            }

            System.out.println("|");
        }

        System.out.print("+");

        for (int i = 0; i < mazeWidth; i++) {
            System.out.print("-");
        }

        System.out.println("+");
    }

    public void movePlayer(Direction dir) {
        switch (dir) {
            case NORTH:
                while (player.getPosY()-1 >= 0 && map.get(player.getPosY()-1).get(player.getPosX()).getType() != TileType.WALL) {
                    if (map.get(player.getPosY()-1).get(player.getPosX()).getType() == TileType.POINT) {
                        player.addPoint();
                    }

                    map.get(player.getPosY()-1).set(player.getPosX(), this.player);
                    map.get(player.getPosY()).set(player.getPosX(), new OpenSpace());

                    player.setPosY(player.getPosY()-1);
                }

                player.setMoves(player.getMoves()-1);
                drawMap();
//                System.out.println(player.getPosX());
//                System.out.println(player.getPosY());
                break;
            case EAST:
                while (player.getPosX()+1 < mazeWidth && map.get(player.getPosY()).get(player.getPosX()+1).getType() != TileType.WALL) {
                    if (map.get(player.getPosY()).get(player.getPosX()+1).getType() == TileType.POINT) {
                        player.addPoint();
                    }

                    map.get(player.getPosY()).set(player.getPosX()+1, this.player);
                    map.get(player.getPosY()).set(player.getPosX(), new OpenSpace());

                    player.setPosX(player.getPosX()+1);
                }

                player.setMoves(player.getMoves()-1);
                drawMap();
//                System.out.println(player.getPosX());
//                System.out.println(player.getPosY());
                break;
            case WEST:
                while (player.getPosX()-1 >= 0 && map.get(player.getPosY()).get(player.getPosX()-1).getType() != TileType.WALL) {
                    if (map.get(player.getPosY()).get(player.getPosX()-1).getType() == TileType.POINT) {
                        player.addPoint();
                    }

                    map.get(player.getPosY()).set(player.getPosX()-1, this.player);
                    map.get(player.getPosY()).set(player.getPosX(), new OpenSpace());

                    player.setPosX(player.getPosX()-1);
                }

                player.setMoves(player.getMoves()-1);
                drawMap();
//                System.out.println(player.getPosX());
//                System.out.println(player.getPosY());
                break;
            case SOUTH:
                while (player.getPosY()+1 < mazeHeight && map.get(player.getPosY()+1).get(player.getPosX()).getType() != TileType.WALL) {
                    if (map.get(player.getPosY()+1).get(player.getPosX()).getType() == TileType.POINT) {
                        player.addPoint();
                    }

                    map.get(player.getPosY()+1).set(player.getPosX(), this.player);
                    map.get(player.getPosY()).set(player.getPosX(), new OpenSpace());

                    player.setPosY(player.getPosY()+1);
                }

                player.setMoves(player.getMoves()-1);
                drawMap();
//                System.out.println(player.getPosX());
//                System.out.println(player.getPosY());
                break;
            default:
                break;
        }
    }
}
