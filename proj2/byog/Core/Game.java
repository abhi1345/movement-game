package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Game {
    World board;
    TERenderer ter = new TERenderer();
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     * GAME STARTS HERE
     */

    public void playWithKeyboard() {
        drawmenu();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                switch (c) {
                    case 'n':
                        newWorld();
                        break;
                    case 'l':
                        loadWorldMain();
                        break;
                    case 'q':
                        System.exit(0);
                        break;
                    case 's':
                        lore();
                        break;
                    default:
                }
            }
        }
    }

    public void drawmenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.clear(Color.black);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(WIDTH / 2, 25, "CS 61B: The Game");
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.text(WIDTH / 2, 20, "New Game (N)");
        StdDraw.text(WIDTH / 2, 15, "Load Game (L)");
        StdDraw.text(WIDTH / 2, 10, "Quit (Q)");
        StdDraw.text(WIDTH / 2, 5, "Lore (S)");
        StdDraw.show();
    }

    public String getSeed() {
        String s = "";
        drawFrame(s);
        boolean typing = true;
        while (typing) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == 's') {
                    return s;
                }
                s += c;
                drawFrame(s);
            }
        }
        return s;
    }


    public void lore() {
        String story4 = "and learning the error of your ways...";
        String story3 = "You must spend the rest of eternity roaming your prison,";
        String story1 = " After cheating on your CS 61B midterm, you have been exiled to a dark prison.";
        String story2 = "There is nothing to do here, except walk around.";

        boolean done = false;

        StdDraw.setPenColor(Color.white);
        StdDraw.clear(Color.black);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.text(WIDTH / 2, 20, story1);
        StdDraw.text(WIDTH / 2, 18, story2);
        StdDraw.text(WIDTH / 2, 16, story3);
        StdDraw.text(WIDTH / 2, 14, story4);
        StdDraw.text(WIDTH / 2, 12, "(Click b to go back to the main menu)");
        StdDraw.show();

        while (!done) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == 'b') {
                    playWithKeyboard();
                    done = true;
                }
            }
        }


    }


    public void movePlayer() {
        String quitSeq = "";
        boolean typing = true;
        while (typing) {
            if (StdDraw.hasNextKeyTyped()) {
                if (quitSeq.equals(":")) {
                    char c = StdDraw.nextKeyTyped();
                    if (c == 'q') {
                        saveWorld(board);
                        System.exit(0);
                    }
                } else {
                    char c = StdDraw.nextKeyTyped();
                    if (c == ':') {
                        quitSeq += c;
                    } else if (c == 'n') {
                        newWorld();

                    } else {
                        board.movePlayer(c);
                        //ter.renderFrame(board.getGameBoard());
                    }
                }
            }
            StdDraw.clear();
            ter.renderFrame(board.getGameBoard());
            int x = (int) StdDraw.mouseX();
            int y = (int) StdDraw.mouseY();
            String HUDtext;
            if (x > 59 || y > 29) {
                HUDtext = Tileset.NOTHING.description();
            } else {
                HUDtext = board.gameBoard[x][y].description();
            }
            StdDraw.setFont(new Font("Monaco", Font.BOLD, 15));
            StdDraw.setPenColor(Color.white);
            StdDraw.text(70, 10, HUDtext);
            StdDraw.show();
        }

    }

    public World loadGame() {
        File f = new File("world.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                this.board = (World) os.readObject();
                return this.board;
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("Class Not Found");
                System.exit(0);
            }
        }
        /* In the case no World has been saved yet, we return a new one. */
        return new World(WorldGeneration.generateWorld());
    }

    private static void saveWorld(World w) {
        File f = new File("world.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(w);

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public void newWorld() {
        String seed = getSeed();
        this.board = new World(WorldGeneration.generateWorld(Long.valueOf(seed)));
        board.addPlayer();
        StdDraw.enableDoubleBuffering();
        ter.renderFrame(board.getGameBoard());
        movePlayer();
        //StdDraw.show();
    }

    public void newWorldKey(String key) {
        this.board = new World(WorldGeneration.generateWorld(Long.valueOf(key)));
        board.addPlayer();
    }

    public void loadWorldMain() {
        this.board = loadGame();
        StdDraw.enableDoubleBuffering();
        ter.renderFrame(board.getGameBoard());
        movePlayer();
        StdDraw.show();
    }

    public void loadWorldMainKey() {
        this.board = loadGame();
    }

    public void movePlayerKey(String moves) {
        for (int i = 0; i < moves.length(); i++) {
            if (moves.charAt(i) == ':' && moves.charAt(i + 1) == 'q') {
                saveWorld(board);
            }
            board.movePlayer(moves.charAt(i));
        }
    }

    public void drawFrame(String s) {
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;

        StdDraw.clear();
        StdDraw.clear(Color.black);

        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH / 2, 25, "Enter Seed (Type s once done): ");

        Font bigFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, s);
        StdDraw.show();
    }


    public String toString() {
        return TETile.toString(board.getGameBoard());
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        char value = input.charAt(0);
        String moves = "";
        switch (value) {
            case 'n':
                String seedString = "";
                int i = 1;
                while (input.charAt(i) != 's') {
                    seedString += input.charAt(i);
                    i++;
                }
                if (input.charAt(i) == 's') {
                    moves = input.replaceAll('n' + seedString, "");
                }
                newWorldKey(seedString);
                break;
            case 'l':
                loadWorldMainKey();
                System.out.println("Hello");
                moves = input.replaceAll("l", "");
                break;
            default:
        }
        movePlayerKey(moves);
        return board.getGameBoard();
    }
}
