package ks.othello;

import ks.common.ArrayGrid;

public class OthelloGrid extends ArrayGrid {
    static final int GRID_SIZE = 8;

    public OthelloGrid() {
        super(GRID_SIZE, GRID_SIZE);
        setAll(Color.BLANK.getColor());
    }

    /**
     * Use this to flip between black and white
     *
     * @param color want to get opposite color
     * @return oppositeColor
     */
    public static Color getOppositeColor(Color color) {
        if (color == Color.BLACK) {
            return Color.WHITE;
        }
        if (color == Color.WHITE) {
            return Color.BLACK;
        }
        return color;
    }

    public enum Color {
        BLANK('-', '-'),
        BLACK('X', 'O'),
        WHITE('O', 'X');
        private final char oppositeColor;
        private final char color;

        Color(char color, char oppositeColor) {
            this.color = color;
            this.oppositeColor = oppositeColor;
        }

        public char getColor() {
            return color;
        }

        public char getOppositeColor() {
            return oppositeColor;
        }
    }

    public enum Direction {
        UP(0, -1),
        UP_RIGHT(1, -1),
        RIGHT(1, 0),
        DOWN_RIGHT(1, 1),
        DOWN(0, 1),
        DOWN_LEFT(-1, 1),
        LEFT(-1, 0),
        UP_LEFT(-1, -1);

        int offsetX;
        int offsetY;

        Direction(int offsetX, int offsetY) {
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }

        public int getOffsetX() {
            return offsetX;
        }

        public int getOffsetY() {
            return offsetY;
        }
    }
}
