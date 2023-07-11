import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The PointsFunction class represents a mathematical function defined by a set of discrete points.
 * It implements the {@link Function} interface and provides methods to evaluate the value, differentiate, integrate,
 * and manipulate the function.
 */
public class PointsFunction implements Function {
    private final ArrayList<Double> xp;
    private final ArrayList<Double> yp;

    /**
     * Constructs a PointsFunction with the given x and y coordinate lists.
     *
     * @param xp The list of x-coordinates of the function points.
     * @param yp The list of y-coordinates of the function points.
     * @throws NullPointerException If xp or yp is null.
     * @throws ArithmeticException  If the number of x and y coordinates does not match.
     */
    public PointsFunction(ArrayList<Double> xp, ArrayList<Double> yp) {
        this.xp = Objects.requireNonNull(xp, "xp cannot be null");
        this.yp = Objects.requireNonNull(yp, "yp cannot be null");
        if (xp.size() != yp.size()) {
            throw new ArithmeticException("number of function points mismatch");
        }
    }

    /**
     * Evaluates the value of the function at the given x-coordinate using interpolation.
     *
     * @param x The x-coordinate at which to evaluate the function.
     * @return The value of the function at the given x-coordinate.
     */
    public double getValueAt(double x) {
        int decimalPlaces = 9;
        double powerOf9 = Math.pow(10, decimalPlaces);
        double truncatedNumber = Math.floor(x * powerOf9) / powerOf9;
        for (double num : xp) {
            if (Math.abs(num - truncatedNumber) <= 0.000000001)
                truncatedNumber = num;
        }
        return yp.get(xp.indexOf(truncatedNumber));
    }

    /**
     * Adds a new point to the function with the given x and y coordinates.
     *
     * @param x The x-coordinate of the new point.
     * @param y The y-coordinate of the new point.
     */
    public void addPoint(double x, double y) {
        xp.add(x);
        yp.add(y);
    }

    /**
     * Computes the value of the derivative of the function at the given x-coordinate using interpolation.
     *
     * @param x    The x-coordinate at which to compute the derivative.
     * @param rank The order of the derivative (e.g., 1 for first derivative, 2 for second derivative, and so on).
     * @return The value of the derivative of the function at the given x-coordinate.
     */
    public double getDiffAt(double x, int rank) {
        Polynomial poly = Interpolation.NewtonForwardDividedSubtractions.getIFAP(this, xp.size() - 1);
        return poly.getDiffAt(x, rank);
    }

    /**
     * Computes the value of the integral of the function at the given x-coordinate using interpolation.
     *
     * @param x    The x-coordinate at which to compute the integral.
     * @param rank The order of the integral (e.g., 1 for first integral, 2 for second integral, and so on).
     * @return The value of the integral of the function at the given x-coordinate.
     */
    public double getIntegralAt(double x, int rank) {
        Polynomial poly = Interpolation.NewtonForwardDividedSubtractions.getIFAP(this, xp.size() - 1);
        return poly.getIntegralAt(x, rank);
    }

    /**
     * Returns the list of x-coordinates of the function points.
     *
     * @return The list of x-coordinates.
     */
    ArrayList<Double> getXp() {
        return xp;
    }

    /**
     * Returns the list of y-coordinates of the function points.
     *
     * @return The list of y-coordinates.
     */
    ArrayList<Double> getYp() {
        return yp;
    }

    /**
     * Returns the string representation of the function.
     *
     * @return The string representation of the function.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < xp.size(); i++) {
            sb.append("X").append(i).append(" = ").append(xp.get(i)).append("\tY").append(i).append(" = ").append(yp.get(i)).append('\n');
        }
        return sb.toString();
    }
}
