package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class TestSeed {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Game g = new Game();
        TETile[][] worldState = g.playWithInputString("N5174563880990S");
        TETile.toString(worldState);
        ter.renderFrame(worldState);
    }
}
