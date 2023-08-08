package SpendWise.Graphics.Menus;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import SpendWise.Bills.Expense;
import SpendWise.Bills.OneTime;
import SpendWise.Graphics.Screen;
import SpendWise.Managers.ExpensesManager;
import SpendWise.Utils.GraphicsUtils;
import SpendWise.Utils.Enums.ExpenseType;
import SpendWise.Utils.Enums.PanelOrder;

public class BillCreator extends Screen {
    private ExpensesManager expensesManager;
    private JPanel pnlCentral;

    private JFormattedTextField valueField;
    private JCheckBox essentialCheckBox;
    private JFormattedTextField dateField;
    private JTextField descriptionField;
    private JComboBox<ExpenseType> typeSelector;
    private JPanel pnlTypeSpecific;

    private JCheckBox onetimeCheckBox;

    public BillCreator(ExpensesManager expensesManager) {
        this.expensesManager = expensesManager;
        this.initialize();
    }

    @Override
    protected void initialize() {
        super.initialize();
        pnlCentral = super.getBlankPanel(PanelOrder.CENTRAL);
        pnlCentral.setBackground(BACKGROUND_COLOR);
        pnlCentral.setLayout(new GridLayout(8, 2));

        this.createValueField();
        this.createEssentialButton();
        this.createDateSelector();
        this.createDescriptionField();
        this.createTypeSelector();

        pnlTypeSpecific = new JPanel();
        pnlTypeSpecific.setBackground(BACKGROUND_COLOR);
        pnlCentral.add(pnlTypeSpecific);

        this.createTypeSpecificFields(null);
        GraphicsUtils.createButton(this.getBlankPanel(PanelOrder.SOUTH), "Submit", e -> submit(e));
    }

    private void createValueField() {
        JLabel lbl = new JLabel("Value:");
        pnlCentral.add(lbl);

        valueField = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        valueField.setValue(Double.valueOf(0.0));
        valueField.setColumns(10);
        valueField.setPreferredSize(new Dimension(100, valueField.getPreferredSize().height));
        pnlCentral.add(valueField);
    }

    private void createEssentialButton() {
        JLabel lbl = new JLabel("Essential:");
        pnlCentral.add(lbl);

        essentialCheckBox = new JCheckBox();
        essentialCheckBox.setBackground(BACKGROUND_COLOR);
        essentialCheckBox.setPreferredSize(new Dimension(100, essentialCheckBox.getPreferredSize().height));
        pnlCentral.add(essentialCheckBox);
    }

    private void createDateSelector() {
        JLabel lbl = new JLabel("Date:");
        pnlCentral.add(lbl);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateField = new JFormattedTextField(dateFormat);
        dateField.setValue(new java.util.Date());
        dateField.setColumns(10);
        dateField.setPreferredSize(new Dimension(100, dateField.getPreferredSize().height));
        pnlCentral.add(dateField);
    }

    private void createDescriptionField() {
        JLabel lbl = new JLabel("Description:");
        pnlCentral.add(lbl);

        descriptionField = new JTextField();
        descriptionField.setColumns(10);
        descriptionField.setPreferredSize(new Dimension(100, descriptionField.getPreferredSize().height));
        pnlCentral.add(descriptionField);
    }

    private void createTypeSelector() {
        JLabel lbl = new JLabel("Type:");
        pnlCentral.add(lbl);

        typeSelector = new JComboBox<ExpenseType>(ExpenseType.values());
        typeSelector.setPreferredSize(new Dimension(100, typeSelector.getPreferredSize().height));
        typeSelector.setSelectedIndex(0);
        typeSelector.addActionListener(e -> createTypeSpecificFields(e));
        pnlCentral.add(typeSelector);
    }

    private void createTypeSpecificFields(ActionEvent e) {
        ExpenseType type = (ExpenseType) typeSelector.getSelectedItem();
        pnlTypeSpecific.removeAll();
        switch (type) {
            case FIXED:
                this.createFixedFields();
                break;
            case ONETIME:
                this.createOnetimeFields();
                break;
            case RECURRING:
                this.createRecurringFields();
                break;
            default:
                break;
        }
        this.refresh();
    }

    private void createFixedFields() {
        JLabel lbl = new JLabel("Fixed:");
        pnlTypeSpecific.add(lbl);

        JCheckBox fixedCheckBox = new JCheckBox();
        fixedCheckBox.setBackground(BACKGROUND_COLOR);
        pnlTypeSpecific.add(fixedCheckBox);
    }

    private void createOnetimeFields() {
        pnlTypeSpecific.setLayout(new BoxLayout(pnlTypeSpecific, BoxLayout.X_AXIS));
        JLabel lbl = new JLabel("Has been paid?");
        lbl.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        pnlTypeSpecific.add(lbl);

        onetimeCheckBox = new JCheckBox();
        onetimeCheckBox.setBackground(BACKGROUND_COLOR);
        pnlTypeSpecific.add(onetimeCheckBox);
    }

    private void createRecurringFields() {
        JLabel lbl = new JLabel("Recurring:");
        pnlTypeSpecific.add(lbl);

        JCheckBox recurringCheckBox = new JCheckBox();
        recurringCheckBox.setBackground(BACKGROUND_COLOR);
        pnlTypeSpecific.add(recurringCheckBox);
    }

    private void submit(ActionEvent action) {
        ExpenseType type = (ExpenseType) typeSelector.getSelectedItem();
        Expense exp = null;

        double value = 0;
        try {
            value = NumberFormat.getCurrencyInstance().parse(valueField.getText()).doubleValue();
        } catch (Exception e) {
            return;
        }

        boolean essential = essentialCheckBox.isSelected();

        LocalDate date = null;
        try {
            date = LocalDate.parse(dateField.getText());
        } catch (Exception e) {
            return;
        }

        String description = descriptionField.getText();

        switch (type) {
            case FIXED:
                this.createFixedFields();
                break;
            case ONETIME:
                boolean paid = onetimeCheckBox.isSelected();
                exp = new OneTime(value, essential, date, description, paid);
                break;
            case RECURRING:
                this.createRecurringFields();
                break;
            default:
                break;
        }

        if (exp != null) {
            expensesManager.addExpense(exp);
        }
    }
}
