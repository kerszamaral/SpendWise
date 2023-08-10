package SpendWise.Interface;

import javax.swing.JPanel;

import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.BlankPanels;
import SpendWise.Utils.Graphics.Colors;
import SpendWise.Utils.Graphics.Fonts;
import SpendWise.Utils.Graphics.Icons;
import SpendWise.Utils.Graphics.Misc;
import SpendWise.Utils.Graphics.Refresh;
import SpendWise.Utils.Graphics.Sizes;

public abstract class Screen extends JPanel implements BlankPanels, Colors, Fonts, Sizes, Refresh, Icons {
    protected JPanel[] blankPanels;

    protected abstract void initialize();

    @Override
    public void refresh() {
        Misc.refresh(this);
    }

    public JPanel getBlankPanel(PanelOrder panel) {
        return blankPanels[panel.ordinal()];
    }
}
