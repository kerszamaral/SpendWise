package Tests.Managers;

import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

import SpendWise.Bills.Expense;
import SpendWise.Bills.Fixed;
import SpendWise.Managers.ExpensesManager;
import SpendWise.Utils.Enums.ExpenseType;

public class ExpensesManagerTest {
    private ExpensesManager expensesManager;

    @BeforeEach
    public void setUpClass() {
        expensesManager = new ExpensesManager();
    }

    @Test
    public void testExpensesManager() {
        assertNotNull(expensesManager);
    }

    @Test
    public void testAddExpense() {
        Expense exp = new Fixed(100, true, LocalDate.now(), "test");
        expensesManager.addExpense(exp);
        assertEquals(1, expensesManager.getExpenses().size());
    }

    @Test
    public void testCreateFixedExpense() {
        expensesManager.createFixedExpense(100, true, LocalDate.now(), "test");
        assertEquals(1, expensesManager.getExpenses().size());
    }

    @Test
    public void testCreateOneTimeExpense() {
        expensesManager.createOneTimeExpense(100, true, LocalDate.now(), "test", false);
        assertEquals(1, expensesManager.getExpenses().size());
    }

    @Test
    public void testCreateRecurringExpense() {
        expensesManager.createRecurringExpense(100, true, LocalDate.now(), "test", LocalDate.now());
        assertEquals(1, expensesManager.getExpenses().size());
    }

    @Test
    public void testCreateExpense() {
        expensesManager.createExpense(100, true, LocalDate.now(), "test", ExpenseType.ONETIME, false, null);
        assertEquals(1, expensesManager.getExpenses().size());
    }

    @Test
    public void testRemoveExpense() {
        expensesManager.createExpense(100, true, LocalDate.now(), "test", ExpenseType.ONETIME, false, null);
        expensesManager.removeExpense(expensesManager.getExpenses().get(0));
        assertEquals(0, expensesManager.getExpenses().size());
    }

    @Test
    public void testGetExpenses() {
        expensesManager.createExpense(100, true, LocalDate.now(), "test", ExpenseType.ONETIME, false, null);
        assertEquals(1, expensesManager.getExpenses().size());
    }

    @Test
    public void testCalculateTotalExpenses() {
        expensesManager.createExpense(100, true, LocalDate.now(), "test", ExpenseType.ONETIME, false, null);
        assertEquals(100, expensesManager.calculateTotalExpense(), 0.01);
    }

    @Test
    public void testCalculateNonEssentialExpenses() {
        expensesManager.createExpense(100, false, LocalDate.now(), "test", ExpenseType.ONETIME, false, null);
        assertEquals(100, expensesManager.calculateNonEssentialExpenses(), 0.01);
    }

    @Test
    public void testCalculateEssentialExpenses() {
        expensesManager.createExpense(100, true, LocalDate.now(), "test", ExpenseType.ONETIME, false, null);
        assertEquals(100, expensesManager.calculateEssentialExpenses(), 0.01);
    }

}
