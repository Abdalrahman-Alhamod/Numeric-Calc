import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Interpolation {
    public static Polynomial getGeneralMethod(Function func) {
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

    private static Polynomial getLagrangePolyAt(int index, ArrayList<Double> xp) {
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

    public static Polynomial getLagrange(Function func) {
        ArrayList<Double> xp = func.getXp();
        ArrayList<Double> yp = func.getYp();
        ArrayList<Double> coeffs = new ArrayList<>();
        coeffs.add(0.0);
        Polynomial res = new Polynomial(coeffs);
        for (int i = 0; i < xp.size(); i++) {
            Polynomial lag = getLagrangePolyAt(i, xp);
            lag = lag.multiply(yp.get(i));
            res = res.add(lag);
        }
        return res;
    }

    public static String getLagrangeNoShorthand(Function func) {
        ArrayList<Double> xp = func.getXp();
        ArrayList<Double> yp = func.getYp();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < yp.size(); i++) {
            double scalar = 1.0;
            for (int j = 0; j < xp.size(); j++) {
                if (j != i) {
                    scalar *= (xp.get(i) - xp.get(j));
                }
            }
            scalar = 1 / scalar;
            scalar *= yp.get(i);
            if (scalar != 1.0) {
                sb.append(scalar);
                sb.append(" ");
            }
            for (int j = 0; j < xp.size(); j++) {
                if (j != i) {
                    ArrayList<Double> coeffs = new ArrayList<>();
                    coeffs.add(-1 * xp.get(j));
                    coeffs.add(1.0);
                    Polynomial poly = new Polynomial(coeffs);
                    sb.append('(');
                    sb.append(poly);
                    sb.append(')');
                }
            }
            if (i != yp.size() - 1) {
                sb.append(" + ");
            }
        }
        return sb.toString();
    }

    public static ArrayList<Double> getNewtonGregoryForwardTable(Function func) {
        ArrayList<Double> yp = func.getYp();
        ArrayList<Double> res = new ArrayList<>();
        res.add(yp.get(0));
        Queue<Double> q = new LinkedList<>(yp);
        int n = yp.size(), i = 0;
        double yi, yi1, temp;
        while (q.size() > 1) {
            yi = q.poll();
            yi1 = q.element();
            temp = yi1 - yi;
            temp = Math.round(temp * 1e10) / 1e10; //Rounding value back to fix floating-point precision errors
            q.add(temp);
            if (i == 0) {
                res.add(temp);
            }
            i++;
            if (i == n - 1) {
                i = 0;
                n--;
                q.poll();
            }
        }
        return res;
    }

    public static ArrayList<Double> getNewtonGregoryBackwardTable(Function func) {
        ArrayList<Double> yp = func.getYp();
        ArrayList<Double> res = new ArrayList<>();
        res.add(yp.get(yp.size() - 1));
        Queue<Double> q = new LinkedList<>(yp);
        int n = yp.size(), i = 0;
        double yi, yi1, temp;
        while (q.size() > 1) {
            yi = q.poll();
            yi1 = q.element();
            temp = yi1 - yi;
            temp = Math.round(temp * 1e10) / 1e10; //Rounding value back to fix floating-point precision errors
            q.add(temp);
            if (i == n-2) {
                res.add(temp);
            }
            i++;
            if (i == n - 1) {
                i = 0;
                n--;
                q.poll();
            }
        }
        return res;
    }
}
