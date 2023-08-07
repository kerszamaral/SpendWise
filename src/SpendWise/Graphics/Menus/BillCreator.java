package SpendWise.Graphics.Menus;

import SpendWise.Graphics.Screen;
import SpendWise.Managers.ExpensesManager;

public class BillCreator extends Screen {
    private ExpensesManager expensesManager;

    // TODO implement BillCreator
    public BillCreator(ExpensesManager expensesManager) {
        this.expensesManager = expensesManager;
        this.initialize();
    }

    @Override
    protected void initialize() {
        super.initialize();
    }
}
