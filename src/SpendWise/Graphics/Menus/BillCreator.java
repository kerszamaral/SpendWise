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

import SpendWise.Bills.*;
import SpendWise.Graphics.Screen;
import SpendWise.Managers.ExpensesManager;
import SpendWise.Utils.GraphicsUtils;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.BillsFields;
import SpendWise.Utils.Enums.ExpenseType;
import SpendWise.Utils.Enums.PanelOrder;

public class BillCreator extends Screen {
    private ExpensesManager expensesManager;
    private JPanel pnlCentral;

    private JComponent[] fields;
    private JComboBox<String> typeSelector;
    final private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    JPanel pnlTypeSpecific;

    private JCheckBox oneTimeCheckBox;
    private JFormattedTextField recurringEndDateField;

    public BillCreator(ExpensesManager expensesManager) {
        this.expensesManager = expensesManager;
        this.initialize();
    }

    @Override
    protected void initialize() {
        Offsets offsets = new Offsets(50, 50, 50, 50);
        blankPanels = GraphicsUtils.createPanelWithCenter(this, offsets, ACCENT_COLOR);

        getBlankPanel(PanelOrder.CENTRAL).add(new JLabel("Create a new bill"));

        pnlCentral = super.getBlankPanel(PanelOrder.CENTRAL);
        pnlCentral.setLayout(new GridLayout(BillsFields.values().length * 2 + 1, 1));



        fields = new JComponent[BillsFields.values().length];

        for (BillsFields field : BillsFields.values()) {
            String label = field.getLabel() + ": ";
            JLabel lbl = new JLabel(label);
            pnlCentral.add(lbl);

            JComponent fieldType = null;
            switch (field) {
                case VALUE:
                    JFormattedTextField valueField = new JFormattedTextField(NumberFormat.getCurrencyInstance());
                    valueField.setValue(Double.valueOf(0.0));
                    valueField.setColumns(10);
                    fieldType = valueField;
                    break;

                case ESSENTIAL:
                    JCheckBox essentialCheckBox = new JCheckBox();
                    fieldType = essentialCheckBox;
                    break;

                case DATE:
                    JFormattedTextField dateField = new JFormattedTextField(dateFormatter);
                    dateField.setValue(new java.util.Date());
                    fieldType = dateField;
                    break;

                case DESCRIPTION:
                    JTextField descriptionField = new JTextField();
                    fieldType = descriptionField;
                    break;

                case TYPE:
                    String[] types = new String[ExpenseType.values().length];
                    for (ExpenseType type : ExpenseType.values()) {
                        types[type.ordinal()] = type.getType();
                    }
                    typeSelector = new JComboBox<String>(types);
                    typeSelector.addActionListener(e -> createTypeSpecificFields(e));
                    fieldType = typeSelector;
                    break;

                default:
                    fieldType = new JTextField();
                    break;
            }

            GraphicsUtils.defineSize(fieldType, DEFAULT_FIELD_SIZE);

            fieldType.setEnabled(true);
            fieldType.setBackground(ACCENT_COLOR);

            fields[field.ordinal()] = fieldType;
            pnlCentral.add(fieldType);
        }

        pnlTypeSpecific = new JPanel();
        pnlTypeSpecific.setBackground(ACCENT_COLOR);
        pnlCentral.add(pnlTypeSpecific);

        Offsets offsetsBtn = new Offsets(10, 10, 400, 20);
        GraphicsUtils.createButton(this.getBlankPanel(PanelOrder.SOUTH), offsetsBtn,
                "Submit", ACCENT_COLOR,
                e -> submit(e));
    }

    private void createTypeSpecificFields(ActionEvent e) {
        ExpenseType type = ExpenseType.values()[typeSelector.getSelectedIndex()];

        pnlTypeSpecific.removeAll();
        pnlTypeSpecific.setLayout(new BoxLayout(pnlTypeSpecific, BoxLayout.X_AXIS));
        JLabel lbl;

        switch (type) {
            case FIXED:
                // Has no new fields;
                break;

            case ONETIME:
                lbl = new JLabel("Has been paid?");
                lbl.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
                pnlTypeSpecific.add(lbl);

                oneTimeCheckBox = new JCheckBox();
                oneTimeCheckBox.setBackground(ACCENT_COLOR);
                pnlTypeSpecific.add(oneTimeCheckBox);
                break;

            case RECURRING:
                lbl = new JLabel("End date: ");
                lbl.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
                pnlTypeSpecific.add(lbl);

                recurringEndDateField = new JFormattedTextField(dateFormatter);
                recurringEndDateField.setValue(new java.util.Date());

                GraphicsUtils.defineSize(recurringEndDateField, DEFAULT_FIELD_SIZE);

                recurringEndDateField.setEnabled(true);
                recurringEndDateField.setBackground(ACCENT_COLOR);

                pnlTypeSpecific.add(recurringEndDateField);
                break;

            default:
                break;
        }

        this.refresh();
    }

    private void submit(ActionEvent action) {
        ExpenseType type = ExpenseType.values()[typeSelector.getSelectedIndex()];
        Expense exp = null;

        JFormattedTextField valueField = (JFormattedTextField) fields[BillsFields.VALUE.ordinal()];
        double value = 0;
        try {
            value = NumberFormat.getCurrencyInstance().parse(valueField.getText()).doubleValue();
        } catch (Exception e) {
            return;
        }

        JCheckBox essentialCheckBox = (JCheckBox) fields[BillsFields.ESSENTIAL.ordinal()];
        boolean essential = essentialCheckBox.isSelected();

        JFormattedTextField dateField = (JFormattedTextField) fields[BillsFields.DATE.ordinal()];
        LocalDate date = null;
        try {
            Date temp = dateFormatter.parse(dateField.getText());
            date = temp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            return;
        }

        JTextField descriptionField = (JTextField) fields[BillsFields.DESCRIPTION.ordinal()];
        String description = descriptionField.getText();

        switch (type) {
            case FIXED:
                exp = new Fixed(value, essential, date, description);
                break;
            case ONETIME:
                boolean paid = oneTimeCheckBox.isSelected();
                exp = new OneTime(value, essential, date, description, paid);
                break;
            case RECURRING:
                LocalDate endDate = null;
                try {
                    Date temp = dateFormatter.parse(recurringEndDateField.getText());
                    endDate = temp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                } catch (Exception e) {
                    return;
                }
                exp = new Recurring(value, essential, date, description, endDate);
                break;
            default:
                break;
        }

        if (exp != null) {
            expensesManager.addExpense(exp);
        }

        for (Expense e : expensesManager.getExpenses()) {
            System.out.println(e.toString());
        }
    }
}
