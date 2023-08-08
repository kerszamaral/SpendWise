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
        blankPanels = GraphicsUtils.createOffsets(this, DEFAULT_OFFSETS);
    }
}
