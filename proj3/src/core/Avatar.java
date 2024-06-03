package core;

import tileengine.TETile;
import tileengine.Tileset;

public class Avatar {
    World world;
    private int x;
    private int y;

    public Avatar(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;
    }

    // setters
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    // getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public boolean canMove(TETile[][] board, int dx, int dy) {
        if (x + dx < board.length && y + dy < board[0].length) {
            if (board[x + dx][y + dy] != Tileset.WALL && board[x + dx][y + dy] != Tileset.LIGHT_SOURCE) {
                return true;
            }
        }
        return false;
    }

    public static int tryMove(Avatar t, TETile[][] board, int dx, int dy, int score) {
        if (t.canMove(board, dx, dy)) {
            board[t.x][t.y] = Tileset.FLOOR;
            if (board[t.x + dx][t.y + dy] == Tileset.COIN) {
                score += 1;
            }
            board[t.x + dx][t.y + dy] = Tileset.AVATAR;
            t.setX(t.x + dx);
            t.setY(t.y + dy);
        }
        return score;
    }
}
