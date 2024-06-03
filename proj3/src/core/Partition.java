package core;

import utils.RandomUtils;

import java.util.Random;

public class Partition {
    // starting position
    private int x;
    private int y;
    private int width;
    private int height;
    private Position center;

    // left and right children after splitting
    private Partition left;
    private Partition right;
    private boolean horizontalSplit;

    // room generated in the partition
    private Room room;

    // constructor
    public Partition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        center = new Position(this.x + this.width / 2, this.y + this.height / 2);
    }

    // setters
    public void setLeft(Partition left) {
        this.left = left;
    }
    public void setRight(Partition right) {
        this.right = right;
    }
    public void setHorizontalSplit(boolean horizontalSplit) {
        this.horizontalSplit = horizontalSplit;
    }

    // getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Position getCenter() {
        return center;
    }
    public Partition getLeft() {
        return left;
    }
    public Partition getRight() {
        return right;
    }
    public boolean getHorizontalSplit() {
        return horizontalSplit;
    }
    public Room getRoom() {
        return room;
    }

    // Generates randomly sized room inside the partition
    public void generateRoom(Random random) {
        room = new Room();
        room.setX(x + RandomUtils.uniform(random, 0, width / 3));
        room.setY(y + RandomUtils.uniform(random, 0, height / 3));
        // starting from room's bottom left coordinate
        room.setWidth(width - (room.getX() - x));
        room.setHeight(height - (room.getY() - y));
        // set the width and height randomly
        room.setWidth(room.getWidth() - RandomUtils.uniform(random, 0, width / 3));
        room.setHeight(room.getHeight() - RandomUtils.uniform(random, 0, height / 3));
    }

}
