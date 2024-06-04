package classes;

public class Diameter {
    private int _value;

    public Diameter(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Diameter value cannot be negative");
        }
        _value = value;
    }

    public int getValue() {
        return _value;
    }

    public boolean isCompatible(Diameter diameter) {
        return diameter.getValue() == _value || diameter.getValue() == 0 || _value == 0;
    }

    @Override
    public String toString() {
        return String.valueOf(_value);
    }
}
