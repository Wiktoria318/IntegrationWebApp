package pl.polsl.mostowska.integrationwebapp.model;

/**
 * Represents the parameters required for numerical integration.
 * This record holds the range of integration (lower and upper bounds) 
 * and the number of partitions used for dividing the range into smaller intervals.
 * 
 * @author Wiktoria Mostowska
 * @version 1.0
 * 
 * The use of a record ensures immutability and simplifies the representation of these parameters.
 * 
 * @param lowerBound The starting point of the integration range.
 * @param upperBound The ending point of the integration range.
 * @param partitions The number of intervals for numerical integration.
 */
public record IntegrationParameters(double lowerBound, double upperBound, int partitions) { }