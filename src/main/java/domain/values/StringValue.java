package domain.values;

public class StringValue implements Value {
    private final String str;

    public StringValue(String str) {
        this.str = str;
    }

    @Override
    public Object getValue() {
        return str;
    }

    @Override
    public String toString() {
        return str;
    }
}
