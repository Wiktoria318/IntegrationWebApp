package pl.polsl.mostowska.integrationwebapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a linear function of the form f(x) = bx + c.
 * This class implements the `FunctionModel` interface and uses `FunctionData` to store coefficients.
 * 
 * Two constructors are provided:
 * - One for initializing coefficients directly as a linear function.
 * - One for wrapping coefficients in a `FunctionData` object.
 * 
 * Lombok annotations are used to generate the constructor and getter methods automatically.
 * 
 * @author Wiktoria Mostowska
 * @version 2.0
 */
@AllArgsConstructor
@Getter
public class LinearFunction implements FunctionModel {
    /**
     * Coefficients of the linear function.
     * `a` is always set to 0, as it represents the quadratic term, which is not used in linear functions.
     */
    private final FunctionData data;

    /**
     * Constructor specifically for linear functions.
     * This sets `a` (quadratic coefficient) to 0, as it is not needed in a linear function.
     * 
     * @param b Coefficient for the linear term (x).
     * @param c Constant term.
     */
    public LinearFunction(double b, double c) {
        this.data = new FunctionData(0, b, c); // a = 0 for linear function
    }

    /**
     * Evaluates the linear function at the given input value.
     * The formula used is f(x) = bx + c.
     * 
     * @param x The input value.
     * @return The result of the function evaluated at x.
     */
    @Override
    public double evaluate(double x) {
        if (Double.isNaN(data.b()) || Double.isNaN(data.c()) || Double.isInfinite(data.b()) || Double.isInfinite(data.c())) {
            throw new IllegalArgumentException("Coefficients b and c cannot be NaN or infinite.");
        }
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            throw new IllegalArgumentException("x value cannot be NaN or infinite.");
        }
        return data.b() * x + data.c();
    }
}

