package expression;

import domain.Set;
import domain.values.NumberValue;
import domain.values.SetValue;
import domain.values.StringValue;
import domain.values.Value;
import domain.token.TokenType;

public class BinaryExpression implements Expression {

    private final Expression expr1, expr2;
    private final TokenType operation;

    public BinaryExpression(TokenType operation, Expression expr1, Expression expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public Value eval() {
        Object value1 = expr1.eval().getValue();
        Object value2 = expr2.eval().getValue();

        if (value1 instanceof String || value2 instanceof String) {
            if (operation == TokenType.PLUS) {
                return new StringValue(value1.toString() + value2.toString());
            }
            throw new RuntimeException("Unknown String operation");
        }

        if (value1 instanceof Number && value2 instanceof Number) {
            Number number1 = (Number) value1;
            Number number2 = (Number) value2;
            switch (operation) {
                case PLUS:
                    return new NumberValue(number1.doubleValue() + number2.doubleValue());
                case STAR:
                    return new NumberValue(number1.doubleValue() * number2.doubleValue());
                case MINUS:
                    return new NumberValue(number1.doubleValue() - number2.doubleValue());
                case DIVIDE:
                    return new NumberValue(number1.doubleValue() / number2.doubleValue());
            }
            throw new RuntimeException("Unknown Number operation");
        }

        if (value1 instanceof Set && value2 instanceof Set) {
            Set set1 = (Set) value1;
            Set set2 = (Set) value2;
            switch (operation) {
                case PLUS:
                    return new SetValue(set1.union(set2));
                case STAR:
                    return new SetValue(set1.intersection(set2));
                case MINUS:
                    return new SetValue(set1.difference(set2));
                case DIVIDE:
                    return new SetValue(set1.symmetricalDifference(set2));
            }
            throw new RuntimeException("Unknown Set operation");
        }

        throw new RuntimeException("Unknown values");
    }
}
