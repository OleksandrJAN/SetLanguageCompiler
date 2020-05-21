package expression;

import domain.Element;
import domain.Set;
import domain.values.SetValue;
import domain.values.Value;
import storage.Variables;

import java.util.SortedSet;

public class SetExpression implements Expression {

    private final Value setValue;

    public SetExpression(Set set) {
        this.setValue = new SetValue(set);
    }

    @Override
    public Value eval() {
        Set set = (Set) setValue.getValue();
        String setView = set.getView();

        while (setView.contains("$")) {
            int variablePosition = setView.indexOf('$') + 1;
            String variableName = getVariableName(setView, variablePosition);

            Value variableValue = Variables.get(variableName);
            if (variableValue instanceof SetValue) {
                Set variableElement = (Set) variableValue.getValue();

                SortedSet<Element> elements = set.getElements();
                for (Element element : elements) {
                    String elementStr = element.toString();
                    if (elementStr.contains("$" + variableName)) {
                        //  replace '$variableName' by variableElement
                        String elementVariableView = variableElement.getView();
                        String newElementWithVariable = elementStr.replace("$" + variableName, elementVariableView);

                        //  remove old element and add new element
                        elements.remove(element);
                        set.addElement(new Element(newElementWithVariable));
                        break;
                    }
                }

                setView = set.getView();    //update new view of current set
            } else {
                throw new RuntimeException("Variable '" + variableName + "' in set '" + setView + "' not found");
            }
        }

        return setValue;
    }

    private String getVariableName(String setView, int variablePosition) {
        StringBuilder builder = new StringBuilder();
        char current = setView.charAt(variablePosition);

        while (current != ',' && current != '}') {
            builder.append(current);
            variablePosition++;
            current = setView.charAt(variablePosition);
        }

        return builder.toString();
    }


    @Override
    public String toString() {
        return setValue.toString();
    }
}
