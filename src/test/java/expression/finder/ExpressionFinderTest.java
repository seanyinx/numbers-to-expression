package expression.finder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionFinderTest {

    private ExpressionFinder finder;

    @Before
    public void setUp() throws Exception {
        finder = new ExpressionFinder();
    }

    private void findExpression(boolean expected, Integer... integers) {
        SearchResult<String> searchResult = finder.find(integers);
        assertEquals(expected, searchResult.exists());

        if (searchResult.exists()) {
            System.out.println("Expression found: " + searchResult.value());
        } else {
            System.out.println("Expression not found");
        }

        System.out.println("Number of recursive calls: " + finder.getNumberOfRecursions());
    }

    @Test
    public void givenNumbers_ShouldFindExpression() {
        final int size = 200;
        Integer[] integers = new Integer[size];
        for (int i = 0; i < size; i++)
            integers[i] = i + 1;
        findExpression(true, integers);

        findExpression(false, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        findExpression(true, 7, 9, 4, 2);
        findExpression(true, 1, 1, 1, 1);
        findExpression(false, 4, 4, 4, 3);
        findExpression(true, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        findExpression(false, 0);
        findExpression(false, 1, -1);
        findExpression(true, 1, 1);
        findExpression(false, -1, -1, 2);
        findExpression(true, -7, 9, -4, -2);
        findExpression(true, 7, 4, 2, 1);
        findExpression(true, 1, 3, 1, 1);
        findExpression(true, 1, 2, 4, 1);
    }
}
