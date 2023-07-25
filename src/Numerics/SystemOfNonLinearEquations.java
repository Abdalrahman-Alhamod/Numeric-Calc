package Numerics;

import Functions.ExpressionFunction;
import Util.Accuracy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * The SystemOfNonLinearEquations class provides methods for solving a system of nonlinear equations using the Newton-Raphson method.
 */
public abstract class SystemOfNonLinearEquations {
    /**
     * The Newton_Raphson class provides a method for solving a system of nonlinear equations using the Newton-Raphson method.
     */
    public static class Newton_Raphson {
        /**
         * Solves a system of nonlinear equations using the Newton-Raphson method.
         *
         * @param fx         The expression function representing the first equation.
         * @param dfdx       The expression function representing the derivative of the first equation with respect to x.
         * @param dfdy       The expression function representing the derivative of the first equation with respect to y.
         * @param gx         The expression function representing the second equation.
         * @param dgdx       The expression function representing the derivative of the second equation with respect to x.
         * @param dgdy       The expression function representing the derivative of the second equation with respect to y.
         * @param x0         The initial value for x.
         * @param y0         The initial value for y.
         * @param iterations The number of iterations to perform.
         * @return A list of lists containing the values of x and y at each iteration.
         */
        public static ArrayList<ArrayList<BigDecimal>> solve(ExpressionFunction fx, ExpressionFunction dfdx, ExpressionFunction dfdy, ExpressionFunction gx, ExpressionFunction dgdx, ExpressionFunction dgdy, BigDecimal x0, BigDecimal y0, int iterations) {
            BigDecimal xi = x0, yi = y0;
            ArrayList<BigDecimal> xip = new ArrayList<>();
            ArrayList<BigDecimal> yip = new ArrayList<>();
            while (iterations > 0) {

                BigDecimal fx_xi_yi = fx.getValueAt(xi, yi), gx_xi_yi = gx.getValueAt(xi, yi);
                //System.out.println("f(xi,yi) : " + fx_xi_yi + " g(xi,yi) : " + gx_xi_yi);

                BigDecimal dfdx_xi_yi = dfdx.getValueAt(xi, yi), dfdy_xi_yi = dfdy.getValueAt(xi, yi);
                //System.out.println("df(xi,yi)/dx : " + dfdx_xi_yi + " df(xi,yi)/dy : " + dfdy_xi_yi);

                BigDecimal dgdx_xi_yi = dgdx.getValueAt(xi, yi), dgdy_xi_yi = dgdy.getValueAt(xi, yi);
                //System.out.println("dg(xi,yi)/dx : " + dgdx_xi_yi + " dg(xi,yi)/dy : " + dgdy_xi_yi);

                BigDecimal j = (dfdx_xi_yi.multiply(dgdy_xi_yi)).subtract(dgdx_xi_yi.multiply(dfdy_xi_yi));
                //System.out.println("j : " + j);

                BigDecimal xi1 = xi.subtract(
                        (
                                new BigDecimal(1).divide(
                                        j
                                        , Accuracy.getValue() + 3, RoundingMode.HALF_UP)
                        ).multiply(
                                (
                                        fx_xi_yi.multiply(
                                                dgdy_xi_yi
                                        )
                                ).subtract(
                                        gx_xi_yi.multiply(
                                                dfdy_xi_yi
                                        )
                                )
                        )
                );
                xi1 = xi1.round(new MathContext(Accuracy.getValue(), RoundingMode.HALF_UP));
                System.out.println("xi+1 : " + xi1);
                xip.add(xi1);

                BigDecimal yi1 = yi.add(
                        (
                                new BigDecimal(1).divide(
                                        j
                                        , Accuracy.getValue() + 3, RoundingMode.HALF_UP
                                )
                        ).multiply(
                                (
                                        fx_xi_yi.multiply(
                                                dgdx_xi_yi
                                        )
                                ).subtract(
                                        gx_xi_yi.multiply(
                                                dfdx_xi_yi
                                        )
                                )
                        )
                );
                yi1 = yi1.round(new MathContext(Accuracy.getValue(), RoundingMode.HALF_UP));
                System.out.println("yi+1 : " + yi1);
                yip.add(yi1);

                xi = xi1;
                yi = yi1;

                iterations--;

            }
            ArrayList<ArrayList<BigDecimal>> xy = new ArrayList<>();
            xy.add(xip);
            xy.add(yip);
            return xy;
        }
    }
}
