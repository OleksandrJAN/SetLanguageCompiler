package expression;

import domain.values.BooleanValue;
import domain.Set;
import domain.values.Value;
import domain.token.TokenType;

public class ConditionalExpression implements Expression {

    private final Expression expr1, expr2;
    private final TokenType operation;

    public ConditionalExpression(TokenType operation, Expression expr1, Expression expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }


    private boolean booleanEval(String str1, String str2) {
        switch (operation) {
            case EQEQ:
                return str1.equals(str2);
            case EXCLEQ:
                return !str1.equals(str2);
            case GT:
                return str1.compareTo(str2) > 0;
            case GTEQ:
                return str1.compareTo(str2) >= 0;
            case LT:
                return str1.compareTo(str2) < 0;
            case LTEQ:
                return str1.compareTo(str2) <= 0;
            default:
                throw new RuntimeException("Unknown String operation for -> '" + str1 + "' and '" + str2 + "'");
        }
    }

    private boolean booleanEval(Set set1, Set set2) {
        switch (operation) {
            case EQEQ:
                return set1.equals(set2);
            case EXCLEQ:
                return !set1.equals(set2);
            case GT:
                return set1.size() > set2.size();
            case GTEQ:
                return set1.size() >= set2.size();
            case LT:
                return set1.size() < set2.size();
            case LTEQ:
                return set1.size() <= set2.size();
            default:
                throw new RuntimeException("Unknown Set operation for -> '" + set1 + "' and '" + set2 + "'");
        }
    }

    private boolean booleanEval(Boolean bool1, Boolean bool2) {
        switch (operation) {
            case BARBAR:
                return bool1 || bool2;
            case AMPAMP:
                return bool1 && bool2;
            default:
                throw new RuntimeException("Unknown Boolean operation for -> '" + bool1 + "' and '" + bool2 + "'");
        }
    }


    @Override
    public Value eval() {
        Object value1 = expr1.eval().getValue();
        Object value2 = expr2.eval().getValue();

        if (value1 instanceof String && value2 instanceof String) {
            String str1 = (String) value1;
            String str2 = (String) value2;

            boolean result = booleanEval(str1, str2);
            return new BooleanValue(result);
        }

        if (value1 instanceof Set && value2 instanceof Set) {
            Set set1 = (Set) value1;
            Set set2 = (Set) value2;

            boolean result = booleanEval(set1, set2);
            return new BooleanValue(result);
        }

        if (value1 instanceof Boolean && value2 instanceof Boolean) {
            Boolean bool1 = (Boolean) value1;
            Boolean bool2 = (Boolean) value2;

            boolean result = booleanEval(bool1, bool2);
            return new BooleanValue(result);
        }

        throw new RuntimeException("Unknown conditional values -> '" + value1 + "' and '" + value2 + "'");
    }


}
