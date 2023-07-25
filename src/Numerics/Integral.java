package Numerics;

import Functions.PointsFunction;
import Util.Accuracy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The Integral class provides methods for numerical integration using various methods.
 * It supports rectangular, trapezoidal, Simpson's 1/3, Simpson's 3/8, and Paul's method.
 */
public abstract class Integral {
    /**
     * Estimated Error value for Integral methods
     */
    private static BigDecimal e;

    /**
     * Calculates the integral using the rectangular method.
     *
     * @param func the function to integrate
     * @param a    the lower limit of integration
     * @param b    the upper limit of integration
     * @param n    the number of sub-intervals
     * @return the calculated integral value
     * @throws ArithmeticException if the function is null, a is greater than or equal to b, or n is less than or equal to 0
     */
    public static BigDecimal getRect(PointsFunction func, BigDecimal a, BigDecimal b, int n) {
        if (func == null)
            throw new ArithmeticException("invalid inputs : Function cannot be null");
        else if (a.compareTo(b) >= 0)
            throw new ArithmeticException("invalid inputs : a cannot be greater or equal to b");
        else if (n <= 0)
            throw new ArithmeticException("invalid inputs : n cannot be smaller or equal to 0");
        BigDecimal h = (b.subtract(a)).divide(new BigDecimal(n), Accuracy.getValue() + 3, RoundingMode.HALF_UP);
        BigDecimal sum = new BigDecimal(0);
        BigDecimal value = a;
        for (int i = 0; i <= n - 1; i++) {
            sum = sum.add(func.getValueAt(i));
            value = value.add(h);
        }
        //Calculating Error
        //e = ((b - a) / 2) * h * (Math.max(func.getDiffAt(a, 1), func.getDiffAt(b, 1)));
        sum = sum.multiply(h);
        return sum;
    }

    /**
     * Calculates the integral using the trapezoidal method.
     *
     * @param func the function to integrate
     * @param a    the lower limit of integration
     * @param b    the upper limit of integration
     * @param n    the number of sub-intervals
     * @return the calculated integral value
     * @throws ArithmeticException if the function is null, a is greater than or equal to b, or n is less than or equal to 0
     */
    public static BigDecimal getTraps(PointsFunction func, BigDecimal a, BigDecimal b, int n) {
        if (func == null)
            throw new ArithmeticException("invalid inputs : Function cannot be null");
        else if (a.compareTo(b) >= 0)
            throw new ArithmeticException("invalid inputs : a cannot be greater or equal to b");
        else if (n <= 0)
            throw new ArithmeticException("invalid inputs : n cannot be smaller or equal to 0");
        BigDecimal h = (b.subtract(a)).divide(new BigDecimal(n), Accuracy.getValue() + 3, RoundingMode.HALF_UP);
        BigDecimal sum = new BigDecimal(0);
        sum = sum.add(func.getValueAt(a).add(func.getValueAt(b)));
        BigDecimal value = a.add(h);
        for (int i = 1; i <= n - 1; i++) {
            sum = sum.add(func.getValueAt(i).multiply(new BigDecimal(2)));
            value = value.add(h);
        }
        //Calculating Error
        //e = ((b - a) / 12) * Math.pow(h, 2) * (Math.max(func.getDiffAt(a, 2), func.getDiffAt(b, 2)));
        sum = sum.multiply(h.divide(new BigDecimal(2), Accuracy.getValue() + 3, RoundingMode.HALF_UP));
        return sum;
    }

    /**
     * Calculates the integral using Simpson's 1/3 method.
     *
     * @param func the function to integrate
     * @param a    the lower limit of integration
     * @param b    the upper limit of integration
     * @param n    the number of sub-intervals (must be an even number)
     * @return the calculated integral value
     * @throws ArithmeticException if the function is null, a is greater than or equal to b, n is less than or equal to 0, or n is not an even number
     */
    public static BigDecimal getSimpson3(PointsFunction func, BigDecimal a, BigDecimal b, int n) {
        if (func == null)
            throw new ArithmeticException("invalid inputs : Function cannot be null");
        else if (a.compareTo(b) >= 0)
            throw new ArithmeticException("invalid inputs : a cannot be greater or equal to b");
        else if (n <= 0)
            throw new ArithmeticException("invalid inputs : n cannot be smaller or equal to 0");
        else if (n % 2 != 0)
            throw new ArithmeticException("invalid inputs : n is not even");
        BigDecimal h = (b.subtract(a)).divide(new BigDecimal(n), Accuracy.getValue() + 3, RoundingMode.HALF_UP);
        BigDecimal sum = new BigDecimal(0);
        sum = sum.add(func.getValueAt(a).add(func.getValueAt(b)));
        BigDecimal value = a.add(h);
        for (int i = 1; i <= n - 1; i++) {
            if (i % 2 == 0)
                sum = sum.add(func.getValueAt(i).multiply(new BigDecimal(2)));
            else
                sum = sum.add(func.getValueAt(i).multiply(new BigDecimal(4)));
            value = value.add(h);
        }
        //Calculating Error
        //e = ((b - a) / 180) * Math.pow(h, 4) * (Math.max(func.getDiffAt(a, 4), func.getDiffAt(b, 4)));
        sum = sum.multiply(h.divide(new BigDecimal(3), Accuracy.getValue() + 3, RoundingMode.HALF_UP));
        return sum;
    }

    /**
     * Calculates the integral using Simpson's 3/8 method.
     *
     * @param func the function to integrate
     * @param a    the lower limit of integration
     * @param b    the upper limit of integration
     * @param n    the number of sub-intervals (must be divisible by 3)
     * @return the calculated integral value
     * @throws ArithmeticException if the function is null, a is greater than or equal to b, n is less than or equal to 0, or n is not divisible by 3
     */
    public static BigDecimal getSimpson8(PointsFunction func, BigDecimal a, BigDecimal b, int n) {
        if (func == null)
            throw new ArithmeticException("invalid inputs : Function cannot be null");
        else if (a.compareTo(b) >= 0)
            throw new ArithmeticException("invalid inputs : a cannot be greater or equal to b");
        else if (n <= 0)
            throw new ArithmeticException("invalid inputs : n cannot be smaller or equal to 0");
        else if (n % 3 != 0)
            throw new ArithmeticException("invalid inputs : n is not divisible by 3");
        BigDecimal h = (b.subtract(a)).divide(new BigDecimal(n), Accuracy.getValue() + 3, RoundingMode.HALF_UP);
        BigDecimal sum = new BigDecimal(0);
        sum = sum.add(func.getValueAt(a).add(func.getValueAt(b)));
        BigDecimal value = a.add(h);
        for (int i = 1; i <= n - 1; i++) {
            if (i % 3 == 0)
                sum = sum.add(func.getValueAt(i).multiply(new BigDecimal(2)));
            else
                sum = sum.add(func.getValueAt(i).multiply(new BigDecimal(3)));
            value = value.add(h);
        }
        //Calculating Error
        //e = ((b - a) / 80) * Math.pow(h, 4) * (Math.max(func.getDiffAt(a, 4), func.getDiffAt(b, 4)));
        sum = sum.multiply(h.divide(new BigDecimal(8), Accuracy.getValue() + 3, RoundingMode.HALF_UP)).multiply(new BigDecimal(3));
        return sum;
    }

    /**
     * Calculates the integral using Paul's method.
     *
     * @param func the function to integrate
     * @param a    the lower limit of integration
     * @param b    the upper limit of integration
     * @param n    the number of sub-intervals (must be divisible by 4)
     * @return the calculated integral value
     * @throws ArithmeticException if the function is null, a is greater than or equal to b, n is less than or equal to 0, or n is not divisible by 4
     */
    public static BigDecimal getPaul(PointsFunction func, BigDecimal a, BigDecimal b, int n) {
        if (func == null)
            throw new ArithmeticException("invalid inputs : Function cannot be null");
        else if (a.compareTo(b) >= 0)
            throw new ArithmeticException("invalid inputs : a cannot be greater or equal to b");
        else if (n <= 0)
            throw new ArithmeticException("invalid inputs : n cannot be smaller or equal to 0");
        else if (n % 4 != 0)
            throw new ArithmeticException("invalid inputs : n is not divisible by 4");
        BigDecimal h = (b.subtract(a)).divide(new BigDecimal(n), Accuracy.getValue() + 3, RoundingMode.HALF_UP);
        BigDecimal sum = new BigDecimal(0);
        sum = sum.add(func.getValueAt(a).multiply(new BigDecimal(7)).add(func.getValueAt(b).multiply(new BigDecimal(7))));
        BigDecimal value = a.add(h);
        for (int i = 1; i <= n - 1; i++) {
            if (i % 4 == 0)
                sum = sum.add(func.getValueAt(i).multiply(new BigDecimal(14)));
            else if (i % 2 == 0)
                sum = sum.add(func.getValueAt(i).multiply(new BigDecimal(12)));
            else
                sum = sum.add(func.getValueAt(i).multiply(new BigDecimal(32)));
            value = value.add(h);
        }
        //Calculating Error
        //e = (2 * (b - a) / 945) * Math.pow(h, 6) * (Math.max(func.getDiffAt(a, 6), func.getDiffAt(b, 6)));
        sum = sum.multiply(h.divide(new BigDecimal(45), Accuracy.getValue() + 3, RoundingMode.HALF_UP)).multiply(new BigDecimal(2));
        return sum;
    }

    /**
     * Returns the estimated error of the last performed integration.
     *
     * @return the estimated error
     */
    public static BigDecimal getError() {
        return e;
    }


}
