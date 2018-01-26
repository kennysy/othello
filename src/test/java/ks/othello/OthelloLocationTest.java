package ks.othello;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OthelloLocationTest {
    @Test
    void testToString() {
        assertEquals("[??]", new OthelloLocation(0, 0).toString());
        assertEquals("[a?]", new OthelloLocation(1, 0).toString());
        assertEquals("[?1]", new OthelloLocation(0, 1).toString());
        assertEquals("[a1]", new OthelloLocation(1, 1).toString());
        assertEquals("[h8]", new OthelloLocation(8, 8).toString());
        assertEquals("[?8]", new OthelloLocation(9, 8).toString());
        assertEquals("[h?]", new OthelloLocation(8, 9).toString());
        assertEquals("[??]", new OthelloLocation(9, 9).toString());
    }

    @Test
    void getX() {
        assertEquals(1, new OthelloLocation(1, 2).getX().intValue());
    }

    @Test
    void getY() {
        assertEquals(2, new OthelloLocation(1, 2).getY().intValue());
    }
}