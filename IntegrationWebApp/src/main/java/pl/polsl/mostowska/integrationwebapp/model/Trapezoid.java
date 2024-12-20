package pl.polsl.mostowska.integrationwebapp.model;
import pl.polsl.mostowska.integrationwebapp.IntegrationErrorHandler;

/**
 * Implementation of numerical integration using the trapezoidal method.
 * This method approximates the integral by dividing the interval [a, b] into subintervals 
 * and calculating the area of trapezoids under the curve.
 * 
 * The sum of the areas of these trapezoids is used to estimate the integral.
 * 
 * @author Wiktoria Mostowska
 * @version 3.0
 */
public class Trapezoid extends IntegrationModel {
    /** Instance of the error handler to manage exceptions. */
    private final IntegrationErrorHandler errorHandler;

    /**
     * Constructor to initialize the Trapezoid integration method.
     * Initializes the error handler for managing exceptions.
     */
    public Trapezoid() {
        this.errorHandler = new IntegrationErrorHandler();
    }
    
    /**
     * Performs integration using the trapezoidal method.
     * The trapezoidal method approximates the area under the curve by dividing the interval [a, b] into 'n' partitions.
     * For each partition, the area of the trapezoid is calculated, where the heights are the values of the function at the left and right endpoints.
     * The sum of these trapezoidal areas gives the approximation of the integral.
     * 
     * @return The approximated value of the integral.
     * @throws IllegalArgumentException If the number of partitions is less than or equal to 0 or if the upper bound is less than the lower bound.
     */
    @Override
    public double calculate(){
        // Retrieve the integration parameters (lower bound, upper bound, number of partitions).
        IntegrationParameters params = getParameters();
        // Check if the parameters object is null
        if (params == null) {
            throw new NullPointerException("Parameters object cannot be null");
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
            throw new IllegalArgumentException("Upper bound (b) cannot be less than the lower bound (a).");
        }

        // Calculate the width of each subinterval (h).
        double h = (params.upperBound() - params.lowerBound()) / params.partitions();
        // Clear the results list to ensure it only contains results from the current calculation.
        getResults().clear();
        // Initialize the sum with contributions from the endpoints of the interval.
        double sum = 0.5 * (evaluateFunction(params.lowerBound()) + evaluateFunction(params.upperBound()));
        // Iterate over the internal points of the interval, summing the function values.
        for (int i = 1; i < params.partitions(); i++) {
            // Calculate the x-coordinate of the current point.
            double x = params.lowerBound() + i * h;
            // Evaluate the function at x to get the height of the trapezoid.
            double trapezoidHeight = evaluateFunction(x);
            // Store the height in the results list for intermediate values.
            getResults().add(trapezoidHeight);
            // Add the height to the running sum.
            sum += trapezoidHeight;
        }
        // Multiply the sum by the width of each subinterval (h) to get the final integral value.
        return sum * h;
    }  
}

