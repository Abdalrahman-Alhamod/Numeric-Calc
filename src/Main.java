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

        ArrayList<Double> coeffs = new ArrayList<>();
        coeffs.add(2.0);
        coeffs.add(7.0);
        coeffs.add(5.0);
        Polynomial p = new Polynomial(coeffs);
        System.out.println(p.getValueAt(4));
        System.out.println(p);
        System.out.println(p.getDerivative());
        System.out.println(p.getIntegral());


    }
}