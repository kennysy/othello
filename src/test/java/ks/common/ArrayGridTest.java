package ks.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayGridTest {

    private ArrayGrid grid;

    @BeforeEach
    void setUp() {
        grid = new ArrayGrid(20, 4);
        grid.setAll(' ');
    }

    @Test
    void testGetHeight() {
        assertEquals(4, grid.getHeight());
    }

    @Test
    void testGetPixel() {
        assertEquals(' ', grid.getPixel(1, 1));
    }

    @Test
    void testGetWidth() {
        assertEquals(20, grid.getWidth());
    }

    @Test
    void testSetPixel() {
        assertEquals(' ', grid.getPixel(1, 1));
        grid.setPixel(1, 1, 'X');
        assertEquals('X', grid.getPixel(1, 1));
    }

    @Test
    void testSetAll() {
        assertEquals(' ', grid.getPixel(1, 1));
        grid.setPixel(1, 1, 'X');
        assertEquals('X', grid.getPixel(1, 1));
        grid.setAll('O');
        assertEquals('O', grid.getPixel(1, 1));
        assertEquals('O', grid.getPixel(20, 4));
    }

    @Test
    void testIsValidRange() {
        assertTrue(grid.isValidRange(1, 1));
        assertFalse(grid.isValidRange(0, 1));
        assertFalse(grid.isValidRange(1, 0));

        assertTrue(grid.isValidRange(20, 1));
        assertFalse(grid.isValidRange(20, 0));
        assertFalse(grid.isValidRange(21, 1));

        assertTrue(grid.isValidRange(1, 4));
        assertFalse(grid.isValidRange(0, 4));
        assertFalse(grid.isValidRange(1, 5));

        assertTrue(grid.isValidRange(20, 4));
        assertFalse(grid.isValidRange(21, 4));
        assertFalse(grid.isValidRange(20, 5));
    }

    @Test
    void testCheckRange() {
        assertEquals("Invalid coordinate error: x[0], y[0]", assertThrows(IllegalArgumentException.class, () -> grid.checkRange(0, 0)).getMessage());
        assertEquals("Invalid coordinate error: x[0], y[1]", assertThrows(IllegalArgumentException.class, () -> grid.checkRange(0, 1)).getMessage());
        assertEquals("Invalid coordinate error: x[1], y[0]", assertThrows(IllegalArgumentException.class, () -> grid.checkRange(1, 0)).getMessage());
        assertEquals("Invalid coordinate error: x[21], y[1]", assertThrows(IllegalArgumentException.class, () -> grid.checkRange(21, 1)).getMessage());
        assertEquals("Invalid coordinate error: x[1], y[5]", assertThrows(IllegalArgumentException.class, () -> grid.checkRange(1, 5)).getMessage());
        assertEquals("Invalid coordinate error: x[21], y[5]", assertThrows(IllegalArgumentException.class, () -> grid.checkRange(21, 5)).getMessage());
    }

    @Test
    void testInvalidSize() {
        assertEquals("Invalid size error: width[0], height[0]", assertThrows(IllegalArgumentException.class, () -> new ArrayGrid(0, 0)).getMessage());
        assertEquals("Invalid size error: width[0], height[1]", assertThrows(IllegalArgumentException.class, () -> new ArrayGrid(0, 1)).getMessage());
        assertEquals("Invalid size error: width[1], height[0]", assertThrows(IllegalArgumentException.class, () -> new ArrayGrid(1, 0)).getMessage());
    }
}