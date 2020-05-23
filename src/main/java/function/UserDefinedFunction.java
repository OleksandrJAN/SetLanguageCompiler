package function;

import domain.values.Value;
import statement.ReturnStatement;
import statement.Statement;
import storage.Variables;

import java.util.List;

public class UserDefinedFunction implements Function {

    private List<String> argNames;
    private Statement body;

    public UserDefinedFunction(List<String> argNames, Statement body) {
        this.argNames = argNames;
        this.body = body;
    }

    public int getArgsCount() {
        return argNames.size();
    }

    public String getArgName(int index) {
        if (index < 0 || index >= argNames.size()) {
            throw new RuntimeException("Argument '" + index + "' not found");
        }
        return argNames.get(index);
    }

    @Override
    public Value execute(Value... args) {
        try {
            body.execute();
            return Variables.voidValue;
        } catch (ReturnStatement returnStatement) {
            return returnStatement.getResult();
        }
    }
}
