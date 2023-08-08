package SpendWise.Graphics;

import javax.swing.JPanel;

import java.awt.*;

import SpendWise.Utils.GraphicsUtils;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.PanelOrder;

public abstract class Screen extends JPanel implements BlankPanels {
    protected final static Color BACKGROUND_COLOR = GraphicsUtils.BACKGROUND_COLOR;
    protected final static Color ACCENT_COLOR = GraphicsUtils.ACCENT_COLOR;
    protected final static Font STD_FONT = GraphicsUtils.STD_FONT;
    protected final static Font STD_FONT_BOLD = GraphicsUtils.STD_FONT_BOLD;
    protected final static Offsets DEFAULT_OFFSETS = new Offsets(50, 50, 100, 0);
    protected final static Dimension DEFAULT_FIELD_SIZE = GraphicsUtils.DEFAULT_FIELD_SIZE;

    protected JPanel[] blankPanels;

    protected abstract void initialize();

    public void refresh() {
        GraphicsUtils.refresh(this);
    }

    /**
     * @param panel the panel to get
     * @return the blankPanels
     */
    public JPanel getBlankPanel(PanelOrder panel) {
        return blankPanels[panel.ordinal()];
    }
}
