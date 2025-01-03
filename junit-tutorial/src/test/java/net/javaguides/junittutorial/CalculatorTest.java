package net.javaguides.junittutorial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    public void addTest() {
        Calculator calculator = new Calculator();
        int actualResult = calculator.add(1, 2);

        assertEquals(3, actualResult);
    }

}