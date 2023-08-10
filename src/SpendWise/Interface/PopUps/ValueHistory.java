package SpendWise.Interface.PopUps;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SpendWise.Interface.PopUp;
import SpendWise.Interface.Screen;
import SpendWise.Logic.Bills.Fixed;
import SpendWise.Utils.Dates;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.Alerts;
import SpendWise.Utils.Graphics.Components;
import SpendWise.Utils.Graphics.Panels;

public class ValueHistory extends PopUp {
    private JComboBox<String> valueHistory;
    private Fixed expense;
    private JFormattedTextField newAmount;
    private JFormattedTextField newStartDate;
    private JPanel alertPanel;

    public ValueHistory(Screen parent, String title, Fixed expense) {
        super(parent, title);
        this.expense = expense;
    }

    @Override
    public void run() {
        Offsets outerOffsets = new Offsets(20, 20, 50, 50);
        Offsets innerOffsets = new Offsets(10, 10, 10, 10);
        blankPanels = Panels.createPanelWithCenter((JPanel) this.getContentPane(), innerOffsets, outerOffsets,
                ACCENT_COLOR);

        // Creating the valueHistory panel and it's fields
        JPanel showPanel = new JPanel(new GridLayout(7, 1));
        showPanel.setBackground(ACCENT_COLOR);

        valueHistory = new JComboBox<String>();
        refresh();
        showPanel.add(new JLabel("Value History:"));
        showPanel.add(valueHistory);

        showPanel.add(new JLabel("New Amount:"));

        newAmount = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        newAmount.setValue(Double.valueOf(0));
        newAmount.setColumns(10);
        newAmount.setBackground(ACCENT_COLOR);

        showPanel.add(newAmount);

        showPanel.add(new JLabel("New Start Date:"));

        newStartDate = new JFormattedTextField(Dates.dateFormatter);
        newStartDate.setValue(Dates.convLocalDate(LocalDate.now()));
        newStartDate.setBackground(ACCENT_COLOR);

        showPanel.add(newStartDate);

        // Creating the buttons panel and it's buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(ACCENT_COLOR);
        JButton confirmButton = Components.createButton("Set new ammount", ACCENT_COLOR, BACKGROUND_COLOR,
                new Dimension(200, 30));

        confirmButton.addActionListener(e -> addValue(e));

        buttonsPanel.add(confirmButton);

        showPanel.add(buttonsPanel);

        blankPanels[PanelOrder.CENTRAL.ordinal()].add(showPanel);
        alertPanel = getBlankPanel(PanelOrder.SOUTH);

        this.setVisible(true);
    }

    private void addValue(ActionEvent action) {
        Alerts.clearErrorMessage(alertPanel);
        Alerts.setErrorBorder(newAmount, false);
        Alerts.setErrorBorder(newStartDate, false);

        double value = 0;
        try {
            value = NumberFormat.getCurrencyInstance().parse(newAmount.getText()).doubleValue();
        } catch (Exception e) {
            return;
        }
        if (value <= 0) {
            Alerts.showErrorMessage(alertPanel, "Value must be greater than 0!");
            Alerts.setErrorBorder(newAmount, true);
            return;
        }

        LocalDate startDate = Dates.convDate(Dates.convString(newStartDate.getText()));

        int lastIndex = expense.getValueHistory().size() - 1;
        LocalDate lastStartDate = (LocalDate) expense.getValueHistory().get(lastIndex).getFirst();
        if (startDate.isBefore(lastStartDate)) {
            Alerts.showErrorMessage(alertPanel, "Start date must be after the last start date!");
            Alerts.setErrorBorder(newStartDate, true);
            return;
        }
        expense.updateValue(value);
        this.refresh();
    }

    private void refresh() {
        valueHistory.removeAllItems();
        for (int i = 0; i < expense.getValueHistory().size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (Object value : expense.getValueHistory().get(i)) {
                boolean isLastDate = value instanceof LocalDate && LocalDate.MAX.minusDays(1).equals(value);
                String valueString = isLastDate ? "No End Date" : value.toString();
                sb.append(valueString);
                sb.append(" || ");
            }
            sb.delete(sb.length() - 4, sb.length());
            valueHistory.addItem(sb.toString());
        }
    }
}
