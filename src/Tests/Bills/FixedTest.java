package Tests.Bills;

import org.junit.jupiter.api.*;

import SpendWise.Logic.Bills.Fixed;

import static org.junit.Assert.*;
import java.time.LocalDate;

public class FixedTest {
    private Fixed fixed;

    @BeforeEach
    public void setUpClass() {
        fixed = new Fixed(500, false, LocalDate.now().minusMonths(3), "description");
    }

    @Test
    public void testFixed() {
        assertNotNull(fixed);
    }

    @Test
    public void testTotalValue() {
        assertEquals(1500, fixed.totalValue(), 0.001);
    }

    @Test
    public void testTotalValueDate() {
        assertEquals(1000, fixed.totalValue(LocalDate.now()), 0.001);
    }

    @Test
    public void testIsDateInBetween() {
        LocalDate date = LocalDate.now();
        LocalDate startDate = date.minusMonths(3);
        LocalDate endDate = date.plusMonths(3);
        assertTrue(fixed.isDateInBetween(date, startDate, endDate));
        assertFalse(fixed.isDateInBetween(date, startDate, date.minusDays(1)));
        assertFalse(fixed.isDateInBetween(date, endDate.plusDays(1), endDate.plusMonths(3)));
    }

    @Test
    public void testValueInMonth() {
        assertEquals(500, fixed.valueInMonth(LocalDate.now()), 0.001);
    }

    @Test
    public void testValueInMonthDateBeforeStart() {
        assertEquals(0, fixed.valueInMonth(LocalDate.now().minusMonths(4)), 0.001);
    }

    @Test
    public void testUpdateValueDate() {
        fixed.updateValue(1000, LocalDate.now().plusMonths(1));
        assertEquals(2000, fixed.totalValue(LocalDate.now().plusMonths(1)), 0.001);
    }

    @Test
    public void testUpdateValue() {
        fixed.updateValue(1000);
        assertEquals(2000, fixed.totalValue(LocalDate.now().plusMonths(1)), 0.001);
    }

    @Test
    public void testGetLastDate() {
        assertEquals(LocalDate.now().minusMonths(3), fixed.getLastDate());
    }

    @Test
    public void testCalculatePayment() {
        assertEquals(500, fixed.calculatePayment(), 0.001);
    }

    @Test
    public void testTotalValueDateBeforeStart() {
        assertEquals(0, fixed.totalValue(LocalDate.now().minusMonths(4)), 0.001);
    }

    @Test
    public void testTotalValueDateAfterEnd() {
        assertEquals(1500, fixed.totalValue(LocalDate.now().plusMonths(4)), 0.001);
    }

    @Test
    public void testEquals() {
        Fixed fixed = new Fixed(500, false, LocalDate.now().minusMonths(3), "description");
        assertTrue(fixed.equals(this.fixed));
        fixed = new Fixed(500, false, LocalDate.now().minusMonths(3), "new description");
        assertFalse(fixed.equals(this.fixed));
    }
}
