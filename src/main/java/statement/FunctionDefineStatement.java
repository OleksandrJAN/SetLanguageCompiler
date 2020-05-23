package statement;

import function.UserDefinedFunction;
import storage.Functions;

import java.util.List;

public class FunctionDefineStatement implements Statement {

    private String name;
    private List<String> argNames;
    private Statement body;

    public FunctionDefineStatement(String name, List<String> argNames, Statement body) {
        this.name = name;
        this.argNames = argNames;
        this.body = body;
    }

    @Override
    public void execute() {
        Functions.add(name, new UserDefinedFunction(argNames, body));
    }
}
