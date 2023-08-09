package SpendWise.Graphics;

import javax.swing.JPanel;

import SpendWise.Utils.GraphicsUtils;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.Colors;
import SpendWise.Utils.Graphics.Fonts;
import SpendWise.Utils.Graphics.Refresh;
import SpendWise.Utils.Graphics.Sizes;

public abstract class Screen extends JPanel implements BlankPanels, Colors, Fonts, Sizes, Refresh {
    protected JPanel[] blankPanels;

    protected abstract void initialize();

    @Override
    public void refresh() {
        GraphicsUtils.refresh(this);
    }

    public JPanel getBlankPanel(PanelOrder panel) {
        return blankPanels[panel.ordinal()];
    }
}
