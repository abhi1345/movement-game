package byog.Core;

import java.io.Serializable;

public class Position implements Serializable {
    int xPos;
    int yPos;

    public Position(int x, int y) {
        xPos = x;
        yPos = y;
    }
}
