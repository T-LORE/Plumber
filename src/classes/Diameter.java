package classes;

public class Diameter {
    private int _value;

    public Diameter(int value) {
        _value = value;
    }

    public int getValue() {
        return _value;
    }

    public boolean isCompatible(Diameter diameter) {
        return diameter.getValue() == _value;
    }

    @Override
    public String toString() {
        return String.valueOf(_value);
    }
}
