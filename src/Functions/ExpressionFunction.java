package Functions;

import Numerics.Interpolation;
import Util.Accuracy;
import Util.EvaluateString;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The ExpressionFunction class represents a mathematical function defined by an expression.
 * It implements the {@link Function} interface and provides methods to evaluate the value, differentiate, integrate,
 * and convert the function into a PointsFunction .
 */
@SuppressWarnings("all")
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
    public BigDecimal getValueAt(BigDecimal x) {
        BigDecimal res;
        try {
            res = EvaluateString.evaluate(func, x);
        } catch (Exception e) {
            throw new ArithmeticException("invalid function");
        }
        return res;
    }

    /**
     * Evaluates the value of the function at the given x-value and y-value
     *
     * @param x The x-value at which to evaluate the function.
     * @param y The y-value at which to evaluate the function.
     * @return The value of the function at the given x-value and y-value.
     * @throws ArithmeticException If an error occurs during evaluation.
     */
    public BigDecimal getValueAt(BigDecimal x, BigDecimal y) {
        BigDecimal res;
        try {
            res = EvaluateString.evaluate(func, x, y);
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
    public BigDecimal getValueAt(String s) {
        if (s == null)
            throw new ArithmeticException("invalid inputs : s cannot be null");
        s = s.replaceAll("pi", Double.toString(Math.PI));
        BigDecimal x = EvaluateString.evaluate(s);
        return getValueAt(x);
    }

    /**
     * Computes the value of the derivative of the function at the given x-coordinate.
     *
     * @param x    The x-coordinate at which to compute the derivative.
     * @param rank The order of the derivative (e.g., 1 for first derivative, 2 for second derivative, and so on).
     * @return The value of the derivative of the function at the given x-coordinate.
     */
    public BigDecimal getDiffAt(BigDecimal x, int rank) {
        PointsFunction pf = this.toPointsFunction(x, x.add(new BigDecimal(100)), 200);
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
    public BigDecimal getIntegralAt(BigDecimal x, int rank) {
        PointsFunction pf = this.toPointsFunction(x, x.add(new BigDecimal(100)), 200);
        Polynomial poly = Interpolation.NewtonForwardDividedSubtractions.getIFAP(pf, pf.getXp().size() - 1);
        return poly.getIntegralAt(x, rank);
    }

    /**
     * Converts the function into a PointsFunction
     *
     * @param a The lower bound of the x-coordinate range.
     * @param b The upper bound of the x-coordinate range.
     * @param n The number of points to generate
     * @return The PointsFunction representing the function over the given range.
     */
    public PointsFunction toPointsFunction(BigDecimal a, BigDecimal b, int n) {
        n--;
        BigDecimal h = (b.subtract(a)).divide(new BigDecimal(n), Accuracy.getValue() + 3, RoundingMode.HALF_UP);
        h = h.round(new MathContext(Accuracy.getValue(), RoundingMode.HALF_UP));
        ArrayList<BigDecimal> xp = new ArrayList<>();
        ArrayList<BigDecimal> yp = new ArrayList<>();
        BigDecimal curr = new BigDecimal(a.toString());
        for (int i = 0; i <= n; i++) {
            xp.add(curr);
            yp.add(getValueAt(curr));
            curr = curr.add(h);
        }
        return new PointsFunction(xp, yp);
    }

    /**
     * Converts the function into a PointsFunction .
     *
     * @param a The lower bound of the x-coordinate range as string
     * @param b The upper bound of the x-coordinate range as string
     * @param n The number of points to generate
     * @return The PointsFunction representing the function over the given range.
     * @throws ArithmeticException if any of the inputs is null
     */
    public PointsFunction toPointsFunction(String a, String b, int n) {
        if (a == null)
            throw new ArithmeticException("invalid inputs : a cannot be null");
        else if (b == null)
            throw new ArithmeticException("invalid inputs : b cannot be null");
        if (n <= 0)
            throw new ArithmeticException("invalid inputs : n cannot be smaller or equal to 0");
        a = a.replaceAll("pi", Double.toString(Math.PI));
        BigDecimal ad = EvaluateString.evaluate(a);
        b = b.replaceAll("pi", Double.toString(Math.PI));
        BigDecimal bd = EvaluateString.evaluate(b);
        if (ad.compareTo(bd) > 0)
            throw new ArithmeticException("invalid inputs : a cannot be greater or equal to b");
        return toPointsFunction(ad, bd, n);
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
