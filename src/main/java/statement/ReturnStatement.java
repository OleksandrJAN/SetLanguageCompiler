package statement;

import domain.values.Value;
import expression.Expression;

public class ReturnStatement extends RuntimeException implements Statement {

    private Expression expression;
    private Value result;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    public Value getResult() {
        return result;
    }

    @Override
    public void execute() {
        result = expression.eval();
        throw this;
    }
}
