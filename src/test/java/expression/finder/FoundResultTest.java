package expression.finder;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FoundResultTest {

    private String value = "blah";
    private FoundResult<String> result = new FoundResult<>(value);

    @Test
    public void resultExistsWithExpectedValue() {
        assertThat(result.exists(), is(true));
        assertThat(result.value(), is(value));
    }
}
