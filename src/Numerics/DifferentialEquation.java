package Numerics;

import Util.EvaluateString;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The DifferentialEquation class represents a differential equation and provides methods for solving it numerically.
 */
public class DifferentialEquation {
    private final String dy;

    /**
     * Constructs a DifferentialEquation object with the given dy/dx expression.
     *
     * @param dy The expression representing the derivative dy/dx.
     */
    public DifferentialEquation(String dy) {
        this.dy = Objects.requireNonNull(dy);
    }

    /**
     * Evaluates the differential equation at the given values of x and y.
     *
     * @param x The value of x.
     * @param y The value of y.
     * @return The value of the differential equation at (x, y).
     */
    public double getValueAt(double x, double y) {
        double ans = EvaluateString.evaluate(dy, x, y);
        //Rounding value back to fix floating-point precision errors
        ans = Math.round(ans * 1e10) / 1e10;
        return ans;
    }

    /**
     * The Euler class provides a method for solving a differential equation using the Euler method.
     */
    public static class Euler {
        /**
         * Solves the differential equation using the Euler method.
         *
         * @param eq The differential equation to solve.
         * @param x0 The initial value of x.
         * @param y0 The initial value of y.
         * @param h  The step size.
         * @param x  The target value of x.
         * @return The approximate value of y at x.
         */
        public static double solve(DifferentialEquation eq, double x0, double y0, double h, double x) {
            // init yi = y0 , xi = x0,  yi+1 = 0
            double yi = y0, xi = x0, yi1 = 0;
            while (xi != x) {//if current value xi != x => continue
                // get yi+1 = yi + yi' * h
                yi1 = yi + eq.getValueAt(xi, yi) * h;
                //Rounding value back to fix floating-point precision errors
                yi1 = Math.round(yi1 * 1e10) / 1e10;
                //System.out.println("yi+1 = " + yi1);
                // update xi
                xi = xi + h;
                //Rounding value back to fix floating-point precision errors
                xi = Math.round(xi * 1e10) / 1e10;
                // update yi
                yi = yi1;
            }
            return yi1;
        }
    }

    /**
     * The Taylor class provides a method for solving a differential equation using the Taylor method.
     */
    public static class Taylor {
        /**
         * Solves the differential equation using the Taylor method.
         *
         * @param eqs The list of differential equations to solve.
         * @param x0  The initial value of x.
         * @param y0  The initial value of y.
         * @param h   The step size.
         * @param x   The target value of x.
         * @return The approximate value of y at x.
         */
        public static double solve(ArrayList<DifferentialEquation> eqs, double x0, double y0, double h, double x) {
            // init yi = y0 , xi = x0,  yi+1 = 0
            double yi = y0, xi = x0, yi1 = 0;
            while (xi != x) {//if current value xi != x => continue
                // init factorial = 1, sum = yi
                double factor = 1, sum = yi;
                for (int i = 0; i < eqs.size(); i++) {// for every Differential Equation
                    // update factorial = 1 * 2 * 3 ... * n = n!
                    factor *= (i + 1);
                    //Rounding value back to fix floating-point precision errors
                    factor = Math.round(factor * 1e10) / 1e10;
                    // update ans = yi + ( yi'* h ) / 1! + ( yi'' * h^2 ) / 2! + ( yi''' * h^3 ) / 3! ...
                    sum += eqs.get(i).getValueAt(xi, yi) * Math.pow(h, i + 1) * (1 / factor);
                    //Rounding value back to fix floating-point precision errors
                    sum = Math.round(sum * 1e10) / 1e10;
                }
                // update yi+1
                yi1 = sum;
                //System.out.println("yi+1 = " + yi1);
                //update xi
                xi = xi + h;
                //Rounding value back to fix floating-point precision errors
                xi = Math.round(xi * 1e10) / 1e10;
                // update yi
                yi = yi1;
            }
            return yi1;
        }

    }

    /**
     * The MidPoint class provides a method for solving a differential equation using the Midpoint method.
     */
    public static class MidPoint {
        /**
         * Solves the differential equation using the Midpoint method.
         *
         * @param eq The differential equation to solve.
         * @param x0 The initial value of x.
         * @param y0 The initial value of y.
         * @param h  The step size.
         * @param x  The target value of x.
         * @param a2 The coefficient for the Midpoint method.
         * @return The approximate value of y at x.
         */
        private static double solve(DifferentialEquation eq, double x0, double y0, double h, double x, double a2) {
            double a1 = 1 - a2;
            double p = 1 / (2 * a2), q = 1 / (2 * a2);
            // init yi = y0 , xi = x0,  yi+1 = 0
            double yi = y0, xi = x0, yi1 = 0;
            while (xi != x) {
                // update k1 = f(xi,yi)
                double k1 = eq.getValueAt(xi, yi);
                //Rounding value back to fix floating-point precision errors
                k1 = Math.round(k1 * 1e10) / 1e10;
                //System.out.println("k1 = " + k1);
                // update k2 = f(xi + p*h , yi +q*h*k1 )
                double k2 = eq.getValueAt(xi + p * h, yi + q * h * k1);
                //Rounding value back to fix floating-point precision errors
                k2 = Math.round(k2 * 1e10) / 1e10;
                //System.out.println("k2 = " + k2);
                // update yi+1 = yi + h[ ai * k1 + a2 * k2 ]
                yi1 = yi + h * (a1 * k1 + a2 * k2);
                //Rounding value back to fix floating-point precision errors
                yi1 = Math.round(yi1 * 1e10) / 1e10;

                //System.out.println("yi+1 = " + yi1);

                //update xi
                xi = xi + h;
                //Rounding value back to fix floating-point precision errors
                xi = Math.round(xi * 1e10) / 1e10;
                // update yi
                yi = yi1;
            }
            return yi1;
        }

        /**
         * The ModifiedEuler class provides a method for solving a differential equation using the Modified Euler method.
         */
        public static class ModifiedEuler {
            /**
             * Solves the differential equation using the Modified Euler method.
             *
             * @param eq The differential equation to solve.
             * @param x0 The initial value of x.
             * @param y0 The initial value of y.
             * @param h  The step size.
             * @param x  The target value of x.
             * @return The approximate value of y at x.
             */
            public static double solve(DifferentialEquation eq, double x0, double y0, double h, double x) {
                return MidPoint.solve(eq, x0, y0, h, x, 1);
            }
        }

        /**
         * The Heun class provides a method for solving a differential equation using the Heun method.
         */
        public static class Heun {
            /**
             * Solves the differential equation using the Heun method.
             *
             * @param eq The differential equation to solve.
             * @param x0 The initial value of x.
             * @param y0 The initial value of y.
             * @param h  The step size.
             * @param x  The target value of x.
             * @return The approximate value of y at x.
             */
            public static double solve(DifferentialEquation eq, double x0, double y0, double h, double x) {
                return MidPoint.solve(eq, x0, y0, h, x, 0.5);
            }
        }

        /**
         * The Ralston class provides a method for solving a differential equation using the Ralston method.
         */
        public static class Ralston {
            /**
             * Solves the differential equation using the Ralston method.
             *
             * @param eq The differential equation to solve.
             * @param x0 The initial value of x.
             * @param y0 The initial value of y.
             * @param h  The step size.
             * @param x  The target value of x.
             * @return The approximate value of y at x.
             */
            public static double solve(DifferentialEquation eq, double x0, double y0, double h, double x) {
                return MidPoint.solve(eq, x0, y0, h, x, ((double) 2 / 3));
            }
        }
    }

    /**
     * The Runge_Kutta class provides a method for solving a differential equation using the Runge-Kutta method.
     */
    public static class Runge_Kutta {
        /**
         * Solves the differential equation using the Runge-Kutta method.
         *
         * @param eq The differential equation to solve.
         * @param x0 The initial value of x.
         * @param y0 The initial value of y.
         * @param h  The step size.
         * @param x  The target value of x.
         * @return The approximate value of y at x.
         */
        public static double solve(DifferentialEquation eq, double x0, double y0, double h, double x) {
            // init yi = y0 , xi = x0,  yi+1 = 0
            double yi = y0, xi = x0, yi1 = 0;
            while (xi != x) {
                // update k1 = f(xi,yi)
                double k1 = eq.getValueAt(xi, yi);
                //Rounding value back to fix floating-point precision errors
                k1 = Math.round(k1 * 1e10) / 1e10;

                //System.out.println("k1 = " + k1);

                // update k2 = f(xi + (1/2)h , yi + (1/2)k1 )
                double k2 = eq.getValueAt(xi + (0.5) * h, yi + (0.5) * k1);
                //Rounding value back to fix floating-point precision errors
                k2 = Math.round(k2 * 1e10) / 1e10;

                //System.out.println("k2 = " + k2);

                // update k3 = f(xi + (1/2)h , yi + (1/2)k2 )
                double k3 = eq.getValueAt(xi + (0.5) * h, yi + (0.5) * k2);
                //Rounding value back to fix floating-point precision errors
                k3 = Math.round(k3 * 1e10) / 1e10;

                //System.out.println("k3 = " + k3);

                // update k4 = f(xi + h , yi + k3 )
                double k4 = eq.getValueAt(xi + h, yi + k3);
                //Rounding value back to fix floating-point precision errors
                k4 = Math.round(k4 * 1e10) / 1e10;

                //System.out.println("k4 = " + k4);

                // update yi+1 = yi + (h/6) [ k1 + 2k2 + 2k3 + k4 ]
                yi1 = yi + (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4);
                //Rounding value back to fix floating-point precision errors
                yi1 = Math.round(yi1 * 1e10) / 1e10;

                //System.out.println("yi+1 = " + yi1);

                //update xi
                xi = xi + h;
                //Rounding value back to fix floating-point precision errors
                xi = Math.round(xi * 1e10) / 1e10;
                // update yi
                yi = yi1;
            }
            return yi1;
        }
    }


}
