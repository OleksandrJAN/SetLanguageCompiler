package storage;

import domain.values.Value;

import java.util.HashMap;
import java.util.Map;

public class Variables {
    private static final Map<String, Value> variables;

    static {
        variables = new HashMap<>();
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
