package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of Rectangular regions.
 */
public class Rectangle {
    public static void buildRoom(TETile[][] world, Position topLeft, int width,
                                 int height, Random r) {
        int counter = 0;
        Position topLeftRoom = topLeft;
        while (counter != height) {
            addRoomRow(world, topLeft, width);
            topLeft = new Position(topLeft.xPos, topLeft.yPos + 1);
            counter++;
        }
        int numHallways = RandomUtils.uniform(r, 5, 8);
        for (int i = 0; i < numHallways; i++) {
            int randomNum = RandomUtils.uniform(r, 1, 20);
            Position hallway = calculatePosition(topLeftRoom, width, height, randomNum, r);
            int heightHallway = RandomUtils.uniform(r, 10, 25);
            if (randomNum % 2 != 0) {
                while (WorldGeneration.HEIGHT < hallway.yPos + heightHallway) {
                    hallway = calculatePosition(topLeftRoom, width, height, randomNum, r);
                    heightHallway = RandomUtils.uniform(r, 1, 10);
                }
            } else {
                while (WorldGeneration.WIDTH < hallway.xPos + heightHallway) {
                    hallway = calculatePosition(topLeftRoom, width, height, randomNum, r);
                    heightHallway = RandomUtils.uniform(r, 1, 10);
                }
            }
            buildHallway(world, hallway, randomNum, heightHallway);
        }

    }

    public static void buildHallway(TETile[][] world, Position hallway,
                                    int randomNum, int heightHallway) {
        if (randomNum % 2 == 0) {
            addRoomRow(world, hallway, heightHallway);
        } else {
            addHallwayColumn(world, hallway, heightHallway);
        }
    }

    private static void addRoomRow(TETile[][] world, Position p, int width) {
        for (int i = p.xPos; i < p.xPos + width; i++) {
            world[i][p.yPos] = Tileset.FLOOR;
        }
    }

    private static void addHallwayColumn(TETile[][] world, Position p, int height) {
        for (int i = p.yPos; i < p.yPos + height; i++) {
            world[p.xPos][i] = Tileset.FLOOR;
        }
    }

    private static Position calculatePosition(Position p, int width, int height,
                                              int counter, Random r) {
        if (counter % 2 == 0) {
            int offSet = RandomUtils.uniform(r, 0, height - 1);
            return new Position(p.xPos + width, p.yPos + offSet);
        }
        int offSet = RandomUtils.uniform(r, 0, width - 1);
        return new Position(p.xPos + offSet, p.yPos + height);
    }
}
