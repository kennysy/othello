package ks.othello;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OthelloGridTest {

    @Test
    void testGetOppositeColor() {
        assertEquals(OthelloGrid.Color.WHITE, OthelloGrid.getOppositeColor(OthelloGrid.Color.BLACK));
        assertEquals(OthelloGrid.Color.BLACK, OthelloGrid.getOppositeColor(OthelloGrid.Color.WHITE));
        assertEquals(OthelloGrid.Color.BLANK, OthelloGrid.getOppositeColor(OthelloGrid.Color.BLANK));
    }
}