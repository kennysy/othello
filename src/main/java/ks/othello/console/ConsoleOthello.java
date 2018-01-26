package ks.othello.console;

import ks.othello.OthelloController;
import ks.othello.OthelloGrid;

/**
 * A simple console version of a Othello program.
 *
 * @author kennysy
 */
public class ConsoleOthello {
    public static void main(String[] args) {
        OthelloGrid grid = new OthelloGrid();
        ConsoleOthelloPrinter printer = new ConsoleOthelloPrinter(grid, System.in, System.out);
        new OthelloController(grid, printer).play();
    }
}
