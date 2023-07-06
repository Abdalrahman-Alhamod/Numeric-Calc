import java.util.Objects;

public class ExpressionFunction extends Function {
    private final String func;

    public ExpressionFunction(String f) {
        func = Objects.requireNonNull(f);
    }

    public double getValueAt(double x) {
        double res;
        try {
            res = EvaluateString.evaluate(func, x);
        } catch (Exception e) {
            throw new ArithmeticException("invalid function");
        }
        return res;
    }

    public double getDiffAt(double x, int rank) {
        return 0;
    }

    public double getIntegralAt(double x, int rank) {
        return 0;
    }

    @Override
    public String toString() {
       return "F(x) = " + func;
    }
}
