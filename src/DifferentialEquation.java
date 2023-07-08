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
            double yi = y0, xi = x0;
            double yi1 = 0;
            while (xi != x) {
                yi1 = yi + eq.getValueAt(xi, yi) * h;
                xi = xi + h;
                yi = yi1;
            }
            return yi1;
        }
    }


}
