package expression;

import domain.values.Value;
import storage.Variables;

public class VariableExpression implements Expression {

    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value eval() {
        if (!Variables.isExists(name)) {
            throw new RuntimeException("Variable '" + name + "' does not exists");
        }
        return Variables.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", name, Variables.get(name));
    }
}
