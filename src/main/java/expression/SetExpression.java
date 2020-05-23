package expression;

import domain.Element;
import domain.Set;
import domain.values.SetValue;
import domain.values.Value;
import org.apache.commons.lang3.SerializationUtils;
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

        if (setView.contains("$")) {
            // надо вернуть новое SetValue, т.к. если мы заменим переменные на текущем, то при повторном вызове этого
            // шаблона мы получим множество со значениями переменных после 1го вызова
            // пример: A = ${a,b}; for(2 раза) A = A + ${$A}
            // без возвращения нового объекта: A = {a,b,{a,b}}, т.к. после замены у нас вместо мн-ва ${$A} находится мн-во {{a,b}}
            // с возвращением нового объекта: A = {a,b,{a,b},{a,b,{a,b}}}
            Set setWithVariables = SerializationUtils.clone(set);
            while (setView.contains("$")) {
                // pos of first variable name
                int variablePosition = setView.indexOf('$') + 1;
                String variableName = getVariableName(setView, variablePosition);

                Value variableValue = Variables.get(variableName);
                if (variableValue instanceof SetValue) {
                    Set variableElement = (Set) variableValue.getValue();

                    SortedSet<Element> elements = setWithVariables.getElements();
                    for (Element element : elements) {
                        String elementStr = element.toString();
                        if (elementStr.contains("$" + variableName)) {
                            //  replace '$variableName' by variableElement
                            String elementVariableView = variableElement.getView();
                            String newElementWithVariable = elementStr.replace("$" + variableName, elementVariableView);

                            //  remove old element and add new element
                            elements.remove(element);
                            setWithVariables.addElement(new Element(newElementWithVariable));
                            break;
                        }
                    }

                    setView = setWithVariables.getView();    //update new view of current set
                } else {
                    throw new RuntimeException("Variable '" + variableName + "' in set '" + setView + "' not found");
                }
            }
            return new SetValue(setWithVariables);
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
