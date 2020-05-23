package expression;

import domain.values.NumberValue;
import domain.values.Value;

public class NumberExpression implements Expression {

    private final Value numberValue;

    public NumberExpression(Number number) {
        this.numberValue = new NumberValue(number);
    }

    @Override
    public Value eval() {
        return numberValue;
    }
}
