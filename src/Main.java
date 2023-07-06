import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Double.parseDouble;

public class Main {
    public static void main(String[] args) {

        //Testing Integral
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
        System.out.println("Paul : " + Integral.getPaul(func, a, b, n));
        // 0 0 0.1 0.0001 0.2 0.0016 0.3 0.0081 0.4 0.0256 0.5 0.0625 0.6 0.1296*/

        //Testing Polynomial
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

        //Testing Matrix
        /*ArrayList<Double> r1 = new ArrayList<>();
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
        System.out.println(m.subtract(m));*/

        //Testing Matrix Solve
        /*ArrayList<Double> r1 = new ArrayList<>();
        r1.add(1.0);
        r1.add(1.0);
        r1.add(1.0);
        r1.add(3.0);
        ArrayList<Double> r2 = new ArrayList<>();
        r2.add(1.0);
        r2.add(2.0);
        r2.add(4.0);
        r2.add(1.0);
        ArrayList<Double> r3 = new ArrayList<>();
        r3.add(1.0);
        r3.add(3.0);
        r3.add(9.0);
        r3.add(5.0);
        ArrayList<ArrayList<Double>> a = new ArrayList<>();
        a.add(r1);
        a.add(r2);
        a.add(r3);
        Matrix m = new Matrix(a);
        System.out.println(m);
        System.out.println(m.solve());*/

        //Testing Vandermonde
        /*ArrayList<Double> r1 = new ArrayList<>();
        r1.add(1.0);
        r1.add(2.0);
        r1.add(3.0);
        r1.add(4.0);
        System.out.println(Matrix.getVandermonde(r1));*/

        //Testing General Method
        /*Scanner in = new Scanner(System.in);
        double n, temp;
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
        System.out.println(Interpolation.GeneralMethod.getIFAP(func));
        // 1 3 2 1 3 5
        // 11.0 + -11.0x + 3.0x^2  */

        //Testing Polynomial Multiplying
        /*ArrayList<Double> a1 = new ArrayList<>();
        a1.add(1.0);
        Polynomial p1 = new Polynomial(a1);
        ArrayList<Double> a2 = new ArrayList<>();
        a2.add(-1.0);
        a2.add(2.0);
        Polynomial p2 = new Polynomial(a2);
        p1 = p1.multiply(p2);
        ArrayList<Double> a3 = new ArrayList<>();
        a3.add(1.0);
        a3.add(3.0);
        Polynomial p3 = new Polynomial(a3);
        p1 = p1.multiply(p3);
        System.out.println(p1);
        System.out.println(p1.multiply(3));*/

        //Testing Lagrange Method
        /*Scanner in = new Scanner(System.in);
        double n, temp;
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
        System.out.println("Interpolated Function using Lagrange method : ");
        System.out.println(Interpolation.Lagrange.getIFAP(func));
        System.out.println("Interpolated Function using Lagrange method without shorthand : ");
        System.out.println(Interpolation.Lagrange.getIFASNS(func));
        // 1 2 2 -1 4 3  */

        //Testing Newton Gregory methods
        /*Scanner in = new Scanner(System.in);
        double n, temp;
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
        System.out.print("Enter Degree : ");
        int degree = in.nextInt();
        System.out.println();
        System.out.println("Upper diameter (▲Yn) Newton Gregory Forward : ");
        System.out.println(Interpolation.Newton_GregoryForwardSubtractions.getUDV(func));
        System.out.println();
        System.out.println("Lower diameter (▼Yn) Newton Gregory Backward : ");
        System.out.println(Interpolation.Newton_GregoryBackwardSubtractions.getLDV(func));
        System.out.println();
        System.out.println("Interpolated Function using Newton Gregory Forward : ");
        System.out.println(Interpolation.Newton_GregoryForwardSubtractions.getIFAP(func, degree));
        System.out.println();
        System.out.println("Interpolated Function using Newton Gregory Forward No Shorthand : ");
        System.out.println(Interpolation.Newton_GregoryForwardSubtractions.getIFASNS(func, degree));
        System.out.println();
        System.out.println("Interpolated Function using Newton Gregory Backward : ");
        System.out.println(Interpolation.Newton_GregoryBackwardSubtractions.getIFAP(func, degree));
        System.out.println();
        System.out.println("Interpolated Function using Newton Gregory Backward No Shorthand : ");
        System.out.println(Interpolation.Newton_GregoryBackwardSubtractions.getIFASNS(func, degree));
        //0 -6 1 2 2 -2 3 6
        // 0 0 0.2 0.203 0.4 0.423 0.6 0.684 0.8 1.030 1 1.557 */

        //Testing Newton Divides
        /*Scanner in = new Scanner(System.in);
        double n, temp;
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
        System.out.print("Enter Degree : ");
        int degree = in.nextInt();
        System.out.println();
        System.out.println("Upper diameter (▲Yn) Newton Divides Forward : ");
        System.out.println(Interpolation.NewtonForwardDividedSubtractions.getUDV(func));
        System.out.println();
        System.out.println("Lower diameter (▼Yn) Newton Divides Backward : ");
        System.out.println(Interpolation.NewtonBackwardDividedSubtractions.getLDV(func));
        System.out.println();
        System.out.println("Interpolated Function using Newton Divides Forward : ");
        System.out.println(Interpolation.NewtonForwardDividedSubtractions.getIFAP(func, degree));
        System.out.println();
        System.out.println("Interpolated Function using Newton Divides Forward No Shorthand : ");
        System.out.println(Interpolation.NewtonForwardDividedSubtractions.getIFASNS(func, degree));
        System.out.println();
        System.out.println("Interpolated Function using Newton Divides Backward : ");
        System.out.println(Interpolation.NewtonBackwardDividedSubtractions.getIFAP(func, degree));
        System.out.println();
        System.out.println("Interpolated Function using Newton Divides Backward No Shorthand : ");
        System.out.println(Interpolation.NewtonBackwardDividedSubtractions.getIFASNS(func, degree));
        // n=4 5 53 3 19 4 30 1 9
        // n=6 0 132.651 0.2 140.877 0.3 157.464 0.4 166.375 0.7 195.112 0.9 216 */

        //Testing Least Squares
        /*Scanner in = new Scanner(System.in);
        double n, temp;
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
        System.out.print("Enter Degree : ");
        int degree = in.nextInt();
        System.out.println();
        System.out.println("Interpolated Function using Least-Squares : ");
        System.out.println(Interpolation.LeastSquares.getIFAP(func, degree));
        // n=6 0 1 2 5.1 4 9 6 13 8 17 10 21 */

        //Testing Spline
        /*Scanner in = new Scanner(System.in);
        double n, temp;
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
        System.out.println();
        ArrayList<Polynomial> ans = Interpolation.Spline.getIFAPs(func);
        System.out.println("Interpolated Function S(x) using Spline : ");
        for (int i = 0; i < ans.size(); i++) {
            System.out.println("S" + i + "(x) = " + ans.get(i) + "\t\t" + xp.get(i) + " <= x <= " + xp.get(i + 1));
        }
        // n=4  1 2 2 -1 3 0 4 2 */

        //Testing Interpolation
        /*Scanner in = new Scanner(System.in);
        double n, temp;
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
        int degree = xp.size() - 1;
        System.out.println();
        System.out.println("Interpolation Function using The General Method : ");
        System.out.println(Interpolation.GeneralMethod.getIFAP(func));
        System.out.println();
        System.out.println("Interpolated Function using Lagrange method : ");
        System.out.println(Interpolation.Lagrange.getIFAP(func));
        System.out.println();
        System.out.println("Interpolated Function using Newton-Gregory Forward : ");
        System.out.println(Interpolation.Newton_GregoryForwardSubtractions.getIFAP(func, degree));
        System.out.println();
        System.out.println("Interpolated Function using Newton-Gregory Backward : ");
        System.out.println(Interpolation.Newton_GregoryBackwardSubtractions.getIFAP(func, degree));
        System.out.println();
        System.out.println("Interpolated Function using Newton Divides Forward : ");
        System.out.println(Interpolation.NewtonForwardDividedSubtractions.getIFAP(func, degree));
        System.out.println();
        System.out.println("Interpolated Function using Newton Divides Backward : ");
        System.out.println(Interpolation.NewtonBackwardDividedSubtractions.getIFAP(func, degree));
        System.out.println();
        System.out.println("Interpolated Function using Least-Squares : ");
        System.out.println(Interpolation.LeastSquares.getIFAP(func, degree));
        System.out.println();
        ArrayList<Polynomial> ans = Interpolation.Spline.getIFAPs(func);
        System.out.println("Interpolated Function S(x) using Spline : ");
        for (int i = 0; i < ans.size(); i++) {
            System.out.println("S" + i + "(x) = " + ans.get(i) + "\t\t" + xp.get(i) + " <= x <= " + xp.get(i + 1));
        } */

        //Testing Differentiation
        //Lagrange
        /*Scanner in = new Scanner(System.in);
        double n, temp;
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
        System.out.print("Enter a : ");
        double a = parseDouble(in.next());
        System.out.println();
        System.out.println("Differentiation Function using Lagrange : ");
        System.out.println(Differentiation.Lagrange.getDFAP(func));
        System.out.println();
        System.out.print("Differentiation Function Value at the giving a number : \nans = ");
        System.out.println(Differentiation.Lagrange.getDFAP(func).getValueAt(a));*/

        //Newton-Gregory
        /*Scanner in = new Scanner(System.in);
        double n, temp;
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
        System.out.print("Enter the degree of the required Polynomial : ");
        int degree = in.nextInt();
        System.out.print("Enter the rank of the required differentiation : ");
        int rank = in.nextInt();
        System.out.print("Enter a : ");
        double a = parseDouble(in.next());
        System.out.println();
        System.out.println("Differentiation Function using Newton-Gregory Forward Subtraction : ");
        Polynomial ans = Differentiation.Newton_GregoryForwardSubtractions.getIFAP(func, degree, rank);
        System.out.println(ans);
        System.out.println();
        System.out.print("Differentiation Function Value at the giving a number : \nans = ");
        System.out.println(ans.getValueAt(a));
        System.out.println();
        System.out.println("Differentiation Function using Newton-Gregory Backward Subtraction : ");
        ans = Differentiation.Newton_GregoryForwardSubtractions.getIFAP(func, degree, rank);
        System.out.println(ans);
        System.out.println();
        System.out.print("Differentiation Function Value at the giving a number : \nans = ");
        System.out.println(ans.getValueAt(a)); */

        // Central,Forward,Backward Subtraction
        /*Scanner in = new Scanner(System.in);
        double n, temp;
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
        System.out.println(func);
        System.out.print("Enter a : ");
        double a = parseDouble(in.next());
        System.out.println();
        System.out.print("Differentiation answer using Central Subtractions : \nans = ");
        System.out.println(Differentiation.Subtractions.Central.getValueAt(func, a));
        System.out.println();
        System.out.print("Differentiation answer using Forward Subtractions : \nans = ");
        System.out.println(Differentiation.Subtractions.Forward.getValueAt(func, a));
        System.out.println();
        System.out.print("Differentiation answer using Backward Subtractions : \nans = ");
        System.out.println(Differentiation.Subtractions.Backward.getValueAt(func, a));*/

        //Testing Function Value
        /*Scanner in = new Scanner(System.in);
        System.out.print("Enter function : ");
        String s = in.next();
        Function f = new ExpressionFunction(s);
        System.out.print("Enter x : ");
        double x = parseDouble(in.next());
        System.out.println(f);
        System.out.println("F(" + x + ") = " + f.getValueAt(x));*/

        //Testing Polynomial getDiffAt and getIntegralAt
        ArrayList<Double> coeffs = new ArrayList<>();
        coeffs.add(5.0);
        coeffs.add(-4.0);
        coeffs.add(6.0);
        Polynomial p = new Polynomial(coeffs);
        System.out.println(p);
        System.out.println(p.getDiffAt(1,2));
        System.out.println(p.getIntegralAt(1,1));



    }

}