package ks.othello.console;

import ks.othello.AbstractOthelloTest;
import ks.othello.OthelloLocation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static ks.othello.OthelloGrid.Color;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleOthelloPrinterTest extends AbstractOthelloTest {
    @Test
    void testGetGrid() {
        assertEquals(grid, printer.getGrid());
    }

    @Test
    void testPrintGrid() throws IOException {
        printer.printGrid();
        compareOutputWithFile("testPrintGrid.txt");
    }

    @Test
    void testPrintUserQuery() throws IOException {
        pis.close();
        printer.setDisplayTips(false);
        assertEquals(false, printer.isDisplayTips());
        printer.printUserQuery(Color.WHITE, othelloController.getValidMove(Color.BLACK), null);
        compareOutputWithFile("testPrintUserQuery.txt");
    }

    @Test
    void testPrintUserQueryWithTips() throws IOException {
        pis.close();
        printer.setDisplayTips(true);
        assertEquals(true, printer.isDisplayTips());
        printer.printUserQuery(Color.WHITE, othelloController.getValidMove(Color.BLACK), null);
        compareOutputWithFile("testPrintUserQueryWithTips.txt");
    }

    @Test
    void testPrintTips() throws IOException {
        printer.printTips(othelloController.getValidMove(Color.BLACK));
        compareOutputWithFile("testPrintTips.txt");
    }

    @Test
    void testWarnNoValidInput() throws IOException {
        printer.warnNoValidInput(Color.BLACK);
        compareOutputWithFile("testWarnNoValidInput.txt");
    }

    @Test
    void testPrintWinner() throws IOException {
        printer.printWinner(Color.BLACK, 4, 5);
        compareOutputWithFile("testPrintWinner.txt");
    }

    @Test
    void testWarnInvalidInput() throws IOException {
        printer.warnInvalidInput(Color.BLACK, new OthelloLocation(0, 0));
        compareOutputWithFile("testWarnInvalidInput.txt");
    }
}