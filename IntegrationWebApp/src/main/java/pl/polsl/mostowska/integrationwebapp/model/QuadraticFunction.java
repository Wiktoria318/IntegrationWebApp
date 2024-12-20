package pl.polsl.mostowska.integrationwebapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a quadratic function of the form f(x) = ax² + bx + c.
 * This class implements the `FunctionModel` interface and uses `FunctionData` to store coefficients.
 * 
 * Lombok annotations are used to automatically generate the constructor and getter methods.
 * 
 * @author Wiktoria Mostowska
 * @version 2.0
 */
@AllArgsConstructor
@Getter
public class QuadraticFunction implements FunctionModel {
    /**
     * Coefficients of the quadratic function.
     * Stored in a `FunctionData` object containing `a`, `b`, and `c`.
     */
    private final FunctionData data;
    
    /**
     * Constructor for initializing a quadratic function with specific coefficients.
     * 
     * @param a Coefficient for the quadratic term (x²).
     * @param b Coefficient for the linear term (x).
     * @param c Constant term.
     */
    public QuadraticFunction(double a, double b, double c) {
        this.data = new FunctionData(a, b, c);
    }

    /**
     * Evaluates the quadratic function at the given input value.
     * The formula used is f(x) = ax² + bx + c.
     * 
     * @param x The input value.
     * @return The result of the function evaluated at x.
     */
    @Override
    public double evaluate(double x) {
        if (Double.isNaN(data.a()) || Double.isNaN(data.b()) || Double.isNaN(data.c()) || Double.isInfinite(data.a()) || Double.isInfinite(data.b()) || Double.isInfinite(data.c())) {
            throw new IllegalArgumentException("Coefficients a, b, and c cannot be NaN or infinite.");
        }
        if (Double.isInfinite(x) || Double.isNaN(x)) {
            throw new IllegalArgumentException("x value cannot be NaN or infinite.");
        }
        return data.a() * x * x + data.b() * x + data.c();
    }
}
