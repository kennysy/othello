package ks.common;

import java.util.stream.IntStream;

public class ArrayGrid implements Grid {
    private final char[][] data;
    private final int width;

    public ArrayGrid(int width, int height) {
        if (width > 0 && height > 0) {
            this.width = width;
            data = new char[height][width];
        } else {
            throw new IllegalArgumentException(
                    String.format("Invalid size error: width[%d], height[%d]", width, height));
        }
    }

    @Override
    public void setAll(char blank_color) {
        IntStream.range(1, getHeight() + 1)
                .forEach(y -> IntStream.range(1, getWidth() + 1).forEach(x -> setPixel(x, y, blank_color)));
    }

    @Override
    public int getHeight() {
        return data.length;
    }

    @Override
    public char getPixel(int x, int y) {
        checkRange(x, y);
        return data[y - 1][x - 1];
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setPixel(int x, int y, char color) {
        checkRange(x, y);
        data[y - 1][x - 1] = color;
    }
}