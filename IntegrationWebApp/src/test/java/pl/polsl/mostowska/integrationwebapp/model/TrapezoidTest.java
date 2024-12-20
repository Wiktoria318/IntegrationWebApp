package pl.polsl.mostowska.integrationwebapp.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 * Unit tests for the Trapezoid class implementing the trapezoidal method for numerical integration.
 * The class validates the calculation of definite integrals using various scenarios,
 * including valid and invalid inputs.
 *
 * @author Wiktoria Mostowska
 * @version 1.0
 */
public class TrapezoidTest {
    private Trapezoid trapezoid;
    
    public TrapezoidTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        trapezoid = new Trapezoid();
        // Example function: f(x) = 2x + 1
        trapezoid.setFunction(x -> 2 * x + 1);
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    /**
     * Tests correct calculation of integrals for valid input parameters.
     *
     * @param a             Lower bound of the integral.
     * @param b             Upper bound of the integral.
     * @param n             Number of partitions.
     * @param expectedResult Expected result of the integral.
     */
    @ParameterizedTest
    @DisplayName("Test correct integral calculation")
    @CsvSource({
        "0, 2, 4, 6.0",  // Integral from 0 to 2 with 4 partitions, result = 6.0
        "1, 3, 4, 10.0", // Integral from 1 to 3 with 4 partitions, result = 10.0
        "0, 1, 2, 2.0"   // Integral from 0 to 1 with 2 partitions, result = 2.0
    })
    public void testCalculateCorrectValues(double a, double b, int n, double expectedResult) {
        // Set integration parameters
        IntegrationParameters params = new IntegrationParameters(a, b, n);
        trapezoid.setParameters(params);

        // Calculate the integral
        double result = trapezoid.calculate();

        // Validate the result
        assertNotNull(result, "Result should not be null.");
        assertEquals(expectedResult, result, 0.0001, 
        String.format("Expected result: %f, but got: %f", expectedResult, result));
    }

    /**
     * Tests that the method throws an exception when the number of partitions is invalid (less or equal to 0).
     *
     * @param n Number of partitions (invalid value).
     */
    @ParameterizedTest
    @DisplayName("Test invalid partition count (<= 0)")
    @CsvSource({
        "0",  // 0 partitions
        "-5"  // Negative partition count
    })
    public void testCalculateWithInvalidPartitions(int n) {
        IntegrationParameters params = new IntegrationParameters(0, 2, n);
        trapezoid.setParameters(params);

        // Test if the method throws an exception
        Executable executable = trapezoid::calculate;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Number of partitions must be a valid positive number, not Infinity, NaN, or zero.", exception.getMessage());
    }

    /**
     * Tests that the method throws an exception when the upper bound is less than the lower bound.
     *
     * @param a Lower bound of the integral.
     * @param b Upper bound of the integral (less than a).
     */
    @ParameterizedTest
    @DisplayName("Test invalid bounds (upper bound less than lower bound)")
    @CsvSource({
        "2, 1",  // Upper bound less than lower bound
        "-3, -5" // Negative bounds
    })
    public void testCalculateWithInvalidBounds(double a, double b) {
        IntegrationParameters params = new IntegrationParameters(a, b, 10);
        trapezoid.setParameters(params);

        // Test if the method throws an exception
        Executable executable = trapezoid::calculate;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Upper bound (b) cannot be less than the lower bound (a).", exception.getMessage());
    }

    /**
     * Tests the calculation of integrals with a very large number of partitions.
     *
     * @param n Very large number of partitions.
     */
    @ParameterizedTest
    @DisplayName("Test for very large number of partitions")
    @CsvSource({
        "100000",  // Very large number of partitions
        "1000000"  // Even larger number of partitions
    })
    public void testCalculateWithLargePartitions(int n) {
        IntegrationParameters params = new IntegrationParameters(0, 2, n);
        trapezoid.setParameters(params);

        // Calculate the integral
        double result = trapezoid.calculate();

        // Check if the result is valid (positive number)
        assertNotNull(result, "Result should not be null.");
        assertTrue(result >= 0, "Result should be a positive number.");
    }

    /**
     * Tests the calculation of integrals with the smallest possible number of partitions (n=1).
     *
     * @param n Minimum number of partitions.
     */
    @ParameterizedTest
    @DisplayName("Test for boundary number of partitions (n=1)")
    @CsvSource({
        "1"  // Minimum number of partitions
    })
    public void testCalculateWithBoundaryPartition(int n) {
        IntegrationParameters params = new IntegrationParameters(0, 2, n);
        trapezoid.setParameters(params);

        // Calculate the integral
        double result = trapezoid.calculate();

        // Validate the result
        assertNotNull(result, "Result should not be null.");
        assertTrue(result >= 0, "Result should be a positive number.");
    }
    
    /**
     * Tests the calculation of integrals for zero-width intervals (a == b).
     * The result should always be 0.
     *
     * @param a Lower and upper bound (equal values).
     * @param b Lower and upper bound (equal values).
     * @param n Number of partitions.
     */
    @ParameterizedTest
    @DisplayName("Test zero-width interval (a == b)")
    @CsvSource({
        "2, 2, 10",  // Zero-width interval
        "5, 5, 50"   // Another zero-width interval
    })
    public void testZeroWidthInterval(double a, double b, int n) {
        IntegrationParameters params = new IntegrationParameters(a, b, n);
        trapezoid.setParameters(params);

        double result = trapezoid.calculate();

        // A zero-width interval should always yield an integral of zero
        assertEquals(0.0, result, 0.0001, 
            String.format("Result for zero-width interval (%f, %f) should be 0, but was %f", a, b, result));
    }
    /**
     * Tests for invalid inputs such as NaN or Infinity in bounds or partition count.
     * These cases validate error handling for edge cases.
     *
     * @param a Lower bound of the integral.
     * @param b Upper bound of the integral.
     * @param n Number of partitions (invalid values).
     */
    @ParameterizedTest
    @DisplayName("Test invalid inputs for trapezoid method")
    @CsvSource({
        "NaN, 1, 1",  // Invalid a (NaN)
        "1, Infinity, 1", // Invalid b (Infinity)
        "1, 1, NaN"        // Invalid n (NaN)
    })
    public void testInvalidInputs(double a, double b, double n) {
        // Create IntegrationParameters with invalid inputs
        IntegrationParameters params = new IntegrationParameters(a, b, (int)n);
        trapezoid.setParameters(params);

        // Check for invalid lower bound (a)
        if (Double.isNaN(a) || Double.isInfinite(a)) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> trapezoid.calculate(),
                String.format("Invalid lower bound: a = %f", a));
            assertEquals("Lower bound (a) must be a valid number (not NaN or Infinity).", exception.getMessage());
        }
        // Check for invalid upper bound (b)
        else if (Double.isNaN(b) || Double.isInfinite(b)) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> trapezoid.calculate(),
                String.format("Invalid upper bound: b = %f", b));
            assertEquals("Upper bound (b) must be a valid number (not NaN or Infinity).", exception.getMessage());
        }
        // Check for invalid number of partitions (n)
        else if (Double.isNaN(n) || Double.isInfinite(n)) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> trapezoid.calculate(),
                String.format("Invalid number of partitions: n = %f", n));
            assertEquals("Number of partitions must be a valid positive number, not Infinity, NaN, or zero.", exception.getMessage());
        }
    }
}
