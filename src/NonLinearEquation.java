import java.util.Objects;

public class NonLinearEquation {
    private final String eq;

    public NonLinearEquation(String eq) {
        this.eq = Objects.requireNonNull(eq);
    }

    public double getValueAt(double x) {
        return EvaluateString.evaluate(eq, x);
    }

    public double getValueAt(double x, double y) {
        return EvaluateString.evaluate(eq, x, y);
    }

}
