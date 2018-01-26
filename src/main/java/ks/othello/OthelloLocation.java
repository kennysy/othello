package ks.othello;

import javafx.util.Pair;

public class OthelloLocation extends Pair<Integer, Integer> {
    public static final String X_INPUT_MAP = " abcdefgh";
    public static final String Y_INPUT_MAP = " 12345678";
    private static final String PRINTOUT = "[%s%s]";

    /**
     * Creates a new pair
     *
     * @param key   The key for this pair
     * @param value The value to use for this pair
     */
    public OthelloLocation(Integer key, Integer value) {
        super(key, value);
    }

    @Override
    public String toString() {
        char x = getKey() > 0 && getKey() < 9 ? X_INPUT_MAP.charAt(this.getKey()) : '?';
        char y = getValue() > 0 && getValue() < 9 ? Y_INPUT_MAP.charAt(this.getValue()) : '?';
        return String.format(PRINTOUT, x, y);
    }

    public Integer getX() {
        return getKey();
    }

    public Integer getY() {
        return getValue();
    }
}
