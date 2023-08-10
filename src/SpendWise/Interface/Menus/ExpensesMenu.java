package SpendWise.Interface.Menus;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import SpendWise.Interface.Screen;
import SpendWise.Interface.PopUps.ValueHistory;
import SpendWise.Logic.Bills.Expense;
import SpendWise.Logic.Bills.Fixed;
import SpendWise.Logic.Bills.OneTime;
import SpendWise.Logic.Bills.Recurring;
import SpendWise.Logic.Managers.ExpensesManager;
import SpendWise.Utils.Dates;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.BillsFields;
import SpendWise.Utils.Enums.ExpenseType;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.Alerts;
import SpendWise.Utils.Graphics.Components;
import SpendWise.Utils.Graphics.Misc;
import SpendWise.Utils.Graphics.Panels;

public class ExpensesMenu extends Screen {
    private ExpensesManager expensesManager;
    private JComboBox<Expense> expensesComboBox;

    private JPanel pnlCentral;
    private JPanel pnlTypeSpecific;

    private JComponent[] fields;
    private JCheckBox oneTimeCheckBox;
    private JFormattedTextField recurringEndDateField;
    private JButton btnValueHistory;

    private JPanel alertPanel;
    private JButton btnChangeExpense;
    private boolean isEditing;

    public ExpensesMenu(ExpensesManager expensesManager) {
        this.expensesManager = expensesManager;
        this.initialize();
    }

    @Override
    protected void initialize() {
        Offsets outerOffsets = new Offsets(20, 20, 100, 0);
        Offsets innerOffsets = new Offsets(50, 70, 50, 50);
        blankPanels = Panels.createPanelWithCenter(this, innerOffsets, outerOffsets, ACCENT_COLOR);

        expensesComboBox = new JComboBox<Expense>(expensesManager.getExpenses().toArray(new Expense[0]));
        expensesComboBox.addActionListener(e -> createBillFields(e));
        Dimension comboBoxSize = new Dimension(450, 30);
        Misc.defineSize(expensesComboBox, comboBoxSize);
        getBlankPanel(PanelOrder.NORTH).add(expensesComboBox);

        pnlCentral = super.getBlankPanel(PanelOrder.CENTRAL);
        createBillFields(null);

        Offsets offsetsBtn = new Offsets(30, 10, 400, 20);
        ActionListener[] listeners = { e -> changeExpense(e), e -> removeExpense(e) };
        String[] btnLabels = { "Edit Expense", "Remove Expense" };
        btnChangeExpense = Components.initializeButtons(getBlankPanel(PanelOrder.SOUTH), offsetsBtn, btnLabels,
                ACCENT_COLOR,
                listeners)[0];

        JPanel southPanel = (JPanel) getBlankPanel(PanelOrder.SOUTH).getComponent(0);
        alertPanel = (JPanel) southPanel.getComponent(0);
    }

    @Override
    public void refresh() {
        expensesComboBox.removeAllItems();
        for (Expense expense : expensesManager.getExpenses()) {
            expensesComboBox.addItem(expense);
        }
        expensesComboBox.setSelectedIndex(-1);
        Alerts.clearErrorMessage(alertPanel);
        super.refresh();
    }

    public void refresh(Expense exp) {
        int index = 0;
        expensesComboBox.removeAllItems();
        expensesComboBox.setSelectedIndex(-1);
        for (Expense expense : expensesManager.getExpenses()) {
            expensesComboBox.addItem(expense);
            if (expense.equals(exp))
                expensesComboBox.setSelectedIndex(index);
            index++;
        }
        createBillFields(null);
        Alerts.clearErrorMessage(alertPanel);
        super.refresh();
    }

    private void createBillFields(ActionEvent e) {
        Expense selectedExpense = (Expense) expensesComboBox.getSelectedItem();
        boolean expenseSelected = selectedExpense != null;

        pnlCentral.removeAll();
        pnlCentral.setLayout(new GridLayout((BillsFields.values().length - 1) * 2 + 1, 1));

        double defaultValue = expenseSelected ? selectedExpense.getValue() : 0.0;

        boolean defaultEssential = expenseSelected ? selectedExpense.isEssential() : false;

        LocalDate temp = expenseSelected ? selectedExpense.getDate() : LocalDate.now();
        Date defaultDate = Dates.convLocalDate(temp);

        String defaultDescription = expenseSelected ? selectedExpense.getDescription() : "";

        fields = new JComponent[BillsFields.values().length];

        for (BillsFields field : BillsFields.values()) {
            if (field == BillsFields.TYPE)
                continue;

            String label = field.getLabel() + ": ";
            JLabel lbl = new JLabel(label);
            pnlCentral.add(lbl);

            JComponent fieldType = null;
            switch (field) {
                case VALUE:
                    JFormattedTextField valueField = new JFormattedTextField(NumberFormat.getCurrencyInstance());
                    valueField.setValue(Double.valueOf(defaultValue));
                    valueField.setColumns(10);
                    valueField.setEditable(false);
                    fieldType = valueField;
                    break;

                case ESSENTIAL:
                    JCheckBox essentialCheckBox = new JCheckBox();
                    essentialCheckBox.setSelected(defaultEssential);
                    essentialCheckBox.setEnabled(false);
                    fieldType = essentialCheckBox;
                    break;

                case DATE:
                    JFormattedTextField dateField = new JFormattedTextField(Dates.dateFormatter);
                    dateField.setValue(defaultDate);
                    dateField.setEditable(false);
                    fieldType = dateField;
                    break;

                case DESCRIPTION:
                    JTextField descriptionField = new JTextField(defaultDescription);
                    descriptionField.setEditable(false);
                    fieldType = descriptionField;
                    break;

                default:
                    break;
            }

            fieldType.setBackground(ACCENT_COLOR);

            fields[field.ordinal()] = fieldType;
            pnlCentral.add(fieldType);
        }

        pnlTypeSpecific = new JPanel();
        pnlTypeSpecific.setBackground(ACCENT_COLOR);
        pnlCentral.add(pnlTypeSpecific);

        fields[BillsFields.TYPE.ordinal()] = createTypeSpecificFields(selectedExpense);
        if (fields[BillsFields.TYPE.ordinal()] != null)
            pnlTypeSpecific.add(fields[BillsFields.TYPE.ordinal()]);

        super.refresh();
    }

    private JComponent createTypeSpecificFields(Expense exp) {
        boolean expenseSelected = (exp != null);
        ExpenseType type = expenseSelected ? exp.getType() : ExpenseType.FIXED;

        pnlTypeSpecific.removeAll();
        pnlTypeSpecific.setLayout(new BoxLayout(pnlTypeSpecific, BoxLayout.X_AXIS));
        JLabel lbl;

        switch (type) {
            case FIXED:
                if (!expenseSelected)
                    return null;

                btnValueHistory = Components.createButton("Value History", ACCENT_COLOR, BACKGROUND_COLOR, null);
                btnValueHistory.addActionListener(e -> {
                    new ValueHistory(this, "Value History", (Fixed) exp).run();
                });
                return btnValueHistory;

            case ONETIME:
                lbl = new JLabel("Has been paid?");
                lbl.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
                pnlTypeSpecific.add(lbl);

                oneTimeCheckBox = new JCheckBox();
                oneTimeCheckBox.setBackground(ACCENT_COLOR);
                oneTimeCheckBox.setEnabled(false);
                oneTimeCheckBox.setSelected(((OneTime) exp).isPaid());
                return oneTimeCheckBox;

            case RECURRING:
                lbl = new JLabel("End date: ");
                lbl.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
                pnlTypeSpecific.add(lbl);

                recurringEndDateField = new JFormattedTextField(Dates.dateFormatter);

                Date dateEnd = Dates.convLocalDate(((Recurring) exp).getEndDate());

                recurringEndDateField.setValue(dateEnd);

                Misc.defineSize(recurringEndDateField, DEFAULT_FIELD_SIZE);

                recurringEndDateField.setEnabled(true);
                recurringEndDateField.setBackground(ACCENT_COLOR);
                recurringEndDateField.setEditable(false);

                return recurringEndDateField;

            default:
                return null;
        }
    }

    private void removeExpense(ActionEvent action) {
        Expense selectedExpense = (Expense) expensesComboBox.getSelectedItem();
        if (selectedExpense == null)
            return;

        expensesManager.removeExpense(selectedExpense);
        expensesComboBox.removeItem(selectedExpense);
        expensesComboBox.setSelectedIndex(-1);
        createBillFields(null);
    }

    private void changeExpense(ActionEvent action) {
        Alerts.clearErrorMessage(alertPanel);

        Expense originalExpense = expensesComboBox.getItemAt(expensesComboBox.getSelectedIndex());
        if (originalExpense == null) {
            Alerts.showErrorMessage(alertPanel, "No expense selected!");
            return;
        }

        if (isEditing) {
            if (!applyChanges())
                return;

            btnChangeExpense.setText("Edit Expense");
            expensesComboBox.setEditable(true);
            isEditing = false;
        } else {
            setFieldsEditable(true);
            btnChangeExpense.setText("Apply Changes");
            expensesComboBox.setEditable(false);
            isEditing = true;
        }
    }

    private void setFieldsEditable(boolean isEditable) {
        for (BillsFields field : BillsFields.values()) {
            if (field == BillsFields.TYPE)
                continue;

            if (field == BillsFields.ESSENTIAL) {
                fields[field.ordinal()].setEnabled(isEditable);
                continue;
            }

            ((JTextField) fields[field.ordinal()]).setEditable(isEditable);
        }
        ExpenseType type = ((Expense) expensesComboBox.getSelectedItem()).getType();
        switch (type) {
            case ONETIME:
                oneTimeCheckBox.setEnabled(isEditable);
                break;
            case RECURRING:
                recurringEndDateField.setEditable(isEditable);
                break;
            default:
                break;
        }
    }

    private boolean applyChanges() {
        Expense originalExpense = expensesComboBox.getItemAt(expensesComboBox.getSelectedIndex());
        ExpenseType type = originalExpense.getType();
        Expense exp = null;

        JFormattedTextField valueField = (JFormattedTextField) fields[BillsFields.VALUE.ordinal()];
        Alerts.setErrorBorder(valueField, false);

        double value = 0;
        try {
            value = NumberFormat.getCurrencyInstance().parse(valueField.getText()).doubleValue();
        } catch (Exception e) {
            return false;
        }
        if (value <= 0) {
            Alerts.showErrorMessage(alertPanel, "Value must be greater than 0!");
            Alerts.setErrorBorder(valueField, true);
            return false;
        }

        JCheckBox essentialCheckBox = (JCheckBox) fields[BillsFields.ESSENTIAL.ordinal()];
        boolean essential = essentialCheckBox.isSelected();

        JFormattedTextField dateField = (JFormattedTextField) fields[BillsFields.DATE.ordinal()];
        LocalDate date = Dates.convDate(Dates.convString(dateField.getText()));

        JTextField descriptionField = (JTextField) fields[BillsFields.DESCRIPTION.ordinal()];
        Alerts.setErrorBorder(descriptionField, false);

        String description = descriptionField.getText();
        if (description.isEmpty()) {
            Alerts.showErrorMessage(alertPanel, "Description cannot be empty!");
            Alerts.setErrorBorder(descriptionField, true);
            return false;
        }

        switch (type) {
            case FIXED:
                exp = new Fixed(value, essential, date, description);
                break;
            case ONETIME:
                boolean paid = oneTimeCheckBox.isSelected();
                exp = new OneTime(value, essential, date, description, paid);
                break;
            case RECURRING:
                LocalDate endDate = Dates.convDate(Dates.convString(recurringEndDateField.getText()));
                exp = new Recurring(value, essential, date, description, endDate);
                break;
            default:
                break;
        }

        if (exp != null) {
            Alerts.showMessage(alertPanel, "Expense Modified successfully!", BACKGROUND_COLOR);
            expensesManager.removeExpense(originalExpense);
            expensesManager.addExpense(exp);
            setFieldsEditable(false);
            refresh(exp);
            return true;
        } else {
            Alerts.showErrorMessage(alertPanel, "Expense could not be modified!");
            return false;
        }
    }
}
