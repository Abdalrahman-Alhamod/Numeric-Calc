package Numerics;

import Util.Accuracy;
import Util.BigDecimalUtil;
import Util.EvaluateString;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public BigDecimal getValueAt(BigDecimal x, BigDecimal y) {
        BigDecimal ans = EvaluateString.evaluate(dy, x, y);
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
        public static BigDecimal solve(DifferentialEquation eq, BigDecimal x0, BigDecimal y0, BigDecimal h, BigDecimal x) {
            // init yi = y0 , xi = x0,  yi+1 = 0
            BigDecimal yi = new BigDecimal(y0.toString()), xi = new BigDecimal(x0.toString()), yi1 = new BigDecimal(0);
            while (!xi.equals(x)) {//if current value xi != x => continue
                // get yi+1 = yi + yi' * h
                yi1 = yi.add(eq.getValueAt(xi, yi).multiply(h));
                //System.out.println("yi+1 = " + yi1);
                // update xi
                xi = x.add(h);
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
        public static BigDecimal solve(ArrayList<DifferentialEquation> eqs, BigDecimal x0, BigDecimal y0, BigDecimal h, BigDecimal x) {
            // init yi = y0 , xi = x0,  yi+1 = 0
            BigDecimal yi = new BigDecimal(y0.toString()), xi = new BigDecimal(x0.toString()), yi1 = new BigDecimal(0);
            while (!xi.equals(x)) {//if current value xi != x => continue
                // init factorial = 1, sum = yi
                BigDecimal factor = new BigDecimal(1), sum = yi;
                for (int i = 0; i < eqs.size(); i++) {// for every Differential Equation
                    // update factorial = 1 * 2 * 3 ... * n = n!
                    factor = factor.multiply(new BigDecimal(i + 1));
                    // update ans = yi + ( yi'* h ) / 1! + ( yi'' * h^2 ) / 2! + ( yi''' * h^3 ) / 3! ...
                    sum = sum.add(
                            eqs.get(i).getValueAt(xi, yi).multiply(
                                    BigDecimalUtil.pow(h, new BigDecimal(i + 1)).multiply(
                                            new BigDecimal(1).divide(
                                                    factor, Accuracy.getValue(), RoundingMode.HALF_UP))));
                }
                // update yi+1
                yi1 = sum;
                //System.out.println("yi+1 = " + yi1);
                //update xi
                xi = xi.add(h);
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
        private static BigDecimal solve(DifferentialEquation eq, BigDecimal x0, BigDecimal y0, BigDecimal h, BigDecimal x, BigDecimal a2) {
            BigDecimal a1 = new BigDecimal(new BigDecimal(1).subtract(a2).toString());
            BigDecimal p = new BigDecimal(1).divide(a2.multiply(new BigDecimal(2)), Accuracy.getValue(), RoundingMode.HALF_UP), q = new BigDecimal(1).divide(a2.multiply(new BigDecimal(2)), Accuracy.getValue(), RoundingMode.HALF_UP);
            // init yi = y0 , xi = x0,  yi+1 = 0
            BigDecimal yi = new BigDecimal(y0.toString()), xi = new BigDecimal(x0.toString()), yi1 = new BigDecimal(0);
            while (!xi.equals(x)) {
                // update k1 = f(xi,yi)
                BigDecimal k1 = eq.getValueAt(xi, yi);
                //System.out.println("k1 = " + k1);
                // update k2 = f(xi + p*h , yi +q*h*k1 )
                BigDecimal k2 = eq.getValueAt(xi.add(p.multiply(h)), yi.add(q.multiply(h.multiply(k1))));
                //System.out.println("k2 = " + k2);
                // update yi+1 = yi + h[ ai * k1 + a2 * k2 ]
                yi1 = yi.add(h.multiply(a1.multiply(k1).add(a2.multiply(k2))));

                //System.out.println("yi+1 = " + yi1);

                //update xi
                xi = xi.add(h);
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
            public static BigDecimal solve(DifferentialEquation eq, BigDecimal x0, BigDecimal y0, BigDecimal h, BigDecimal x) {
                return MidPoint.solve(eq, x0, y0, h, x, new BigDecimal(1));
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
            public static BigDecimal solve(DifferentialEquation eq, BigDecimal x0, BigDecimal y0, BigDecimal h, BigDecimal x) {
                return MidPoint.solve(eq, x0, y0, h, x, new BigDecimal("0.5"));
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
            public static BigDecimal solve(DifferentialEquation eq, BigDecimal x0, BigDecimal y0, BigDecimal h, BigDecimal x) {
                return MidPoint.solve(eq, x0, y0, h, x, (new BigDecimal(2).divide(new BigDecimal(3), Accuracy.getValue(), RoundingMode.HALF_UP)));

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
        public static BigDecimal solve(DifferentialEquation eq, BigDecimal x0, BigDecimal y0, BigDecimal h, BigDecimal x) {
            // init yi = y0 , xi = x0,  yi+1 = 0
            BigDecimal yi = new BigDecimal(y0.toString()), xi = new BigDecimal(x0.toString()), yi1 = new BigDecimal(0);
            while (!xi.equals(x)) {
                // update k1 = f(xi,yi)
                BigDecimal k1 = eq.getValueAt(xi, yi);

                //System.out.println("k1 = " + k1);

                // update k2 = f(xi + (1/2)h , yi + (1/2)k1 )
                BigDecimal xi0_5h = xi.add(h.multiply(new BigDecimal("0.5")));
                BigDecimal k2 = eq.getValueAt(xi0_5h, yi.add(k1.multiply(new BigDecimal("0.5"))));

                //System.out.println("k2 = " + k2);

                // update k3 = f(xi + (1/2)h , yi + (1/2)k2 )
                BigDecimal k3 = eq.getValueAt(xi0_5h, yi.add(k2.multiply(new BigDecimal("0.5"))));

                //System.out.println("k3 = " + k3);

                // update k4 = f(xi + h , yi + k3 )
                BigDecimal k4 = eq.getValueAt(xi.add(h), yi.add(k3));

                //System.out.println("k4 = " + k4);

                // update yi+1 = yi + (h/6) [ k1 + 2k2 + 2k3 + k4 ]
                yi1 = yi.add(
                        h.divide(new BigDecimal(6), Accuracy.getValue(), RoundingMode.HALF_UP)).multiply(
                        k1.add(
                                k2.multiply(new BigDecimal(2)).add(
                                        k3.multiply(new BigDecimal(2)).add(
                                                k4))));

                // System.out.println("yi+1 = " + yi1);

                //update xi
                xi = xi.add(h);
                // update yi
                yi = yi1;
            }
            return yi1;
        }
    }
}
