package pl.polsl.mostowska.integrationwebapp.model;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

/**
 * Abstract class modeling the process of numerical integration.
 * This class provides a framework for implementing various numerical integration methods,
 * such as the trapezoidal rule or Simpson's rule.
 * 
 * It manages integration parameters, the function to be integrated, and intermediate results.
 * 
 * Subclasses must provide a specific implementation for the `calculate()` method.
 * 
 * The class utilizes Lombok annotations to automatically generate boilerplate code 
 * like getters, setters, and constructors.
 * 
 * @author Wiktoria Mostowska
 * @version 3.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class IntegrationModel { 
    /**
     * Parameters defining the integration range and the number of partitions.
     */
    private IntegrationParameters parameters; 
    /**
     * Mathematical function to be integrated.
     */
    private FunctionModel function;
    private FunctionData data;
    
    /**
     * List to store intermediate results or computed values during integration.
     */
    private List<Double> results = new ArrayList<>();
    
    /**
     * Sets the function to be integrated.
     * 
     * @param function The mathematical function.
     */
    public void setFunction(FunctionModel function) {
        this.function = function;
    }
//    /**
//    * Sets the parameters for the integration process.
//    * Validates the parameters before setting them.
//    * 
//    * @param parameters The integration parameters.
//    * @throws IllegalArgumentException if the parameters are invalid.
//    */
//    public void setParameters(IntegrationParameters parameters) {
//        if (parameters == null) {
//            throw new IllegalArgumentException("Integration parameters cannot be null.");
//        }
//        if (parameters.lowerBound() >= parameters.upperBound()) {
//            throw new IllegalArgumentException("The lower bound (a) must be less than the upper bound (b).");
//        }
//        if (parameters.partitions() <= 0) {
//            throw new IllegalArgumentException("The number of partitions must be a positive integer.");
//        }
//        this.parameters = parameters;
//    }
    /**
     * Retrieves the current mathematical function.
     * 
     * @return The function to be integrated.
     */
    protected FunctionModel getFunction() {
        return function;
    }
    /**
     * Abstract method to calculate the integral.
     * This method must be implemented by subclasses to define the specific integration logic.
     * 
     * @return The calculated integral value.
     */
    public abstract double calculate();
    
    /**
     * Evaluates the mathematical function at a given point.
     * 
     * This method checks if a function is set, and throws an exception if no function is selected.
     * 
     * @param x The input value.
     * @return The value of the function evaluated at x.
     * @throws IllegalStateException if no function is set.
     */
    protected double evaluateFunction(double x) {
        if (function == null) {
            throw new IllegalStateException("No function selected for integration.");
        }
        return function.evaluate(x);
    }
}