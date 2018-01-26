package ks.common;

public interface Grid {
    /**
     * Throw exception when it is out of range.
     *
     * @param x axis coordinates start with 1
     * @param y axis coordinates start with 1
     * @throws IllegalArgumentException when x and y out of range
     */
    default void checkRange(int x, int y) {
        if (!isValidRange(x, y)) {
            throw new IllegalArgumentException(String.format("Invalid coordinate error: x[%d], y[%d]", x, y));
        }
    }

    void setAll(char blank_color);

    /**
     * Get the height
     *
     * @return height of the canvas
     */
    int getHeight();

    /**
     * Get color of the pixel
     *
     * @param x axis coordinates start with 1
     * @param y axis coordinates start with 1
     * @return color
     */
    char getPixel(int x, int y);

    /**
     * Get the width
     *
     * @return width of the canvas
     */
    int getWidth();

    /**
     * Return true if x and y are within the current canvas limit.
     *
     * @param x axis coordinates start with 1
     * @param y axis coordinates start with 1
     * @return true when valid
     */
    default boolean isValidRange(int x, int y) {
        return (x > 0 && y > 0 && x <= getWidth() && y <= getHeight());
    }

    /**
     * Set the pixel to given color
     *
     * @param x           axis coordinates start with 1
     * @param y           axis coordinates start with 1
     * @param targetColor color to set
     */
    void setPixel(int x, int y, char targetColor);
}
