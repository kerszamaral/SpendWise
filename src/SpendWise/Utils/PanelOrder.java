package SpendWise.Utils;

import java.awt.BorderLayout;

public enum PanelOrder {
    NORTH(BorderLayout.NORTH),
    WEST(BorderLayout.WEST),
    EAST(BorderLayout.EAST),
    SOUTH(BorderLayout.SOUTH),
    CENTRAL(BorderLayout.CENTER);

    private final String constrains;

    PanelOrder(String constrains) {
        this.constrains = constrains;
    }

    public String getConstrains() {
        return constrains;
    }

}
