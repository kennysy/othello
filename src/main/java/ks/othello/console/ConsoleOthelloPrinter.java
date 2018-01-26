package ks.othello.console;

import ks.common.Grid;
import ks.othello.OthelloGrid;
import ks.othello.OthelloLocation;
import ks.othello.OthelloPrinter;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.IntStream;

import static ks.othello.OthelloGrid.Color;
import static ks.othello.OthelloLocation.X_INPUT_MAP;
import static ks.othello.OthelloLocation.Y_INPUT_MAP;


public class ConsoleOthelloPrinter implements OthelloPrinter {
    private static final String GET_USER_INPUT = "Player '%s' move: ";
    private static final String USER_INPUT_TIPS = "Available move(s): %s";
    private static final String INVALID_MOVE = "Invalid move. Please try again." + System.lineSeparator();
    private static final String NO_VALID_MOVE = "Player '%s' no valid move." + System.lineSeparator();
    private static final String GAME_OVER = "No further moves available." + System.lineSeparator() + "Player '%s' wins ( %d vs %d )" + System.lineSeparator();
    private final PrintStream stream;
    private final OthelloGrid grid;
    private final Scanner scanner;
    private boolean displayTips = false;

    public ConsoleOthelloPrinter(OthelloGrid grid, InputStream input, PrintStream stream) {
        this.grid = grid;
        this.stream = stream;
        scanner = new Scanner(input);
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public void printGrid() {
        stream.println();
        int width = grid.getWidth();
        int height = grid.getHeight();
        IntStream.rangeClosed(1, height).forEach(y -> {
            stream.append(Integer.toString(y));
            stream.append(' ');
            IntStream.rangeClosed(1, width).forEach(x -> stream.append(grid.getPixel(x, y)));
            stream.println();
        });
        stream.println("  abcdefgh");
        stream.flush();
    }

    @Override
    public OthelloLocation printUserQuery(Color currentColor, Collection<OthelloLocation> validMove, OthelloLocation result) {
        int x = -1;
        int y = -1;
        if (result == null) {
            if (displayTips) {
                printTips(validMove);
            }
            stream.print(String.format(GET_USER_INPUT, currentColor.getColor()));
            String input;
            try {
                input = scanner.nextLine();
            } catch (Exception e) {
                return null;
            }
            if (input.length() == 2) {
                char ch1 = input.charAt(0);
                char ch2 = input.charAt(1);
                x = X_INPUT_MAP.indexOf(ch1);
                if (x == -1) {
                    x = X_INPUT_MAP.indexOf(ch2);
                    y = Y_INPUT_MAP.indexOf(ch1);
                } else {
                    y = Y_INPUT_MAP.indexOf(ch2);
                }
            }
            return new OthelloLocation(x, y);
        } else {
            printTips(validMove);
            stream.println(String.format(GET_USER_INPUT, currentColor.getColor()) + result);
            return result;
        }
    }

    @Override
    public void printTips(Collection<OthelloLocation> validMove) {
        stream.println(String.format(USER_INPUT_TIPS, validMove));
    }

    @Override
    public void warnNoValidInput(Color currentColor) {
        stream.print(String.format(NO_VALID_MOVE, currentColor.getColor()));
    }

    @Override
    public void printWinner(Color winner, int whiteCount, int blackCount) {
        stream.print(String.format(GAME_OVER, winner == null ? "?" : winner.getColor(), Math.max(whiteCount, blackCount), Math.min(whiteCount, blackCount)));
    }

    @Override
    public void warnInvalidInput(Color currentColor, OthelloLocation input) {
        stream.print(INVALID_MOVE);
        stream.println(input);
        printGrid();
    }

    public boolean isDisplayTips() {
        return displayTips;
    }

    public void setDisplayTips(boolean displayTips) {
        this.displayTips = displayTips;
    }
}
