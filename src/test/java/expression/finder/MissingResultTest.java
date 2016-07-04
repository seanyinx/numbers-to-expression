package expression.finder;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class MissingResultTest {

    private SearchResult<String> result = new MissingResult<>();

    @Test
    public void resultNotExist() {
        assertThat(result.exists(), is(false));
    }

    @Test
    public void blowsUpOnCallingValue() {
        try {
            result.value();
            fail(ResultNotFoundException.class + " expected");
        } catch (ResultNotFoundException e) {
            assertThat(e.getMessage(), is("Result not found"));
        }
    }
}
