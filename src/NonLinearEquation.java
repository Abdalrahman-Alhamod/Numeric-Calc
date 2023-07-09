import java.util.Objects;

public class NonLinearEquation {
    private final String eqs;

    public NonLinearEquation(String eq) {
        this.eqs = Objects.requireNonNull(eq);
    }

    public double getValueAt(double x) {
        double res = EvaluateString.evaluate(eqs, x);
        //Rounding value back to fix floating-point precision errors
        res = Math.round(res * 1e10) / 1e10;
        return res;
    }

    public double getValueAt(double x, double y) {
        double res = EvaluateString.evaluate(eqs, x, y);
        //Rounding value back to fix floating-point precision errors
        res = Math.round(res * 1e10) / 1e10;
        return res;
    }

    public static class Bisection {
        public static double solve(NonLinearEquation eq, double a, double b, double e) {
            double fa = eq.getValueAt(a), fb = eq.getValueAt(b);
            double c = a;
            while (Math.abs(a - b) >= e) {
                //System.out.println("a : " + a + " b : " + b + " c : " + c);
                c = (a + b) / 2;
                //Rounding value back to fix floating-point precision errors
                c = Math.round(c * 1e10) / 1e10;
                double fc = eq.getValueAt(c);
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

        public static double solve(NonLinearEquation eq, double a, double b) {
            return solve(eq, a, b, 0.000000001);
        }
    }

    public static class FalsePosition {
        public static double solve(NonLinearEquation eq, double a, double b, double e) {
            double fa = eq.getValueAt(a), fb = eq.getValueAt(b);
            double c = a;
            while (Math.abs(a - b) >= e) {
                c = ((a * fb) - (b * fa)) / (fb - fa);
                //Rounding value back to fix floating-point precision errors
                c = Math.round(c * 1e10) / 1e10;
                //System.out.println("a : " + a + " b : " + b + " c : " + c);
                double fc = eq.getValueAt(c);
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

        public static double solve(NonLinearEquation eq, double a, double b) {
            return solve(eq, a, b, 0.000000001);
        }
    }

    public static class Secant {
        public static double solve(NonLinearEquation eq, double x0, double x1, double e) {
            double xi_1 = x0, xi = x1;
            double fxi_1 = eq.getValueAt(x0), fxi = eq.getValueAt(x1);
            double xi1 = xi_1;
            while (true) {
                xi1 = xi - ((xi - xi_1) / (fxi - fxi_1)) * fxi;
                //Rounding value back to fix floating-point precision errors
                xi1 = Math.round(xi1 * 1e10) / 1e10;
                //System.out.println("xi-1 : " + xi_1 + " xi : " + xi + " xi+1 : " + xi1);
                double fxi1 = eq.getValueAt(xi1);
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

        public static double solve(NonLinearEquation eq, double a, double b) {
            return solve(eq, a, b, 0.000000001);
        }
    }

}
