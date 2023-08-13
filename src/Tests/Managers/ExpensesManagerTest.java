package Tests.Managers;

import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

import SpendWise.Logic.Bills.Expense;
import SpendWise.Logic.Bills.Fixed;
import SpendWise.Logic.Managers.ExpensesManager;

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
    // TODO finish the tests
}
