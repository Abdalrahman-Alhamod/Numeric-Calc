import java.util.ArrayList;
import java.util.Objects;

public class Function {

    private ArrayList<Double> xp;
    private ArrayList<Double> yp;

    private String func;

    public Function(String f) {
        func = Objects.requireNonNull(f);
    }

    public Function(ArrayList<Double> xp, ArrayList<Double> yp) {
        this.xp = Objects.requireNonNull(xp, "xp cannot be null");
        this.yp = Objects.requireNonNull(yp, "yp cannot be null");
        if (xp.size() != yp.size()) {
            throw new ArithmeticException("number of function points mismatch");
        }
    }

    public double getValueAt(double x) {
        //System.out.println("Value : " + x);
        //System.out.println("Func : " + yp.get(xp.indexOf(x)));
        if (yp != null)
            return yp.get(xp.indexOf(x));
        else {
            double res;
           // try {
                res = EvaluateString.evaluate(func, x);
            /*} catch (Exception e) {
                throw new ArithmeticException("invalid function");
            }*/
            return res;
        }
    }

    public double getDiffAt(double x, int rank) {
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
        if (func != null) {
            return "F(x) = " + func;
        } else {
            return "X : " + xp + "\nY : " + yp;
        }
    }
}
