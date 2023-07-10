import java.util.ArrayList;

public class SystemOfNonLinearEquations {
    public static class Newton_Raphson {
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
