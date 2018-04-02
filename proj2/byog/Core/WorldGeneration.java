package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;


public class WorldGeneration {
    long seed;
    static TETile[][] world;
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    public static final int XOFFSET = 2;
    public static final int YOFFSET = 2;
    public static final int MIN_RECT_WIDTH = 2;
    public static final int MAX_RECT_WIDTH = 6;
    public static final int MIN_RECT_LENGTH = 6;
    public static final int MAX_RECT_LENGTH = 8;

    public WorldGeneration() {
        seed = 0;
    }

    public WorldGeneration(long seed) {
        this.seed = seed;
    }


    public static TETile[][] generateWorld() {
        Random r = new Random();
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int numRooms = RandomUtils.uniform(r, 15, 20);
        for (int i = 0; i < numRooms; i++) {
            int widthRect = RandomUtils.uniform(r, MIN_RECT_WIDTH, MAX_RECT_WIDTH);
            int lengthRect = RandomUtils.uniform(r, MIN_RECT_LENGTH, MAX_RECT_LENGTH);
            int topLeftX = RandomUtils.uniform(r, XOFFSET, WIDTH - XOFFSET);
            while (WIDTH < topLeftX + widthRect) {
                topLeftX = RandomUtils.uniform(r, XOFFSET, WIDTH - XOFFSET);
            }
            int topLeftY = RandomUtils.uniform(r, YOFFSET, HEIGHT - YOFFSET);
            while (HEIGHT < topLeftY + lengthRect) {
                topLeftY = RandomUtils.uniform(r, YOFFSET, HEIGHT - YOFFSET);
            }
            Rectangle.buildRoom(world, new Position(topLeftX - 1, topLeftY - 1),
                    widthRect, lengthRect, r);
        }
        fillWall();
        return world;
    }

    public static TETile[][] generateWorld(long seed) {
        Random r = new Random(seed);
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int numRooms = RandomUtils.uniform(r, 10, 20);
        for (int i = 0; i < numRooms; i++) {
            int widthRect = RandomUtils.uniform(r, MIN_RECT_WIDTH, MAX_RECT_WIDTH);
            int lengthRect = RandomUtils.uniform(r, MIN_RECT_LENGTH, MAX_RECT_LENGTH);
            int topLeftX = RandomUtils.uniform(r, XOFFSET, WIDTH - XOFFSET);
            while (WIDTH < topLeftX + widthRect) {
                topLeftX = RandomUtils.uniform(r, XOFFSET, WIDTH - XOFFSET);
            }
            int topLeftY = RandomUtils.uniform(r, YOFFSET, HEIGHT - YOFFSET);
            while (HEIGHT < topLeftY + lengthRect) {
                topLeftY = RandomUtils.uniform(r, YOFFSET, HEIGHT - YOFFSET);
            }
            Rectangle.buildRoom(world, new Position(topLeftX - 1, topLeftY - 1),
                    widthRect, lengthRect, r);
        }
        fillWall();
        return world;
    }

    public static void fillWall() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (x < WIDTH - 1 && y < HEIGHT - 1) {
                    if (world[x + 1][y].equals(Tileset.FLOOR) || world[x][y + 1].equals(Tileset.FLOOR)
                            || world[x + 1][y + 1].equals(Tileset.FLOOR)) {
                        if (world[x][y].equals(Tileset.NOTHING)) {
                            world[x][y] = Tileset.WALL;
                        }
                    }
                    if (world[x + 1][y].equals(Tileset.WALL) && world[x][y + 1].equals(Tileset.WALL)) {
                        if (world[x][y].equals(Tileset.NOTHING)) {
                            world[x][y] = Tileset.WALL;
                        }
                    }
                }
                if (x > 0 && y > 0) {
                    if (world[x - 1][y].equals(Tileset.FLOOR) || world[x][y - 1].equals(Tileset.FLOOR)
                            || world[x - 1][y - 1].equals(Tileset.FLOOR)) {
                        if (world[x][y].equals(Tileset.NOTHING)) {
                            world[x][y] = Tileset.WALL;
                        }
                    }

                }
            }
        }
    }


}
