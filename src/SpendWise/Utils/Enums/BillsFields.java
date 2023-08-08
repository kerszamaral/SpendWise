package SpendWise.Utils.Enums;

public enum BillsFields {
    VALUE("Value"),
    ESSENTIAL("Essential"),
    DATE("Date"),
    DESCRIPTION("Description"),
    TYPE("Type");

    private final String label;

    private BillsFields(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
