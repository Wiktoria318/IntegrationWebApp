package pl.polsl.mostowska.integrationwebapp.model;

/**
 * Represents a set of coefficients for a mathematical function.
 * This record is used to hold three parameters: 'a', 'b', and 'c', 
 * which could be applied, for example, in a quadratic function: f(x) = ax² + bx + c.
 * 
 * @author Wiktoria Mostowska
 * @version 1.0
 * 
 * The use of a record in Java provides an immutable and concise representation of data.
 * 
 * @param a Coefficient for the quadratic term (x²).
 * @param b Coefficient for the linear term (x).
 * @param c Constant term.
 */
public record FunctionData(double a, double b, double c) { }
