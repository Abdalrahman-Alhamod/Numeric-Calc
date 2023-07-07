import java.util.ArrayList;
import java.util.Objects;

/**
 * The ExpressionFunction class represents a mathematical function defined by an expression.
 * It implements the {@link Function} interface and provides methods to evaluate the value, differentiate, integrate,
 * and convert the function into a PointsFunction for interpolation.
 */
public class ExpressionFunction implements Function {

    private final String func;

    /**
     * Constructs an ExpressionFunction with the given expression.
     *
     * @param func The expression representing the function.
     */
    public ExpressionFunction(String func) {
        this.func = Objects.requireNonNull(func);
    }

    /**
     * Evaluates the value of the function at the given x-coordinate.
     *
     * @param x The x-coordinate at which to evaluate the function.
     * @return The value of the function at the given x-coordinate.
     * @throws ArithmeticException If an error occurs during evaluation.
     */
    public double getValueAt(double x) {
        double res;
        try {
            res = EvaluateString.evaluate(func, x);
            //Rounding value back to fix floating-point precision errors
            res = Math.round(res * 1e10) / 1e10;
        } catch (Exception e) {
            throw new ArithmeticException("invalid function");
        }
        return res;
    }

    /**
     * Evaluates the value of the function at the given x-coordinate as string
     *
     * @param s The string representing x-coordinate at which to evaluate the function.
     * @return The value of the function at the given x-coordinate.
     * @throws ArithmeticException If an error occurs during evaluation OR s is null
     */
    public double getValueAt(String s) {
        if (s == null)
            throw new ArithmeticException("invalid inputs");
        s = s.replaceAll("pi", Double.toString(Math.PI));
        double x = EvaluateString.evaluate(s, 0);
        return getValueAt(x);
    }

    /**
     * Computes the value of the derivative of the function at the given x-coordinate.
     *
     * @param x    The x-coordinate at which to compute the derivative.
     * @param rank The order of the derivative (e.g., 1 for first derivative, 2 for second derivative, and so on).
     * @return The value of the derivative of the function at the given x-coordinate.
     */
    public double getDiffAt(double x, int rank) {
        PointsFunction pf = this.toPointsFunction(x, x + 100, 200);
        Polynomial poly = Interpolation.NewtonForwardDividedSubtractions.getIFAP(pf, pf.getXp().size() - 1);
        return poly.getDiffAt(x, rank);
    }

    /**
     * Computes the value of the integral of the function at the given x-coordinate.
     *
     * @param x    The x-coordinate at which to compute the integral.
     * @param rank The order of the integral (e.g., 1 for first integral, 2 for second integral, and so on).
     * @return The value of the integral of the function at the given x-coordinate.
     */
    public double getIntegralAt(double x, int rank) {
        PointsFunction pf = this.toPointsFunction(x, x + 100, 200);
        Polynomial poly = Interpolation.NewtonForwardDividedSubtractions.getIFAP(pf, pf.getXp().size() - 1);
        return poly.getIntegralAt(x, rank);
    }

    /**
     * Converts the function into a PointsFunction for interpolation.
     *
     * @param a The lower bound of the x-coordinate range.
     * @param b The upper bound of the x-coordinate range.
     * @param n The number of points to generate for interpolation.
     * @return The PointsFunction representing the function over the given range.
     */
    public PointsFunction toPointsFunction(double a, double b, double n) {
        double h = (a + b) / n;
        //Rounding value back to fix floating-point precision errors
        h = Math.round(h * 1e10) / 1e10;
        ArrayList<Double> xp = new ArrayList<>();
        ArrayList<Double> yp = new ArrayList<>();
        for (double i = a; i <= b; i += h) {
            //Rounding value back to fix floating-point precision errors
            i = Math.round(i * 1e10) / 1e10;
            xp.add(i);
            yp.add(getValueAt(i));
        }
        return new PointsFunction(xp, yp);
    }

    /**
     * Converts the function into a PointsFunction for interpolation.
     *
     * @param a The lower bound of the x-coordinate range as string
     * @param b The upper bound of the x-coordinate range as string
     * @param n The number of points to generate for interpolation as string
     * @return The PointsFunction representing the function over the given range.
     * @throws ArithmeticException if any of the inputs is null
     */
    public PointsFunction toPointsFunction(String a, String b, String n) {
        if (a == null || b == null || n == null)
            throw new ArithmeticException("invalid inputs");
        a = a.replaceAll("pi", Double.toString(Math.PI));
        double ad = EvaluateString.evaluate(a, 0);
        b = b.replaceAll("pi", Double.toString(Math.PI));
        double bd = EvaluateString.evaluate(b, 0);
        n = n.replaceAll("pi", Double.toString(Math.PI));
        double nd = EvaluateString.evaluate(n, 0);
        return toPointsFunction(ad, bd, nd);
    }

    /**
     * Returns the string representation of the function.
     *
     * @return The string representation of the function.
     */
    @Override
    public String toString() {
        return "F(x) = " + func;
    }
}
