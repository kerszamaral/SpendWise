package SpendWise.Utils;

public enum LoginLabels {
    NAME(0),
    USERNAME(1),
    EMAIL(2),
    PASSWORD(3),
    REPEAT_PASSWORD(4);

    private final int value;

    private LoginLabels(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
