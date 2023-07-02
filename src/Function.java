public class Function {
    public Function(String f) {

    }

    public double getValueAt(double x) {
        //System.out.println("Value : "+x);
        //System.out.println("Func : "+Math.log(x));
        return (1 / x);
    }

    public double getDiffAt(double x, int rank) {
        return 0;
    }
}
