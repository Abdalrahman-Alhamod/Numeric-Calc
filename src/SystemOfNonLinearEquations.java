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
        public static ArrayList<ArrayList<Double>> solve(ExpressionFunction fx, ExpressionFunction dfdx, ExpressionFunction dfdy, ExpressionFunction gx, ExpressionFunction dgdx, ExpressionFunction dgdy, double x0, double y0, int iterations) {
            double xi = x0, yi = y0;
            ArrayList<Double> xip = new ArrayList<>();
            ArrayList<Double> yip = new ArrayList<>();
            while (iterations > 0) {

                double fx_xi_yi = fx.getValueAt(xi, yi), gx_xi_yi = gx.getValueAt(xi, yi);
                //System.out.println("f(xi,yi) : " + fx_xi_yi + " g(xi,yi) : " + gx_xi_yi);

                double dfdx_xi_yi = dfdx.getValueAt(xi, yi), dfdy_xi_yi = dfdy.getValueAt(xi, yi);
                //System.out.println("df(xi,yi)/dx : " + dfdx_xi_yi + " df(xi,yi)/dy : " + dfdy_xi_yi);

                double dgdx_xi_yi = dgdx.getValueAt(xi, yi), dgdy_xi_yi = dgdy.getValueAt(xi, yi);
                //System.out.println("dg(xi,yi)/dx : " + dgdx_xi_yi + " dg(xi,yi)/dy : " + dgdy_xi_yi);

                double j = (dfdx_xi_yi * dgdy_xi_yi) - (dgdx_xi_yi * dfdy_xi_yi);
                //System.out.println("j : " + j);

                double xi1 = xi - (1 / j) * ((fx_xi_yi * dgdy_xi_yi) - (gx_xi_yi * dfdy_xi_yi));
                //Rounding value back to fix floating-point precision errors
                xi1 = Math.round(xi1 * 1e10) / 1e10;
                //System.out.println("xi+1 : " + xi1);
                xip.add(xi1);

                double yi1 = yi + (1 / j) * ((fx_xi_yi * dgdx_xi_yi) - (gx_xi_yi * dfdx_xi_yi));
                //Rounding value back to fix floating-point precision errors
                yi1 = Math.round(yi1 * 1e10) / 1e10;
                ///System.out.println("yi+1 : " + yi1);
                yip.add(yi1);

                xi = xi1;
                yi = yi1;

                iterations--;

            }
            ArrayList<ArrayList<Double>> xy = new ArrayList<>();
            xy.add(xip);
            xy.add(yip);
            return xy;
        }
    }
}
