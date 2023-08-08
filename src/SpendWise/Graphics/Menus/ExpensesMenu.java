package SpendWise.Graphics.Menus;

import SpendWise.Graphics.Screen;
import SpendWise.Utils.GraphicsUtils;

public class ExpensesMenu extends Screen {
    // TODO implement ExpensesMenu
    public ExpensesMenu() {
        this.initialize();
    }

    @Override
    protected void initialize() {
        blankPanels = GraphicsUtils.initializeOffsets(this, DEFAULT_OFFSETS);
    }
}
