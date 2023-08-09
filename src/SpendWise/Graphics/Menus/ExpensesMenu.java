package SpendWise.Graphics.Menus;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.*;

import SpendWise.Graphics.Screen;
import SpendWise.Bills.Expense;
import SpendWise.Bills.OneTime;
import SpendWise.Bills.Recurring;
import SpendWise.Managers.ExpensesManager;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.BillsFields;
import SpendWise.Utils.Enums.ExpenseType;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.Components;
import SpendWise.Utils.Graphics.Misc;
import SpendWise.Utils.Graphics.Panels;

public class ExpensesMenu extends Screen {
    private ExpensesManager expensesManager;
    private JComboBox<Expense> expensesComboBox;

    private JPanel pnlCentral;
    private JPanel pnlTypeSpecific;

    private JComponent[] fields;
    final private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private JCheckBox oneTimeCheckBox;
    private JFormattedTextField recurringEndDateField;

    public ExpensesMenu(ExpensesManager expensesManager) {
        this.expensesManager = expensesManager;
        this.initialize();
    }

    @Override
    protected void initialize() {
        Offsets outerOffsets = new Offsets(50, 50, 100, 0);
        Offsets innerOffsets = new Offsets(50, 50, 50, 50);
        blankPanels = Panels.createPanelWithCenter(this, innerOffsets, outerOffsets, ACCENT_COLOR);

        expensesComboBox = new JComboBox<Expense>(expensesManager.getExpenses().toArray(new Expense[0]));
        expensesComboBox.addActionListener(e -> createBillFields(e));
        Dimension comboBoxSize = new Dimension(450, 30);
        Misc.defineSize(expensesComboBox, comboBoxSize);
        getBlankPanel(PanelOrder.NORTH).add(expensesComboBox);

        pnlCentral = super.getBlankPanel(PanelOrder.CENTRAL);
        createBillFields(null);

        Offsets offsetsBtn = new Offsets(10, 10, 400, 20);
        Components.initializeButton(getBlankPanel(PanelOrder.SOUTH), offsetsBtn, "Removed Expense", ACCENT_COLOR,
                e -> removeExpense(e));
    }

    @Override
    public void refresh() {
        expensesComboBox.removeAllItems();
        for (Expense expense : expensesManager.getExpenses()) {
            expensesComboBox.addItem(expense);
        }
        expensesComboBox.setSelectedIndex(-1);
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
        Date defaultDate = Date.from(temp.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

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
                    JFormattedTextField dateField = new JFormattedTextField(dateFormatter);
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

            fieldType.setEnabled(true);
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
        boolean expenseSelected = (exp == null);
        ExpenseType type = expenseSelected ? ExpenseType.FIXED : exp.getType();

        pnlTypeSpecific.removeAll();
        pnlTypeSpecific.setLayout(new BoxLayout(pnlTypeSpecific, BoxLayout.X_AXIS));
        JLabel lbl;

        switch (type) {
            case FIXED:
                // Has no new fields;
                return null;

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

                recurringEndDateField = new JFormattedTextField(dateFormatter);

                Date dateEnd = Date.from(((Recurring) exp).getEndDate()
                        .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

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
}
