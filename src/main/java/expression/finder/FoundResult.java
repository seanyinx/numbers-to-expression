package expression.finder;

public class FoundResult<T> implements SearchResult<T> {
    private final T value;

    public FoundResult(T value) {
        this.value = value;
    }

    public boolean exists() {
        return true;
    }

    public T value() {
        return value;
    }
}
