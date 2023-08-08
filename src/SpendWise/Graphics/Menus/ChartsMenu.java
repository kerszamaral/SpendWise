package SpendWise.Graphics.Menus;

import SpendWise.Graphics.Screen;
import SpendWise.Utils.GraphicsUtils;

public class ChartsMenu extends Screen {
    // TODO implement ChartsMenu
    public ChartsMenu() {
        this.initialize();
    }

    @Override
    protected void initialize() {
        blankPanels = GraphicsUtils.initializeOffsets(this, DEFAULT_OFFSETS);
    }
}
