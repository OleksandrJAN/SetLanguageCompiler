package parse;

import domain.token.Token;
import domain.token.TokenType;
import expression.*;
import statement.AssignmentStatement;
import statement.IfElseStatement;
import statement.PrintStatement;
import statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final static Token EOF = new Token(TokenType.EOF, "");

    private final List<Token> tokens;
    private final int size;

    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.size = tokens.size();
    }

    public List<Statement> parse() {
        final List<Statement> result = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            result.add(statement());
        }
        return result;
    }

    private Statement statement() {
        if (match(TokenType.PRINT)) {
            return new PrintStatement(expression());
        }
        if (match(TokenType.IF)) {
            return ifElseStatement();
        }

        return assignmentStatement();
    }

    private Statement ifElseStatement() {
        final Expression condition = expression();
        final Statement ifStatement = statement();
        final Statement elseStatement;

        if (match(TokenType.ELSE)) {
            elseStatement = statement();
        } else {
            elseStatement = null;
        }
        return new IfElseStatement(condition, ifStatement, elseStatement);
    }

    private Statement assignmentStatement() {
        final Token current = get(0);
        if (match(TokenType.WORD) && get(0).getType() == TokenType.EQ) {
            String variable = current.getText();
            consume(TokenType.EQ);
            return new AssignmentStatement(variable, expression());
        }
        throw new RuntimeException("Unknown statement");
    }


    private Expression expression() {
        return logicalOperation();
    }

    private Expression logicalOperation() {
        Expression result = conditional();

        while (true) {
            if (match(TokenType.BARBAR)) {
                result = new ConditionalExpression(TokenType.BARBAR, result, conditional());
                continue;
            }
            if (match(TokenType.AMPAMP)) {
                result = new ConditionalExpression(TokenType.AMPAMP, result, conditional());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression conditional() {
        Expression result = operation();

        while (true) {
            if (match(TokenType.EQEQ)) {
                result = new ConditionalExpression(TokenType.EQEQ, result, operation());
                continue;
            }
            if (match(TokenType.EXCLEQ)) {
                result = new ConditionalExpression(TokenType.EXCLEQ, result, operation());
                continue;
            }
            if (match(TokenType.LT)) {
                result = new ConditionalExpression(TokenType.LT, result, operation());
                continue;
            }
            if (match(TokenType.LTEQ)) {
                result = new ConditionalExpression(TokenType.LTEQ, result, operation());
                continue;
            }
            if (match(TokenType.GT)) {
                result = new ConditionalExpression(TokenType.GT, result, operation());
                continue;
            }
            if (match(TokenType.GTEQ)) {
                result = new ConditionalExpression(TokenType.GTEQ, result, operation());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression operation() {
        Expression result = primary();

        while (true) {
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression(TokenType.PLUS, result, primary());
                continue;
            }
            if (match(TokenType.STAR)) {
                result = new BinaryExpression(TokenType.STAR, result, primary());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result = new BinaryExpression(TokenType.MINUS, result, primary());
                continue;
            }
            break;
        }

        return result;
    }


    private Expression primary() {
        final Token current = get(0);
        if (match(TokenType.SET)) {
            return new SetExpression(current.getSet());
        }
        if (match(TokenType.WORD)) {
            return new VariableExpression(current.getText());
        }
        if (match(TokenType.TEXT)) {
            return new StringExpression(current.getText());
        }
        if (match(TokenType.LPAREN)) {
            Expression result = expression();
            if (!match(TokenType.RPAREN)) {
                throw new RuntimeException("')' missing");
            }
            return result;
        }
        throw new RuntimeException("Unknown expression");
    }


    private Token consume(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) {
            throw new RuntimeException("Token " + current + " does not match " + type);
        }
        pos++;
        return current;
    }

    private boolean match(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) {
            return false;
        }
        pos++;
        return true;
    }

    private Token get(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= size) {
            return EOF;
        }
        return tokens.get(position);
    }

}