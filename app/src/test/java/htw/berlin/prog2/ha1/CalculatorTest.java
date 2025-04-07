package htw.berlin.prog2.ha1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.checkerframework.checker.units.qual.C;

@DisplayName("Retro calculator")
class CalculatorTest {

    static final int MAX_DECIMAL_COUNT = 10;

    @Test
    @DisplayName("should display result after adding two positive multi-digit numbers")
    void testPositiveAddition() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(2);
        calc.pressDigitKey(0);
        calc.pressBinaryOperationKey("+");
        calc.pressDigitKey(2);
        calc.pressDigitKey(0);
        calc.pressEqualsKey();

        String expected = "40";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should display result after getting the square root of two")
    void testSquareRoot() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(2);
        calc.pressUnaryOperationKey("√");

        String expected = "1.41421356";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should display error when dividing by zero")
    void testDivisionByZero() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(7);
        calc.pressBinaryOperationKey("/");
        calc.pressDigitKey(0);
        calc.pressEqualsKey();

        String expected = "Error";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should display error when drawing the square root of a negative number")
    void testSquareRootOfNegative() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(7);
        calc.pressNegativeKey();
        calc.pressUnaryOperationKey("√");

        String expected = "Error";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should not allow multiple decimal dots")
    void testMultipleDecimalDots() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(1);
        calc.pressDotKey();
        calc.pressDigitKey(7);
        calc.pressDotKey();
        calc.pressDigitKey(8);

        String expected = "1.78";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }

    void randomActions(Calculator calc, int count) {
        for (int i = 0; i < count; i++) {
            int random = (int) (Math.random() * 10) % 3;
            switch (random) {
                case 0:
                    int randomNumber = (int) (Math.random() * 10);
                    calc.pressDigitKey(randomNumber);
                    break;
                case 1:
                    calc.pressDotKey();
                    break;
                case 2:
                    calc.pressNegativeKey();
                    break;
            }
        }
    }

    void pressRandomNubers(Calculator calc, int count) {
        for (int i = 0; i < count; i++) {
            int random = (int) (Math.random() * 9);

            calc.pressDigitKey(random);
        }
    }

    @Test
    @DisplayName("screen clears")
    void testClearScreen() {
        var calc = new Calculator();

        pressRandomNubers(calc, 10);
        calc.pressDotKey();
        pressRandomNubers(calc, 10);
        calc.pressClearKey();

        var actual = calc.readScreen();
        String expected = "0";

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Test big numbers")
    void testBigNumbers() {
        var calc = new Calculator();
        pressRandomNubers(calc, 1000);
        calc.pressBinaryOperationKey("+");
        pressRandomNubers(calc, 1000);
    }

    @Test
    @DisplayName("Removed decimal zero (1.0 -> 1)")
    void testRemoveZeroAtEnd() {
        var calc = new Calculator();

        calc.pressDigitKey(1);
        calc.pressDotKey();
        calc.pressDigitKey(0);
        calc.pressBinaryOperationKey("*");
        calc.pressDigitKey(1);

        var actual = calc.readScreen();
        var expected = "1";

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test maximal decimal count")
    void testMaxDecimalCount() {
        var calc = new Calculator();

        calc.pressDigitKey(1);
        calc.pressDotKey();
        pressRandomNubers(calc, MAX_DECIMAL_COUNT + 10);

        var out = calc.readScreen();
        var actual = out.length();
        var expected = MAX_DECIMAL_COUNT + 2;

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("test maximal digit count")
    void testMaxDigitCount() {
        var calc = new Calculator();

        pressRandomNubers(calc, 9);

        var out = calc.readScreen();
        var actual = out.length();
        var expected = MAX_DECIMAL_COUNT + 2;

        assertEquals(expected, actual);

    }

}
