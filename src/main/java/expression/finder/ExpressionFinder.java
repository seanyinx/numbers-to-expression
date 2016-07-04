package expression.finder;

import java.util.*;

public class ExpressionFinder {
    private int numberOfRecursions;
    private final Map<String, Map<Integer, String>> checkedSubsetToExpressionMap;

    public ExpressionFinder() {
        checkedSubsetToExpressionMap = new HashMap<>();
    }

    public SearchResult<String> find(Integer... elements) {
        numberOfRecursions = 0;

        if (elements.length <= 1)
            return new MissingResult<>();

        checkedSubsetToExpressionMap.clear();

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

    private SearchResult<String> matchHeadWithTarget(List<Integer> integers, Integer target, String expression) {
        Integer head = integers.get(0);
        if (Objects.equals(target, head)) {
            return new FoundResult<>(expression + " = " + head.toString());
        }

        addSingleIntegerExpression(head, integers.toString());
        return new MissingResult<>();
    }

    private SearchResult<String> searchSetForExpression(List<Integer> integers, Integer target, String expression, String setKey) {
        checkedSubsetToExpressionMap.put(setKey, new HashMap<Integer, String>());

        Integer head = integers.get(0);
        List<Integer> tail = integers.subList(1, integers.size());
        SearchResult<String> searchResult = searchTailToMatchTarget(tail, head, target, expression);
        if (searchResult.exists())
            return searchResult;

        formSupersetWithHeadAndTail(head, tail.toString(), setKey);
        return new MissingResult<>();
    }

    private boolean isSetSearchedForExpression(String key) {
        return checkedSubsetToExpressionMap.containsKey(key);
    }

    private SearchResult<String> searchTailToMatchTarget(List<Integer> tail,
                                                         Integer head, Integer target, String expression) {
        String headString = Integer.toString(head);
        SearchResult<String> searchResult = find(tail, target + head, expression + " + " + headString);

        if (!searchResult.exists()) {
            searchResult = find(tail, target - head, expression + " - " + headString);
            if (!searchResult.exists()) {
                searchResult = find(tail, -target + head, " - " + swapOperators(expression) + " + " + headString);
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
        Map<Integer, String> subsetMap = checkedSubsetToExpressionMap.get(tailKey);
        Map<Integer, String> supersetMap = checkedSubsetToExpressionMap.get(supersetKey);

        for (Integer result : subsetMap.keySet()) {
            supersetMap.put(result + head, subsetMap.get(result) + " + " + head);
            supersetMap.put(result - head, subsetMap.get(result) + " - " + head);
        }
    }

    private SearchResult<String> matchTargetWithCachedCombinationsOfElements(Integer target, String expression, String key) {
        Map<Integer, String> resultExpressionMap = checkedSubsetToExpressionMap.get(key);
        for (Integer result : resultExpressionMap.keySet()) {
            if (Objects.equals(result, target)) {
                return new FoundResult<>(expression + " = " + resultExpressionMap.get(result));
            }
        }
        return new MissingResult<>();
    }

    private void addSingleIntegerExpression(Integer currentInteger, String key) {
        if (!checkedSubsetToExpressionMap.containsKey(key)) {
            checkedSubsetToExpressionMap.put(key, new HashMap<Integer, String>());
        }
        checkedSubsetToExpressionMap.get(key).put(currentInteger, currentInteger.toString());
    }

    int getNumberOfRecursions() {
        return numberOfRecursions;
    }
}
