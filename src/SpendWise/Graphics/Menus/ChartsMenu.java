package SpendWise.Graphics.Menus;

import SpendWise.Graphics.Screen;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.Misc;
import SpendWise.Utils.Graphics.Panels;
import SpendWise.Utils.Enums.GroupFields;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ChartsMenu extends Screen {
    // TODO implement ChartsMenu
    private JPanel pnlChartsMenu;
    private static final String BACKGROUND_COLOR = "-fx-background-color: " + Misc.getColorHex(ACCENT_COLOR)
            + ";";

    public ChartsMenu() {
        initialize();
    }

    @Override
    protected void initialize() {
        blankPanels = Panels.createPanelWithCenter(this, new Offsets(50, 50, 50, 50), ACCENT_COLOR);

        pnlChartsMenu = super.getBlankPanel(PanelOrder.CENTRAL);

        JFXPanel fxPanel = new JFXPanel();
        Platform.runLater(() -> createPieChart(fxPanel));
        pnlChartsMenu.add(fxPanel);   
    }

    private static void createPieChart(JFXPanel fxPanel) {
        PieChart.Data slice1 = new PieChart.Data("Slice 1", 30);
        PieChart.Data slice2 = new PieChart.Data("Slice 2", 20);
        PieChart.Data slice3 = new PieChart.Data("Slice 3", 25);
        PieChart.Data slice4 = new PieChart.Data("Slice 4", 15);

        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(slice1, slice2, slice3, slice4);
    
        Scene scene = new Scene(pieChart, 300, 300);
        scene.getRoot().setStyle(BACKGROUND_COLOR);
        fxPanel.setScene(scene);
    }
}

