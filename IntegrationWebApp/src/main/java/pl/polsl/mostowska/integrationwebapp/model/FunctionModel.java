package pl.polsl.mostowska.integrationwebapp.model;

/**
 * Functional interface representing a mathematical function.
 * This interface defines a single abstract method for evaluating a function at a given input.
 * 
 * Functional interfaces can be implemented using lambda expressions or method references.
 * 
 * @author Wiktoria Mostowska
 * @version 1.0
 */
@FunctionalInterface
public interface FunctionModel {
    /**
     * Evaluates the function at the specified input value.
     * 
     * @param x The input value for the function.
     * @return The result of the function evaluated at x.
     */
    double evaluate(double x);
}