package ks.othello.console;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import ks.othello.AbstractOthelloTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleOthelloTest extends AbstractOthelloTest {
    @Test
    void testMain() throws IOException {
        String[] args = null;
        final InputStream originalIn = System.in;
        final PrintStream originalOut = System.out;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(("4c" + System.lineSeparator()).getBytes());
        System.setIn(inputStream);
        System.setOut(outputPrintStream);
        ConsoleOthello.main(args);
        System.setIn(originalIn);
        System.setOut(originalOut);
        assertEquals(Resources.toString(Resources.getResource("ConsoleOthelloTest.txt"), Charsets.UTF_8), outputStream.toString());
    }
}