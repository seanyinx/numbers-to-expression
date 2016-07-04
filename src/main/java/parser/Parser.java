package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public Integer[] parse(String input) {
        Pattern pattern = Pattern.compile("\\s*-*\\d+\\s*(,\\s*-*\\d+\\s*)+");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches())
            throw new InvalidInputFormatException();

        return toIntegers(input);
    }

    private Integer[] toIntegers(String input) {
        String[] intStrings = input.trim().split(",");
        Integer[] integers = new Integer[intStrings.length];

        for (int i = 0; i < intStrings.length; i++)
            integers[i] = Integer.valueOf(intStrings[i].trim());

        return integers;
    }

    public static class InvalidInputFormatException extends RuntimeException {
    }
}
