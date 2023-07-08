import java.util.ArrayList;
import java.util.Objects;

public class DifferentialEquation {
    String dy;

    public DifferentialEquation(String dy) {
        this.dy = Objects.requireNonNull(dy);
    }

    public double getValueAt(double x, double y) {
        return EvaluateString.evaluate(dy, x, y);
    }

    public static class Euler {
        public static double solve(DifferentialEquation eq, double x0, double y0, double h, double x) {
            // init yi = y0 , xi = x0,  yi+1 = 0
            double yi = y0, xi = x0, yi1 = 0;
            while (xi != x) {//if current value xi != x => continue
                // get yi+1 = yi + yi' * h
                yi1 = yi + eq.getValueAt(xi, yi) * h;
                //System.out.println(yi1);
                // update xi
                xi = xi + h;
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
                    // update ans = yi + ( yi'* h ) / 1! + ( yi'' * h^2 ) / 2! + ( yi''' * h^3 ) / 3! ...
                    sum += eqs.get(i).getValueAt(xi, yi) * Math.pow(h, i + 1) * (1 / factor);
                }
                // update yi+1
                yi1 = sum;
                //System.out.println(yi1);
                //update xi
                xi = xi + h;
                // update yi
                yi = yi1;
            }
            return yi1;
        }

    }


}
