package expression;

import function.Function;
import function.UserDefinedFunction;
import domain.values.Value;
import storage.Functions;
import storage.Variables;

import java.util.ArrayList;
import java.util.List;

public class FunctionalExpression implements Expression {

    private String name;
    private List<Expression> args;

    public FunctionalExpression(String name) {
        this.name = name;
        this.args = new ArrayList<>();
    }

    public FunctionalExpression(String name, List<Expression> args) {
        this.name = name;
        this.args = args;
    }

    public void addArg(Expression arg) {
        args.add(arg);
    }

    @Override
    public Value eval() {
        int size = args.size();
        Value[] values = new Value[size];
        for (int i = 0; i < size; i++) {
            values[i] = args.get(i).eval();
        }

        Function function = Functions.get(name);
        if (function instanceof UserDefinedFunction) {
            UserDefinedFunction userFunction = (UserDefinedFunction) function;
            if (size != userFunction.getArgsCount()) {
                throw new RuntimeException("Args count mismatch");
            }

            Variables.push();
            for (int i = 0; i < size; i++) {
                Variables.add(userFunction.getArgName(i), values[i]);
            }

            Value result = function.execute(values);
            Variables.pop();
            return result;
        }

        return function.execute(values);
    }
}
