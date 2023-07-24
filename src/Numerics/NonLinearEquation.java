package Numerics;

import Functions.ExpressionFunction;
import Util.Accuracy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The NonLinearEquation class provides implementations of various numerical methods for solving non-linear equations.
 * It includes methods for solving equations using the Bisection, False Position, Secant, Newton-Raphson, Halley,
 * and Fixed-Point Iteration methods.
 */
public abstract class NonLinearEquation {
    /**
     * The Bisection class provides methods for solving equations using the Bisection method.
     */
    public static class Bisection {
        /**
         * Solves the given equation using the Bisection method within a specified tolerance.
         *
         * @param fx The expression function representing the equation.
         * @param a  The lower bound of the interval.
         * @param b  The upper bound of the interval.
         * @param e  The tolerance value.
         * @return The approximate root of the equation.
         */
        public static BigDecimal solve(ExpressionFunction fx, BigDecimal a, BigDecimal b, BigDecimal e) {
            BigDecimal fa = fx.getValueAt(a), fb = fx.getValueAt(b);
            BigDecimal c = a;
            while ((a.subtract(b)).abs().compareTo(e) >= 0) {
                //System.out.println("a : " + a + " b : " + b + " c : " + c);
                c = (a.add(b)).divide(new BigDecimal(2), Accuracy.getValue(), RoundingMode.HALF_UP);
                BigDecimal fc = fx.getValueAt(c);
                if (fc.multiply(fa).compareTo(new BigDecimal(0)) < 0) {
                    b = c;
                    fb = fc;
                } else if (fc.multiply(fb).compareTo(new BigDecimal(0)) < 0) {
                    a = c;
                    fa = fc;
                }
                if ((a.subtract(b)).abs().equals(new BigDecimal(0)))
                    break;
            }
            return c;
        }

        /**
         * Solves the given equation using the Bisection method with a default tolerance value.
         *
         * @param fx The expression function representing the equation.
         * @param a  The lower bound of the interval.
         * @param b  The upper bound of the interval.
         * @return The approximate root of the equation.
         */

        public static BigDecimal solve(ExpressionFunction fx, BigDecimal a, BigDecimal b) {
            return solve(fx, a, b, new BigDecimal(0));
        }
    }

    /**
     * The FalsePosition class provides methods for solving equations using the False Position method.
     */
    public static class FalsePosition {
        /**
         * Solves the given equation using the False Position method within a specified tolerance.
         *
         * @param fx The expression function representing the equation.
         * @param a  The lower bound of the interval.
         * @param b  The upper bound of the interval.
         * @param e  The tolerance value.
         * @return The approximate root of the equation.
         */
        public static BigDecimal solve(ExpressionFunction fx, BigDecimal a, BigDecimal b, BigDecimal e) {
            BigDecimal fa = fx.getValueAt(a), fb = fx.getValueAt(b);
            BigDecimal c = a;
            while ((a.subtract(b)).abs().compareTo(e) >= 0) {
                c = ((a.multiply(fb)).subtract(b.multiply(fa))).divide(fb.subtract(fa), Accuracy.getValue(), RoundingMode.HALF_UP);
                //System.out.println("a : " + a + " b : " + b + " c : " + c);
                BigDecimal fc = fx.getValueAt(c);
                //System.out.println("fa : " + fa + " fb : " + fb + " fc : " + fc);
                if (fc.multiply(fa).compareTo(new BigDecimal(0)) < 0) {
                    b = c;
                    fb = fc;
                } else if (fc.multiply(fb).compareTo(new BigDecimal(0)) < 0) {
                    a = c;
                    fa = fc;
                }
                if ((a.subtract(b)).abs().compareTo(e) < 0 || (a.subtract(b)).abs().equals(new BigDecimal(0)) || fc.equals(new BigDecimal(0)))
                    break;
            }
            return c;
        }

        /**
         * Solves the given equation using the False Position method with a default tolerance value.
         *
         * @param fx The expression function representing the equation.
         * @param a  The lower bound of the interval.
         * @param b  The upper bound of the interval.
         * @return The approximate root of the equation.
         */
        public static BigDecimal solve(ExpressionFunction fx, BigDecimal a, BigDecimal b) {
            return solve(fx, a, b, new BigDecimal(0));
        }
    }

    /**
     * The Secant class provides methods for solving equations using the Secant method.
     */
    public static class Secant {
        /**
         * Solves the given equation using the Secant method within a specified tolerance.
         *
         * @param fx The expression function representing the equation.
         * @param x0 The initial guess for the root.
         * @param x1 The second guess for the root.
         * @param e  The tolerance value.
         * @return The approximate root of the equation.
         */
        public static BigDecimal solve(ExpressionFunction fx, BigDecimal x0, BigDecimal x1, BigDecimal e) {
            BigDecimal xi_1 = x0, xi = x1;
            BigDecimal fxi_1 = fx.getValueAt(x0), fxi = fx.getValueAt(x1);
            BigDecimal xi1 = xi_1;
            while (true) {
                xi1 = xi.subtract((xi.subtract(xi_1)).divide(fxi.subtract(fxi_1), Accuracy.getValue(), RoundingMode.HALF_UP)).multiply(fxi);
                //System.out.println("xi-1 : " + xi_1 + " xi : " + xi + " xi+1 : " + xi1);
                BigDecimal fxi1 = fx.getValueAt(xi1);
                //System.out.println("f(xi-1) : " + fxi_1 + " f(xi) : " + fxi + " f(xi+1) : " + fxi1);
                if ((xi1.subtract(xi)).abs().compareTo(e) < 0 || (xi1.subtract(xi)).abs().equals(new BigDecimal(0)) || fxi1.equals(new BigDecimal(0)))
                    break;
                xi_1 = xi;
                fxi_1 = fxi;
                xi = xi1;
                fxi = fxi1;
            }
            return xi1;
        }

        /**
         * Solves the given equation using the Secant method with a default tolerance value.
         *
         * @param fx The expression function representing the equation.
         * @param x0 The initial guess for the root.
         * @param x1 The second guess for the root.
         * @return The approximate root of the equation.
         */
        public static BigDecimal solve(ExpressionFunction fx, BigDecimal x0, BigDecimal x1) {
            return solve(fx, x0, x1, new BigDecimal(0));
        }
    }

    /**
     * The Newton_Raphson class provides methods for solving equations using the Newton-Raphson method.
     */
    public static class Newton_Raphson {
        /**
         * Solves the given equation using the Newton-Raphson method within a specified tolerance.
         *
         * @param fx  The expression function representing the equation.
         * @param dfx The expression function representing the derivative of the equation.
         * @param x0  The initial guess for the root.
         * @param e   The tolerance value.
         * @return The approximate root of the equation.
         */
        public static BigDecimal solve(ExpressionFunction fx, ExpressionFunction dfx, BigDecimal x0, BigDecimal e) {
            BigDecimal xi = x0;
            BigDecimal fxi = fx.getValueAt(xi), dfxi = dfx.getValueAt(xi);
            BigDecimal xi1;
            while (true) {
                xi1 = xi.subtract(fxi.divide(dfxi, Accuracy.getValue(), RoundingMode.HALF_UP));
                BigDecimal fxi1 = fx.getValueAt(xi1);
                if ((xi1.subtract(xi)).abs().compareTo(e) < 0 || (xi1.subtract(xi)).abs().equals(new BigDecimal(0)) || fxi1.equals(new BigDecimal(0)))
                    break;
                BigDecimal dfxi1 = dfx.getValueAt(xi1);
                xi = xi1;
                fxi = fxi1;
                dfxi = dfxi1;
            }
            return xi1;
        }

        /**
         * Solves the given equation using the Newton-Raphson method with a default tolerance value.
         *
         * @param fx  The expression function representing the equation.
         * @param dfx The expression function representing the derivative of the equation.
         * @param x0  The initial guess for the root.
         * @return The approximate root of the equation.
         */

        public static BigDecimal solve(ExpressionFunction fx, ExpressionFunction dfx, BigDecimal x0) {
            return solve(fx, dfx, x0, new BigDecimal(0));
        }

        /**
         * Solves the given equation using the Newton-Raphson method within a specified range and tolerance.
         *
         * @param fx  The expression function representing the equation.
         * @param dfx The expression function representing the derivative of the equation.
         * @param a   The lower bound of the interval.
         * @param b   The upper bound of the interval.
         * @param e   The tolerance value.
         * @return The approximate root of the equation within the specified range.
         */

        public static BigDecimal solveRange(ExpressionFunction fx, ExpressionFunction dfx, BigDecimal a, BigDecimal b, BigDecimal e) {
            BigDecimal fa = fx.getValueAt(a), dfa = dfx.getValueAt(a);
            BigDecimal x = a.subtract(fa.divide(dfa, Accuracy.getValue(), RoundingMode.HALF_UP));
            if (x.compareTo(a) >= 0 && x.compareTo(b) <= 0)
                return solve(fx, dfx, a, e);
            else
                return solve(fx, dfx, b, e);
        }

        /**
         * Solves the given equation using the Newton-Raphson method within a specified range and with a default tolerance value.
         *
         * @param fx  The expression function representing the equation.
         * @param dfx The expression function representing the derivative of the equation.
         * @param a   The lower bound of the interval.
         * @param b   The upper bound of the interval.
         * @return The approximate root of the equation within the specified range.
         */

        public static BigDecimal solveRange(ExpressionFunction fx, ExpressionFunction dfx, BigDecimal a, BigDecimal b) {
            return solveRange(fx, dfx, a, b, new BigDecimal(0));
        }
    }

    /**
     * The Halley class provides methods for solving equations using the Halley method.
     */
    public static class Halley {
        /**
         * Solves the given equation using the Halley method within a specified tolerance.
         *
         * @param fx   The expression function representing the equation.
         * @param dfx  The expression function representing the derivative of the equation.
         * @param d2fx The expression function representing the second derivative of the equation.
         * @param x0   The initial guess for the root.
         * @param e    The tolerance value.
         * @return The approximate root of the equation.
         */
        public static BigDecimal solve(ExpressionFunction fx, ExpressionFunction dfx, ExpressionFunction d2fx, BigDecimal x0, BigDecimal e) {
            BigDecimal xi = x0;
            BigDecimal fxi = fx.getValueAt(xi), dfxi = dfx.getValueAt(xi), d2fxi = d2fx.getValueAt(xi);
            BigDecimal xi1;
            while (true) {
                //System.out.println("f(xi) : "+fxi+" f'(xi) : "+dfxi+" f''(xi) : "+d2fxi);
                xi1 = xi.subtract(
                        (fxi).divide(
                                dfxi.subtract(
                                        (
                                                (d2fxi).divide(
                                                        dfxi.multiply(
                                                                new BigDecimal(2)
                                                        )
                                                        , Accuracy.getValue(), RoundingMode.HALF_UP
                                                )
                                        ).multiply(
                                                fxi
                                        )
                                ), Accuracy.getValue(), RoundingMode.HALF_UP)
                );
                //System.out.println(" xi : " + xi + " xi+1 : " + xi1);
                BigDecimal fxi1 = fx.getValueAt(xi1);
                if ((xi1.subtract(xi)).abs().compareTo(e) < 0 || (xi1.subtract(xi)).abs().equals(new BigDecimal(0)) || fxi1.equals(new BigDecimal(0)))
                    break;
                BigDecimal dfxi1 = dfx.getValueAt(xi1), d2fxi1 = d2fx.getValueAt(xi1);
                //System.out.println("f(xi+1) : "+fxi1+" f'(xi+1) : "+dfxi1+" f''(xi+1) : "+d2fxi1);
                xi = xi1;
                fxi = fxi1;
                dfxi = dfxi1;
                d2fxi = d2fxi1;
            }
            return xi1;
        }
    }

    /**
     * The FixedPointIteration class provides methods for solving equations using the Fixed-Point Iteration method.
     */
    public static class FixedPointIteration {
        /**
         * Solves the given equation using the Fixed-Point Iteration method within a specified tolerance.
         *
         * @param gx The expression function representing the iterative equation.
         * @param x0 The initial guess for the root.
         * @param e  The tolerance value.
         * @return The approximate root of the equation.
         */
        public static BigDecimal solve(ExpressionFunction gx, BigDecimal x0, BigDecimal e) {
            BigDecimal xi = x0;
            BigDecimal gxi = gx.getValueAt(xi);
            BigDecimal xi1;
            while (true) {
                xi1 = gxi;
                //System.out.println(" xi : " + xi + " xi+1 : " + xi1);
                BigDecimal gxi1 = gx.getValueAt(xi1);
                //System.out.println("g(xi) : " + xi);
                if ((xi1.subtract(xi)).abs().compareTo(e) < 0 || (xi1.subtract(xi)).equals(new BigDecimal(0)) || gxi1.equals(new BigDecimal(0)))
                    break;
                xi = xi1;
                gxi = gxi1;
            }
            return xi1;
        }
    }

}
