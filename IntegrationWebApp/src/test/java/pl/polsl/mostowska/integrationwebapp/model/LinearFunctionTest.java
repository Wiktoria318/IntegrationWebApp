package pl.polsl.mostowska.integrationwebapp.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit tests for the LinearFunction class.
 * These tests cover all public methods, including correct, incorrect, and edge cases.
 * 
 * @author Wiktoria Mostowska
 * @version 1.0
 */
public class LinearFunctionTest {
    private LinearFunction linear;
    
    public LinearFunctionTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        linear = new LinearFunction(2,1);
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    /**
     * Test for valid inputs to the evaluate method.
     * Covers various linear functions and x-values to ensure the method calculates correctly.
     * @param b Coefficient for the linear term (x).
     * @param c Constant term.
     * @param x Value of x to evaluate
     * @param expected Expected value
     */
    @ParameterizedTest
    @DisplayName("Test linear function with valid inputs")
    @CsvSource({
        "2, 1, 1, 3.0",    // f(x) = 2x + 1, x = 1
        "-2, 5, 2, 1.0",   // f(x) = -2x + 5, x = 2
        "0, 10, -5, 10.0"  // f(x) = 10 (constant), x = -5
    })
    public void testEvaluateWithValidInputs(double b, double c, double x, double expected) {
        linear = new LinearFunction(b, c);
        double result = linear.evaluate(x);
        assertEquals(expected, result, 0.0001, 
            String.format("f(x) = %fx + %f, f(%f) should be %f", b, c, x, expected));
    }

    /**
     * Test for edge cases in the evaluate method.
     * These cases include the zero function and constant functions.
     * @param b Coefficient for the linear term (x).
     * @param c Constant term.
     * @param x Value of x to evaluate
     * @param expected Expected value
     */
    @ParameterizedTest
    @DisplayName("Test edge cases for linear function")
    @CsvSource({
        "0, 0, 0, 0.0",   // f(x) = 0 (zero function)
        "0, 5, 100, 5.0", // f(x) = 5 (constant function)
        "1, 0, 0, 0.0"    // f(x) = x, x = 0
    })
    public void testEdgeCases(double b, double c, double x, double expected) {
        linear = new LinearFunction(b, c);
        double result = linear.evaluate(x);
        assertEquals(expected, result, 0.0001, 
            String.format("Edge case failed for f(x) = %fx + %f, f(%f)", b, c, x));
    }
    
    /**
     * Test for invalid inputs that should throw exceptions.
     * These cases validate error handling in the constructor or evaluate method.
     * 
     * @param b Coefficient for the linear term (x).
     * @param c Constant term.
     * @param x Value of x to evaluate.
     */
    @ParameterizedTest
    @DisplayName("Test invalid inputs for linear function")
    @CsvSource({
        "Infinity, 1, 0",  // Invalid coefficient for b
        "1, NaN, 0",       // Invalid constant term
        "1, 1, NaN"        // Invalid x value for evaluation
    })
    public void testInvalidInputs(double b, double c, double x) {
        linear = new LinearFunction(b, c);
        if (Double.isInfinite(b) || Double.isNaN(c)) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> linear.evaluate(x), 
                String.format("Invalid coefficients: b = %f, c = %f", b, c));
            assertEquals("Coefficients b and c cannot be NaN or infinite.", exception.getMessage());
        } else if (Double.isNaN(x)) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> linear.evaluate(x), 
                String.format("Invalid x value: %f for f(x) = %fx + %f", x, b, c));
            assertEquals("x value cannot be NaN or infinite.", exception.getMessage());
        }
    }
}
