import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Double.*;

public class Main {
    public static void main(String[] args) {
        /*Scanner in = new Scanner(System.in);
        double a, b, n, temp;
        ArrayList<Double> xp = new ArrayList<>(), yp = new ArrayList<>();
        System.out.print("Enter number of points : ");
        n = in.nextDouble();
        n--; //Because n is the number of domains ; number of domains = number of points -1
        for (int i = 0; i <= n; i++) {
            System.out.print("Enter x" + i + " : ");
            temp = parseDouble(in.next());
            xp.add(temp);
            System.out.print("Enter y" + i + " : ");
            temp = parseDouble(in.next());
            yp.add(temp);
        }
        Function func = new Function(xp, yp);
        a = xp.get(0);
        b = xp.get(xp.size() - 1);
        System.out.println("xp : " + xp);
        System.out.println("yp : " + yp);
        System.out.println("a = " + a + "\nb = " + b + "\nn = " + n);
        System.out.println("Function Integral : ");
        System.out.println("Rect : " + Integral.getRect(func, a, b, n));
        System.out.println("Traps : " + Integral.getTraps(func, a, b, n));
        System.out.println("Simpson 1/3 : " + Integral.getSimpson3(func, a, b, n));
        System.out.println("Simpson 3/8 : " + Integral.getSimpson8(func, a, b, n));
        System.out.println("Paul : " + Integral.getPaul(func, a, b, n));*/
        // 0 0 0.1 0.0001 0.2 0.0016 0.3 0.0081 0.4 0.0256 0.5 0.0625 0.6 0.1296

        /*ArrayList<Double> coeffs = new ArrayList<>();
        coeffs.add(-4.0);
        coeffs.add(5.0);
        coeffs.add(0.0);
        coeffs.add(7.0);
        Polynomial p = new Polynomial(coeffs);
        System.out.println(p.getValueAt(0.4));
        System.out.println(p);
        System.out.println(p.getDerivative());
        System.out.println(p.getIntegral());*/

        ArrayList<Double> r1 = new ArrayList<>();
        r1.add(1.2);
        r1.add(5.7);
        r1.add(2.4);
        r1.add(13.9);
        ArrayList<Double> r2 = new ArrayList<>();
        r2.add(1.2);
        r2.add(2.4);
        r2.add(13.9);
        r2.add(5.7);
        ArrayList<Double> r3 = new ArrayList<>();
        r3.add(13.9);
        r3.add(1.2);
        r3.add(2.4);
        r3.add(5.7);
        ArrayList<Double> r4 = new ArrayList<>();
        r4.add(1.2);
        r4.add(2.4);
        r4.add(5.7);
        r4.add(13.9);
        ArrayList<ArrayList<Double>>a=new ArrayList<>();
        a.add(r1);
        a.add(r2);
        a.add(r3);
        a.add(r4);
        Matrix m = new Matrix(a);
        System.out.println(m);
        System.out.println(m.add(1));
        System.out.println(m.multiply(2));
        System.out.println(m.assign(2));
        System.out.println(m.subtract(6));
        System.out.println(m.getRow(1));
        System.out.println(m.getData());
        System.out.println(m.assign(m.add(1)));
        System.out.println(m.multiply(m));
        System.out.println(m.add(m));
        System.out.println(m.subtract(m));


    }
}