package Numerics;

import Functions.Function;

/**
 * The Integral class provides methods for numerical integration using various methods.
 * It supports rectangular, trapezoidal, Simpson's 1/3, Simpson's 3/8, and Paul's method.
 */
public abstract class Integral {
    /**
     * Estimated Error value for Integral methods
     */
    private static double e;

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
    public static double getRect(Function func, double a, double b, double n) {
        if (func == null)
            throw new ArithmeticException("invalid inputs : Function cannot be null");
        else if (a >= b)
            throw new ArithmeticException("invalid inputs : a cannot be greater or equal to b");
        else if (n <= 0)
            throw new ArithmeticException("invalid inputs : n cannot be smaller or equal to 0");
        double h = (b - a) / n;
        //Rounding value back to fix floating-point precision errors
        h = Math.round(h * 1e10) / 1e10;
        double sum = 0;
        double value = a;
        for (int i = 0; i <= n - 1; i++) {
            sum += func.getValueAt(value);
            //Rounding value back to fix floating-point precision errors
            sum = Math.round(sum * 1e10) / 1e10;
            value += h;
            //Rounding value back to fix floating-point precision errors
            value = Math.round(value * 1e10) / 1e10;
        }
        //Calculating Error
        //e = ((b - a) / 2) * h * (Math.max(func.getDiffAt(a, 1), func.getDiffAt(b, 1)));
        sum *= h;
        //Rounding value back to fix floating-point precision errors
        sum = Math.round(sum * 1e10) / 1e10;
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
    public static double getTraps(Function func, double a, double b, double n) {
        if (func == null)
            throw new ArithmeticException("invalid inputs : Function cannot be null");
        else if (a >= b)
            throw new ArithmeticException("invalid inputs : a cannot be greater or equal to b");
        else if (n <= 0)
            throw new ArithmeticException("invalid inputs : n cannot be smaller or equal to 0");
        double h = (b - a) / n;
        //Rounding value back to fix floating-point precision errors
        h = Math.round(h * 1e10) / 1e10;
        double sum = 0;
        sum += func.getValueAt(a) + func.getValueAt(b);
        double value = a + h;
        value = Math.round(value * 1e10) / 1e10; //Rounding value back to fix floating-point precision errors
        for (int i = 1; i <= n - 1; i++) {
            sum += 2 * func.getValueAt(value);
            //Rounding value back to fix floating-point precision errors
            sum = Math.round(sum * 1e10) / 1e10;
            value += h;
            value = Math.round(value * 1e10) / 1e10; //Rounding value back to fix floating-point precision errors
        }
        //Calculating Error
        //e = ((b - a) / 12) * Math.pow(h, 2) * (Math.max(func.getDiffAt(a, 2), func.getDiffAt(b, 2)));
        sum *= (h / 2);
        //Rounding value back to fix floating-point precision errors
        sum = Math.round(sum * 1e10) / 1e10;
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
    public static double getSimpson3(Function func, double a, double b, double n) {
        if (func == null)
            throw new ArithmeticException("invalid inputs : Function cannot be null");
        else if (a >= b)
            throw new ArithmeticException("invalid inputs : a cannot be greater or equal to b");
        else if (n <= 0)
            throw new ArithmeticException("invalid inputs : n cannot be smaller or equal to 0");
        else if (n % 2 != 0)
            throw new ArithmeticException("invalid inputs : n is not even");
        double h = (b - a) / n;
        //Rounding value back to fix floating-point precision errors
        h = Math.round(h * 1e10) / 1e10;
        double sum = 0;
        sum += func.getValueAt(a) + func.getValueAt(b);
        double value = a + h;
        value = Math.round(value * 1e10) / 1e10; //Rounding value back to fix floating-point precision errors
        for (int i = 1; i <= n - 1; i++) {
            if (i % 2 == 0)
                sum += 2 * func.getValueAt(value);
            else
                sum += 4 * func.getValueAt(value);
            //Rounding value back to fix floating-point precision errors
            sum = Math.round(sum * 1e10) / 1e10;
            value += h;
            value = Math.round(value * 1e10) / 1e10; //Rounding value back to fix floating-point precision errors
        }
        //Calculating Error
        //e = ((b - a) / 180) * Math.pow(h, 4) * (Math.max(func.getDiffAt(a, 4), func.getDiffAt(b, 4)));
        sum *= (h / 3);
        //Rounding value back to fix floating-point precision errors
        sum = Math.round(sum * 1e10) / 1e10;
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
    public static double getSimpson8(Function func, double a, double b, double n) {
        if (func == null)
            throw new ArithmeticException("invalid inputs : Function cannot be null");
        else if (a >= b)
            throw new ArithmeticException("invalid inputs : a cannot be greater or equal to b");
        else if (n <= 0)
            throw new ArithmeticException("invalid inputs : n cannot be smaller or equal to 0");
        else if (n % 3 != 0)
            throw new ArithmeticException("invalid inputs : n is not divisible by 3");
        double h = (b - a) / n;
        //Rounding value back to fix floating-point precision errors
        h = Math.round(h * 1e10) / 1e10;
        double sum = 0;
        sum += func.getValueAt(a) + func.getValueAt(b);
        double value = a + h;
        value = Math.round(value * 1e10) / 1e10; //Rounding value back to fix floating-point precision errors
        for (int i = 1; i <= n - 1; i++) {
            if (i % 3 == 0)
                sum += 2 * func.getValueAt(value);
            else
                sum += 3 * func.getValueAt(value);
            //Rounding value back to fix floating-point precision errors
            sum = Math.round(sum * 1e10) / 1e10;
            value += h;
            value = Math.round(value * 1e10) / 1e10; //Rounding value back to fix floating-point precision errors
        }
        //Calculating Error
        //e = ((b - a) / 80) * Math.pow(h, 4) * (Math.max(func.getDiffAt(a, 4), func.getDiffAt(b, 4)));
        sum *= 3 * (h / 8);
        //Rounding value back to fix floating-point precision errors
        sum = Math.round(sum * 1e10) / 1e10;
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
    public static double getPaul(Function func, double a, double b, double n) {
        if (func == null)
            throw new ArithmeticException("invalid inputs : Function cannot be null");
        else if (a >= b)
            throw new ArithmeticException("invalid inputs : a cannot be greater or equal to b");
        else if (n <= 0)
            throw new ArithmeticException("invalid inputs : n cannot be smaller or equal to 0");
        else if (n % 3 != 0)
            throw new ArithmeticException("invalid inputs : n is not divisible by 4");
        double h = (b - a) / n;
        //Rounding value back to fix floating-point precision errors
        h = Math.round(h * 1e10) / 1e10;
        double sum = 0;
        sum += 7 * func.getValueAt(a) + 7 * func.getValueAt(b);
        double value = a + h;
        value = Math.round(value * 1e10) / 1e10; //Rounding value back to fix floating-point precision errors
        for (int i = 1; i <= n - 1; i++) {
            if (i % 4 == 0)
                sum += 14 * func.getValueAt(value);
            else if (i % 2 == 0)
                sum += 12 * func.getValueAt(value);
            else
                sum += 32 * func.getValueAt(value);
            //Rounding value back to fix floating-point precision errors
            sum = Math.round(sum * 1e10) / 1e10;
            value += h;
            value = Math.round(value * 1e10) / 1e10; //Rounding value back to fix floating-point precision errors
        }
        //Calculating Error
        //e = (2 * (b - a) / 945) * Math.pow(h, 6) * (Math.max(func.getDiffAt(a, 6), func.getDiffAt(b, 6)));
        sum *= 2 * (h / 45);
        //Rounding value back to fix floating-point precision errors
        sum = Math.round(sum * 1e10) / 1e10;
        return sum;
    }

    /**
     * Returns the estimated error of the last performed integration.
     *
     * @return the estimated error
     */
    public static double getError() {
        return e;
    }


}