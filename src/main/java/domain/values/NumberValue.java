package domain.values;

public class NumberValue implements Value {
    private final Number number;

    public NumberValue(Number number) {
        this.number = number;
    }

    @Override
    public Object getValue() {
        return number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
