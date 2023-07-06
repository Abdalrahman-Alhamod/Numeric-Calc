import java.util.ArrayList;
import java.util.Objects;

public class ExpressionFunction implements Function {
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

    public PointsFunction toPointsFunction(double a, double b, double n) {
        double h = (a + b) / n;
        //Rounding value back to fix floating-point precision errors
        h = Math.round(h * 1e10) / 1e10;
        ArrayList<Double> xp = new ArrayList<>();
        ArrayList<Double> yp = new ArrayList<>();
        for (double i = a; i <= b; i += h) {
            //Rounding value back to fix floating-point precision errors
            i = Math.round(i * 1e10) / 1e10;
            xp.add(i);
            yp.add(getValueAt(i));
        }
        return new PointsFunction(xp,yp);
    }

    @Override
    public String toString() {
        return "F(x) = " + func;
    }
}
