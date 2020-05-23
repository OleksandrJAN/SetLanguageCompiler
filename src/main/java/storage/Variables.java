package storage;

import domain.values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Variables {
    public static final Value voidValue = () -> Void.TYPE;

    private static Map<String, Value> variables;
    private static Stack<Map<String, Value>> stack;

    static {
        stack = new Stack<>();
        variables = new HashMap<>();
    }

    public static void push() {
        stack.push(new HashMap<>(variables));
    }

    public static void pop() {
        variables = stack.pop();
    }

    public static boolean isExists(String key) {
        return variables.containsKey(key);
    }

    public static Value get(String key) {
        return variables.get(key);
    }

    public static void add(String key, Value value) {
        variables.put(key, value);
    }
}
