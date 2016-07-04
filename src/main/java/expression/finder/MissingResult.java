package expression.finder;

public class MissingResult<T> implements SearchResult<T> {
    public boolean exists() {
        return false;
    }

    public T value() {
        throw new ResultNotFoundException();
    }
}
