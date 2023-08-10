package Tests.Bills;

import org.junit.jupiter.api.*;

import SpendWise.Logic.Bills.Recurring;

import static org.junit.Assert.*;
import java.time.LocalDate;

public class RecurringTest {
    private Recurring recurring;

    @BeforeEach
    public void setUpClass() {
        recurring = new Recurring(500, false, LocalDate.now().minusMonths(3), "description",
                LocalDate.now().plusMonths(2));
    }

    @Test
    public void testRecurring() {
        assertNotNull(recurring);
    }

    @Test
    public void testgetEndDate() {
        assertEquals(LocalDate.now().plusMonths(2), recurring.getEndDate());
    }

    @Test
    public void testsetEndDate() {
        recurring.setEndDate(LocalDate.now().plusMonths(3));
        assertEquals(LocalDate.now().plusMonths(3), recurring.getEndDate());
    }

    @Test
    public void testtotalValue() {
        assertEquals(1000, recurring.totalValue(), 0.001);
    }

    @Test
    public void testcalculatePayment() {
        assertEquals(500, recurring.calculatePayment(), 0.001);
    }

    @Test
    public void testcalculatePaymentZero() {
        recurring.setEndDate(LocalDate.now().minusMonths(1));
        assertEquals(0, recurring.calculatePayment(), 0.001);
    }

    @Test
    public void testtotalValueZero() {
        recurring.setEndDate(LocalDate.now().minusMonths(1));
        assertEquals(0, recurring.totalValue(), 0.001);
    }

    @Test
    public void testtotalPayed() {
        assertEquals(1500, recurring.totalPayed(), 0.001);
    }

    @Test
    public void testtotalPayedZero() {
        recurring.setEndDate(LocalDate.now().minusMonths(3));
        assertEquals(0, recurring.totalPayed(), 0.001);
    }

    @Test
    public void testtotalCost() {
        assertEquals(2500, recurring.totalCost(), 0.001);
    }
}
