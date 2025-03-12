package com.trios.dianatpizzaapp_test3b;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PizzaOrderControllerTest {

    @Test
    void testCalculateTotalBill_XLWith3Toppings() {
        PizzaOrderController controller = new PizzaOrderController();
        double expected = (15.0 + (3 * 1.5)) * 1.15;  // (15 + 4.5) * 1.15
        double actual = controller.calculateTotalBill("XL", 3);
        assertEquals(expected, actual, 0.01);
    }

    @Test
    void testCalculateTotalBill_LWith2Toppings() {
        PizzaOrderController controller = new PizzaOrderController();
        double expected = (12.0 + (2 * 1.5)) * 1.15;  // (12 + 3) * 1.15
        double actual = controller.calculateTotalBill("L", 2);
        assertEquals(expected, actual, 0.01);
    }

    @Test
    void testCalculateTotalBill_MWith0Toppings() {
        PizzaOrderController controller = new PizzaOrderController();
        double expected = (10.0 + (0 * 1.5)) * 1.15;  // (10 + 0) * 1.15
        double actual = controller.calculateTotalBill("M", 0);
        assertEquals(expected, actual, 0.01);
    }

    @Test
    void testCalculateTotalBill_InvalidSize() {
        PizzaOrderController controller = new PizzaOrderController();
        double expected = (0.0 + (2 * 1.5)) * 1.15;  // (0 + 3) * 1.15
        double actual = controller.calculateTotalBill("XXL", 2);
        assertEquals(expected, actual, 0.01);
    }
}
