package domain.values;

public class BooleanValue implements Value {
    private final Boolean bool;

    public BooleanValue(boolean bool) {
        this.bool = bool;
    }

    @Override
    public Object getValue() {
        return bool;
    }

    @Override
    public String toString() {
        return bool.toString();
    }
}
