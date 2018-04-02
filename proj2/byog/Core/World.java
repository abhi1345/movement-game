package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class World implements Serializable {
    TETile[][] gameBoard;
    Position playerPosition;

    public World(TETile[][] board) {
        gameBoard = board;
        playerPosition = null;
    }

    public void addPlayer() {
        for (int x = 0; x < gameBoard.length; x += 1) {
            for (int y = 0; y < gameBoard[x].length; y += 1) {
                if (gameBoard[x][y].equals(Tileset.FLOOR)) {
                    gameBoard[x][y] = Tileset.PLAYER;
                    playerPosition = new Position(x, y);
                    return;
                }
            }
        }
    }

    public void movePlayer(char c) {
        switch (c) {
            case 'w':
                movePlayerUp();
                break;
            case 'a':
                movePlayerLeft();
                break;
            case 's':
                movePlayerDown();
                break;
            case 'd':
                movePlayerRight();
                break;
            default:
        }
    }

    public void movePlayerUp() {
        int x = playerPosition.xPos;
        int y = playerPosition.yPos;

        if (getGameBoard()[x][y + 1].equals(Tileset.FLOOR)) {
            getGameBoard()[x][y + 1] = Tileset.PLAYER;
            getGameBoard()[x][y] = Tileset.FLOOR;
            playerPosition.yPos++;
        }
    }

    public void movePlayerDown() {
        int x = playerPosition.xPos;
        int y = playerPosition.yPos;

        if (gameBoard[x][y - 1].equals(Tileset.FLOOR)) {
            gameBoard[x][y - 1] = Tileset.PLAYER;
            gameBoard[x][y] = Tileset.FLOOR;
            playerPosition.yPos--;
        }
    }

    public void movePlayerLeft() {
        int x = playerPosition.xPos;
        int y = playerPosition.yPos;

        if (gameBoard[x - 1][y].equals(Tileset.FLOOR)) {
            gameBoard[x - 1][y] = Tileset.PLAYER;
            gameBoard[x][y] = Tileset.FLOOR;
            playerPosition.xPos--;
        }
    }

    public void movePlayerRight() {
        int x = playerPosition.xPos;
        int y = playerPosition.yPos;
        if (gameBoard[x + 1][y].equals(Tileset.FLOOR)) {
            gameBoard[x + 1][y] = Tileset.PLAYER;
            gameBoard[x][y] = Tileset.FLOOR;
            playerPosition.xPos++;
        }
    }

    public TETile[][] getGameBoard() {
        return gameBoard;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }
}
