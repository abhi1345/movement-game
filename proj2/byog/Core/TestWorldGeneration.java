package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class TestWorldGeneration {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] worldState = WorldGeneration.generateWorld();
        TETile.toString(worldState);
        ter.renderFrame(worldState);
    }
}
