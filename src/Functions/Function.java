package Functions;

/**
 * The Function interface represents a mathematical function.
 * It provides methods to evaluate the value, differentiate, integrate,
 * and retrieve the string representation of the function.
 */
public interface Function {

    /**
     * Evaluates the value of the function at the given x-coordinate.
     *
     * @param x The x-coordinate at which to evaluate the function.
     * @return The value of the function at the given x-coordinate.
     */
    double getValueAt(double x);

    /**
     * Computes the value of the derivative of the function at the given x-coordinate.
     *
     * @param x    The x-coordinate at which to compute the derivative.
     * @param rank The order of the derivative (e.g., 1 for first derivative, 2 for second derivative, and so on).
     * @return The value of the derivative of the function at the given x-coordinate.
     */
    double getDiffAt(double x, int rank);

    /**
     * Computes the value of the integral of the function at the given x-coordinate.
     *
     * @param x    The x-coordinate at which to compute the integral.
     * @param rank The order of the integral (e.g., 1 for first integral, 2 for second integral, and so on).
     * @return The value of the integral of the function at the given x-coordinate.
     */
    double getIntegralAt(double x, int rank);

    /**
     * Returns the string representation of the function.
     *
     * @return The string representation of the function.
     */
    String toString();

}
