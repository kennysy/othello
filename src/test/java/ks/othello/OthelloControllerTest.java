package ks.othello;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

import static ks.othello.OthelloGrid.Color;
import static ks.othello.OthelloGrid.getOppositeColor;
import static org.junit.jupiter.api.Assertions.*;

class OthelloControllerTest extends AbstractOthelloTest {
    @Test
    void testGetColorLocation() {
        Collection<OthelloLocation> black = othelloController.getColorLocation(Color.BLACK);
        Collection<OthelloLocation> white = othelloController.getColorLocation(Color.WHITE);
        Collection<OthelloLocation> blank = othelloController.getColorLocation(Color.BLANK);
        assertEquals(2, black.size());
        assertEquals(2, white.size());
        assertEquals(60, blank.size());
        assertEquals("[[e4], [d5]]", black.toString());
        assertEquals("[[d4], [e5]]", white.toString());
        assertEquals("[[a1], [b1], [c1], [d1], [e1], [f1], [g1], [h1], [a2], [b2], [c2], [d2], [e2], [f2], [g2], [h2], [a3], [b3], [c3], [d3], [e3], [f3], [g3], [h3], [a4], [b4], [c4], [f4], [g4], [h4], [a5], [b5], [c5], [f5], [g5], [h5], [a6], [b6], [c6], [d6], [e6], [f6], [g6], [h6], [a7], [b7], [c7], [d7], [e7], [f7], [g7], [h7], [a8], [b8], [c8], [d8], [e8], [f8], [g8], [h8]]", blank.toString());
    }

    @Test
    void testIsSameColor() {
        assertTrue(othelloController.isSameColor(4, 5, Color.BLACK));
        assertFalse(othelloController.isSameColor(4, 5, Color.WHITE));
        assertFalse(othelloController.isSameColor(4, 5, Color.BLANK));

        assertFalse(othelloController.isSameColor(5, 5, Color.BLACK));
        assertTrue(othelloController.isSameColor(5, 5, Color.WHITE));
        assertFalse(othelloController.isSameColor(5, 5, Color.BLANK));

        assertFalse(othelloController.isSameColor(1, 1, Color.BLACK));
        assertFalse(othelloController.isSameColor(1, 1, Color.WHITE));
        assertTrue(othelloController.isSameColor(1, 1, Color.BLANK));
    }

    @Test
    void testIsOppositeColor() {
        assertFalse(othelloController.isOppositeColor(4, 5, Color.BLACK));
        assertTrue(othelloController.isOppositeColor(4, 5, Color.WHITE));
        assertFalse(othelloController.isOppositeColor(4, 5, Color.BLANK));

        assertTrue(othelloController.isOppositeColor(5, 5, Color.BLACK));
        assertFalse(othelloController.isOppositeColor(5, 5, Color.WHITE));
        assertFalse(othelloController.isOppositeColor(5, 5, Color.BLANK));

        assertFalse(othelloController.isOppositeColor(1, 1, Color.BLACK));
        assertFalse(othelloController.isOppositeColor(1, 1, Color.WHITE));
        assertTrue(othelloController.isOppositeColor(1, 1, Color.BLANK));
    }

    private void testIsValidDirection(int x, int y, Color color, OthelloGrid.Direction direction, int differentColorCount, boolean valid, Color expectedEndColor) {
        assertEquals(differentColorCount, othelloController.getOppositeCount(x, y, color, direction));
        assertEquals(valid, othelloController.isValidDirection(x, y, color, direction));
        int endX = x + ((differentColorCount + 1) * direction.offsetX);
        int endY = y + ((differentColorCount + 1) * direction.offsetY);
        if (expectedEndColor == null) {
            assertFalse(grid.isValidRange(endX, endY));
        } else {
            assertEquals(expectedEndColor.getColor(), grid.getPixel(endX, endY));
        }
    }

    @Test
    void testIsValidDirectionHitWall() {
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.UP, 0, false, null);
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 0, false, null);
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.LEFT, 0, false, null);
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.DOWN_LEFT, 0, false, null);
        testIsValidDirection(8, 8, Color.BLACK, OthelloGrid.Direction.DOWN, 0, false, null);
        testIsValidDirection(8, 8, Color.BLACK, OthelloGrid.Direction.DOWN_RIGHT, 0, false, null);
        testIsValidDirection(8, 8, Color.BLACK, OthelloGrid.Direction.RIGHT, 0, false, null);
        testIsValidDirection(8, 8, Color.BLACK, OthelloGrid.Direction.UP_RIGHT, 0, false, null);
    }

    @Test
    void testIsValidDirectionHitSameColor() {
        testIsValidDirection(4, 6, Color.BLACK, OthelloGrid.Direction.UP, 0, false, Color.BLACK);
        testIsValidDirection(5, 6, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 0, false, Color.BLACK);
        testIsValidDirection(6, 4, Color.BLACK, OthelloGrid.Direction.LEFT, 0, false, Color.BLACK);
        testIsValidDirection(6, 3, Color.BLACK, OthelloGrid.Direction.DOWN_LEFT, 0, false, Color.BLACK);
        testIsValidDirection(5, 3, Color.BLACK, OthelloGrid.Direction.DOWN, 0, false, Color.BLACK);
        testIsValidDirection(4, 3, Color.BLACK, OthelloGrid.Direction.DOWN_RIGHT, 0, false, Color.BLACK);
        testIsValidDirection(3, 5, Color.BLACK, OthelloGrid.Direction.RIGHT, 0, false, Color.BLACK);
        testIsValidDirection(3, 6, Color.BLACK, OthelloGrid.Direction.UP_RIGHT, 0, false, Color.BLACK);
    }

    @Test
    void testIsValidDirectionHitBlank() {
        testIsValidDirection(2, 2, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 0, false, Color.BLANK);
        testIsValidDirection(2, 2, Color.BLACK, OthelloGrid.Direction.LEFT, 0, false, Color.BLANK);
        testIsValidDirection(2, 2, Color.BLACK, OthelloGrid.Direction.DOWN_LEFT, 0, false, Color.BLANK);
        testIsValidDirection(2, 2, Color.BLACK, OthelloGrid.Direction.DOWN, 0, false, Color.BLANK);
        testIsValidDirection(2, 2, Color.BLACK, OthelloGrid.Direction.DOWN_RIGHT, 0, false, Color.BLANK);
        testIsValidDirection(2, 2, Color.BLACK, OthelloGrid.Direction.RIGHT, 0, false, Color.BLANK);
        testIsValidDirection(2, 2, Color.BLACK, OthelloGrid.Direction.UP_RIGHT, 0, false, Color.BLANK);
    }

    @Test
    void testIsValidDirectionHitDifferentColorThenHitBlank() {
        grid.setPixel(2, 2, Color.WHITE.getColor());
        testIsValidDirection(2, 3, Color.BLACK, OthelloGrid.Direction.UP, 1, false, Color.BLANK);
        testIsValidDirection(3, 3, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 1, false, Color.BLANK);
        testIsValidDirection(3, 2, Color.BLACK, OthelloGrid.Direction.LEFT, 1, false, Color.BLANK);
        testIsValidDirection(3, 1, Color.BLACK, OthelloGrid.Direction.DOWN_LEFT, 1, false, Color.BLANK);
        testIsValidDirection(2, 1, Color.BLACK, OthelloGrid.Direction.DOWN, 1, false, Color.BLANK);
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.DOWN_RIGHT, 1, false, Color.BLANK);
        testIsValidDirection(1, 2, Color.BLACK, OthelloGrid.Direction.RIGHT, 1, false, Color.BLANK);
        testIsValidDirection(1, 3, Color.BLACK, OthelloGrid.Direction.UP_RIGHT, 1, false, Color.BLANK);
    }

    @Test
    void testIsValidDirectionHitDifferentColorThenHitSameColor() {
        testIsValidDirection(5, 6, Color.BLACK, OthelloGrid.Direction.UP, 1, true, Color.BLACK);
        testIsValidDirection(6, 5, Color.BLACK, OthelloGrid.Direction.LEFT, 1, true, Color.BLACK);
        testIsValidDirection(4, 3, Color.BLACK, OthelloGrid.Direction.DOWN, 1, true, Color.BLACK);
        testIsValidDirection(3, 4, Color.BLACK, OthelloGrid.Direction.RIGHT, 1, true, Color.BLACK);
        grid.setPixel(2, 2, Color.WHITE.getColor());
        grid.setPixel(1, 3, Color.BLACK.getColor());
        grid.setPixel(1, 1, Color.BLACK.getColor());
        grid.setPixel(3, 1, Color.BLACK.getColor());
        grid.setPixel(3, 3, Color.BLACK.getColor());
        testIsValidDirection(3, 3, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 1, true, Color.BLACK);
        testIsValidDirection(3, 1, Color.BLACK, OthelloGrid.Direction.DOWN_LEFT, 1, true, Color.BLACK);
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.DOWN_RIGHT, 1, true, Color.BLACK);
        testIsValidDirection(1, 3, Color.BLACK, OthelloGrid.Direction.UP_RIGHT, 1, true, Color.BLACK);
    }

    @Test
    void testIsValidDirectionHitDifferentColorThenHitWall() {
        grid.setPixel(1, 1, Color.WHITE.getColor());
        grid.setPixel(1, 8, Color.WHITE.getColor());
        grid.setPixel(8, 1, Color.WHITE.getColor());
        grid.setPixel(8, 8, Color.WHITE.getColor());
        testIsValidDirection(1, 2, Color.BLACK, OthelloGrid.Direction.UP, 1, false, null);
        testIsValidDirection(2, 2, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 1, false, null);
        testIsValidDirection(2, 1, Color.BLACK, OthelloGrid.Direction.LEFT, 1, false, null);
        testIsValidDirection(2, 7, Color.BLACK, OthelloGrid.Direction.DOWN_LEFT, 1, false, null);
        testIsValidDirection(8, 7, Color.BLACK, OthelloGrid.Direction.DOWN, 1, false, null);
        testIsValidDirection(7, 7, Color.BLACK, OthelloGrid.Direction.DOWN_RIGHT, 1, false, null);
        testIsValidDirection(7, 8, Color.BLACK, OthelloGrid.Direction.RIGHT, 1, false, null);
        testIsValidDirection(7, 2, Color.BLACK, OthelloGrid.Direction.UP_RIGHT, 1, false, null);
    }

    @Test
    void testIsValidDirectionHitMultipleDifferentColorThenHitBlank() {
        grid.setPixel(1, 2, Color.WHITE.getColor());
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.DOWN, 1, false, Color.BLANK);

        grid.setPixel(1, 3, Color.WHITE.getColor());
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.DOWN, 2, false, Color.BLANK);

        grid.setPixel(1, 4, Color.WHITE.getColor());
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.DOWN, 3, false, Color.BLANK);

        grid.setPixel(1, 5, Color.WHITE.getColor());
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.DOWN, 4, false, Color.BLANK);

        grid.setPixel(1, 6, Color.WHITE.getColor());
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.DOWN, 5, false, Color.BLANK);

        grid.setPixel(1, 7, Color.WHITE.getColor());
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.DOWN, 6, false, Color.BLANK);
    }

    @Test
    void testIsValidDirectionHitMultipleDifferentColorThenHitSameColor() {
        grid.setPixel(8, 1, Color.BLACK.getColor());

        grid.setPixel(7, 1, Color.WHITE.getColor());
        testIsValidDirection(6, 1, Color.BLACK, OthelloGrid.Direction.RIGHT, 1, true, Color.BLACK);

        grid.setPixel(6, 1, Color.WHITE.getColor());
        testIsValidDirection(5, 1, Color.BLACK, OthelloGrid.Direction.RIGHT, 2, true, Color.BLACK);

        grid.setPixel(5, 1, Color.WHITE.getColor());
        testIsValidDirection(4, 1, Color.BLACK, OthelloGrid.Direction.RIGHT, 3, true, Color.BLACK);

        grid.setPixel(4, 1, Color.WHITE.getColor());
        testIsValidDirection(3, 1, Color.BLACK, OthelloGrid.Direction.RIGHT, 4, true, Color.BLACK);

        grid.setPixel(3, 1, Color.WHITE.getColor());
        testIsValidDirection(2, 1, Color.BLACK, OthelloGrid.Direction.RIGHT, 5, true, Color.BLACK);

        grid.setPixel(2, 1, Color.WHITE.getColor());
        testIsValidDirection(1, 1, Color.BLACK, OthelloGrid.Direction.RIGHT, 6, true, Color.BLACK);
    }

    @Test
    void testIsValidDirectionHitMultipleDifferentColorThenHitWall() {
        grid.setPixel(1, 1, Color.WHITE.getColor());
        testIsValidDirection(2, 2, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 1, false, null);

        grid.setPixel(2, 2, Color.WHITE.getColor());
        testIsValidDirection(3, 3, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 2, false, null);

        grid.setPixel(3, 3, Color.WHITE.getColor());
        testIsValidDirection(4, 4, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 3, false, null);

        grid.setPixel(4, 4, Color.WHITE.getColor());
        testIsValidDirection(5, 5, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 4, false, null);

        grid.setPixel(5, 5, Color.WHITE.getColor());
        testIsValidDirection(6, 6, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 5, false, null);

        grid.setPixel(6, 6, Color.WHITE.getColor());
        testIsValidDirection(7, 7, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 6, false, null);

        grid.setPixel(7, 7, Color.WHITE.getColor());
        testIsValidDirection(8, 8, Color.BLACK, OthelloGrid.Direction.UP_LEFT, 7, false, null);
    }

    @Test
    void testGetValidMove() {
        Collection<OthelloLocation> black = othelloController.getValidMove(Color.BLACK);
        Collection<OthelloLocation> white = othelloController.getValidMove(Color.WHITE);
        Collection<OthelloLocation> blank = othelloController.getValidMove(Color.BLANK);
        assertEquals(4, black.size());
        assertEquals(4, white.size());
        assertEquals(0, blank.size());
        assertEquals("[[d3], [c4], [f5], [e6]]", black.toString());
        assertEquals("[[e3], [f4], [c5], [d6]]", white.toString());
        assertEquals("[]", blank.toString());
    }

    @Test
    void testIsValidMove() {
        assertTrue(othelloController.isValidMove(4, 3, Color.BLACK));
        assertTrue(othelloController.isValidMove(3, 4, Color.BLACK));
        assertTrue(othelloController.isValidMove(6, 5, Color.BLACK));
        assertTrue(othelloController.isValidMove(5, 6, Color.BLACK));

        assertFalse(othelloController.isValidMove(4, 3, Color.WHITE));
        assertFalse(othelloController.isValidMove(3, 4, Color.WHITE));
        assertFalse(othelloController.isValidMove(6, 5, Color.WHITE));
        assertFalse(othelloController.isValidMove(5, 6, Color.WHITE));

        assertFalse(othelloController.isValidMove(4, 3, Color.BLANK));
        assertFalse(othelloController.isValidMove(3, 4, Color.BLANK));
        assertFalse(othelloController.isValidMove(6, 5, Color.BLANK));
        assertFalse(othelloController.isValidMove(5, 6, Color.BLANK));
    }

    @Test
    void testExecuteMove() throws IOException {
        printer.printGrid();
        assertEquals("[[e4], [d5]]", othelloController.getColorLocation(Color.BLACK).toString());
        assertEquals("[[d4], [e5]]", othelloController.getColorLocation(Color.WHITE).toString());
        assertEquals("[[a1], [b1], [c1], [d1], [e1], [f1], [g1], [h1], [a2], [b2], [c2], [d2], [e2], [f2], [g2], [h2], [a3], [b3], [c3], [d3], [e3], [f3], [g3], [h3], [a4], [b4], [c4], [f4], [g4], [h4], [a5], [b5], [c5], [f5], [g5], [h5], [a6], [b6], [c6], [d6], [e6], [f6], [g6], [h6], [a7], [b7], [c7], [d7], [e7], [f7], [g7], [h7], [a8], [b8], [c8], [d8], [e8], [f8], [g8], [h8]]", othelloController.getColorLocation(Color.BLANK).toString());

        OthelloLocation move = othelloController.getValidMove(Color.BLACK).iterator().next();
        assertEquals("[d3]", move.toString());
        assertEquals(null, othelloController.getWinner());
        othelloController.executeMove(move.getKey(), move.getValue(), Color.BLACK);
        printer.printGrid();
        assertEquals("[[d3], [d4], [e4], [d5]]", othelloController.getColorLocation(Color.BLACK).toString());
        assertEquals("[[e5]]", othelloController.getColorLocation(Color.WHITE).toString());
        assertEquals("[[a1], [b1], [c1], [d1], [e1], [f1], [g1], [h1], [a2], [b2], [c2], [d2], [e2], [f2], [g2], [h2], [a3], [b3], [c3], [e3], [f3], [g3], [h3], [a4], [b4], [c4], [f4], [g4], [h4], [a5], [b5], [c5], [f5], [g5], [h5], [a6], [b6], [c6], [d6], [e6], [f6], [g6], [h6], [a7], [b7], [c7], [d7], [e7], [f7], [g7], [h7], [a8], [b8], [c8], [d8], [e8], [f8], [g8], [h8]]", othelloController.getColorLocation(Color.BLANK).toString());

        move = othelloController.getValidMove(Color.WHITE).iterator().next();
        assertEquals("[c3]", move.toString());
        assertEquals(null, othelloController.getWinner());
        othelloController.executeMove(move.getKey(), move.getValue(), Color.WHITE);
        printer.printGrid();
        assertEquals("[[d3], [e4], [d5]]", othelloController.getColorLocation(Color.BLACK).toString());
        assertEquals("[[c3], [d4], [e5]]", othelloController.getColorLocation(Color.WHITE).toString());
        assertEquals("[[a1], [b1], [c1], [d1], [e1], [f1], [g1], [h1], [a2], [b2], [c2], [d2], [e2], [f2], [g2], [h2], [a3], [b3], [e3], [f3], [g3], [h3], [a4], [b4], [c4], [f4], [g4], [h4], [a5], [b5], [c5], [f5], [g5], [h5], [a6], [b6], [c6], [d6], [e6], [f6], [g6], [h6], [a7], [b7], [c7], [d7], [e7], [f7], [g7], [h7], [a8], [b8], [c8], [d8], [e8], [f8], [g8], [h8]]", othelloController.getColorLocation(Color.BLANK).toString());
        assertEquals(null, othelloController.getWinner());

        Color currentColor = Color.BLACK;
        while (othelloController.getWinner() == null) {
            Collection<OthelloLocation> moves = othelloController.getValidMove(currentColor);
            if (!moves.isEmpty()) {
                move = moves.iterator().next();
                othelloController.executeMove(move.getKey(), move.getValue(), currentColor);
                printer.printGrid();
            }
            currentColor = getOppositeColor(currentColor);
        }
        assertEquals(Color.WHITE, othelloController.getWinner());
        assertEquals(Resources.toString(Resources.getResource("testExecuteMove.txt"), Charsets.UTF_8), outputStream.toString());
    }

    @Test
    void testGetUserInput() throws IOException {
        pos.write("1a".getBytes());
        pos.write(System.lineSeparator().getBytes());
        OthelloLocation move = othelloController.getUserInput(Color.BLACK);
        assertEquals("[a1]", move.toString());

        pos.write("a1".getBytes());
        pos.write(System.lineSeparator().getBytes());
        move = othelloController.getUserInput(Color.BLACK);
        assertEquals("[a1]", move.toString());

        pos.write("8h".getBytes());
        pos.write(System.lineSeparator().getBytes());
        move = othelloController.getUserInput(Color.BLACK);
        assertEquals("[h8]", move.toString());

        pos.write("0a".getBytes());
        pos.write(System.lineSeparator().getBytes());
        move = othelloController.getUserInput(Color.BLACK);
        assertEquals("[a?]", move.toString());

        pos.write("0x".getBytes());
        pos.write(System.lineSeparator().getBytes());
        move = othelloController.getUserInput(Color.BLACK);
        assertEquals("[??]", move.toString());

        pos.write("1x".getBytes());
        pos.write(System.lineSeparator().getBytes());
        move = othelloController.getUserInput(Color.BLACK);
        assertEquals("[?1]", move.toString());
    }
}