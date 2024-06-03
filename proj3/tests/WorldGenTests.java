import core.AutograderBuddy;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.jupiter.api.Test;
import tileengine.TERenderer;
import tileengine.TETile;

public class WorldGenTests {
    @Test
    public void basicTest() {
        // put different seeds here to test different worlds
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n1234567890123456789s");

        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(5000); // pause for 5 seconds so you can see the output
    }

    @Test
    public void basicInteractivityTest() {
        // put different seeds here to test different worlds
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n123s");

        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(1000); // pause for 5 seconds so you can see the output

        tiles = AutograderBuddy.getWorldFromInput("n123ss");

        ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(1000); // pause for 5 seconds so you can see the output

        tiles = AutograderBuddy.getWorldFromInput("n123ssa");

        ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(1000); // pause for 5 seconds so you can see the output

        tiles = AutograderBuddy.getWorldFromInput("n123ssas");

        ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(1000); // pause for 5 seconds so you can see the output

        tiles = AutograderBuddy.getWorldFromInput("n123ssasd");

        ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(1000); // pause for 5 seconds so you can see the output
    }

    @Test
    public void basicSaveTest() {
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n523s");

        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(3000); // pause for 5 seconds so you can see the output

        tiles = AutograderBuddy.getWorldFromInput("n123sww:q");

        ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(1000); // pause for 5 seconds so you can see the output

        tiles = AutograderBuddy.getWorldFromInput("ls:q");

        ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(1000); // pause for 5 seconds so you can see the output

        tiles = AutograderBuddy.getWorldFromInput("lss:q");

        ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(1000); // pause for 5 seconds so you can see the output

        tiles = AutograderBuddy.getWorldFromInput("lsss:q");

        ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(1000); // pause for 5 seconds so you can see the output
    }
}
