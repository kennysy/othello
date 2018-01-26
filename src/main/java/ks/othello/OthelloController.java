package ks.othello;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ks.othello.OthelloGrid.*;

public class OthelloController {
    private final OthelloGrid grid;
    private final OthelloPrinter printer;
    private boolean randomBlack = false;
    private boolean randomWhite = false;

    public OthelloController(OthelloGrid grid, OthelloPrinter printer) {
        this.printer = printer;
        this.grid = grid;
        resetGame();
    }

    /**
     * Prepare for a new game
     */
    private void resetGame() {
        grid.setAll(Color.BLANK.getColor());
        grid.setPixel(4, 4, Color.WHITE.getColor());
        grid.setPixel(5, 5, Color.WHITE.getColor());
        grid.setPixel(4, 5, Color.BLACK.getColor());
        grid.setPixel(5, 4, Color.BLACK.getColor());
    }

    /**
     * Get all location as a list for the input color
     *
     * @param color color wanted to check
     * @return list of location with that color
     */
    @VisibleForTesting
    public Collection<OthelloLocation> getColorLocation(Color color) {
        ArrayList<OthelloLocation> result = new ArrayList<>();
        IntStream.range(1, GRID_SIZE + 1)
                .forEach(y -> IntStream.range(1, GRID_SIZE + 1).filter(x -> grid.getPixel(x, y) == color.getColor()).forEach(x -> result.add(new OthelloLocation(x, y))));
        return result;
    }

    /**
     * Get the game's winner, return BLANK if it is draw game, return null if not yet finished.
     *
     * @return the winner
     */
    @VisibleForTesting
    public Color getWinner() {
        Collection<OthelloLocation> whiteValidMove = getValidMove(Color.WHITE);
        Collection<OthelloLocation> blackValidMove = getValidMove(Color.BLACK);
        if (blackValidMove.isEmpty() && whiteValidMove.isEmpty()) {
            int whiteCount = getColorLocation(Color.WHITE).size();
            int blackCount = getColorLocation(Color.BLACK).size();
            if (whiteCount > blackCount) return Color.WHITE;
            if (blackCount > whiteCount) return Color.BLACK;
            return Color.BLANK;
        }
        return null;
    }

    /**
     * Return frue if any direction is valid
     *
     * @param x     axis coordinates start with 1
     * @param y     axis coordinates start with 1
     * @param color of current user, either black or white
     * @return true if any direction is valid
     */
    @VisibleForTesting
    public boolean isValidMove(int x, int y, Color color) {
        return EnumSet.allOf(Direction.class).stream().anyMatch(direction -> isValidDirection(x, y, color, direction));
    }

    /**
     * Put the location to the user's color and flip all captured
     *
     * @param x     axis coordinates start with 1
     * @param y     axis coordinates start with 1
     * @param color of current user, either black or white
     */
    @VisibleForTesting
    public void executeMove(int x, int y, Color color) {
        EnumSet.allOf(Direction.class).stream().filter(d -> isValidDirection(x, y, color, d)).forEach(direction -> {
            int count = getOppositeCount(x, y, color, direction);
            IntStream.rangeClosed(0, count).forEach(c -> grid.setPixel(x + (count * direction.offsetX), y + (count * direction.offsetY), color.getColor()));
        });
        grid.setPixel(x, y, color.getColor());
    }

    /**
     * Return true if that direction first found some opposite color and then same color
     *
     * @param x         axis coordinates start with 1
     * @param y         axis coordinates start with 1
     * @param color     color of current user, either black or white
     * @param direction any of the eight directions
     * @return true if that direction first found some opposite color and then same color
     */
    @VisibleForTesting
    public boolean isValidDirection(int x, int y, Color color, Direction direction) {
        int oppositeCount = getOppositeCount(x, y, color, direction);
        return oppositeCount > 0 && isSameColor(x + ((oppositeCount + 1) * direction.getOffsetX()), y + ((oppositeCount + 1) * direction.getOffsetY()), color);
    }

    /**
     * Find the number of location that is opposite color in that direction
     *
     * @param x         axis coordinates start with 1
     * @param y         axis coordinates start with 1
     * @param color     color of current user, either black or white
     * @param direction any of the eight directions
     * @return number of location with opposite color
     */
    @VisibleForTesting
    public int getOppositeCount(int x, int y, Color color, Direction direction) {
        int oppositeCount = 0;
        while (isOppositeColor(x + ((oppositeCount + 1) * direction.getOffsetX()), y + ((oppositeCount + 1) * direction.getOffsetY()), color)) {
            oppositeCount++;
        }
        return oppositeCount;
    }

    /**
     * Return true if it is same color
     *
     * @param x     axis coordinates start with 1
     * @param y     axis coordinates start with 1
     * @param color of current user, either black or white
     * @return true if it is same color
     */
    @VisibleForTesting
    public boolean isSameColor(int x, int y, Color color) {
        return grid.isValidRange(x, y) && grid.getPixel(x, y) == color.getColor();
    }

    /**
     * Return true if it is opposite color
     *
     * @param x     axis coordinates start with 1
     * @param y     axis coordinates start with 1
     * @param color of current user, either black or white
     * @return true if it is opposite color
     */
    @VisibleForTesting
    public boolean isOppositeColor(int x, int y, Color color) {
        return grid.isValidRange(x, y) && grid.getPixel(x, y) == color.getOppositeColor();
    }

    /**
     * Loop through all the empty location and find all valid move
     *
     * @param color of current user, either black or white
     * @return list of valid location(s)
     */
    @VisibleForTesting
    public Collection<OthelloLocation> getValidMove(Color color) {
        return getColorLocation(Color.BLANK).stream().filter(loc -> isValidMove(loc.getKey(), loc.getValue(), color)).collect(Collectors.toList());
    }

    /**
     * Get user input until have a winner or user close the input stream
     *
     * @return the winner, null if user close the input stream
     */
    public Color play() {
        Color winner;
        OthelloLocation input = null;
        Color currentColor = Color.BLACK;
        resetGame();
        printer.printGrid();
        do {
            if (getValidMove(currentColor).isEmpty()) {
                printer.warnNoValidInput(currentColor);
                currentColor = getOppositeColor(currentColor);
            } else {
                input = getUserInput(currentColor);
                if (input != null && isValidMove(input.getKey(), input.getValue(), currentColor)) {
                    executeMove(input.getKey(), input.getValue(), currentColor);
                    currentColor = getOppositeColor(currentColor);
                    printer.printGrid();
                } else {
                    printer.warnInvalidInput(currentColor, input);
                }
            }
            winner = getWinner();
        } while (winner == null && input != null);
        int whiteCount = getColorLocation(Color.WHITE).size();
        int blackCount = getColorLocation(Color.BLACK).size();
        printer.printWinner(winner, whiteCount, blackCount);
        return winner;
    }

    /**
     * @param currentColor of current user, either black or white
     * @return the user input, return null if input stream is closed.
     */
    @VisibleForTesting
    public OthelloLocation getUserInput(Color currentColor) {
        Collection<OthelloLocation> validMove = getValidMove(currentColor);
        OthelloLocation result = null;
        if ((randomBlack && currentColor == Color.BLACK) || (randomWhite && currentColor == Color.WHITE)) {
            result = (OthelloLocation) validMove.toArray()[ThreadLocalRandom.current().nextInt(0, validMove.size())];
        }
        return printer.printUserQuery(currentColor, validMove, result);
    }


    public void setRandomBlack(boolean randomBlack) {
        this.randomBlack = randomBlack;
    }

    public void setRandomWhite(boolean randomWhite) {
        this.randomWhite = randomWhite;
    }
}
