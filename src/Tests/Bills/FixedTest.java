package Tests.Bills;

import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import java.time.LocalDate;
import SpendWise.Bills.Fixed;

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
    public void testValueInMonth() {
        assertEquals(500, fixed.valueInMonth(LocalDate.now()), 0.001);
    }

    @Test
    public void testUpdateValue() {
        fixed.updateValue(1000);
        assertEquals(2000, fixed.totalValue(LocalDate.now().plusMonths(1)), 0.001);
    }

    @Test
    public void testCalculatePayment() {
        assertEquals(500, fixed.calculatePayment(), 0.001);
    }

    @Test
    public void testTotalValueDateBeforeStart() {
        assertEquals(0, fixed.totalValue(LocalDate.now().minusMonths(4)), 0.001);
    }

}
