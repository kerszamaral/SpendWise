package SpendWise.Managers;

import java.util.ArrayList;
import java.time.LocalDate;
import SpendWise.Bills.*;
import SpendWise.Utils.ExpenseType;

public class ExpensesManager {
    private ArrayList<Expense> expenses;

    public ExpensesManager() {
        this.expenses = new ArrayList<Expense>();
    }

    public boolean createExpense(double value, boolean isEssential, LocalDate date, String description,
            ExpenseType type,
            LocalDate endDate) {
        Expense exp;
        switch (type) {
            case FIXED:
                exp = new Fixed(value, isEssential, date, description);
                expenses.add(exp);
                return true;

            case ONETIME:
                exp = new OneTime(value, isEssential, date, description, false);
                expenses.add(exp);
                return true;

            case RECURRING:
                if (endDate == null) {
                    return false;
                }
                exp = new Recurring(value, isEssential, date, description, endDate);
                expenses.add(exp);
                return true;

            default:
                return false;
        }
    }

    public void removeExpense(Expense exp) {
        expenses.remove(exp);
    }

    public double calculateTotalExpense() {
        double totalExpenses = 0;
        for (Expense exp : expenses) {
            totalExpenses += exp.calculatePayment();
        }
        return totalExpenses;
    }

    public double calculateEssentialExpenses() {
        double totalEssentialExpenses = 0;
        for (Expense exp : expenses) {
            if (exp.isEssential()) {
                totalEssentialExpenses += exp.calculatePayment();
            }
        }
        return totalEssentialExpenses;
    }

    public double calculateNonEssentialExpenses() {
        double totalNonEssentialExpenses = 0;
        for (Expense exp : expenses) {
            if (!exp.isEssential()) {
                totalNonEssentialExpenses += exp.calculatePayment();
            }
        }
        return totalNonEssentialExpenses;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }
}
