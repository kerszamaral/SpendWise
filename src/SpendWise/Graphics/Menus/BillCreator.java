package SpendWise.Graphics.Menus;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import SpendWise.Graphics.Screen;
import SpendWise.Managers.ExpensesManager;

public class BillCreator extends Screen {
    private JPanel pnlMiddle;
    private ExpensesManager expensesManager;

    // TODO implement BillCreator
    public BillCreator(ExpensesManager expensesManager) {
        this.expensesManager = expensesManager;
        this.pnlMiddle = new JPanel();
        this.initialize();

    }

    @Override
    protected void initialize() {
        super.initialize();

        this.setLayout(new BorderLayout());
        this.setBackground(BACKGROUND_COLOR);

        pnlMiddle.setLayout(new BoxLayout(pnlMiddle, BoxLayout.Y_AXIS));
        pnlMiddle.setBackground(Color.WHITE);
        this.add(pnlMiddle, BorderLayout.CENTER);

        /*
         * All those panels are just to make the user data to be in the center, but
         * with some small borders around it.
         */
        JPanel blankPanelNorth = new JPanel();
        initializeBlankPanel(blankPanelNorth, 100, 50);
        this.add(blankPanelNorth, BorderLayout.NORTH);
        JPanel blankPanelWest = new JPanel();
        initializeBlankPanel(blankPanelWest, 100, 100);
        this.add(blankPanelWest, BorderLayout.WEST);
        JPanel blankPanelEast = new JPanel();
        initializeBlankPanel(blankPanelEast, 25, 100);
        this.add(blankPanelEast, BorderLayout.EAST);
    }
}
