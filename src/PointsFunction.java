import java.util.ArrayList;
import java.util.Objects;

public class PointsFunction implements  Function{
    private final ArrayList<Double> xp;
    private final ArrayList<Double> yp;

    public PointsFunction(ArrayList<Double> xp, ArrayList<Double> yp) {
        this.xp = Objects.requireNonNull(xp, "xp cannot be null");
        this.yp = Objects.requireNonNull(yp, "yp cannot be null");
        if (xp.size() != yp.size()) {
            throw new ArithmeticException("number of function points mismatch");
        }
    }

    public double getValueAt(double x) {
        return yp.get(xp.indexOf(x));
    }

    public void addPoint(double x, double y) {
        xp.add(x);
        yp.add(y);
    }

    public double getDiffAt(double x, int rank) {
        return 0;
    }

    public double getIntegralAt(double x, int rank) {
        return 0;
    }

    ArrayList<Double> getXp() {
        return xp;
    }

    ArrayList<Double> getYp() {
        return yp;
    }

    @Override
    public String toString() {
        return "X : " + xp + "\nY : " + yp;
    }
}
