import java.util.ArrayList;

public class Function {

    private ArrayList<Double> xp;
    private ArrayList<Double> yp;

    public Function(String f) {

    }

    public Function(ArrayList<Double> xp, ArrayList<Double> yp) {
        this.xp = xp;
        this.yp = yp;
    }

    public double getValueAt(double x) {
        //System.out.println("Value : "+x);
        //System.out.println("Func : "+Math.log(x));
        return yp.get(xp.indexOf(x));
    }

    public double getDiffAt(double x, int rank) {
        return 0;
    }
}
