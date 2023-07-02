import java.util.ArrayList;

public class Interpolation {
    static Polynomial getGeneralMethod(Function func) {
        ArrayList<Double> xp = func.getXp();
        ArrayList<Double> yp = func.getYp();
        Matrix SE = new Matrix(Matrix.getVandermonde(xp).getData());
        for (int i = 0; i < yp.size(); i++) {
            SE.getRow(i).add(yp.get(i));
        }
        SE.setColsNum(SE.getColsNum() + 1);
        ArrayList<Double> coeffs = SE.solve();
        System.out.println("Interpolated Function using the general method : ");
        return new Polynomial(coeffs);
    }

    static Polynomial getLagrangePolyAt(int index, ArrayList<Double> xp) {
        double scalar = 1.0;
        ArrayList<Double> coeffs = new ArrayList<>();
        coeffs.add(1.0);
        Polynomial lag = new Polynomial(coeffs);
        for (int i = 0; i < xp.size(); i++) {
            if (i != index) {
                scalar *= (xp.get(index) - xp.get(i));
                coeffs = new ArrayList<>();
                coeffs.add(-1 * xp.get(i));
                coeffs.add(1.0);
                Polynomial poly = new Polynomial(coeffs);
                lag = lag.multiply(poly);
            }
        }
        lag = lag.multiply(1 / scalar);
        return lag;
    }
}
