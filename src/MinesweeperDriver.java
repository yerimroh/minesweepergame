package cs1302.game;

import cs1302.game.MinesweeperGame;

/**
 * This is the Driver class of the {@link cs1302.game.MinesweeperGame} program
 * Given the command line argument with the path of the seed file, the driver
 * creates a {@link cs1302.game.MinesweeperGame} object and calls the method {@code play()}.
 *
 * @param args Represents the command line arguments.
 */

public class MinesweeperDriver {
    public static void main (String[] args) {

        if ((args.length == 2) && (args[0].equals("--seed"))) {
            String filePath = args[1];

            MinesweeperGame game = new MinesweeperGame(filePath);
            game.play();

        } else if ((args.length > 2) || (args.length < 2)) {
            System.out.println ("\nUnable to interpret supplied command-line arguments.\n");
            System.exit(1);

        } else {
            System.out.println("\nUnable to interpret supplied command-line arguments.\n");
            System.exit(1);
        }

    } // main
} // MinesweeperDriver
