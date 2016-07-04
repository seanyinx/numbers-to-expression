package expression.finder;

public interface SearchResult<T> {
    boolean exists();

    T value();
}
