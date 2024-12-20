package pl.polsl.mostowska.integrationwebapp.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the QuadraticFunction class.
 * Tests cover correct, incorrect, and boundary scenarios.
 *
 * @author Wiktoria Mostowska
 * @version 1.0
 */
public class QuadraticFunctionTest {
    private QuadraticFunction quadratic;
    
    public QuadraticFunctionTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        // Initialize a sample quadratic function f(x) = 2x² + x + 1
        quadratic = new QuadraticFunction(2,1,1);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test for valid inputs to the evaluate method.
     * Tests the quadratic function formula f(x) = ax² + bx + c with valid inputs.
     * 
     * @param a Coefficient for the quadratic term (x²).
     * @param b Coefficient for the linear term (x).
     * @param c Constant term.
     * @param x Value of x to evaluate.
     * @param expected Expected result.
     */
    @ParameterizedTest
    @DisplayName("Test quadratic function with valid inputs")
    @CsvSource({
        "2, 1, 1, 1, 4.0",   // f(x) = 2x² + x + 1, x = 1
        "-2, 5, -1, 2, 1.0", // f(x) = -2x² + 5x - 1, x = 2
        "0, 10, 3, -5, -47.0"  // f(x) = 10, x = -5
    })
    public void testEvaluateWithValidInputs(double a, double b, double c, double x, double expected) {
        quadratic = new QuadraticFunction(a, b, c);
        double result = quadratic.evaluate(x);
        assertEquals(expected, result, 0.0001, 
            String.format("f(x) = %fx² + %fx + %f, f(%f) should be %f", a, b, c, x, expected));
    }

    /**
     * Test for edge cases in the evaluate method.
     * These cases include zero and constant quadratic functions.
     * 
     * @param a Coefficient for the quadratic term (x²).
     * @param b Coefficient for the linear term (x).
     * @param c Constant term.
     * @param x Value of x to evaluate.
     * @param expected Expected result.
     */
    @ParameterizedTest
    @DisplayName("Test edge cases for quadratic function")
    @CsvSource({
        "0, 0, 0, 0, 0.0",    // f(x) = 0 (zero function), x = 0
        "0, 0, 5, 100, 5.0",  // f(x) = 5 (constant function), x = 100
        "1, 0, 0, 2, 4.0"     // f(x) = x², x = 2
    })
    public void testEdgeCases(double a, double b, double c, double x, double expected) {
        quadratic = new QuadraticFunction(a, b, c);
        double result = quadratic.evaluate(x);
        assertEquals(expected, result, 0.0001, 
            String.format("Edge case failed for f(x) = %fx² + %fx + %f, f(%f) should be %f", a, b, c, x, expected));
    }

    /**
     * Test for invalid inputs that should throw exceptions.
     * These cases validate error handling in the constructor or evaluate method.
     * 
     * @param a Coefficient for the quadratic term (x²).
     * @param b Coefficient for the linear term (x).
     * @param c Constant term.
     * @param x Value of x to evaluate.
     */
    @ParameterizedTest
    @DisplayName("Test invalid inputs for quadratic function")
    @CsvSource({
        "Infinity, 1, 1, 0",  // Invalid coefficient for a (Infinity)
        "1, NaN, 1, 0",       // Invalid coefficient for b (NaN)
        "1, 1, 1, NaN"        // Invalid x value for evaluation (NaN)
    })
    public void testInvalidInputs(double a, double b, double c, double x) {
        quadratic = new QuadraticFunction(a, b, c);
        if (Double.isInfinite(a) || Double.isNaN(b) || Double.isNaN(c)) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> quadratic.evaluate(x), 
                String.format("Invalid coefficients: a = %f, b = %f, c = %f", a, b, c));
            assertEquals("Coefficients a, b, and c cannot be NaN or infinite.", exception.getMessage());
        } else if (Double.isNaN(x)) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> quadratic.evaluate(x), 
                String.format("Invalid x value: %f for f(x) = %fx² + %fx + %f", x, a, b, c));
            assertEquals("x value cannot be NaN or infinite.", exception.getMessage());
        }
    }
}
