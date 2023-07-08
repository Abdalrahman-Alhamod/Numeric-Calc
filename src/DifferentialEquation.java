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
            double yi = y0, xi = x0,yi1 = 0;
            while (xi != x) {//if current value xi != x => continue
                // get yi+1 = yi + yi' * h
                yi1 = yi + eq.getValueAt(xi, yi) * h;
                // update xi
                xi = xi + h;
                // update yi
                yi = yi1;
            }
            return yi1;
        }
    }



}
