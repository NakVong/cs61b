package core;

public class Main {
    // only deciding on the method to run the program
    public static void main(String[] args) {

        // build your own world!
        if (args.length > 1) {
            System.out.println("Can only have one argument - the replay string");
            System.exit(0);
        } else if (args.length == 1) {
            World world = new World();
            world.playWithInputString(args[0]);
        } else {
            World world = new World();
            world.playWithKeyboard();
        }
    }
}
