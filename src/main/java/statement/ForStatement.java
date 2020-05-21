package statement;

import expression.Expression;

public class ForStatement implements Statement {

    private Statement initialization;
    private Expression termination;
    private Statement modification;
    private Statement statement;

    public ForStatement(Statement initialization, Expression termination, Statement modification, Statement statement) {
        this.initialization = initialization;
        this.termination = termination;
        this.modification = modification;
        this.statement = statement;
    }

    @Override
    public void execute() {
        for (initialization.execute(); (boolean) termination.eval().getValue(); modification.execute()) {
            try {
                statement.execute();
            } catch (BreakStatement breakStatement) {
                break;
            } catch (ContinueStatement continueStatement) {
                // continue;
            }
        }
    }
}
