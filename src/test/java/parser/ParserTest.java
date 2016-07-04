package parser;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ParserTest {

    private Parser parser;

    @Before
    public void setUp() throws Exception {
        parser = new Parser();
    }

    @Test
    public void givenValidString_ShouldReturnArrayOfIntegers() {
        assertArrayEquals(new Integer[]{71, 9, 4, 2}, parser.parse("71,9,4,2"));
        assertArrayEquals(new Integer[]{7, 9, 4, 2}, parser.parse(" 7 , 9 , 4 , 2 "));
        assertArrayEquals(new Integer[]{-7, 9, -4, 2}, parser.parse(" -7 , 9 , -4 , 2 "));
    }

    @Test(expected = Parser.InvalidInputFormatException.class)
    public void givenSingleInteger_ShouldThrow() {
        parser.parse("7");
    }

    @Test(expected = Parser.InvalidInputFormatException.class)
    public void givenInputWithMissingField_ShouldThrow() {
        parser.parse(",5,1");
    }

    @Test(expected = Parser.InvalidInputFormatException.class)
    public void givenInputWithInvalidCharacters_ShouldThrow() {
        parser.parse("7,a,5");
    }

    @Test(expected = Parser.InvalidInputFormatException.class)
    public void givenEmptyString_ShouldThrow() {
        parser.parse("");
    }
}
