package core;

public class Position {
    // x and y coordinates
    private int x;
    private int y;

    // constructor
    public Position(int x, int y) {
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
}
