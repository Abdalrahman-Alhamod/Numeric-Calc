import java.util.ArrayList;
import java.util.Objects;

public class DifferentialEquation {
    String dy;

    public DifferentialEquation(String dy) {
        this.dy = Objects.requireNonNull(dy);
    }

    public double getValueAt(double x, double y) {
        double ans = EvaluateString.evaluate(dy, x, y);
        //Rounding value back to fix floating-point precision errors
        ans = Math.round(ans * 1e10) / 1e10;
        return ans;
    }

    public static class Euler {
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

    public static class Taylor {
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

    public static class MidPoint {
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

        public static class ModifiedEuler {
            public static double solve(DifferentialEquation eq, double x0, double y0, double h, double x) {
                return MidPoint.solve(eq, x0, y0, h, x, 1);
            }
        }

        public static class Heun {
            public static double solve(DifferentialEquation eq, double x0, double y0, double h, double x) {
                return MidPoint.solve(eq, x0, y0, h, x, 0.5);
            }
        }

        public static class Ralston {
            public static double solve(DifferentialEquation eq, double x0, double y0, double h, double x) {
                return MidPoint.solve(eq, x0, y0, h, x, ((double) 2 / 3));
            }
        }
    }

    public static class Runge_Kutta {
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
