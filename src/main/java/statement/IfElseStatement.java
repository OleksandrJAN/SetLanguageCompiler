package statement;

import expression.Expression;

public class IfElseStatement implements Statement {

    private final Expression expression;
    private final Statement ifStatement, elseStatement;

    public IfElseStatement(Expression expression, Statement ifStatement, Statement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
        final boolean result = (boolean) expression.eval().getValue();
        if (result) {
            ifStatement.execute();
        } else if (elseStatement != null){
            elseStatement.execute();
        }
    }
}
