package ks.othello;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomTest extends AbstractOthelloTest {
    private void testRandomGame(OthelloGrid.Color winner, boolean fullFill) {
        othelloController.setRandomBlack(true);
        othelloController.setRandomWhite(true);
        OthelloGrid.Color result;
        do {
            result = othelloController.play();
        } while (result != winner || othelloController.getColorLocation(OthelloGrid.Color.BLANK).isEmpty() != fullFill);
        outputStream.reset();
        printer.printGrid();
        assertEquals(othelloController.getWinner(), result);
        int blackCount = othelloController.getColorLocation(OthelloGrid.Color.BLACK).size();
        int whiteCount = othelloController.getColorLocation(OthelloGrid.Color.WHITE).size();
        assertEquals(fullFill, othelloController.getColorLocation(OthelloGrid.Color.BLANK).isEmpty());
        if (winner == OthelloGrid.Color.BLANK) {
            assertEquals(blackCount, whiteCount);
        }
        if (winner == OthelloGrid.Color.BLACK) {
            assertThat("Black win",
                    blackCount,
                    greaterThan(whiteCount));
        }
        if (winner == OthelloGrid.Color.WHITE) {
            assertThat("White win",
                    whiteCount,
                    greaterThan(blackCount));
        }
    }

    @Test
    void testPlayDrawGameFullFill() {
        testRandomGame(OthelloGrid.Color.BLANK, true);
    }

    @Test
    void testPlayWhiteWinGameFullFill() {
        testRandomGame(OthelloGrid.Color.WHITE, true);
    }

    @Test
    void testPlayBlackWinGameFullFill() {
        testRandomGame(OthelloGrid.Color.BLACK, true);
    }

    @Test
    void testPlayWhiteWinGameNotFullFill() {
        testRandomGame(OthelloGrid.Color.WHITE, false);
    }

    @Test
    void testPlayBlackWinGameNotFullFill() {
        testRandomGame(OthelloGrid.Color.BLACK, false);
    }
}