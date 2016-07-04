package application;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class AppTest {
    private ByteArrayOutputStream output;

    @Before
    public void setUp() {
        output = new ByteArrayOutputStream();
    }

    @Test
    public void givenInputWithValidExpression_ShouldFindExpression() {
        String s = "7,9,4,2";
        App app = new App(new ByteArrayInputStream(s.getBytes()), new PrintStream(output));
        app.run();
        assertEquals(true, output.toString().trim().contains("YES"));
    }

    @Test
    public void givenInputWithoutValidExpression_ShouldNotFindExpression() {
        String s = "1,1,1";
        App app = new App(new ByteArrayInputStream(s.getBytes()), new PrintStream(output));
        app.run();
        assertEquals("NO", output.toString().trim());
    }

    @Test
    public void givenInvalidInput_ShouldWarn() {
        String s = "1,";
        App app = new App(new ByteArrayInputStream(s.getBytes()), new PrintStream(output));
        app.run();
        assertEquals("Invalid Input", output.toString().trim());
    }
}
