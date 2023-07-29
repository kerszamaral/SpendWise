package Tests.Managers;

import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import java.time.LocalDate;
import SpendWise.Utils.ExpenseType;
import SpendWise.Managers.ExpensesManager;

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
        expensesManager.createExpense(100, true, LocalDate.now(), "test", ExpenseType.ONETIME, null);
        assertEquals(1, expensesManager.getExpenses().size());
    }

    @Test
    public void testRemoveExpense() {
        expensesManager.createExpense(100, true, LocalDate.now(), "test", ExpenseType.ONETIME, null);
        expensesManager.removeExpense(expensesManager.getExpenses().get(0));
        assertEquals(0, expensesManager.getExpenses().size());
    }

    @Test
    public void testGetExpenses() {
        expensesManager.createExpense(100, true, LocalDate.now(), "test", ExpenseType.ONETIME, null);
        assertEquals(1, expensesManager.getExpenses().size());
    }

    @Test
    public void testCalculateTotalExpenses() {
        expensesManager.createExpense(100, true, LocalDate.now(), "test", ExpenseType.ONETIME, null);
        assertEquals(100, expensesManager.calculateTotalExpense(), 0.01);
    }

    @Test
    public void testCalculateNonEssentialExpenses() {
        expensesManager.createExpense(100, false, LocalDate.now(), "test", ExpenseType.ONETIME, null);
        assertEquals(100, expensesManager.calculateNonEssentialExpenses(), 0.01);
    }

    @Test
    public void testCalculateEssentialExpenses() {
        expensesManager.createExpense(100, true, LocalDate.now(), "test", ExpenseType.ONETIME, null);
        assertEquals(100, expensesManager.calculateEssentialExpenses(), 0.01);
    }

}
