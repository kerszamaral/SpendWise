package SpendWise.Utils.Enums;

public enum AccountFields {
    NAME("Name"),
    USERNAME("Username"),
    EMAIL("E-mail"),
    PASSWORD("Password");

    private String value;

    AccountFields(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
