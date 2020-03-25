package Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Sort {

    private List<Pair<Direction, String>> parameters;

    public Sort(){
        this.parameters = new ArrayList<>();
    }

    public Sort(Direction direction, String first, String ... others){
        this.parameters = new ArrayList<>();
        this.parameters.add(new Pair<>(direction, first));
        this.parameters.addAll(Arrays.stream(others)
                .map(parameter -> new Pair<>(direction, parameter))
                .collect(Collectors.toList())
        );
    }

    public Sort(String first, String ... others){
        this(Direction.ASC, first, others);
    }

    public void setParameters(List<Pair<Direction, String>> parameters) {
        this.parameters = parameters;
    }

    public List<Pair<Direction, String>> getParameters() {
        return parameters;
    }

    public <T> Iterable<T> sort(Iterable<T> elements) {
        return StreamSupport.stream(elements.spliterator(), false)
                .sorted(new SortComparator())
                .collect(Collectors.toList());
    }

    private class SortComparator implements Comparator<Object>{

        public int compareCustom(Object first, Object second, Pair<Direction, String> criteria) {
            Object firstValue;
            try {
                firstValue = getValueByFieldName(first, criteria.getSecond());
                Object secondValue = getValueByFieldName(second, criteria.getSecond());
                int result = ((Comparable)firstValue).compareTo(secondValue);
                if(criteria.getFirst() == Direction.ASC)
                    return result;
                else
                    return -result;
            } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        public int compare(Object first, Object second) {
            return parameters.stream()
                    .map(entry -> compareCustom(first, second, entry))
                    .filter(value -> value != 0)
                    .findFirst()
                    .orElse(0);
        }
    }

    private Object getValueByFieldName(Object element, String field) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class<?> c = Class.forName(element.getClass().getName());
        String methodName = field;
        methodName = "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        Method method = c.getDeclaredMethod(methodName);
        return method.invoke(element);
    }

    public Sort and(Sort other){
        Sort result = new Sort();
        List<Pair<Direction, String>> parameters = new ArrayList<>(this.parameters);
        parameters.addAll(other.getParameters());
        result.setParameters(parameters);
        return result;
    }

}
