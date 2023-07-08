import java.util.Objects;

public class DifferentialEquation {
    String dy;

    public DifferentialEquation(String dy) {
        this.dy = Objects.requireNonNull(dy);
    }
    public double getValueAt(double x, double y) {
        return EvaluateString.evaluate(dy, x, y);
    }




}
