import java.util.Objects;

public class NonLinearEquation {
    private final String eqs;

    public NonLinearEquation(String eq) {
        this.eqs = Objects.requireNonNull(eq);
    }

    public double getValueAt(double x) {
        return EvaluateString.evaluate(eqs, x);
    }

    public double getValueAt(double x, double y) {
        return EvaluateString.evaluate(eqs, x, y);
    }

    public static class Bisection {
        public static double solve(NonLinearEquation eq, double a, double b, double e) {
            double fa = eq.getValueAt(a), fb = eq.getValueAt(b);
            //System.out.println("fa : " + fa + " fb : " + fb);
            double c = a;
            while (Math.abs(a - b) >= e) {
                //System.out.println("a : " + a + " b : " + b + " c : " + c);
                c = (a + b) / 2;
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
    }

}
