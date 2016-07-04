package application;

import expression.finder.ExpressionFinder;
import expression.finder.SearchResult;
import parser.Parser;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class App {
    private final InputStream inputStream;
    private final PrintStream outputStream;
    private Parser parser = new Parser();

    public App(InputStream input, PrintStream output) {
        inputStream = input;
        outputStream = output;
    }

    public void run() {
        try (Scanner scanner = new Scanner(inputStream)) {
            ExpressionFinder finder = new ExpressionFinder();

            while (scanner.hasNextLine())
                findExpression(finder, scanner.nextLine());
        }
    }

    private void findExpression(ExpressionFinder finder, String input) {
        try {
            SearchResult<String> searchResult = finder.find(parser.parse(input));
            if (searchResult.exists()) {
                outputStream.println("YES");
                outputStream.println("Expression: " + searchResult.value());
            } else {
                outputStream.println("NO");
            }
        } catch (Parser.InvalidInputFormatException e) {
            outputStream.println("Invalid Input");
        }
    }
}
