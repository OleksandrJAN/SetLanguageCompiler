package statement;

import expression.Expression;

public class DoWhileStatement implements Statement {

    private Expression condition;
    private Statement statement;

    public DoWhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void execute() {
        do {
            try {
                statement.execute();
            } catch (BreakStatement breakStatement) {
                break;
            } catch (ContinueStatement continueStatement) {
                // continue;
            }
        } while ((boolean) condition.eval().getValue());
    }
}
