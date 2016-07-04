package expression.finder;

import java.util.*;

public class ExpressionFinder {
    private int numberOfRecursions;
    private final Map<String, Map<Integer, String>> subsetValueToExpressionMap;

    public ExpressionFinder() {
        subsetValueToExpressionMap = new HashMap<>();
    }

    public SearchResult<String> find(Integer... elements) {
        numberOfRecursions = 0;

        if (elements.length <= 1)
            return new MissingResult<>();

        subsetValueToExpressionMap.clear();

        List<Integer> list = Arrays.asList(elements);
        Integer target = list.get(0);
        return find(list.subList(1, list.size()), target, target.toString());
    }

    // combine elements in terms of arithmetic expressions, so that the result of a expression is equal to target
    private SearchResult<String> find(List<Integer> elements, Integer target, String expression) {
        numberOfRecursions++;

        if (elements.size() == 1)
            return matchHeadWithTarget(elements, target, expression);

        String setKey = elements.toString();
        if (isSetSearchedForExpression(setKey))
            return matchTargetWithCachedCombinationsOfElements(target, expression, setKey);

        return searchSetForExpression(elements, target, expression, setKey);
    }

    private SearchResult<String> matchHeadWithTarget(List<Integer> elements, Integer target, String expression) {
        Integer head = elements.get(0);
        if (Objects.equals(target, head)) {
            return new FoundResult<>(expression + " = " + head.toString());
        }

        addSingleIntegerExpression(head, elements.toString());
        return new MissingResult<>();
    }

    private SearchResult<String> searchSetForExpression(List<Integer> elements, Integer target, String expression, String setKey) {
        subsetValueToExpressionMap.put(setKey, new HashMap<Integer, String>());

        Integer head = elements.get(0);
        List<Integer> tail = elements.subList(1, elements.size());
        SearchResult<String> searchResult = searchTailToMatchTarget(tail, head, target, expression);
        if (!searchResult.exists())
            formSupersetWithHeadAndTail(head, tail.toString(), setKey);

        return searchResult;
    }

    private boolean isSetSearchedForExpression(String key) {
        return subsetValueToExpressionMap.containsKey(key);
    }

    private SearchResult<String> searchTailToMatchTarget(List<Integer> tail,
                                                         Integer head, Integer target, String expression) {
        String headString = Integer.toString(head);
        SearchResult<String> searchResult = find(tail, target + head, expression + " + " + headString);

        if (!searchResult.exists()) {
            searchResult = find(tail, target - head, expression + " - " + headString);
            if (!searchResult.exists()) {
                searchResult = find(tail, head - target, headString + " - " + swapOperators(expression));
            }
        }

        return searchResult;
    }

    private String swapOperators(String expression) {
        return expression.replace('+', '$').replace('-', '+').replace('$', '-');
    }

    private void formSupersetWithHeadAndTail(Integer head,
                                             String tailKey,
                                             String supersetKey) {
        Map<Integer, String> subsetMap = subsetValueToExpressionMap.get(tailKey);
        Map<Integer, String> supersetMap = subsetValueToExpressionMap.get(supersetKey);

        for (Integer result : subsetMap.keySet()) {
            supersetMap.put(result + head, head + " + " + subsetMap.get(result));
            supersetMap.put(result - head, subsetMap.get(result) + " - " + head);
        }
    }

    private SearchResult<String> matchTargetWithCachedCombinationsOfElements(Integer target, String expression, String key) {
        Map<Integer, String> resultExpressionMap = subsetValueToExpressionMap.get(key);
        if (resultExpressionMap.containsKey(target)) {
            return new FoundResult<>(expression + " = " + resultExpressionMap.get(target));
        }
        return new MissingResult<>();
    }

    private void addSingleIntegerExpression(Integer currentInteger, String key) {
        if (!subsetValueToExpressionMap.containsKey(key)) {
            subsetValueToExpressionMap.put(key, new HashMap<Integer, String>());
        }
        subsetValueToExpressionMap.get(key).put(currentInteger, currentInteger.toString());
    }

    int getNumberOfRecursions() {
        return numberOfRecursions;
    }
}
