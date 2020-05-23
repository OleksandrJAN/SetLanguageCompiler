package storage;

import function.Function;

import java.util.HashMap;
import java.util.Map;

public class Functions {
    private static final Map<String, Function> functions;

    static {
        functions = new HashMap<>();
    }

    public static boolean isExists(String key) {
        return functions.containsKey(key);
    }

    public static Function get(String key) {
        return functions.get(key);
    }

    public static void add(String key, Function function) {
        functions.put(key, function);
    }
}
