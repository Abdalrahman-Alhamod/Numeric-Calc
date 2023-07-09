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
            //System.out.println("fa : " + fa + " fb : " + fb );
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

}
