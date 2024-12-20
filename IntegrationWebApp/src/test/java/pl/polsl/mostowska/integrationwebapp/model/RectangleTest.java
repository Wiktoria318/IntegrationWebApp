package pl.polsl.mostowska.integrationwebapp.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 * Unit tests for the Rectangle class implementing the rectangle method for numerical integration.
 * 
 * This class contains various test cases to validate the correctness, edge cases, 
 * and error handling of the Rectangle class.
 * 
 * @author Wiktoria Mostowska
 * @version 1.0
 */
public class RectangleTest {
    /** Instance of Rectangle for testing. */
    private Rectangle rectangle;
    
    public RectangleTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        rectangle = new Rectangle();
        // Example function: f(x) = x^2
        rectangle.setFunction(x -> x * x);
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    
    /**
     * Test correct integral calculation for various parameter sets.
     * 
     * @param a the lower bound of the interval
     * @param b the upper bound of the interval
     * @param n the number of partitions
     */
    @ParameterizedTest
    @DisplayName("Test correct integral calculation")
    @CsvSource({
        "0, 2, 20",  // Integral from 0 to 2 with 20 partitions
        "1, 3, 50",  // Integral from 1 to 3 with 50 partitions
        "2, 5, 100"  // Integral from 2 to 5 with 100 partitions
    })
    public void testCalculateCorrectValues(double a, double b, int n) {
        IntegrationParameters params = new IntegrationParameters(a, b, n);
        rectangle.setParameters(params);  // Set parameters for the test

        // Calculate the integral
        double result = rectangle.calculate();

        // Check if the result is valid (positive number)
        assertNotNull(result, "Result should not be null.");
        assertTrue(result >= 0, String.format("Result should be a positive number, but was %f", result));
    }

    /**
     * Test invalid partition count less or equal to 0 to verify exception handling.
     * 
     * @param n the number of partitions, which should be invalid
     */
    @ParameterizedTest
    @DisplayName("Test invalid partition count (<= 0)")
    @CsvSource({
        "0",  // 0 partitions
        "-5"  // Negative partition count
    })
    public void testCalculateWithInvalidPartitions(int n) {
        IntegrationParameters params = new IntegrationParameters(0, 2, n);
        rectangle.setParameters(params);

        // Test if the method throws an exception
        Executable executable = rectangle::calculate;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Number of partitions must be a valid positive number, not Infinity, NaN, or zero.", exception.getMessage());
    }

    /**
     * Test invalid bounds where the upper bound is less than the lower bound.
     * 
     * @param a the lower bound of the interval
     * @param b the upper bound of the interval, which should be less than the lower bound
     */
    @ParameterizedTest
    @DisplayName("Test invalid bounds (upper bound less than lower bound)")
    @CsvSource({
        "2, 1",  // Upper bound less than lower bound
        "-3, -5" // Negative bounds
    })
    public void testCalculateWithInvalidBounds(double a, double b) {
        IntegrationParameters params = new IntegrationParameters(a, b, 10);
        rectangle.setParameters(params);

        // Test if the method throws an exception
        Executable executable = rectangle::calculate;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Upper bound (b) cannot be less than lower bound (a).", exception.getMessage());
    }

    /**
     * Test calculation with a very large number of partitions.
     * 
     * @param n the number of partitions
     */
    @ParameterizedTest
    @DisplayName("Test for very large number of partitions")
    @CsvSource({
        "100000",  // Very large number of partitions
        "1000000"  // Even larger number of partitions
    })
    public void testCalculateWithLargePartitions(int n) {
        IntegrationParameters params = new IntegrationParameters(0, 2, n);
        rectangle.setParameters(params);

        // Calculate the integral
        double result = rectangle.calculate();

        // Check if the result is valid (positive number)
        assertNotNull(result, "Result should not be null.");
        assertTrue(result >= 0, "Result should be a positive number.");
    }

    /**
     * Test calculation with the smallest valid number of partitions (n=1).
     * 
     * @param n the number of partitions (minimum: 1)
     */
    @ParameterizedTest
    @DisplayName("Test for boundary number of partitions (n=1)")
    @CsvSource({
        "1"  // Minimum number of partitions
    })
    public void testCalculateWithBoundaryPartition(int n) {
        IntegrationParameters params = new IntegrationParameters(0, 2, n);
        rectangle.setParameters(params);

        // Calculate the integral
        double result = rectangle.calculate();

        // Check if the result is valid (positive number)
        assertNotNull(result, "Result should not be null.");
        assertTrue(result >= 0, "Result should be a positive number.");
    }
    
    /**
     * Test calculation for zero-width intervals (a == b).
     * 
     * @param a the lower bound of the interval
     * @param b the upper bound of the interval (equal to the lower bound)
     * @param n the number of partitions
     */
    @ParameterizedTest
    @DisplayName("Test zero-width interval (a == b)")
    @CsvSource({
        "2, 2, 10",  // Zero-width interval
        "5, 5, 50"   // Another zero-width interval
    })
    public void testZeroWidthInterval(double a, double b, int n) {
        IntegrationParameters params = new IntegrationParameters(a, b, n);
        rectangle.setParameters(params);

        double result = rectangle.calculate();

        // A zero-width interval should always yield an integral of zero
        assertEquals(0.0, result, 0.0001, 
            String.format("Result for zero-width interval (%f, %f) should be 0, but was %f", a, b, result));
    }
    
    /**
     * Test invalid inputs, such as NaN or Infinity, to verify exception handling.
     * 
     * @param a the lower bound of the interval
     * @param b the upper bound of the interval
     * @param n the number of partitions (invalid values: NaN or Infinity)
     */
    @ParameterizedTest
    @DisplayName("Test invalid inputs for rectangle method")
    @CsvSource({
        "NaN, 1, 1",  // Invalid a (NaN)
        "1, Infinity, 1", // Invalid b (Infinity)
        "1, 1, NaN"        // Invalid n (NaN)
    })
    public void testInvalidInputs(double a, double b, double n) {
        // Create IntegrationParameters with invalid inputs
        IntegrationParameters params = new IntegrationParameters(a, b, (int)n);
        rectangle.setParameters(params);

        // Check for invalid lower bound (a)
        if (Double.isNaN(a) || Double.isInfinite(a)) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> rectangle.calculate(),
                String.format("Invalid lower bound: a = %f", a));
            assertEquals("Lower bound (a) must be a valid number (not NaN or Infinity).", exception.getMessage());
        }
        // Check for invalid upper bound (b)
        else if (Double.isNaN(b) || Double.isInfinite(b)) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> rectangle.calculate(),
                String.format("Invalid upper bound: b = %f", b));
            assertEquals("Upper bound (b) must be a valid number (not NaN or Infinity).", exception.getMessage());
        }
        // Check for invalid number of partitions (n)
        else if (Double.isNaN(n) || Double.isInfinite(n)) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> rectangle.calculate(),
                String.format("Invalid number of partitions: n = %f", n));
            assertEquals("Number of partitions must be a valid positive number, not Infinity, NaN, or zero.", exception.getMessage());
        }
    }
}
