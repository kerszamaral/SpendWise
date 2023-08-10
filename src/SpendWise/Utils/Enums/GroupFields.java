package SpendWise.Utils.Enums;

public enum GroupFields {
    SELECT_GROUP("Select Group"),
    SELECT_OPERATION("Select Operation"),
    USERNAME("Username");

    private final String label;

    private GroupFields(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
