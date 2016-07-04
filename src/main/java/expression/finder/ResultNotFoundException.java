package expression.finder;

public class ResultNotFoundException extends RuntimeException {
    public ResultNotFoundException() {
        super("Result not found");
    }
}
