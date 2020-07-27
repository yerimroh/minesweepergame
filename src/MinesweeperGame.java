package cs1302.game;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class represents the Minesweeper Game.
 * Includes instance variables such as {@code row}, {@code column},
 * {@code rounds}, {@code remainingMine}, {@code score}, etc.
 * Taking a path to a seed file from the command line argument,
 * the program interprets and initializes each of the instance variables.
 */
public class MinesweeperGame {
    private int row;
    private int column;
    private int rounds = 0;
    private int remainingMine; // number of Mines
    private double score;
    private String[][] userField; // mineField the user interacts with
    private String[][] secretField; // mineField that stores the location of mines
    private int[] mineCoordinates;
    private boolean mineRevealed;
    private Scanner k = new Scanner(System.in);

/**
 * This constructor sets up the instance variables {@code row}, {@code column},
 * {@code remainingMine}, and sets up the dimensions of the two minefields, using the values.
 *
 * @param seed a command line argument string {@code seed} that has the relative path
 * of the seedfile.
 * @throws FileNotFoundException if the file cannot be found and terminates the program.
 */
    public MinesweeperGame(String seed) {
        try {
            File seedFile = new File(seed);
            Scanner configScanner = new Scanner(seedFile);

            setUpVariables(seedFile);
            userField = new String [row][column];
            secretField = new String [row][column];

            for (int j = 0; j < row; j++) {
                for (int k = 0; k < column; k++) {
                    userField [j][k] = " ";
                    secretField[j][k] = " ";
                } // nested for
            } // for

            for (int i = 0; i < mineCoordinates.length; i += 2) {
                secretField[mineCoordinates[i]][mineCoordinates[i + 1]] = "*";
            } // for

        } catch (FileNotFoundException fnfe) {
            String fileName = seed;
            if (seed.contains("/")) {
                int index = seed.lastIndexOf('/');
                fileName = seed.substring(index + 1);
            }

            System.out.println("\nSeedfile Not Found Error: Cannot create game with ");
            System.out.print(fileName + ", because it cannot be found ");
            System.out.print("or cannot be read due to permission.\n\n");
            System.exit(1);
        } //try
    } // MinesweeperGame

/**
 * This method sets up the variables {@code row}, {@code column}, and {@code remainingMines}.
 * given the object {@code File} file.
 *
 * @param file the seed file object from the path of command line argument.
 * @throws FileNotFoundException to the constructor that calls it.
 */
    public void setUpVariables(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        if (sc.hasNextInt()) {
            this.row = sc.nextInt();
            if (row < 5) {
                System.out.print("\nSeedfile Value Error: Cannot create a mine ");
                System.out.print("field with that many rows and/or columns!\n");
                System.exit(3);
            } // if row
            if (sc.hasNextInt()) {
                this.column = sc.nextInt();
                if (column < 5) {
                    System.out.print("\nSeedfile Value Error: Cannot create a mine ");
                    System.out.print("field with that many rows and/or columns!\n");
                    System.exit(3);
                } // if column
                if (sc.hasNextInt()) {
                    this.remainingMine = sc.nextInt();
                    if (remainingMine > (row * column)) {
                        printFileError(file);
                    } // if
                } else {
                    printFileError(file);
                } // else
            } else {
                printFileError(file);
            } // else
        } else {
            printFileError(file);
        } // else

        mineCoordinates = new int[remainingMine * 2];
        for (int i = 0; i < mineCoordinates.length; i += 2) {
            if (sc.hasNextInt()) {
                mineCoordinates[i] = sc.nextInt();
            } else {
                printFileError(file);
            } // if-else
            if (sc.hasNextInt()) {
                mineCoordinates[i + 1] = sc.nextInt();
            } else {
                printFileError(file);
            } // if-else
        } // for
        if (sc.hasNextInt()) {
            printFileError(file);
        }
    } // setUpVariables

/**
 * prints out the error message.
 *
 * @param file a {@code file} object.
 */
    public void printFileError(File file) {
        System.out.print("\n\nSeedfile Format Error: Cannot create game with " + file.getName());
        System.out.print(", because it is not formatted correctly.\n");
        System.exit(1);
    } // printFileError

 /**
 * Prints out the welcome banner once the game starts for the first time.
 *
 */
    public void printWelcome() {
        System.out.print("\n        _\n");
        System.out.print("  /\\/\\ (_)_ __   ___  _____      _____  ___ _ __   ___ _ __\n");
        System.out.print(" /    \\| | '_ \\ / _ \\/ __\\ \\ /\\ / / _ \\/ _ \\ '_ \\ / _ \\ '__|");
        System.out.print("\n/ /\\/\\ \\ | | | |  __/\\__ \\  V  V /  __/  __/ |_) |  __/ |");
        System.out.print("\n\\/    \\/_|_| |_|\\___||___/ \\_/\\_/ \\___|\\___| .__/ \\___|_|");
        System.out.print("\n                 A L P H A   E D I T I O N |_| v2020.sp\n");

    } // prints the welcome banner

/**
 * Prints the current contents of the {@code userField}.
 */
    public void printMineField() {
        int rowDigit = (int) (Math.log10(userField.length - 1) + 1);
        int colDigit = (int) (Math.log10(userField[0].length - 1) + 1);
        String rowFormat = " %" + rowDigit + "d |";
        String colFormat = "%" + (colDigit + 3) + "d";
        String gridFormat = "%" + (colDigit + 1) + "s";
        String noFogFormat = "%" + (colDigit + 2) + "s|";

        System.out.println("\n\nRounds Completed: " + rounds + "\n");

        for (int i = 0; i < userField.length; i++) {
            System.out.print("\n");
            System.out.printf(rowFormat, i);

            for (int j = 0; j < userField[i].length; j++) {
                if (userField[i][j].length() == 1) {
                    System.out.printf(gridFormat, userField[i][j]);
                    System.out.print(" |");
                } // if
                if (userField[i][j].length() == 3) {
                    System.out.printf(noFogFormat, userField[i][j]);

                } // if
            } // for
        } // for

        System.out.print("\n");

        for (int l = 0; l < rowDigit + 1; l++) {
            System.out.print(" ");
        }
        for (int k = 0; k < userField[0].length; k++) {
            System.out.printf(colFormat, k);
        }

        System.out.print("\n");

    } // printMineField

/**
 * Prints the game prompt to standard output and interpret user input.
 * Reads the user input from the keyboard and interprets it.
 */
    public void promptUser() {
        resetField();
        System.out.print("\nminesweeper-alpha: ");
        String fullInput = k.nextLine();
        Scanner sc = new Scanner(fullInput);
        String userInput = "";
        int[] tok = new int[2];
        int numTokens = 0;

        if (sc.hasNext()) {
            userInput = sc.next();
        } else {
            System.out.println("Input Error: Command not recognized!");
        } // has a command?

      if (sc.hasNextInt()) {
            tok[0] = sc.nextInt();
            numTokens++;
            if (sc.hasNextInt()) {
                tok[1] = sc.nextInt();
                numTokens++;
            }
        }

        if ((userInput.equals("mark")) || (userInput.equals("m"))) {
            if (((isInBounds(tok[0],tok[1])) && (numTokens == 2)) && (!sc.hasNext())) {
                userField[tok[0]][tok[1]] = "F";
                rounds++;
            } else {
                System.out.println("Input Error: Command not recognized!");
            }
        } else if ((userInput.equals("reveal")) || (userInput.equals("r"))) {
            if (((isInBounds(tok[0],tok[1])) && (numTokens == 2)) && (!sc.hasNext())) {
                if (hasMine(tok[0],tok[1])) {
                    mineRevealed = true;
                } else {
                    userField[tok[0]][tok[1]] = Integer.toString(getAdjMines(tok[0],tok[1]));
                    rounds++;
                } // else
            } else {
                System.out.println("Input Error: Command not recognized!");
            }
        } else if ((userInput.equals("guess")) || (userInput.equals("g"))) {
            otherCommand(userInput, tok[0], tok[1], numTokens, sc.hasNext());
        } else if ((userInput.equals("help")) || (userInput.equals("h"))) {
            otherCommand(userInput, tok[0], tok[1], numTokens, sc.hasNext());
        } else if ((userInput.equals("quit")) || (userInput.equals("q"))) {
            otherCommand(userInput, tok[0], tok[1], numTokens, sc.hasNext());
        } else if (userInput.equals("nofog")) {
            otherCommand(userInput, tok[0], tok[1], numTokens, sc.hasNext());
        } else {
            System.out.println("Input Error: Command not recognized!");
        } // else

    } // promptUser

/**
 * Given the userInput string from the {@code promptUser()} method, this method
 * interprets and executes the help, and quit commands to make the {@code promptUser()}
 * method shorter.
 *
 * @param userInput a {@code String} that represents the user command.
 * @param r an {@code int} value that represents the row coordinate from the user command
 * @param c an {@code int} value that represents the column coordinate from the user command
 * @param numTokens {@code numTokens} that represents the number of tokens after the letter command
 * @param hasNext {@code hasNext} that represents the boolean value if the user command has an
 * extra token at the end
 */
    public void otherCommand(String userInput, int r, int c, int numTokens, boolean hasNext) {

        if ((userInput.equals("quit")) || (userInput.equals("q"))) {
            System.out.println("\nQuitting the game... \nBye!\n");
            System.exit(0);
        } else if ((userInput.equals("h")) || (userInput.equals("help"))) {
            System.out.println("\nCommands Available...\n - Reveal: r/reveal row col");
            System.out.println(" -   Mark: m/mark   row col\n -  Guess: g/guess  row col");
            System.out.println(" -   Help: h/help\n -   Quit: q/quit");
            rounds++;
        } else if (userInput.equals("nofog")) {
            noFog();
            rounds++;
        } else if ((userInput.equals("g")) || (userInput.equals("guess"))) {
            if (((isInBounds(r, c)) && (numTokens == 2)) && (hasNext == false)) {
                userField[r][c] = "?";
                rounds++;
            } else {
                System.out.println("Input Error: Command not recognized!");
            }
        } // promptUser
    }


/**
 * Resets the mine field so that it won't have any greater or less than symbols.
 * Used in the beginning of {@code promptUser()} method so that it does not show
 * the "hints" for the next round.
 */
    public void resetField() {
        for (int i = 0; i < userField.length; i++) {
            for (int j = 0; j < userField[i].length; j++) {
                if (userField[i][j].charAt(0) == '<') {
                    userField[i][j] = Character.toString(userField[i][j].charAt(1));
                } // if
            } // for
        } // for
    } // resetField

/**
 * Manipulates the user's minefield so that it executes the nofog command.
 * It identifies which of the grids contain a mine and adds the less than and
 * greater sign to the userField array.
 */
    public void noFog() {
        for (int i = 0; i < secretField.length; i++) {
            for (int j = 0; j < secretField[i].length; j++) {
                if (secretField[i][j].equals("*")) {
                    userField[i][j] = "<" + userField[i][j] + ">";
                }
            } // for
        } // for
    } // nofog

 /**
 * Returns true if all the conditions for wining are met.
 * Winning conditions are:
 * All squares containing a mine are marked as 'definitly' containing a mine.
 * All squares not containing a mine are revealed.
 * @return boolean condition 'true' if player wins.
 */
    public boolean isWon() {
        boolean isWon = false;
        int markedCount = 0;
        int revealedCount = 0;
        for (int i = 0; i < userField.length; i++) {
            for (int j = 0; j < userField[i].length; j++) {
                if ((userField[i][j].equals("F")) && (secretField[i][j].equals("*"))) {
                    markedCount++;
                } // if
            } // for
        } // if all the grids containing mine are marked as "F"
        for (int k = 0; k < userField.length; k++) {
            for (int l = 0; l < userField[k].length; l++) {
                if (isInteger(userField[k][l])) {
                    revealedCount++;
                } // if
            } // for
        } // for
        if ((markedCount == remainingMine) && (revealedCount == ((row * column) - remainingMine))) {
            isWon = true;
        } // if
        return isWon;
    } // isWon()

/**
 * Returns true if the {@code String} s is a numeric value.
 *
 * @param s a {@code String} s. The target of the method in checking if it represents a numetic
 * value.
 * @return isInteger represents the state of the string value. True if {@code String} s is true,
 * false otherwise.
 */
    public boolean isInteger(String s) {
        boolean isInteger;
        try {
            Integer.parseInt(s);
            isInteger = true;
        } catch (NumberFormatException nfe) {
            isInteger = false;
        }
        return isInteger;
    } // isInteger

/**
 * Returns true if all the conditions for losing are met.
 * Losing conditions are: The player reveals a square containing a mine.
 * @return boolean condition 'true' if player loses.
 */
    public boolean isLost() {
        if (mineRevealed == true) {
            return true;
        } else {
            return false;
        } // else
    }

/**
 * Prints out the winning banner when the player wins.
 * Also prints out the score the user received.
 */
    public void printWin() {
        calculateScore();
        System.out.println("\n ░░░░░░░░░▄░░░░░░░░░░░░░░▄░░░░ \"So Doge\"");
        System.out.println(" ░░░░░░░░▌▒█░░░░░░░░░░░▄▀▒▌░░░");
        System.out.println(" ░░░░░░░░▌▒▒█░░░░░░░░▄▀▒▒▒▐░░░ \"Such Score\"");
        System.out.println(" ░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐░░░");
        System.out.println(" ░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐░░░ \"Much Minesweeping\"");
        System.out.println(" ░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌░░░");
        System.out.println(" ░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒▌░░ \"Wow\"");
        System.out.println(" ░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐░░");
        System.out.println(" ░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄▌░");
        System.out.println(" ░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒▌░");
        System.out.println(" ▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒▐░");
        System.out.println(" ▐▒▒▐▀▐▀▒░▄▄▒▄▒▒▒▒▒▒░▒░▒░▒▒▒▒▌");
        System.out.println(" ▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒▒▒░▒░▒░▒▒▐░");
        System.out.println(" ░▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒░▒░▒░▒░▒▒▒▌░");
        System.out.println(" ░▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▒▄▒▒▐░░");
        System.out.println(" ░░▀▄▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▄▒▒▒▒▌░░");
        System.out.println(" ░░░░▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀░░░ CONGRATULATIONS!");
        System.out.println(" ░░░░░░▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀░░░░░ YOU HAVE WON!");
        System.out.print(" ░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▀▀░░░░░░░░ SCORE: ");
        System.out.printf("%1.2f",score);
        System.out.println("");
        System.exit(0);
    } // printWin

/**
 * Print out the losing banner when the game is over and the player loses.
 */
    public void printLoss() {
        System.out.println("\n Oh no... You revealed a mine!");
        System.out.println("  __ _  __ _ _ __ ___   ___    _____   _____ _ __");
        System.out.println(" / _` |/ _` | '_ ` _ \\ / _ \\  / _ \\ \\ / / _ \\ '__|");
        System.out.println("| (_| | (_| | | | | | |  __/ | (_) \\ V /  __/ |");
        System.out.println(" \\__, |\\__,_|_| |_| |_|\\___|  \\___/ \\_/ \\___|_|");
        System.out.println(" |___/\n");
        System.exit(0);
    } // printLoss

/**
 * Provide the main game loop by invoking other instance methods.
 */
    public void play() {
        printWelcome();
        while ((isLost() == false) || (isWon() == false)) {
            printMineField();
            promptUser();
            if (isLost()) {
                printLoss();
            } else if (isWon()) {
                printWin();
            }

        } // while

    } // play

/**
 * Calculates the score for the user.
 */
    public void calculateScore () {
        score = 100.0 * row * column / rounds;
    } // calculateScore

/**
 * Returns the number of mines adjacent to the specified square in the grid.
 *
 * @param row the row index of the square
 * @param col the column index of the square
 * @return the number of adjacent mines
 */
    private int getAdjMines(int row, int col) {
        int numOfMines = 0;
        if ((isInBounds(row - 1, col - 1)) && (hasMine(row - 1, col - 1))) {
            numOfMines++;
        } // isInBounds

        if ((isInBounds(row - 1, col)) && (hasMine(row - 1, col))) {
            numOfMines++;
        } // isInBounds

        if ((isInBounds(row - 1, col + 1)) && (hasMine(row - 1, col + 1))) {
            numOfMines++;
        } // isInBounds

        if ((isInBounds(row, col - 1)) && (hasMine(row, col - 1))) {
            numOfMines++;
        } // isInBounds

        if ((isInBounds(row, col + 1)) && (hasMine(row, col + 1))) {
            numOfMines++;
        } //isInBounds

        if ((isInBounds(row + 1, col - 1)) && (hasMine(row + 1, col - 1))) {
            numOfMines++;
        } //isInBounds

        if ((isInBounds(row + 1, col)) && (hasMine(row + 1, col))) {
            numOfMines++;
        } // isInBounds

        if ((isInBounds(row + 1, col + 1)) && (hasMine(row + 1,col + 1))) {
            numOfMines++;
        } // isInBounds

        return numOfMines;
    } // getNumAdjMines

/**
 * Returns true if the square contains a mine in it.
 *
 * @param row the row index of the square
 * @param col the column index of the square
 * @return true if the square does contain a mine, false otherwise.
 */
    public boolean hasMine(int row, int col) {
        boolean hasMine = false;
        if (isInBounds(row, col)) {
            if (secretField[row][col].equals("*")) {
                hasMine = true;
            } // nested if
        } // if
        return hasMine;
    } // isExistMine

/**
 * Returns true if the square is part of the mine field(valid).
 *
 * @param row the row index of the square
 * @param col the column index of the square
 * @return true if the square is in the same grid; false otherwise.
 */
    private boolean isInBounds(int row, int col) {
        boolean isInBounds = false;
        if ((row >= 0) && (col >= 0)) {
            if ((row < userField.length) && (col < userField[0].length)) {
                isInBounds = true;
            } // nested if
        } // if 1
        return isInBounds;
    } // isInBounds

}
