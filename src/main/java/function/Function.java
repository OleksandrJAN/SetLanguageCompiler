package function;

import domain.values.Value;

public interface Function {
    Value execute(Value... args);
}
