package ks.othello;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import ks.othello.console.ConsoleOthelloPrinter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractOthelloTest {
    protected ByteArrayOutputStream outputStream;
    protected PrintStream outputPrintStream;
    protected PipedOutputStream pos;
    protected PipedInputStream pis;
    protected OthelloGrid grid;
    protected ConsoleOthelloPrinter printer;
    protected OthelloController othelloController;

    @BeforeEach
    void setUp() throws IOException {
        outputStream = new ByteArrayOutputStream();
        outputPrintStream = new PrintStream(outputStream);
        pos = new PipedOutputStream();
        pis = new PipedInputStream(pos);
        grid = new OthelloGrid();
        printer = new ConsoleOthelloPrinter(grid, pis, outputPrintStream);
        othelloController = new OthelloController(grid, printer);
    }

    @AfterEach
    void tearDown() throws IOException {
        pis.close();
    }

    public void compareOutputWithFile(String fileName) throws IOException {
        String rawText = Resources.toString(Resources.getResource(fileName), Charsets.UTF_8);
        String text = rawText.replaceAll("\r\n", System.lineSeparator());
        assertEquals(text, outputStream.toString());
    }
}
