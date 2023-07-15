package Numerics;

import Functions.ExpressionFunction;

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
        public static double solve(ExpressionFunction fx, double a, double b, double e) {
            double fa = fx.getValueAt(a), fb = fx.getValueAt(b);
            double c = a;
            while (Math.abs(a - b) >= e) {
                //System.out.println("a : " + a + " b : " + b + " c : " + c);
                c = (a + b) / 2;
                //Rounding value back to fix floating-point precision errors
                c = Math.round(c * 1e10) / 1e10;
                double fc = fx.getValueAt(c);
                if (fc * fa < 0) {
                    b = c;
                    fb = fc;
                } else if (fc * fb < 0) {
                    a = c;
                    fa = fc;
                }
                if (Math.abs(a - b) == 0)
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

        public static double solve(ExpressionFunction fx, double a, double b) {
            return solve(fx, a, b, 0.000000001);
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
        public static double solve(ExpressionFunction fx, double a, double b, double e) {
            double fa = fx.getValueAt(a), fb = fx.getValueAt(b);
            double c = a;
            while (Math.abs(a - b) >= e) {
                c = ((a * fb) - (b * fa)) / (fb - fa);
                //Rounding value back to fix floating-point precision errors
                c = Math.round(c * 1e10) / 1e10;
                //System.out.println("a : " + a + " b : " + b + " c : " + c);
                double fc = fx.getValueAt(c);
                //System.out.println("fa : " + fa + " fb : " + fb + " fc : " + fc);
                if (fc * fa < 0) {
                    b = c;
                    fb = fc;
                } else if (fc * fb < 0) {
                    a = c;
                    fa = fc;
                }
                if (Math.abs(a - b) < e || Math.abs(a - b) == 0 || fc == 0)
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
        public static double solve(ExpressionFunction fx, double a, double b) {
            return solve(fx, a, b, 0.000000001);
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
        public static double solve(ExpressionFunction fx, double x0, double x1, double e) {
            double xi_1 = x0, xi = x1;
            double fxi_1 = fx.getValueAt(x0), fxi = fx.getValueAt(x1);
            double xi1 = xi_1;
            while (true) {
                xi1 = xi - ((xi - xi_1) / (fxi - fxi_1)) * fxi;
                //Rounding value back to fix floating-point precision errors
                xi1 = Math.round(xi1 * 1e10) / 1e10;
                //System.out.println("xi-1 : " + xi_1 + " xi : " + xi + " xi+1 : " + xi1);
                double fxi1 = fx.getValueAt(xi1);
                //System.out.println("f(xi-1) : " + fxi_1 + " f(xi) : " + fxi + " f(xi+1) : " + fxi1);
                if (Math.abs(xi1 - xi) < e || Math.abs(xi1 - xi) == 0 || fxi1 == 0)
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
        public static double solve(ExpressionFunction fx, double x0, double x1) {
            return solve(fx, x0, x1, 1E-9);
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
        public static double solve(ExpressionFunction fx, ExpressionFunction dfx, double x0, double e) {
            double xi = x0;
            double fxi = fx.getValueAt(xi), dfxi = dfx.getValueAt(xi);
            double xi1;
            while (true) {
                xi1 = xi - (fxi / dfxi);
                //Rounding value back to fix floating-point precision errors
                xi1 = Math.round(xi1 * 1e10) / 1e10;
                double fxi1 = fx.getValueAt(xi1);
                if (Math.abs(xi1 - xi) < e || Math.abs(xi1 - xi) == 0 || fxi1 == 0)
                    break;
                double dfxi1 = dfx.getValueAt(xi1);
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

        public static double solve(ExpressionFunction fx, ExpressionFunction dfx, double x0) {
            return solve(fx, dfx, x0, 1E-9);
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

        public static double solveRange(ExpressionFunction fx, ExpressionFunction dfx, double a, double b, double e) {
            double fa = fx.getValueAt(a), dfa = dfx.getValueAt(a);
            double x = a - (fa / dfa);
            //Rounding value back to fix floating-point precision errors
            x = Math.round(x * 1e10) / 1e10;
            if (x >= a && x <= b)
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

        public static double solveRange(ExpressionFunction fx, ExpressionFunction dfx, double a, double b) {
            return solveRange(fx, dfx, a, b, 1E-9);
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
        public static double solve(ExpressionFunction fx, ExpressionFunction dfx, ExpressionFunction d2fx, double x0, double e) {
            double xi = x0;
            double fxi = fx.getValueAt(xi), dfxi = dfx.getValueAt(xi), d2fxi = d2fx.getValueAt(xi);
            double xi1;
            while (true) {
                //System.out.println("f(xi) : "+fxi+" f'(xi) : "+dfxi+" f''(xi) : "+d2fxi);
                xi1 = xi - ((fxi) / (dfxi - (((d2fxi) / (2 * dfxi)) * fxi)));
                //Rounding value back to fix floating-point precision errors
                xi1 = Math.round(xi1 * 1e10) / 1e10;
                //System.out.println(" xi : " + xi + " xi+1 : " + xi1);
                double fxi1 = fx.getValueAt(xi1);
                if (Math.abs(xi1 - xi) < e || Math.abs(xi1 - xi) == 0 || fxi1 == 0)
                    break;
                double dfxi1 = dfx.getValueAt(xi1), d2fxi1 = d2fx.getValueAt(xi1);
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
        public static double solve(ExpressionFunction gx, double x0, double e) {
            double xi = x0;
            double gxi = gx.getValueAt(xi);
            double xi1;
            while (true) {
                xi1 = gxi;
                //Rounding value back to fix floating-point precision errors
                xi1 = Math.round(xi1 * 1e10) / 1e10;
                //System.out.println(" xi : " + xi + " xi+1 : " + xi1);
                double gxi1 = gx.getValueAt(xi1);
                //System.out.println("g(xi) : " + xi);
                if (Math.abs(xi1 - xi) < e || Math.abs(xi1 - xi) == 0 || gxi1 == 0)
                    break;
                xi = xi1;
                gxi = gxi1;
            }
            return xi1;
        }
    }

}
