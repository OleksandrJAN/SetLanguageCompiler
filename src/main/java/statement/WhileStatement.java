package statement;

import expression.Expression;

public class WhileStatement implements Statement {

    private Expression condition;
    private Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void execute() {
        while ((boolean) condition.eval().getValue()) {
            statement.execute();
        }
    }
}
