import java.util.ArrayList;
import java.util.Objects;

public class Function {

    private ArrayList<Double> xp;
    private ArrayList<Double> yp;

    public Function(String f) {

    }

    public Function(ArrayList<Double> xp, ArrayList<Double> yp) {
        this.xp = Objects.requireNonNull(xp, "xp cannot be null");
        this.yp = Objects.requireNonNull(yp, "yp cannot be null");
        ;
    }

    public double getValueAt(double x) {
        //System.out.println("Value : " + x);
        //System.out.println("Func : " + yp.get(xp.indexOf(x)));
        return yp.get(xp.indexOf(x));
    }

    public double getDiffAt(double x, int rank) {
        return 0;
    }

    ArrayList<Double> getXp(){
        return xp;
    }
    ArrayList<Double> getYp(){
        return yp;
    }
}
