package statement;

import domain.values.Value;
import expression.Expression;
import storage.Variables;

public class AssignmentStatement implements Statement {

    private final String variable;
    private final Expression expression;

    public AssignmentStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void execute() {
        final Value result = expression.eval();
        Variables.add(variable, result);
    }

}
