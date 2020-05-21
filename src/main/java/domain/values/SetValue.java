package domain.values;

import domain.Set;

public class SetValue implements Value {
    private final Set set;

    public SetValue(Set set) {
        this.set = set;
    }

    @Override
    public Object getValue() {
        return set;
    }

    @Override
    public String toString() {
        return set.getView();
    }
}
