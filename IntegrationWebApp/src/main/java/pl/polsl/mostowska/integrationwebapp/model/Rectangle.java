package pl.polsl.mostowska.integrationwebapp.model;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import pl.polsl.mostowska.integrationwebapp.IntegrationErrorHandler;

/**
 * Implementation of numerical integration using the rectangle method.
 * This method approximates the integral by dividing the interval [a, b] into rectangles and summing their areas.
 * 
 * The height of each rectangle is determined by the value of the function at the midpoint of the corresponding subinterval.
 * 
 * This class extends the `IntegrationModel` abstract class and provides the concrete implementation of the `calculate` method.
 * 
 * @author Wiktoria Mostowska
 * @version 3.0
 */
public class Rectangle extends IntegrationModel {
    /** Instance of the error handler to manage exceptions. */
    private final IntegrationErrorHandler errorHandler;

    /**
     * Default constructor for the rectangle integration method.
     * Initializes the error handler for managing potential exceptions during integration.
     */
    public Rectangle() {
        this.errorHandler = new IntegrationErrorHandler();
    }
    
    /**
     * Performs numerical integration using the rectangle method.
     * This method divides the interval [a, b] into `n` equal partitions and calculates the area of rectangles.
     * The height of each rectangle is determined by evaluating the function at the midpoint of each subinterval.
     * 
     * The result is the sum of all rectangle areas.
     * 
     * @return The approximated value of the integral.
     * @throws IllegalArgumentException If the number of partitions is less than or equal to 0 or if the upper bound is less than the lower bound.
     */
    @Override
    public double calculate() {
        // Retrieve the integration parameters (lower bound, upper bound, number of partitions).
        IntegrationParameters params = getParameters(); // Get integration parameters
        // Check if the parameters object is null
        if (params == null) {
            throw new NullPointerException("Parameters object cannot be null.");
        }
        // Validate: Check if the number of partitions is a valid number (not Infinite, NaN or zero)
        if (Double.isInfinite(params.partitions()) || Double.isNaN(params.partitions()) || params.partitions() <= 0) {
            throw new IllegalArgumentException("Number of partitions must be a valid positive number, not Infinity, NaN, or zero.");
        }

        // Validate: Check if the upper bound is a valid number (not Infinite or NaN)
        if (Double.isInfinite(params.upperBound()) || Double.isNaN(params.upperBound())) {
            throw new IllegalArgumentException("Upper bound (b) must be a valid number (not NaN or Infinity).");
        }

        // Validate: Check if the lower bound is a valid number (not Infinite or NaN)
        if (Double.isInfinite(params.lowerBound()) || Double.isNaN(params.lowerBound())) {
            throw new IllegalArgumentException("Lower bound (a) must be a valid number (not NaN or Infinity).");
        }
        // Validation: Ensure the upper bound is greater than or equal to the lower bound.
        if (params.upperBound() < params.lowerBound()) {
            throw new IllegalArgumentException("Upper bound (b) cannot be less than lower bound (a).");
        }
        // Calculate the width of each rectangle (h).
        double h = (params.upperBound() - params.lowerBound()) / params.partitions();
        // Clear the results list to ensure no residual data from previous calculations.
        getResults().clear();
        // List to store the height of each rectangle for intermediate results.
        List<Double> values = new ArrayList<>();
        
        // Use a functional approach with IntStream to calculate rectangle areas.
        IntStream.range(0, params.partitions())
         .forEach(i -> {
             // Calculate the x-coordinate at the midpoint of the current subinterval.
             double x = params.lowerBound() + i * h + h / 2;
             // Calculate the rectangle height by evaluating the function at x.
             double rectangleHeight = evaluateFunction(x);
             // Store the rectangle height in the results list.
             getResults().add(rectangleHeight);
             // Add the rectangle height to the intermediate values list.
             values.add(rectangleHeight);            
         });
        
        // Calculate the total sum of rectangle heights using the stream API.
        double sum = values.stream()
                   .mapToDouble(Double::doubleValue)
                   .sum();

        // Multiply the total sum by the rectangle width (h) to get the final integral value.
        return sum * h;
    }
}
