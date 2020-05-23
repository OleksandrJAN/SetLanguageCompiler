package expression;

import domain.values.StringValue;
import domain.values.Value;

public class StringExpression implements Expression {

    private final Value strValue;

    public StringExpression(String str) {
        this.strValue = new StringValue(str);
    }

    @Override
    public Value eval() {
        return strValue;
    }
}
