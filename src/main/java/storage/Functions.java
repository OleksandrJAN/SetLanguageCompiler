package storage;

import function.Function;

import java.util.HashMap;
import java.util.Map;

public class Functions {
    private static final Map<String, Function> functions;

    static {
        functions = new HashMap<>();
    }

    public static Function get(String key) {
        if (!functions.containsKey(key)) {
            throw new RuntimeException("Function '" + key + "' does not exists");
        }
        return functions.get(key);
    }

    public static void add(String key, Function function) {
        functions.put(key, function);
    }
}
