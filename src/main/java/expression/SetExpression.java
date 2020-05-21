package expression;

import domain.Set;
import domain.values.SetValue;
import domain.values.Value;

public class SetExpression implements Expression {

    private final Value setValue;

    public SetExpression(Set set) {
        this.setValue = new SetValue(set);
    }

    @Override
    public Value eval() {
        return setValue;
    }

    @Override
    public String toString() {
        return setValue.toString();
    }
}
