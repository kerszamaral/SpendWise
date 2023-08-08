package SpendWise.Utils.Enums;

public enum GroupFields {
    SELECT("Select Group"),
    ADD("Add user"),
    REMOVE("Remove user");

    private final String label;

    private GroupFields(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
