package SpendWise.Utils;

public enum SignUpLabels {
    NAME("Name: "),
    USERNAME("Username: "),
    EMAIL("E-mail: "),
    PASSWORD("Password: "),
    REPEAT_PASSWORD("Repeat password: ");

    private final String labelName;

    SignUpLabels(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelName() {
        return labelName;
    }
}