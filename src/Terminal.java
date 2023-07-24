
import Functions.*;
import Numerics.*;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Double.parseDouble;

/**
 * Terminal Class
 * <p>
 * This class provides a command-line interface for interacting with the project.
 * It handles input and output on the command line and executes the necessary
 * operations based on user commands.
 */
public class Terminal {
    /**
     * Constructs a new instance of the Terminal class.
     */
//    public Terminal() {
//        Scanner in = new Scanner(System.in);
//        int choice;
//
//
//        while (true) {
//            System.out.print("Enter :" +
//                    "\n 1- Interpolation" +
//                    "\n 2- Differentiation" +
//                    "\n 3- Integral" +
//                    "\n 4- Differential Equations" +
//                    "\n 5- Non-Linear Equation" +
//                    "\n 6- Polynomials" +
//                    "\n 7- System of Non-Linear Equations" +
//                    "\n Choice : ");
//            choice = in.nextInt();
//            switch (choice) {
//
//
//                case 1: {
//                    System.out.print("Enter :" +
//                            "\n 1- Points Function" +
//                            "\n 2- Expression Function" +
//                            "\n Choice : ");
//                    choice = in.nextInt();
//                    PointsFunction func = null;
//                    switch (choice) {
//                        case 1: {
//                            double n, temp;
//                            //Points Function
//                            ArrayList<Double> xp = new ArrayList<>(), yp = new ArrayList<>();
//                            System.out.print("Enter number of points : ");
//                            n = in.nextDouble();
//                            n--; //Because n is the number of domains ; number of domains = number of points -1
//                            for (int i = 0; i <= n; i++) {
//                                System.out.print("Enter x" + i + " : ");
//                                temp = parseDouble(in.next());
//                                xp.add(temp);
//                                System.out.print("Enter y" + i + " : ");
//                                temp = parseDouble(in.next());
//                                yp.add(temp);
//                            }
//                            func = new PointsFunction(xp, yp);
//                            break;
//                        }
//                        case 2: {
//                            double a, b;
//                            int n;
//                            in.nextLine();
//                            System.out.print("Enter function : ");
//                            String s = in.nextLine();
//                            ExpressionFunction expFunc = new ExpressionFunction(s);
//                            System.out.print("Enter a : ");
//                            a = parseDouble(in.next());
//                            System.out.print("Enter b : ");
//                            b = parseDouble(in.next());
//                            System.out.print("Enter n : ");
//                            n = in.nextInt();
//                            func = expFunc.toPointsFunction(a, b, n);
//                            break;
//                        }
//                        default: {
//                            System.out.println("invalid choice");
//                            continue;
//                        }
//                    }
//                    if (func != null) {
//                        System.out.print("Enter : " +
//                                "\n 1- General Method" +
//                                "\n 2- Lagrange Method" +
//                                "\n 3- Newton-Gregory Forward Subtractions" +
//                                "\n 4- Newton-Gregory Backward  Subtractions" +
//                                "\n 5- Newton Forward Divided Subtractions" +
//                                "\n 6- Newton Backward Divided Subtractions" +
//                                "\n 7- Least Squares" +
//                                "\n 8- Spline" +
//                                "\n Choice : ");
//                        choice = in.nextInt();
//                        switch (choice) {
//                            case 1: {
//                                System.out.println("Interpolation Function using The General Method : ");
//                                System.out.println(Interpolation.GeneralMethod.getIFAP(func));
//                                System.out.print("Enter x value : ");
//                                double x = parseDouble(in.next());
//                                System.out.print("Interpolation Function using The General Method at the given x = ");
//                                System.out.println(Interpolation.GeneralMethod.getIFAP(func).getValueAt(x));
//                                break;
//                            }
//                            case 2: {
//                                System.out.println("Interpolated Function using Lagrange method : ");
//                                System.out.println(Interpolation.Lagrange.getIFAP(func));
//                                System.out.print("Enter x value : ");
//                                double x = parseDouble(in.next());
//                                System.out.print("Interpolation Function using Lagrange at the given x = ");
//                                System.out.println(Interpolation.Lagrange.getIFAP(func).getValueAt(x));
//                                break;
//
//                            }
//                            case 3: {
//                                System.out.print("Enter degree : ");
//                                int degree = in.nextInt();
//                                System.out.println("Upper diameter (▲Yn) Newton Gregory Forward Subtractions : ");
//                                System.out.println(Interpolation.Newton_GregoryForwardSubtractions.getUDV(func));
//                                System.out.println("Interpolated Function using Newton-Gregory Forward Subtractions : ");
//                                System.out.println(Interpolation.Newton_GregoryForwardSubtractions.getIFAP(func, degree));
//                                System.out.println("Interpolated Function using Newton Gregory Forward Subtractions No Shorthand : ");
//                                System.out.println(Interpolation.Newton_GregoryForwardSubtractions.getIFASNS(func, degree));
//                                System.out.print("Enter x value : ");
//                                double x = parseDouble(in.next());
//                                System.out.print("Interpolation Function using Newton Gregory Forward Subtractions at the given x = ");
//                                System.out.println(Interpolation.Newton_GregoryForwardSubtractions.getIFAP(func, degree).getValueAt(x));
//                                break;
//                            }
//                            case 4: {
//                                System.out.print("Enter degree : ");
//                                int degree = in.nextInt();
//                                System.out.println("Lower diameter (▼Yn) Newton Gregory Backward Subtractions : ");
//                                System.out.println(Interpolation.Newton_GregoryBackwardSubtractions.getLDV(func));
//                                System.out.println("Interpolated Function using Newton Gregory Backward Subtractions : ");
//                                System.out.println(Interpolation.Newton_GregoryBackwardSubtractions.getIFAP(func, degree));
//                                System.out.println("Interpolated Function using Newton Gregory Backward Subtractions  No Shorthand : ");
//                                System.out.println(Interpolation.Newton_GregoryBackwardSubtractions.getIFASNS(func, degree));
//                                System.out.print("Enter x value : ");
//                                double x = parseDouble(in.next());
//                                System.out.print("Interpolation Function using Newton Gregory Backward Subtractions at the given x = ");
//                                System.out.println(Interpolation.Newton_GregoryBackwardSubtractions.getIFAP(func, degree).getValueAt(x));
//                                break;
//                            }
//                            case 5: {
//                                System.out.print("Enter degree : ");
//                                int degree = in.nextInt();
//                                System.out.println("Upper diameter (▲Yn) Newton Divides Forward Subtractions : ");
//                                System.out.println(Interpolation.NewtonForwardDividedSubtractions.getUDV(func));
//                                System.out.println("Interpolated Function using Newton Divides Forward Subtractions : ");
//                                System.out.println(Interpolation.NewtonForwardDividedSubtractions.getIFAP(func, degree));
//                                System.out.println("Interpolated Function using Newton Divides Forward Subtractions No Shorthand : ");
//                                System.out.println(Interpolation.NewtonForwardDividedSubtractions.getIFASNS(func, degree));
//                                System.out.print("Enter x value : ");
//                                double x = parseDouble(in.next());
//                                System.out.print("Interpolation Function using Newton Divides Forward Subtractions at the given x = ");
//                                System.out.println(Interpolation.NewtonForwardDividedSubtractions.getIFAP(func, degree).getValueAt(x));
//                                break;
//                            }
//                            case 6: {
//                                System.out.print("Enter degree : ");
//                                int degree = in.nextInt();
//                                System.out.println("Lower diameter (▼Yn) Newton Divides Backward Subtractions : ");
//                                System.out.println(Interpolation.NewtonBackwardDividedSubtractions.getLDV(func));
//                                System.out.println("Interpolated Function using Newton Divides Backward Subtractions : ");
//                                System.out.println(Interpolation.NewtonBackwardDividedSubtractions.getIFAP(func, degree));
//                                System.out.println("Interpolated Function using Newton Divides Backward No Shorthand Subtractions : ");
//                                System.out.println(Interpolation.NewtonBackwardDividedSubtractions.getIFASNS(func, degree));
//                                System.out.print("Enter x value : ");
//                                double x = parseDouble(in.next());
//                                System.out.print("Interpolation Function using Newton Divides Backward Subtractions at the given x = ");
//                                System.out.println(Interpolation.NewtonBackwardDividedSubtractions.getIFAP(func, degree).getValueAt(x));
//                                break;
//                            }
//                            case 7: {
//                                System.out.print("Enter Degree : ");
//                                int degree = in.nextInt();
//                                System.out.println("Interpolated Function using Least-Squares : ");
//                                System.out.println(Interpolation.LeastSquares.getIFAP(func, degree));
//                                System.out.print("Enter x value : ");
//                                double x = parseDouble(in.next());
//                                System.out.print("Interpolation Function using Least-Squares at the given x = ");
//                                System.out.println(Interpolation.LeastSquares.getIFAP(func, degree).getValueAt(x));
//                                break;
//                            }
//                            case 8: {
//                                ArrayList<Polynomial> ans = Interpolation.Spline.getIFAPs(func);
//                                System.out.println("Interpolated Function S(x) using Spline : ");
//                                for (int i = 0; i < ans.size(); i++) {
//                                    System.out.println("S" + i + "(x) = " + ans.get(i) + "\t\t" + func.getXp().get(i) + " <= x <= " + func.getXp().get(i + 1));
//                                }
//                                break;
//                            }
//                            default: {
//                                System.out.println("invalid choice");
//                            }
//                        }
//                    }
//                    break;
//                }
//
//
//                case 2: {
//                    System.out.print("Enter :" +
//                            "\n 1- Points Function" +
//                            "\n 2- Expression Function" +
//                            "\n Choice : ");
//                    choice = in.nextInt();
//                    PointsFunction func = null;
//                    switch (choice) {
//                        case 1: {
//                            double n, temp;
//                            //Points Function
//                            ArrayList<Double> xp = new ArrayList<>(), yp = new ArrayList<>();
//                            System.out.print("Enter number of points : ");
//                            n = in.nextDouble();
//                            n--; //Because n is the number of domains ; number of domains = number of points -1
//                            for (int i = 0; i <= n; i++) {
//                                System.out.print("Enter x" + i + " : ");
//                                temp = parseDouble(in.next());
//                                xp.add(temp);
//                                System.out.print("Enter y" + i + " : ");
//                                temp = parseDouble(in.next());
//                                yp.add(temp);
//                            }
//                            func = new PointsFunction(xp, yp);
//                            break;
//                        }
//                        case 2: {
//                            double a, b, n;
//                            in.nextLine();
//                            System.out.print("Enter function : ");
//                            String s = in.nextLine();
//                            ExpressionFunction expFunc = new ExpressionFunction(s);
//                            System.out.print("Enter a : ");
//                            a = parseDouble(in.next());
//                            System.out.print("Enter b : ");
//                            b = parseDouble(in.next());
//                            System.out.print("Enter n : ");
//                            n = parseDouble(in.next());
//                            func = expFunc.toPointsFunction(a, b, n);
//                            break;
//                        }
//                        default: {
//                            System.out.println("invalid choice");
//                            continue;
//                        }
//                    }
//                    if (func != null) {
//                        System.out.print("Enter : " +
//                                "\n 1- Lagrange" +
//                                "\n 2- Newton-Gregory Forward Subtractions" +
//                                "\n 3- Newton-Gregory Back Subtractions" +
//                                "\n 4- Central,Forward,Backward Subtractions" +
//                                "\n Choice : ");
//                        choice = in.nextInt();
//                        switch (choice) {
//                            case 1: {
//                                System.out.println("Differentiation Function using Lagrange : ");
//                                System.out.println(Differentiation.Lagrange.getDFAP(func));
//                                System.out.print("Enter x value : ");
//                                double x = parseDouble(in.next());
//                                System.out.print("Differentiation Function using Lagrange at the given x = ");
//                                System.out.println(Interpolation.Lagrange.getIFAP(func).getValueAt(x));
//                                break;
//                            }
//                            case 2: {
//                                System.out.print("Enter degree : ");
//                                int degree = in.nextInt();
//                                System.out.print("Enter rank : ");
//                                int rank = in.nextInt();
//                                System.out.println("Differentiation Function using Newton-Gregory Forward Subtraction : ");
//                                System.out.println(Differentiation.Newton_GregoryForwardSubtractions.getIFAP(func, degree, rank));
//                                System.out.print("Enter x value : ");
//                                double x = parseDouble(in.next());
//                                System.out.print("Differentiation Function using Newton-Gregory Forward Subtraction at the given x = ");
//                                System.out.println(Differentiation.Newton_GregoryForwardSubtractions.getIFAP(func, degree, rank).getValueAt(x));
//                                break;
//
//                            }
//                            case 3: {
//                                System.out.print("Enter degree : ");
//                                int degree = in.nextInt();
//                                System.out.print("Enter rank : ");
//                                int rank = in.nextInt();
//                                System.out.println("Differentiation Function using Newton-Gregory Backward Subtraction : ");
//                                System.out.println(Differentiation.Newton_GregoryBackwardSubtractions.getIFAP(func, degree, rank));
//                                System.out.print("Enter x value : ");
//                                double x = parseDouble(in.next());
//                                System.out.print("Differentiation Function using Newton-Gregory Backward Subtraction at the given x = ");
//                                System.out.println(Differentiation.Newton_GregoryBackwardSubtractions.getIFAP(func, degree, rank).getValueAt(x));
//                                break;
//                            }
//                            case 4: {
//                                System.out.print("Enter x value : ");
//                                double x = parseDouble(in.next());
//                                System.out.print("Differentiation answer using Central Subtractions : ");
//                                System.out.println(Differentiation.Subtractions.Central.getValueAt(func, x));
//                                System.out.println();
//                                System.out.print("Differentiation answer using Forward Subtractions : ");
//                                System.out.println(Differentiation.Subtractions.Forward.getValueAt(func, x));
//                                System.out.println();
//                                System.out.print("Differentiation answer using Backward Subtractions : ");
//                                System.out.println(Differentiation.Subtractions.Backward.getValueAt(func, x));
//
//                            }
//                            default: {
//                                System.out.println("invalid choice");
//                                break;
//                            }
//                        }
//                    }
//                    break;
//                }
//                case 3: {
//                    System.out.print("Enter :" +
//                            "\n 1- Points Function" +
//                            "\n 2- Expression Function" +
//                            "\n Choice : ");
//                    choice = in.nextInt();
//                    PointsFunction func = null;
//                    double a, b, n, temp;
//                    switch (choice) {
//                        case 1: {
//                            //Points Function
//                            ArrayList<Double> xp = new ArrayList<>(), yp = new ArrayList<>();
//                            System.out.print("Enter number of points : ");
//                            n = in.nextDouble();
//                            for (int i = 0; i < n; i++) {
//                                System.out.print("Enter x" + i + " : ");
//                                temp = parseDouble(in.next());
//                                xp.add(temp);
//                                System.out.print("Enter y" + i + " : ");
//                                temp = parseDouble(in.next());
//                                yp.add(temp);
//                            }
//                            a = xp.get(0);
//                            b = xp.get(xp.size() - 1);
//                            func = new PointsFunction(xp, yp);
//                            break;
//                        }
//                        case 2: {
//                            in.nextLine();
//                            System.out.print("Enter function : ");
//                            String s = in.nextLine();
//                            ExpressionFunction expFunc = new ExpressionFunction(s);
//                            System.out.print("Enter a : ");
//                            a = parseDouble(in.next());
//                            System.out.print("Enter b : ");
//                            b = parseDouble(in.next());
//                            System.out.print("Enter number of points : ");
//                            n = parseDouble(in.next());
//                            func = expFunc.toPointsFunction(a, b, n);
//                            break;
//                        }
//                        default: {
//                            System.out.println("invalid choice");
//                            continue;
//                        }
//                    }
//                    n--; //Because n is the number of domains ; number of domains = number of points -1
//                    if (func != null) {
//                        System.out.print("Enter : " +
//                                "\n 1- Rectangle" +
//                                "\n 2- Trapezoidal" +
//                                "\n 3- Simpson 1/3" +
//                                "\n 4- Simpson 3/8" +
//                                "\n 5- Paul" +
//                                "\n Choice : ");
//                        choice = in.nextInt();
//                        switch (choice) {
//                            case 1: {
//                                System.out.print("Integral Function answer using Rectangle = ");
//                                System.out.println(Integral.getRect(func, a, b, n));
//                                break;
//                            }
//                            case 2: {
//                                System.out.print("Integral Function answer using Trapezoidal = ");
//                                System.out.println(Integral.getTraps(func, a, b, n));
//                                break;
//                            }
//                            case 3: {
//                                System.out.print("Integral Function answer using Simpson 1/3 = ");
//                                System.out.println(Integral.getSimpson3(func, a, b, n));
//                                break;
//                            }
//                            case 4: {
//                                System.out.print("Integral Function answer using Simpson 3/8 = ");
//                                System.out.println(Integral.getSimpson8(func, a, b, n));
//                                break;
//                            }
//                            case 5: {
//                                System.out.print("Integral Function answer using Paul = ");
//                                System.out.println(Integral.getPaul(func, a, b, n));
//                                break;
//                            }
//                            default: {
//                                System.out.println("invalid choice");
//                                break;
//                            }
//                        }
//                    }
//                    break;
//                }
//                case 4: {
//                    System.out.print("Enter : " +
//                            "\n 1- Euler" +
//                            "\n 2- Taylor" +
//                            "\n 3- MidPoint Modified Euler" +
//                            "\n 4- MidPoint Heun" +
//                            "\n 5- MidPoint Ralston" +
//                            "\n 6- Runge-Kutta" +
//                            "\n Choice : ");
//                    choice = in.nextInt();
//                    switch (choice) {
//                        case 1: {
//                            double x0, y0, h, x;
//                            String eqSt;
//                            in.nextLine(); // consume the newline character left in the input buffer
//                            System.out.print("Enter y' : ");
//                            eqSt = in.nextLine();
//                            System.out.print("Enter x0 : ");
//                            x0 = parseDouble(in.next());
//                            System.out.print("Enter y0 : ");
//                            y0 = parseDouble(in.next());
//                            System.out.print("Enter h : ");
//                            h = parseDouble(in.next());
//                            System.out.print("Enter x : ");
//                            x = parseDouble(in.next());
//                            DifferentialEquation eq = new DifferentialEquation(eqSt);
//                            System.out.print("The solution of the differential equation using Euler = ");
//                            System.out.println(DifferentialEquation.Euler.solve(eq, x0, y0, h, x));
//                            break;
//                        }
//                        case 2: {
//                            double x0, y0, h, x;
//                            int rank;
//                            System.out.print("Enter rank : ");
//                            rank = in.nextInt();
//                            in.nextLine(); // consume the newline character left in the input buffer
//                            ArrayList<DifferentialEquation> eqs = new ArrayList<>();
//                            for (int i = 0; i < rank; i++) {
//                                System.out.print("Enter y");
//                                for (int j = 0; j <= i; j++) {
//                                    System.out.print('\'');
//                                }
//                                System.out.print(" : ");
//                                String eq = in.nextLine();
//                                eqs.add(new DifferentialEquation(eq));
//                            }
//                            System.out.print("Enter x0 : ");
//                            x0 = parseDouble(in.next());
//                            System.out.print("Enter y0 : ");
//                            y0 = parseDouble(in.next());
//                            System.out.print("Enter h : ");
//                            h = parseDouble(in.next());
//                            System.out.print("Enter x : ");
//                            x = parseDouble(in.next());
//                            System.out.print("The solution of the differential equation using Taylor = ");
//                            System.out.println(DifferentialEquation.Taylor.solve(eqs, x0, y0, h, x));
//                            break;
//                        }
//                        case 3: {
//                            double x0, y0, h, x;
//                            in.nextLine(); // consume the newline character left in the input buffer
//                            String eqSt;
//                            System.out.print("Enter y' : ");
//                            eqSt = in.nextLine();
//                            System.out.print("Enter x0 : ");
//                            x0 = parseDouble(in.next());
//                            System.out.print("Enter y0 : ");
//                            y0 = parseDouble(in.next());
//                            System.out.print("Enter h : ");
//                            h = parseDouble(in.next());
//                            System.out.print("Enter x : ");
//                            x = parseDouble(in.next());
//                            DifferentialEquation eq = new DifferentialEquation(eqSt);
//                            System.out.print("The solution of the differential equation using Modified Euler = ");
//                            System.out.println(DifferentialEquation.MidPoint.ModifiedEuler.solve(eq, x0, y0, h, x));
//                            break;
//                        }
//                        case 4: {
//                            double x0, y0, h, x;
//                            in.nextLine(); // consume the newline character left in the input buffer
//                            String eqSt;
//                            System.out.print("Enter y' : ");
//                            eqSt = in.nextLine();
//                            System.out.print("Enter x0 : ");
//                            x0 = parseDouble(in.next());
//                            System.out.print("Enter y0 : ");
//                            y0 = parseDouble(in.next());
//                            System.out.print("Enter h : ");
//                            h = parseDouble(in.next());
//                            System.out.print("Enter x : ");
//                            x = parseDouble(in.next());
//                            DifferentialEquation eq = new DifferentialEquation(eqSt);
//                            System.out.print("The solution of the differential equation using Heun = ");
//                            System.out.println(DifferentialEquation.MidPoint.Heun.solve(eq, x0, y0, h, x));
//                            break;
//                        }
//                        case 5: {
//                            double x0, y0, h, x;
//                            in.nextLine(); // consume the newline character left in the input buffer
//                            String eqSt;
//                            System.out.print("Enter y' : ");
//                            eqSt = in.nextLine();
//                            System.out.print("Enter x0 : ");
//                            x0 = parseDouble(in.next());
//                            System.out.print("Enter y0 : ");
//                            y0 = parseDouble(in.next());
//                            System.out.print("Enter h : ");
//                            h = parseDouble(in.next());
//                            System.out.print("Enter x : ");
//                            x = parseDouble(in.next());
//                            DifferentialEquation eq = new DifferentialEquation(eqSt);
//                            System.out.print("The solution of the differential equation using Ralston = ");
//                            System.out.println(DifferentialEquation.MidPoint.Ralston.solve(eq, x0, y0, h, x));
//                            break;
//                        }
//                        case 6: {
//                            double x0, y0, h, x;
//                            in.nextLine(); // consume the newline character left in the input buffer
//                            String eqSt;
//                            System.out.print("Enter y' : ");
//                            eqSt = in.nextLine();
//                            System.out.print("Enter x0 : ");
//                            x0 = parseDouble(in.next());
//                            System.out.print("Enter y0 : ");
//                            y0 = parseDouble(in.next());
//                            System.out.print("Enter h : ");
//                            h = parseDouble(in.next());
//                            System.out.print("Enter x : ");
//                            x = parseDouble(in.next());
//                            DifferentialEquation eq = new DifferentialEquation(eqSt);
//                            System.out.print("The solution of the differential equation using Runge_Kutta = ");
//                            System.out.println(DifferentialEquation.Runge_Kutta.solve(eq, x0, y0, h, x));
//                            break;
//                        }
//                        default: {
//                            System.out.println("invalid choice");
//                            break;
//                        }
//                    }
//                    break;
//                }
//                case 5: {
//                    System.out.print("Enter : " +
//                            "\n 1- Bisection" +
//                            "\n 2- False Position" +
//                            "\n 3- Secant" +
//                            "\n 4- Newton-Raphson" +
//                            "\n 5- Halley" +
//                            "\n 6- Fixed Point Iteration" +
//                            "\n Choice : ");
//                    choice = in.nextInt();
//                    switch (choice) {
//                        case 1: {
//                            double a, b, e;
//                            in.nextLine(); // consume the newline character left in the input buffer
//                            String fxS;
//                            System.out.print("Enter f(x) : ");
//                            fxS = in.nextLine();
//                            ExpressionFunction fx = new ExpressionFunction(fxS);
//                            System.out.print("Enter a : ");
//                            a = parseDouble(in.next());
//                            System.out.print("Enter b : ");
//                            b = parseDouble(in.next());
//                            System.out.print("Enter e : ");
//                            e = parseDouble(in.next());
//                            System.out.print("The solution of the non-linear equation using Bisection = ");
//                            System.out.println(NonLinearEquation.Bisection.solve(fx, a, b, e));
//                            break;
//                        }
//                        case 2: {
//                            double a, b, e;
//                            in.nextLine(); // consume the newline character left in the input buffer
//                            String fxS;
//                            System.out.print("Enter f(x) : ");
//                            fxS = in.nextLine();
//                            ExpressionFunction fx = new ExpressionFunction(fxS);
//                            System.out.print("Enter a : ");
//                            a = parseDouble(in.next());
//                            System.out.print("Enter b : ");
//                            b = parseDouble(in.next());
//                            System.out.print("Enter e : ");
//                            e = parseDouble(in.next());
//                            System.out.print("The solution of the non-linear equation using  False Position = ");
//                            System.out.println(NonLinearEquation.FalsePosition.solve(fx, a, b, e));
//                            break;
//                        }
//                        case 3: {
//                            double x0, x1, e;
//                            in.nextLine(); // consume the newline character left in the input buffer
//                            String fxS;
//                            System.out.print("Enter f(x) : ");
//                            fxS = in.nextLine();
//                            ExpressionFunction fx = new ExpressionFunction(fxS);
//                            System.out.print("Enter x0 : ");
//                            x0 = parseDouble(in.next());
//                            System.out.print("Enter x1 : ");
//                            x1 = parseDouble(in.next());
//                            System.out.print("Enter e : ");
//                            e = parseDouble(in.next());
//                            System.out.print("The solution of the non-linear equation using Secant = ");
//                            System.out.println(NonLinearEquation.Secant.solve(fx, x0, x1, e));
//                            break;
//                        }
//                        case 4: {
//                            double a, b, e;
//                            in.nextLine(); // consume the newline character left in the input buffer
//                            String fxS, dfxS;
//                            System.out.print("Enter f(x) : ");
//                            fxS = in.nextLine();
//                            ExpressionFunction fx = new ExpressionFunction(fxS);
//                            System.out.print("Enter f'(x) : ");
//                            dfxS = in.nextLine();
//                            ExpressionFunction dfx = new ExpressionFunction(dfxS);
//                            System.out.print("Enter a : ");
//                            a = parseDouble(in.next());
//                            System.out.print("Enter b : ");
//                            b = parseDouble(in.next());
//                            System.out.print("Enter e : ");
//                            e = parseDouble(in.next());
//                            System.out.print("The solution of the non-linear equation using Newton-Raphson = ");
//                            System.out.println(NonLinearEquation.Newton_Raphson.solveRange(fx, dfx, a, b, e));
//                            break;
//                        }
//                        case 5: {
//                            double x0, e;
//                            in.nextLine(); // consume the newline character left in the input buffer
//                            String fxS, dfxS, d2fxS;
//                            System.out.print("Enter f(x) : ");
//                            fxS = in.nextLine();
//                            ExpressionFunction fx = new ExpressionFunction(fxS);
//                            System.out.print("Enter f'(x) : ");
//                            dfxS = in.nextLine();
//                            ExpressionFunction dfx = new ExpressionFunction(dfxS);
//                            System.out.print("Enter f''(x) : ");
//                            d2fxS = in.nextLine();
//                            ExpressionFunction d2fx = new ExpressionFunction(d2fxS);
//                            System.out.print("Enter x0 : ");
//                            x0 = parseDouble(in.next());
//                            System.out.print("Enter e : ");
//                            e = parseDouble(in.next());
//                            System.out.print("The solution of the non-linear equation using Halley : ");
//                            System.out.println(NonLinearEquation.Halley.solve(fx, dfx, d2fx, x0, e));
//                            break;
//                        }
//                        case 6: {
//                            double x0, e;
//                            in.nextLine(); // consume the newline character left in the input buffer
//                            String gS;
//                            System.out.print("Enter g(x) : ");
//                            gS = in.nextLine();
//                            ExpressionFunction gx = new ExpressionFunction(gS);
//                            System.out.print("Enter x0 : ");
//                            x0 = parseDouble(in.next());
//                            System.out.print("Enter e : ");
//                            e = parseDouble(in.next());
//                            System.out.print("The solution of the non-linear equation using Fixed Point Iteration = ");
//                            System.out.println(NonLinearEquation.FixedPointIteration.solve(gx, x0, e));
//                            break;
//                        }
//                        default: {
//                            System.out.println("invalid choice");
//                            break;
//                        }
//                    }
//
//                    break;
//                }
//                case 6: {
//                    System.out.print("Horner's Method" +
//                            "\nEnter : " +
//                            "\n 1- To get Polynomial value at given x" +
//                            "\n 2- To get Division ans on (x-a) " +
//                            "\n 3- To get the derivative Polynomial at given x with rank" +
//                            "\n Choice : ");
//                    choice = in.nextInt();
//                    switch (choice) {
//                        case 1: {
//                            int degree;
//                            ArrayList<Double> coeffs = new ArrayList<>();
//                            System.out.print("Enter Polynomial degree : ");
//                            degree = in.nextInt();
//                            for (int i = 0; i <= degree; i++) {
//                                System.out.print("Enter a" + i + " : ");
//                                double a = parseDouble(in.next());
//                                coeffs.add(a);
//                            }
//                            System.out.print("Enter x : ");
//                            double x = parseDouble(in.next());
//                            Polynomial poly = new Polynomial(coeffs);
//                            System.out.println("Your Polynomial = " + poly);
//                            System.out.print("The value of given x using Horner = ");
//                            System.out.println(Polynomial.Horner.getValueAt(poly, x));
//                            break;
//                        }
//                        case 2: {
//                            int degree;
//                            ArrayList<Double> coeffs = new ArrayList<>();
//                            System.out.print("Enter Polynomial degree : ");
//                            degree = in.nextInt();
//                            for (int i = 0; i <= degree; i++) {
//                                System.out.print("Enter a" + i + " : ");
//                                double a = parseDouble(in.next());
//                                coeffs.add(a);
//                            }
//                            System.out.print("Enter x : ");
//                            double x = parseDouble(in.next());
//                            Polynomial poly = new Polynomial(coeffs);
//                            System.out.println("Your Polynomial = " + poly);
//                            System.out.print("The result polynomial of dividing your Polynomial on (x - " + x + ") using Horner = ");
//                            System.out.println(Polynomial.Horner.getDivideOn(poly, x));
//                            break;
//                        }
//                        case 3: {
//                            int degree;
//                            ArrayList<Double> coeffs = new ArrayList<>();
//                            System.out.print("Enter Polynomial degree : ");
//                            degree = in.nextInt();
//                            for (int i = 0; i <= degree; i++) {
//                                System.out.print("Enter a" + i + " : ");
//                                double a = parseDouble(in.next());
//                                coeffs.add(a);
//                            }
//                            System.out.print("Enter x : ");
//                            double x = parseDouble(in.next());
//                            Polynomial poly = new Polynomial(coeffs);
//                            System.out.print("Enter Differentiation degree : ");
//                            int diffDegree = in.nextInt();
//                            System.out.println("Your Polynomial = " + poly);
//                            System.out.print("The value of the differentiation at the given x = ");
//                            System.out.println(Polynomial.Horner.getDiffAt(poly, x, diffDegree));
//                            break;
//                        }
//                        default: {
//                            System.out.println("invalid choice");
//                            break;
//                        }
//                    }
//                    break;
//                }
//                case 7: {
//                    System.out.println("Newton-Raphson's Method");
//                    String fxS, dfdxS, dfdyS, gxS, dgdxS, dgdyS;
//                    in.nextLine();
//                    System.out.print("Enter fx(x) : ");
//                    fxS = in.nextLine();
//                    ExpressionFunction fx = new ExpressionFunction(fxS);
//                    System.out.print("Enter g(x) : ");
//                    gxS = in.nextLine();
//                    ExpressionFunction gx = new ExpressionFunction(gxS);
//                    System.out.print("Enter df/dx : ");
//                    dfdxS = in.nextLine();
//                    ExpressionFunction dfdx = new ExpressionFunction(dfdxS);
//                    System.out.print("Enter df/dy : ");
//                    dfdyS = in.nextLine();
//                    ExpressionFunction dfdy = new ExpressionFunction(dfdyS);
//                    System.out.print("Enter dg/dx : ");
//                    dgdxS = in.nextLine();
//                    ExpressionFunction dgdx = new ExpressionFunction(dgdxS);
//                    System.out.print("Enter dg/dy : ");
//                    dgdyS = in.nextLine();
//                    ExpressionFunction dgdy = new ExpressionFunction(dgdyS);
//                    System.out.print("Enter x0 : ");
//                    double x0 = parseDouble(in.next());
//                    System.out.print("Enter y0 : ");
//                    double y0 = parseDouble(in.next());
//                    System.out.print("Enter number of iterations : ");
//                    int iteration = in.nextInt();
//                    System.out.println("The solution of the system of non-linear equations using Newton-Raphson : ");
//                    ArrayList<ArrayList<Double>> xy = SystemOfNonLinearEquations.Newton_Raphson.solve(fx, dfdx, dfdy, gx, dgdx, dgdy, x0, y0, iteration);
//                    for (int i = 0; i < iteration; i++) {
//                        System.out.println("x" + (i + 1) + " = " + xy.get(0).get(i) + "\t y" + (i + 1) + " = " + xy.get(1).get(i));
//                    }
//                    break;
//                }
//                default: {
//                    System.out.println("invalid choice");
//                }
//            }
//        }
//    }
}
