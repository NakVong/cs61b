package core;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class World {
    // actually responsible for initializing, updating, and rendering
    private static final int WIDTH = 80;
    private static final int HEIGHT = 50;
    private static final double RATIO = 0.45;
    private static final String FILE = "./save-file.txt";
    private String seed;
    private Random random;
    private int score;
    LocalDateTime currentDateTime;
    DateTimeFormatter formatter;
    TERenderer ter;

    // dungeon generation
    private Partition root;
    private List<Partition> partitions;
    private List<Room> rooms;
    private List<Character> moves;

    // current world state
    private TETile[][] world;
    private TETile[][] perspective;
    private Avatar player;
    private boolean isGameOver;
    private boolean mainMenu;
    private boolean entered;
    private StringBuilder enteredSeed;
    private boolean full;
    private boolean replay;
    private int replayMove;

    // constructor (fills with nothing)
    public World() {
        // empty window
        ter = new TERenderer();
        world = new TETile[WIDTH][HEIGHT];
        perspective = new TETile[WIDTH][HEIGHT];
        moves = new ArrayList<>();
        replayMove = 0;
        full = false;
        replay = false;
        fillBoard(world, Tileset.NOTHING);
        fillBoard(perspective, Tileset.NOTHING);
    }

    // autograder things
    public TETile[][] autograder(String input) {
        // parses for the seed
        partitions = new ArrayList<>();
        readInput(input);
        // start game
        score = 0;
        isGameOver = false;
        Out saveFile = new Out(FILE);
        saveState(saveFile);
        return world;
    }

    // uses a seed for world generation (running parameter)
    public void playWithInputString(String arg) {
        // parses for the seed
        partitions = new ArrayList<>();
        readInput(arg);

        // new window
        ter.initialize(WIDTH, HEIGHT + 3);
        score = 0;

        // start game
        isGameOver = false;
        while (!isGameOver) {
            updateBoard();
            renderBoard();
        }
        Out saveFile = new Out(FILE);
        saveState(saveFile);
        System.out.println(moves);
        System.exit(0);
    }

    // main menu and pick
    public void playWithKeyboard() {
        partitions = new ArrayList<>();
        enteredSeed = new StringBuilder();

        // new window
        ter.initialize(WIDTH, HEIGHT + 3);
        score = 0;

        // main menu
        mainMenu = true;
        entered = false;
        displayMenu();
        while (mainMenu) {
            userInput();
        }
        StdDraw.clear(new Color(0, 0, 0));

        // start game
        isGameOver = false;
        while (!isGameOver) {
            if (!replay) {
                updateBoard();
            } else {
                updateReplay();
            }
            renderBoard();
        }
        Out saveFile = new Out(FILE);
        saveState(saveFile);
        System.out.println(moves);
        System.exit(0);
    }

    // update with a move each time for replay
    private void updateReplay() {
        if (!moves.isEmpty() && moves.get(replayMove) != moves.size()) {
            char currMove = moves.get(replayMove);
            if (currMove == 'w') {
                score = Avatar.tryMove(player, world, 0, 1, score);
            }
            if (currMove == 's') {
                score = Avatar.tryMove(player, world, 0, -1, score);
            }
            if (currMove == 'a') {
                score = Avatar.tryMove(player, world, -1, 0, score);
            }
            if (currMove == 'd') {
                score = Avatar.tryMove(player, world, 1, 0, score);
            }
            if (currMove == 'p') {
                full = !full;
            }

            replayMove += 1;

            fillBoard(perspective, Tileset.NOTHING);
            for (int w = Math.max(0, player.getX() - 5); w < Math.min(WIDTH, player.getX() + 5); w++) {
                for (int h = Math.max(0, player.getY() - 5); h < Math.min(HEIGHT, player.getY() + 5); h++) {
                    perspective[w][h] = world[w][h];
                }
            }
        }
        if (replayMove == moves.size()) {
            replay = !replay;
        }
    }

    // update using user input
    private void updateBoard() {
        char keyPressed = ' ';
        if (StdDraw.hasNextKeyTyped()) {
            keyPressed = StdDraw.nextKeyTyped();
        }
        if (keyPressed == 'w') {
            score = Avatar.tryMove(player, world, 0, 1, score);
            moves.add(keyPressed);
        }
        if (keyPressed == 's') {
            score = Avatar.tryMove(player, world, 0, -1, score);
            moves.add(keyPressed);
        }
        if (keyPressed == 'a') {
            score = Avatar.tryMove(player, world, -1, 0, score);
            moves.add(keyPressed);
        }
        if (keyPressed == 'd') {
            score = Avatar.tryMove(player, world, 1, 0, score);
            moves.add(keyPressed);
        }
        if (keyPressed == ':') {
            moves.add(keyPressed);
        }
        if (keyPressed == 'q') {
            if (moves.get(moves.size() - 1) == ':') {
                moves.remove(moves.size() - 1);
                isGameOver = true;
            }
        }
        if (keyPressed == 'p') {
            full = !full;
        }

        fillBoard(perspective, Tileset.NOTHING);
        for (int w = Math.max(0, player.getX() - 5); w < Math.min(WIDTH, player.getX() + 5); w++) {
            for (int h = Math.max(0, player.getY() - 5); h < Math.min(HEIGHT, player.getY() + 5); h++) {
                perspective[w][h] = world[w][h];
            }
        }
    }

    private void renderBoard() {
        StdDraw.clear(new Color(0, 0, 0));
        if (full) {
            ter.drawTiles(world);
        } else {
            ter.drawTiles(perspective);
        }
        drawHUD();
        StdDraw.show();
        if (replay) {
            StdDraw.pause(500);
        }
    }

    // MAIN MENU
    private void displayMenu() {
        // score
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 5, "CS61B: THE GAME");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 4, "New Game (N)");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 3, "Load Game (L)");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 2, "Replay Game (R)");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 1, "Quit Game (Q)");
        StdDraw.show();
    }

    // enter seed
    private void enterSeed() {
        char number = ' ';
        if (StdDraw.hasNextKeyTyped()) {
            number = StdDraw.nextKeyTyped();
        }

        if (number == 's') {
            seed = enteredSeed.toString();
            entered = true;
        }
        if (number != ' ') {
            enteredSeed.append(number);
        }
    }

    // user picks
    private void userInput() {
        char keyPressed = ' ';
        if (StdDraw.hasNextKeyTyped()) {
            keyPressed = StdDraw.nextKeyTyped();
        }

        if (keyPressed == 'n') {
            // pick seed

            while (!entered) {
                StdDraw.clear(new Color(0, 0, 0));
                StdDraw.setPenColor(255, 255, 255);
                StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 5, "ENTER THE GOD SEED: ");
                enterSeed();
                StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 4, enteredSeed.toString());
                StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 3, "Press (S) to begin ");
                StdDraw.show();
            }
            random = new Random(Long.parseLong(seed));
            generateWorld();
            mainMenu = false;
        }
        if (keyPressed == 'l') {
            In loadFile = new In(FILE);
            loadState(loadFile);
            mainMenu = false;
        }
        if (keyPressed == 'r') {
            In loadFile = new In(FILE);
            replay = true;
            loadState(loadFile);
            mainMenu = false;
        }
        if (keyPressed == 'q') {
            System.exit(0);
        }
    }

    // HUD
    private void drawHUD() {
        // score
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.text(2.0, HEIGHT + 2, "Score: " + this.score);
        // date and time
        currentDateTime = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        StdDraw.text(WIDTH - 5, HEIGHT + 2, formattedDateTime);
        // tile
        String tileType = showTileType();
        StdDraw.text(WIDTH - 5, HEIGHT + 1, "Tile: " + tileType);
    }

    // what tile is mouse hovering on
    private String showTileType() {
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();
        if (mouseX < WIDTH && mouseY < HEIGHT) {
            if (full) {
                if (world[mouseX][mouseY] == Tileset.WALL) {
                    return "WALL";
                } else if (world[mouseX][mouseY] == Tileset.FLOOR) {
                    return "FLOOR";
                } else if (world[mouseX][mouseY] == Tileset.AVATAR) {
                    return "AVATAR";
                } else if (world[mouseX][mouseY] == Tileset.LIGHT_SOURCE) {
                    return "LIGHT";
                } else if (world[mouseX][mouseY] == Tileset.COIN) {
                    return "COIN";
                } else {
                    return "NOTHING";
                }
            } else {
                if (perspective[mouseX][mouseY] == Tileset.WALL) {
                    return "WALL";
                } else if (perspective[mouseX][mouseY] == Tileset.FLOOR) {
                    return "FLOOR";
                } else if (perspective[mouseX][mouseY] == Tileset.AVATAR) {
                    return "AVATAR";
                } else if (perspective[mouseX][mouseY] == Tileset.LIGHT_SOURCE) {
                    return "LIGHT";
                } else if (perspective[mouseX][mouseY] == Tileset.COIN) {
                    return "COIN";
                } else {
                    return "NOTHING";
                }
            }
        } else {
            return "NOTHING";
        }
    }

    // WORLD GENERATION
    // randomly generate dungeons
    public void generateWorld() {
        root = split(new Partition(0, 0, WIDTH, HEIGHT), 4);
        addRooms();
        addPaths(root);
        addPlayer();
        addCoins();
    }

    // COINS
    // randomly place coins in each room
    private void addCoins() {
        for (Room room : rooms) {
            int randomX = RandomUtils.uniform(random, room.getX() + 1, room.getX() + room.getWidth() - 1);
            int randomY = RandomUtils.uniform(random, room.getY() + 1, room.getY() + room.getHeight() - 1);
            if (world[randomX][randomY] != Tileset.AVATAR || world[randomX][randomY] != Tileset.LIGHT_SOURCE) {
                world[randomX][randomY] = Tileset.COIN;
            }
        }
    }

    // PLAYER
    // randomly place player within one of the rooms
    private void addPlayer() {
        int randomRoom = RandomUtils.uniform(random, 0, rooms.size());
        Room room = rooms.get(randomRoom);
        int randomX = RandomUtils.uniform(random, room.getX() + 1, room.getX() + room.getWidth() - 1);
        int randomY = RandomUtils.uniform(random, room.getY() + 1, room.getY() + room.getHeight() - 1);
        world[randomX][randomY] = Tileset.AVATAR;
        player = new Avatar(this, randomX, randomY);
    }

    // PATHS
    // add path between rooms
    private void addPaths(Partition partition) {
        if (partition.getLeft() != null && partition.getRight() != null) {
            Partition left = partition.getLeft();
            Partition right = partition.getRight();
            if (partition.getHorizontalSplit()) {
                for (int d = left.getCenter().getY(); d < right.getCenter().getY(); d++) {
                    world[left.getCenter().getX()][d] = Tileset.FLOOR;
                    if (world[left.getCenter().getX() + 1][d] == Tileset.NOTHING) {
                        world[left.getCenter().getX() + 1][d] = Tileset.WALL;
                    }
                    if (world[left.getCenter().getX() - 1][d] == Tileset.NOTHING) {
                        world[left.getCenter().getX() - 1][d] = Tileset.WALL;
                    }
                }
            } else {
                for (int d = left.getCenter().getX(); d < right.getCenter().getX(); d++) {
                    world[d][left.getCenter().getY()] = Tileset.FLOOR;
                    if (world[d][left.getCenter().getY() + 1] == Tileset.NOTHING) {
                        world[d][left.getCenter().getY() + 1] = Tileset.WALL;
                    }
                    if (world[d][left.getCenter().getY() - 1] == Tileset.NOTHING) {
                        world[d][left.getCenter().getY() - 1] = Tileset.WALL;
                    }
                }
            }
            addPaths(left);
            addPaths(right);
        }
    }

    // ROOMS
    // add rooms to partitions
    private void addRooms() {
        rooms = new ArrayList<>();
        for (Partition partition : partitions) {
            partition.generateRoom(random);
            rooms.add(partition.getRoom());
        }
        drawRooms();
    }

    // draws the room into the grid
    public void drawRooms() {
        for (Room room : rooms) {
            // bottom and top wall
            for (int w = room.getX(); w < room.getX() + room.getWidth(); w++) {
                world[w][room.getY()] = Tileset.WALL;
                world[w][room.getY() + room.getHeight() - 1] = Tileset.WALL;
            }
            // side walls
            for (int h = room.getY(); h < room.getY() + room.getHeight(); h++) {
                world[room.getX()][h] = Tileset.WALL;
                world[room.getX() + room.getWidth() - 1][h] = Tileset.WALL;
            }
            // floor
            for (int w = room.getX() + 1; w < room.getX() + room.getWidth() - 1; w++) {
                for (int h = room.getY() + 1; h < room.getY() + room.getHeight() - 1; h++) {
                    world[w][h] = Tileset.FLOOR;
                }
            }
        }
    }

    // SPLITTING HEURISTIC
    // partitions the grid into smaller partitions
    private Partition split(Partition partition, int iterations) {
        if (iterations > 0) {
            Partition[] children = randomSplit(partition);
            partition.setLeft(split(children[0], iterations - 1));
            partition.setRight(split(children[1], iterations - 1));
        } else {
            partitions.add(partition);
            partition.setLeft(null);
            partition.setRight(null);
        }
        return partition;
    }

    // partitions an area of the grid into two randomly sized partitions
    private Partition[] randomSplit(Partition partition) {
        // decide whether to split horizontally or vertically at random
        boolean horizontalSplit = RandomUtils.bernoulli(random);
        int x = partition.getX();
        int y = partition.getY();
        int width = partition.getWidth();
        int height = partition.getHeight();

        if (horizontalSplit) {
            // create two new boxes representing the bottom and top parts
            partition.setHorizontalSplit(true);

            // pick random heights for the partitions
            int randomHeight = RandomUtils.uniform(random, 0, height);
            int resultantHeight = height - randomHeight;

            // set the children
            Partition left = new Partition(x, y, width, randomHeight);
            Partition right = new Partition(x, y + left.getHeight(), width, resultantHeight);

            // re-splits if it does not fit desired ratio
            double leftRatio = (double) left.getHeight() / left.getWidth();
            double rightRatio = (double) right.getHeight() / right.getWidth();
            if (leftRatio < RATIO || rightRatio < RATIO) {
                return randomSplit(partition);
            } else {
                return new Partition[]{left, right};
            }
        } else {
            // create two new boxes representing the left and right parts
            partition.setHorizontalSplit(false);

            // pick random widths for the partitions
            int randomWidth = RandomUtils.uniform(random, 0, width);
            int resultantWidth = width - randomWidth;

            // set the children
            Partition left = new Partition(x, y, randomWidth, height);
            Partition right = new Partition(x + left.getWidth(), y, resultantWidth, height);

            // re-splits if it does not fit desired ratio
            double leftRatio = (double) left.getWidth() / left.getHeight();
            double rightRatio = (double) right.getWidth() / right.getHeight();
            if (leftRatio < RATIO || rightRatio < RATIO) {
                return randomSplit(partition);
            } else {
                return new Partition[]{left, right};
            }
        }
    }

    // SAVING AND LOADING
    // read input
    private void readInput(String input) {
        input = input.toLowerCase();
        String[] deleteN = input.split("n", 2);
        // check for loading string
        if (deleteN.length == 1) {
            // load the most recent save
            In loadFile = new In(FILE);
            loadState(loadFile);
            String[] checkQ = deleteN[0].split(":", 2);
            char[] givenMoves = checkQ[0].toCharArray();
            for (char move : givenMoves) {
                moves.add(move);
            }
            setOfMoves(givenMoves);
            if (checkQ.length != 1) {
                // will quit
                Out saveFile = new Out(FILE);
                saveState(saveFile);
            }
        } else {
            String[] deleteS = deleteN[1].split("s", 2);
            seed = deleteS[0];
            random = new Random(Long.parseLong(deleteS[0]));
            generateWorld();
            if (deleteS.length != 1) {
                // movements and potentially quit
                String[] checkQ = deleteS[1].split(":", 2);
                char[] givenMoves = checkQ[0].toCharArray();
                for (char move : givenMoves) {
                    moves.add(move);
                }
                setOfMoves(givenMoves);
                if (checkQ.length != 1) {
                    // will quit
                    Out saveFile = new Out(FILE);
                    saveState(saveFile);
                }
            }
        }
    }

    // load state
    private void loadState(In loadFile) {
        seed = loadFile.readLine();
        random = new Random(Long.parseLong(seed));
        generateWorld();
        if (!loadFile.isEmpty()) {
            String readMoves = loadFile.readLine();
            char[] givenMoves = readMoves.toCharArray();
            for (char move : givenMoves) {
                moves.add(move);
            }
            if (!replay) {
                setOfMoves(givenMoves);
            }
        }
        String lastScore = loadFile.readLine();
        if (!replay) {
            score = Integer.parseInt(lastScore);
        }
    }

    // save state
    private void saveState(Out saveFile) {
        saveFile.println(seed);
        for (int m = 0; m < moves.size(); m++) {
            if (m != moves.size() - 1) {
                saveFile.print(moves.get(m));
            } else {
                saveFile.println(moves.get(m));
            }
        }
        saveFile.print(score);
    }

    // move given a set of moves
    private void setOfMoves(char[] givenMoves) {
        for (Character move : givenMoves) {
            if (move == 'w') {
                score = Avatar.tryMove(player, world, 0, 1, score);
            }
            if (move == 's') {
                score = Avatar.tryMove(player, world, 0, -1, score);
            }
            if (move == 'a') {
                score = Avatar.tryMove(player, world, -1, 0, score);
            }
            if (move == 'd') {
                score = Avatar.tryMove(player, world, 1, 0, score);
            }
        }
    }

    // SCREEN FILL
    // fills the board with specific tile passed in
    private void fillBoard(TETile[][] board,TETile tile) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                board[i][j] = tile;
            }
        }
    }
}
