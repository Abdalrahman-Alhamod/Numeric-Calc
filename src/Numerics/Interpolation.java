package Numerics;

import Functions.PointsFunction;
import Functions.Polynomial;
import Util.Accuracy;
import Util.BigDecimalUtil;
import Util.Matrix;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Interpolation class provides Inner classes representing various methods to get Interpolated
 * Function as a {@link Polynomial} <b>OR</b> as a {@link String}
 * and some useful methods related to the currently used Interpolation Class
 */
public abstract class Interpolation {
    /**
     * The GeneralMethod class provides methods to get the Interpolation Function using the <b>General Method</b>
     * by solving a system of equations using <a href="https://en.wikipedia.org/wiki/Gaussian_elimination">Gaussian elimination</a>
     */
    public static class GeneralMethod {
        /**
         * Returns Interpolation Function As {@link Polynomial} using the <b>General Method</b>
         *
         * @param func {@link PointsFunction} object representing the function to be interpolated
         * @return the result of Interpolation as {@link Polynomial}
         * @throws ArithmeticException if the given function is null
         */
        public static Polynomial getIFAP(PointsFunction func) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            // Get x point
            ArrayList<BigDecimal> xp = func.getXp();
            // Get y points
            ArrayList<BigDecimal> yp = func.getYp();
            //Get vandermonde Matrix
            // where vandermonde Matrix is :
            // | a0^0 a0^1 a0^2 ... a0^n |
            // | a1^0 a1^1 a1^2 ... a1^n |
            // | ...  .... .... ... .... |
            // | am^0 am^1 am^2 ... am^n |
            Matrix SE = new Matrix(Matrix.getVandermonde(xp).getData());
            for (int i = 0; i < yp.size(); i++) {
                //Add the y points to vandermonde Matrix to solve the system of equations
                SE.getRow(i).add(yp.get(i));
            }
            SE.setColsNum(SE.getColsNum() + 1); // increase the number of columns in vandermonde Matrix ; because of adding y points
            ArrayList<BigDecimal> coeffs = SE.solve(); // solve the system of equations
            return new Polynomial(coeffs);
        }
    }

    /**
     * The Lagrange class provides methods to get the Interpolation Function using <b>Lagrange</b> method
     */
    public static class Lagrange {
        /**
         * Returns <b>Lagrange Polynomial</b> at the given index
         *
         * @param index the index of the required Lagrange Polynomial (Lj)
         * @param xp    an ArrayList of x points
         * @return the result Lagrange Polynomial as {@link Polynomial}
         */
        private static Polynomial getLagrangePolyAt(int index, ArrayList<BigDecimal> xp) {
            // initialize scalar to 1 to be multiplied with a number
            BigDecimal scalar = new BigDecimal(1);
            // initialize a Polynomial with the value a0 = 1 to be multiplied with other polynomials ; the Lagrange Polynomial
            Polynomial lag = new Polynomial(new BigDecimal(1));
            for (int i = 0; i < xp.size(); i++) {
                // xi != xj ; where j is Lagrange Polynomial is created for (Lj) ; other words j = index
                if (i != index) {
                    // make scalar = (xj - xj) => (xj - x0 ) * (xj - x1 ) ... (xj - xj-1) * (xj - xj+1)
                    scalar = scalar.multiply(xp.get(index).subtract(xp.get(i)));
                    // create a Polynomial with a0 = -xi and a1 = 1 (a1*x + a0) ; (x - xi ) => (x - x0 ) * (x - x1)
                    Polynomial poly = new Polynomial(xp.get(i).multiply(new BigDecimal(-1)), new BigDecimal(1));
                    // create Lagrange Polynomial by : (x - x0 ) * (x - x1) .. (x - xj) * (x - xj+1) ..
                    lag = lag.multiply(poly);
                }
            }
            // Divide the multiplied polynomials ( (x - x0 ) * (x - x1) .. (x - xj) * (x - xj+1) ..)
            // on scalar ( (xj - x0 ) * (xj - x1 ) ... (xj - xj-1) * (xj - xj+1)..)
            // which is Lagrange Polynomial
            lag = lag.multiply(new BigDecimal(1).divide(scalar, Accuracy.getValue(), RoundingMode.HALF_UP));
            return lag;
        }

        /**
         * Returns Interpolation Function As {@link Polynomial} using <b>Lagrange</b>
         *
         * @param func {@link PointsFunction} object representing the function to be interpolated
         * @return the result of Interpolation as {@link Polynomial}
         * @throws ArithmeticException if the given function is null
         */
        public static Polynomial getIFAP(PointsFunction func) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            // get x points
            ArrayList<BigDecimal> xp = func.getXp();
            // get y point
            ArrayList<BigDecimal> yp = func.getYp();
            // initialize result Polynomial (Interpolation answer by Lagrange )
            // with value 0 to add it to other Polynomial
            Polynomial res = new Polynomial(new BigDecimal(0));
            for (int i = 0; i < xp.size(); i++) {
                //get Lagrange Polynomial at index = i (Li)
                Polynomial lag = getLagrangePolyAt(i, xp);
                //multiply Lagrange Polynomial with yi ( yi * Li)
                lag = lag.multiply(yp.get(i));
                // add the multiply result to result Polynomial (Interpolation answer by Lagrange )
                res = res.add(lag);
            }
            return res;
        }

        /**
         * Returns Interpolation Function As <b>String</b> without shorthand using <b>Lagrange</b>
         *
         * @param func {@link PointsFunction} object representing the function to be interpolated
         * @return the result of Interpolation as {@link String}
         * @throws ArithmeticException if the given function is null
         */
        public static String getIFASNS(PointsFunction func) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            // get x points
            ArrayList<BigDecimal> xp = func.getXp();
            // get y points
            ArrayList<BigDecimal> yp = func.getYp();
            // initialize a string builder to create the wanted Polynomial without shorthand
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < yp.size(); i++) {
                // initialize scalar with value 1 to multiply with other number
                BigDecimal scalar = new BigDecimal(1);
                for (int j = 0; j < xp.size(); j++) {
                    if (j != i) {
                        // make scalar = (xj - xj) => (xj - x0 ) * (xj - x1 ) ... (xj - xj-1) * (xj - xj+1)
                        scalar = scalar.multiply(xp.get(i).subtract(xp.get(j)));
                    }
                }
                // to divide on the scalar => make 1/scalar and multiply with it
                scalar = new BigDecimal(1).divide(scalar, Accuracy.getValue(), RoundingMode.HALF_UP);
                // divide yi on scalar ( (xj - x0 ) * (xj - x1 ) ... (xj - xj-1) * (xj - xj+1) )
                scalar = scalar.multiply(yp.get(i));
                // if scalar = 1.0 don't append it => don't make (1.0 x +..) do (x + ...)
                if (!scalar.equals(new BigDecimal(1))) {
                    sb.append(scalar);
                    sb.append(" ");
                }
                for (int j = 0; j < xp.size(); j++) {
                    // xj != xi ; where i is Lagrange Polynomial is created for (Li)
                    if (j != i) {
                        // create a Polynomial with a0 = -xj and a1 = 1 (a1*x + a0) ; (x - xj ) => (x - x0 ) * (x - x1)
                        Polynomial poly = new Polynomial(xp.get(j).multiply(new BigDecimal(-1)), new BigDecimal(1));
                        // append Polynomial with practices ; ( x - xj )
                        sb.append('(');
                        sb.append(poly);
                        sb.append(')');
                    }
                }
                // if it is the end of the Polynomial , don't append ' + '
                if (i != yp.size() - 1) {
                    sb.append(" + ");
                }
            }
            return sb.toString();
        }
    }

    /**
     * Newton-Gregory Forward Subtractions class provides methods for getting the interpolation function using <b>Newton-Gregory Forward Subtractions</b> method
     * <p>It also provide function to get Newton-Gregory Forward Subtractions Table values </p>
     */
    public static class Newton_GregoryForwardSubtractions {
        /**
         * Returns the values of the upper diameter of the Newton-Gregory Forward Subtractions Table
         *
         * @param func {@link PointsFunction} object representing a function to get its x and y point
         * @return the values of the upper diameter of the <b>Newton-Gregory Forward Subtractions Table</b>
         * @throws ArithmeticException if the given function is null
         */
        public static ArrayList<BigDecimal> getUDV(PointsFunction func) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            //get y points
            ArrayList<BigDecimal> yp = func.getYp();
            // initialize result ArrayList (Upper diameter values)
            ArrayList<BigDecimal> res = new ArrayList<>();
            // add y0
            res.add(yp.get(0));
            // initialize queue with the first column values ( yi )
            Queue<BigDecimal> q = new LinkedList<>(yp);
            // n : the number of the valid element to the current columns
            // i = the index of the current element
            int n = yp.size(), i = 0;
            // yi : current y value
            // yi1 : forward y value ( yi+1 )
            // temp : temporary result of yi+1 - yi
            BigDecimal yi, yi1, temp;
            while (q.size() > 1) {
                // get yi and delete it from queue
                yi = q.poll();
                // get yi+1 without deleting ; to use it in the next iteration
                yi1 = q.element();
                // get current element result
                temp = yi1.subtract(yi);
                // add the current element to the queue
                q.add(temp);
                // reaching first element of the current columns ( upper diameter element)
                if (i == 0) {
                    // reaching zeros ; no more terms
                    if (temp.equals(new BigDecimal(0)))
                        break;
                    // add the current element to result
                    res.add(temp);
                }
                // i = i+1 the index of the second element to the next iteration
                i++;
                // reaching end of valid element of the current column
                if (i == n - 1) {
                    // reset index
                    i = 0;
                    // resize the valid element in next column in the next iteration
                    n--;
                    // delete the last element of this current columns ;
                    // so it do not interfere with the next column answer
                    q.poll();
                }
            }
            return res;
        }

        /**
         * Returns Interpolation Function As {@link Polynomial} using <b>Newton-Gregory Forward Subtractions</b>
         *
         * @param func   {@link PointsFunction} object representing the function to be interpolated
         * @param degree the degree of the required Polynomial
         * @return the result of Interpolation as {@link Polynomial}
         * @throws ArithmeticException if the given function is null <b>or</b> degree is smaller than zero
         */
        public static Polynomial getIFAP(PointsFunction func, int degree) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            else if (degree < 0)
                throw new ArithmeticException("invalid inputs : degree cannot be smaller or equal to zero");
            //get x point
            ArrayList<BigDecimal> xp = func.getXp();
            // initialize and calculate h ; h = xi+1 - xi;
            BigDecimal h = xp.get(1).subtract(xp.get(0));
            for (int i = 1; i < xp.size() - 1; i++) {
                // get the temporary value of xi+1 - xi
                BigDecimal temp = (xp.get(i + 1).subtract(xp.get(i)));
                // if the temporary value do not equal h => the step is invalid
                if (!temp.equals(h)) {
                    throw new ArithmeticException("step h is not static");
                }
            }
            // Creating P Polynomial with a0 = -x0 and a1 = 1 ; x - x0
            Polynomial P = new Polynomial(new BigDecimal(-1).multiply(xp.get(0)), new BigDecimal(1));
            // Dividing P on h
            P = P.multiply(new BigDecimal(1).divide(h, Accuracy.getValue(), RoundingMode.HALF_UP));
            // Initialize the result Polynomial (Interpolation Polynomial by Newton-Gregory Forward)
            // with 0 to add it to other Polynomials
            Polynomial res = new Polynomial(new BigDecimal(0));
            //Get Newton Gregory Forward Table values (Upper diameter values)
            ArrayList<BigDecimal> df0 = getUDV(func);
            // Adding y0
            res = res.add(df0.get(0));
            for (int i = 1; i <= degree; i++) {
                // Initialize Newton-Gregory Forward Polynomial
                // with value 1 to multiply it with other Polynomials
                Polynomial NGFPoly = new Polynomial(new BigDecimal(1));
                //Multiply by P ( x-x0/h)
                NGFPoly = NGFPoly.multiply(P);
                for (int j = 1; j < i; j++) {
                    // Multiply Newton-Gregory Forward Polynomial by (P-1)(P-2)..
                    NGFPoly = NGFPoly.multiply(P.add(new BigDecimal(-1 * j)));
                }
                //if the current iteration is valid according to the
                // upper diameter values (not reaching zeros)
                if (i < df0.size())
                    // Multiply by df0
                    NGFPoly = NGFPoly.multiply(df0.get(i));
                else
                    // otherwise exit the loop
                    break;
                // init factorial with value 1 to multiply it by other numbers
                BigDecimal factorial = new BigDecimal(1);
                for (int j = 2; j <= i; j++) {
                    // Get n! : 1 * 2 * 3 * .. * n
                    factorial = factorial.multiply(new BigDecimal(j));
                }
                // Dividing Newton-Gregory Forward Polynomial by n!
                NGFPoly = NGFPoly.multiply(new BigDecimal(1).divide(factorial, Accuracy.getValue(), RoundingMode.HALF_UP));
                // add this Newton-Gregory Forward Polynomial to result
                res = res.add(NGFPoly);
            }
            return res;
        }

        /**
         * Returns Interpolation Function As <b>String</b> without shorthand using <b>Newton-Gregory Forward Subtractions</b>
         *
         * @param func   {@link PointsFunction} object representing the function to be interpolated
         * @param degree the degree of the required Polynomial
         * @return the result of Interpolation as {@link String}
         * @throws ArithmeticException if the given function is null <b>or</b> degree is smaller than zero
         */
        public static String getIFASNS(PointsFunction func, int degree) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            else if (degree < 0)
                throw new ArithmeticException("invalid inputs : degree cannot be smaller or equal to zero");
            //get x point
            ArrayList<BigDecimal> xp = func.getXp();
            // initialize and calculate h ; h = xi+1 - xi;
            BigDecimal h = xp.get(1).subtract(xp.get(0));
            for (int i = 1; i < xp.size() - 1; i++) {
                // get the temporary value of xi+1 - xi
                BigDecimal temp = (xp.get(i + 1).subtract(xp.get(i)));
                // if the temporary value do not equal h => the step is invalid
                if (!temp.equals(h)) {
                    throw new ArithmeticException("step h is not static");
                }
            }
            //init a string builder to create the Interpolation answer
            StringBuilder sb = new StringBuilder();
            //Getting Newton Gregory Forward Table values (Upper diameter values)
            ArrayList<BigDecimal> df0 = getUDV(func);
            // if the y0 != 0
            if (!df0.get(0).equals(new BigDecimal(0))) {
                // Adding f0 with formatted string value ; if y0 = 1.0 => append 1
                sb.append(df0.get(0));
            }
            // Creating P Polynomial with a0 = -x0 and a1 = 1 ; x - x0
            Polynomial P = new Polynomial(xp.get(0).multiply(new BigDecimal(-1)), new BigDecimal(1));
            // Dividing P on h
            P = P.multiply(new BigDecimal(1).divide(h, Accuracy.getValue(), RoundingMode.HALF_UP));
            for (int i = 1; i <= degree; i++) {
                // reaching zeros in the upper diameter values
                if (i >= df0.size())
                    break;
                // init factorial with value 1 to multiply it by other numbers
                BigDecimal factorial = new BigDecimal(1);
                for (int j = 2; j <= i; j++) {
                    // Get n! : 1 * 2 * 3 * .. * n
                    factorial = factorial.multiply(new BigDecimal(j));
                }
                // init a temporary value with the answer of dividing df0 on n!
                BigDecimal temp = df0.get(i).multiply(new BigDecimal(1).divide(factorial, Accuracy.getValue(), RoundingMode.HALF_UP));
                // reaching zeros
                if (temp.equals(new BigDecimal(0)))
                    break;
                    // if it is the start of the result , do not append ' + '
                else if (!sb.isEmpty())
                    sb.append(" + ");
                // append the temporary value to result ; df0/n!
                sb.append(temp);
                // append P with practices ; (x-x0/h)
                sb.append(" ");
                sb.append('(');
                sb.append(P);
                sb.append(')');
                for (int j = 1; j < i; j++) {
                    // append (P-1)(P-2)..
                    sb.append('(');
                    Polynomial NGFPoly = P.add(new BigDecimal(-1 * j));
                    sb.append(NGFPoly);
                    sb.append(')');
                }
            }
            return sb.toString();
        }
    }

    /**
     * Newton-Gregory Backward Subtractions class provides methods for getting the interpolation function using <b>Newton-Gregory Backward Subtractions</b> method
     * <p>It also provide function to get Newton-Gregory Backward Subtractions Table values </p>
     */
    public static class Newton_GregoryBackwardSubtractions {
        /**
         * Returns the values of the lower diameter of the Newton-Gregory Backward Subtractions Table
         *
         * @param func {@link PointsFunction} object representing a function to get its x and y point
         * @return the values of the upper diameter of the <b>Newton-Gregory Backward Subtractions Table</b>
         * @throws ArithmeticException if the given function is null
         */
        public static ArrayList<BigDecimal> getLDV(PointsFunction func) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            //get y points
            ArrayList<BigDecimal> yp = func.getYp();
            // initialize result ArrayList (Lower diameter values)
            ArrayList<BigDecimal> res = new ArrayList<>();
            // add yn
            res.add(yp.get(yp.size() - 1));
            // initialize queue with the first column values ( yi )
            Queue<BigDecimal> q = new LinkedList<>(yp);
            // n : the number of the valid element to the current columns
            // i = the index of the current element
            int n = yp.size(), i = 0;
            // yi : current y value ( yi-1 )
            // yi1 : forward y value ( yi )
            // temp : temporary result of yi - yi-1
            BigDecimal yi, yi1, temp;
            while (q.size() > 1) {
                // get yi-1 and delete it from queue
                yi = q.poll();
                // get yi without deleting ; to use it in the next iteration
                yi1 = q.element();
                // get current element result
                temp = yi1.subtract(yi);
                // add the current element to the queue
                q.add(temp);
                // reaching last element of the current columns ( Lower diameter element)
                if (i == n - 2) {
                    // reaching zeros ; no more terms
                    if (temp.equals(new BigDecimal(0)))
                        break;
                    // add the current element to result
                    res.add(temp);
                }
                // i = i+1 the index of the second element to the next iteration
                i++;
                if (i == n - 1) {
                    // reset index
                    i = 0;
                    // resize the valid element in next column in the next iteration
                    n--;
                    // delete the last element of this current columns ;
                    // so it do not interfere with the next column answer
                    q.poll();
                }
            }
            return res;
        }

        /**
         * Returns Interpolation Function As {@link Polynomial} using <b>Newton-Gregory Backward Subtractions</b>
         *
         * @param func   {@link PointsFunction} object representing the function to be interpolated
         * @param degree the degree of the required Polynomial
         * @return the result of Interpolation as {@link Polynomial}
         * @throws ArithmeticException if the given function is null <b>or</b> degree is smaller than zero
         */
        public static Polynomial getIFAP(PointsFunction func, int degree) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            else if (degree < 0)
                throw new ArithmeticException("invalid inputs : degree cannot be smaller or equal to zero");
            //get x point
            ArrayList<BigDecimal> xp = func.getXp();
            // initialize and calculate h ; h = xi+1 - xi;
            BigDecimal h = xp.get(1).subtract(xp.get(0));
            for (int i = 1; i < xp.size() - 1; i++) {
                // get the temporary value of xi+1 - xi
                BigDecimal temp = (xp.get(i + 1).subtract(xp.get(i)));
                // if the temporary value do not equal h => the step is invalid
                if (!temp.equals(h)) {
                    throw new ArithmeticException("step h is not static");
                }
            }
            // Creating S Polynomial with a0 = -xn and a1 = 1 ; x - xn
            Polynomial S = new Polynomial(xp.get(xp.size() - 1).multiply(new BigDecimal(-1)), new BigDecimal(1)); // Creating S Polynomial
            // Dividing S on h
            S = S.multiply(new BigDecimal(1).divide(h, Accuracy.getValue(), RoundingMode.HALF_UP));
            // Initialize the result Polynomial (Interpolation Polynomial by Newton-Gregory Backward)
            // with 0 to add it to other Polynomials
            Polynomial res = new Polynomial(new BigDecimal(0));
            //Getting Newton Gregory Backward Table values (Lower diameter values)
            ArrayList<BigDecimal> dfn = getLDV(func);
            // Adding yn
            res = res.add(dfn.get(0));
            for (int i = 1; i <= degree; i++) {
                // Initialize Newton-Gregory Backward Polynomial
                // with value 1 to multiply it with other Polynomials
                Polynomial NGBPoly = new Polynomial(new BigDecimal(1));
                //Multiply Newton-Gregory Backward Polynomial by S
                NGBPoly = NGBPoly.multiply(S);
                for (int j = 1; j < i; j++) {
                    // Multiply Newton-Gregory Backward Polynomial by (S+1)(S+2)..
                    NGBPoly = NGBPoly.multiply(S.add(new BigDecimal(j)));
                }
                //if the current iteration is valid according to the
                // lower diameter values (not reaching zeros)
                if (i < dfn.size())
                    // Multiply Newton-Gregory Backward Polynomial by dfn
                    NGBPoly = NGBPoly.multiply(dfn.get(i));
                else
                    //otherwise exit from the loop
                    break;
                // init factorial with value 1 to multiply it by other numbers
                BigDecimal factorial = new BigDecimal(1);
                for (int j = 2; j <= i; j++) {
                    // Get n! : 1 * 2 * 3 * .. * n
                    factorial = factorial.multiply(new BigDecimal(j));
                }
                // Dividing Newton-Gregory Backward Polynomial by n!
                NGBPoly = NGBPoly.multiply(new BigDecimal(1).divide(factorial, Accuracy.getValue(), RoundingMode.HALF_UP));
                // add this Newton-Gregory Backward Polynomial to result
                res = res.add(NGBPoly);
            }
            return res;
        }

        /**
         * Returns Interpolation Function As <b>String</b> without shorthand using <b>Newton-Gregory Backward Subtractions</b>
         *
         * @param func   {@link PointsFunction} object representing the function to be interpolated
         * @param degree the degree of the required Polynomial
         * @return the result of Interpolation as {@link String}
         * @throws ArithmeticException if the given function is null <b>or</b> degree is smaller than zero
         */
        public static String getIFASNS(PointsFunction func, int degree) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            else if (degree < 0)
                throw new ArithmeticException("invalid inputs : degree cannot be smaller or equal to zero");
            //get x point
            ArrayList<BigDecimal> xp = func.getXp();
            // initialize and calculate h ; h = xi+1 - xi;
            BigDecimal h = xp.get(1).subtract(xp.get(0));
            for (int i = 1; i < xp.size() - 1; i++) {
                // get the temporary value of xi+1 - xi
                BigDecimal temp = (xp.get(i + 1).subtract(xp.get(i)));
                // if the temporary value do not equal h => the step is invalid
                if (!temp.equals(h)) {
                    throw new ArithmeticException("step h is not static");
                }
            }
            //init a string builder to create the Interpolation answer
            StringBuilder sb = new StringBuilder();
            //Getting Newton Gregory Backward Table values (Lower diameter values)
            ArrayList<BigDecimal> dfn = getLDV(func);
            // if yn != 0
            if (!dfn.get(0).equals(new BigDecimal(0))) {
                // Adding yn with formatted string value ; if y0 = 1.0 => append 1
                sb.append(dfn.get(0));
            }
            // Creating S Polynomial with a0 = -xn and a1 = 1 ; x - xn
            Polynomial S = new Polynomial(xp.get(xp.size() - 1).multiply(new BigDecimal(-1)), new BigDecimal(1));
            // Dividing S on h
            S = S.multiply(new BigDecimal(1).divide(h, Accuracy.getValue(), RoundingMode.HALF_UP));
            for (int i = 1; i <= degree; i++) {
                // reaching zeros ; no more terms
                if (i >= dfn.size())
                    break;
                // init factorial with value 1 to multiply it by other numbers
                BigDecimal factorial = new BigDecimal(1);
                for (int j = 2; j <= i; j++) {
                    // Get n! : 1 * 2 * 3 * .. * n
                    factorial = factorial.multiply(new BigDecimal(j));
                }
                // init a temporary value with the answer of dividing dfn on n!
                BigDecimal temp = dfn.get(i).multiply(new BigDecimal(1).divide(factorial, Accuracy.getValue(), RoundingMode.HALF_UP));
                //reaching zeros
                if (temp.equals(new BigDecimal(0)))
                    break;
                    // if it is the start of the result , do not append ' + '
                else if (!sb.isEmpty())
                    sb.append(" + ");
                // append the temporary value to result ; dfn/n!
                sb.append(temp);
                // append S with practices ; (x-xn/h)
                sb.append(" ");
                sb.append('(');
                sb.append(S);
                sb.append(')');
                for (int j = 1; j < i; j++) {
                    // append (S+1)(S+2)..
                    sb.append('(');
                    Polynomial NGBPoly = S.add(new BigDecimal(j));
                    sb.append(NGBPoly);
                    sb.append(')');
                }
            }
            return sb.toString();
        }
    }

    /**
     * Newton Forward Divided Subtractions class provides methods for getting the interpolation function using <b>Newton Forward Divided Subtractions</b> method
     * <p>It also provide function to get Newton Forward Divided Subtractions Table values </p>
     */
    public static class NewtonForwardDividedSubtractions {

        /**
         * Returns the values of the lower diameter of the Newton Forward Divided Subtractions Table
         *
         * @param func {@link PointsFunction} object representing a function to get its x and y point
         * @return the values of the upper diameter of the <b>Newton Forward Divided Subtractions Table</b>
         * @throws ArithmeticException if the given function is null
         */
        public static ArrayList<BigDecimal> getUDV(PointsFunction func) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            // get x points
            ArrayList<BigDecimal> xp = func.getXp();
            // get y points
            ArrayList<BigDecimal> yp = func.getYp();
            // init result ArrayList (Upper diameter values)
            ArrayList<BigDecimal> res = new ArrayList<>();
            //add y0 to result
            res.add(yp.get(0));
            // initialize queue with the first column values ( yi )
            Queue<BigDecimal> q = new LinkedList<>(yp);
            // n : the number of the valid element to the current columns
            // i : the index of the current element
            // j : the difference between the two x point
            // in the current iteration : xi+j - xi
            int n = yp.size(), i = 0, j = 1;
            // yi : current y value
            // yi1 : forward y value ( yi+1 )
            // xi : current x value
            // xi1 : forward x value ( xi+j )
            // temp : temporary result of (yi+1 - yi) / (xi+j - xi)
            BigDecimal yi, yi1, xi, xi1, temp;
            while (q.size() > 1) {
                // get yi and delete it from queue
                yi = q.poll();
                // get yi+1 without deleting ; to use it in the next iteration
                yi1 = q.element();
                // get xi value
                xi = xp.get(i);
                // get xi+j value
                xi1 = xp.get(i + j);
                // get current element result
                temp = (yi1.subtract(yi)).divide(xi1.subtract(xi), Accuracy.getValue(), RoundingMode.HALF_UP);
                // add the current element to the queue
                q.add(temp);
                // reaching the start of the column c
                if (i == 0) {
                    // reaching zeros ; no more terms => end while -> return result
                    if (temp.equals(new BigDecimal(0)))
                        break;
                    // add this value to result
                    res.add(temp);
                }
                // i = i+1 the index of the second element to the next iteration
                i++;
                // reaching end of columns c => reinitialize variables for the next loop
                if (i == n - 1) {
                    // reset index
                    i = 0;
                    // resize the valid element in next column in the next iteration
                    n--;
                    // make the difference bigger between xi+j and xi
                    j++;
                    // delete the last element of this current columns ;
                    // so it do not interfere with the next column answer
                    q.poll();
                }
            }
            return res;
        }

        /**
         * Returns Interpolation Function As {@link Polynomial} using <b>Newton Forward Divided Subtractions</b>
         *
         * @param func   {@link PointsFunction} object representing the function to be interpolated
         * @param degree the degree of the required Polynomial
         * @return the result of Interpolation as {@link Polynomial}
         * @throws ArithmeticException if the given function is null <b>or</b> degree is smaller than zero
         */
        public static Polynomial getIFAP(PointsFunction func, int degree) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            else if (degree < 0)
                throw new ArithmeticException("invalid inputs : degree cannot be smaller or equal to zero");
            // get x points
            ArrayList<BigDecimal> xp = func.getXp();
            // get Newton Divides table values (upper diameter values)
            ArrayList<BigDecimal> f0 = getUDV(func);
            // init result Polynomial (Interpolation answer by Newton Divides ) with the y0 value;
            Polynomial res = new Polynomial(f0.get(0));
            for (int i = 1; i <= degree; i++) {
                // reaching zeros
                if (i >= f0.size())
                    break;
                // init a temporary Polynomial with the value 1
                // to multiply it by other Polynomials
                Polynomial poly = new Polynomial(new BigDecimal(1));
                for (int j = 0; j < i; j++) {
                    //init the current poly with the values
                    // a0 = -xj , a1 = 1 ; x - xj
                    Polynomial curr = new Polynomial(xp.get(j).multiply(new BigDecimal(-1)), new BigDecimal(1));
                    // get the temp poly : ( x - x0 ) * ( x - x1 ) * ... * ( x - xi-1 )
                    poly = poly.multiply(curr);
                }
                // multiply the temp poly by f0i
                poly = poly.multiply(f0.get(i));
                // add the temp poly to answer
                res = res.add(poly);
            }
            return res;
        }

        /**
         * Returns Interpolation Function As <b>String</b> without shorthand using <b>Newton Forward Divided Subtractions</b>
         *
         * @param func   {@link PointsFunction} object representing the function to be interpolated
         * @param degree the degree of the required Polynomial
         * @return the result of Interpolation as {@link String}
         * @throws ArithmeticException if the given function is null <b>or</b> degree is smaller than zero
         */
        public static String getIFASNS(PointsFunction func, int degree) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            else if (degree < 0)
                throw new ArithmeticException("invalid inputs : degree cannot be smaller or equal to zero");
            // get x points
            ArrayList<BigDecimal> xp = func.getXp();
            // get Newton Divides table values (upper diameter values)
            ArrayList<BigDecimal> f0 = getUDV(func);
            // init string builder to create string representing
            // the answer of interpolation by Newton Divides Forward
            StringBuilder sb = new StringBuilder();
            // append y0 to answer
            sb.append(f0.get(0));
            for (int i = 1; i <= degree; i++) {
                // reaching zeros
                if (i >= f0.size())
                    break;
                // append ' + ' to make .. + ..
                sb.append(" + ");
                // append f0i to answer
                sb.append(f0.get(i));
                sb.append(" ");
                for (int j = 0; j < i; j++) {
                    //init the current poly with the values
                    // a0 = -xj , a1 = 1 ; x - xj
                    Polynomial curr = new Polynomial(xp.get(j).multiply(new BigDecimal(-1)), new BigDecimal(1));
                    // append the curr poly to answer with practices
                    sb.append('(');
                    sb.append(curr);
                    sb.append(')');
                }
            }
            return sb.toString();
        }
    }

    /**
     * Newton Backward Divided Subtractions class provides methods for getting the interpolation function using <b>Newton Backward Divided Subtractions</b> method
     * <p>It also provide function to get Newton Backward Divided Subtractions Table values </p>
     */
    public static class NewtonBackwardDividedSubtractions {
        /**
         * Returns the values of the lower diameter of the Newton Backward Divided Subtractions Table
         *
         * @param func {@link PointsFunction} object representing a function to get its x and y point
         * @return the values of the upper diameter of the <b>Newton Backward Divided Subtractions Table</b>
         * @throws ArithmeticException if the given function is null
         */
        public static ArrayList<BigDecimal> getLDV(PointsFunction func) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            // get x points
            ArrayList<BigDecimal> xp = func.getXp();
            // get y points
            ArrayList<BigDecimal> yp = func.getYp();
            // init result ArrayList (Lower diameter values)
            ArrayList<BigDecimal> res = new ArrayList<>();
            //add yn to result
            res.add(yp.get(yp.size() - 1));
            // initialize queue with the first column values ( yi )
            Queue<BigDecimal> q = new LinkedList<>(yp);
            // n : the number of the valid element to the current columns
            // i : the index of the current element
            // j : the difference between the two x point
            // in the current iteration : xi+j - xi
            int n = yp.size(), i = 0, add = 1;
            // yi : current y value ( yi-1)
            // yi1 : forward y value ( yi )
            // xi : current x value
            // xi1 : forward x value ( xi+j )
            // temp : temporary result of (yi - yi-1) / (xi+j - xi)
            BigDecimal yi, yi1, xi, xi1, temp;
            while (q.size() > 1) {
                // get yi-1 and delete it from queue
                yi = q.poll();
                // get yi without deleting ; to use it in the next iteration
                yi1 = q.element();
                // get xi value
                xi = xp.get(i);
                // get xi+j value
                xi1 = xp.get(i + add);
                // get current element result
                temp = (yi1.subtract(yi)).divide(xi1.subtract(xi), Accuracy.getValue(), RoundingMode.HALF_UP);
                // add the current element to the queue
                q.add(temp);
                // reaching the end of the column c
                if (i == n - 2) {
                    // reaching zeros ; no more terms => end while -> return result
                    if (temp.equals(new BigDecimal(0)))
                        break;
                    // add this value to result
                    res.add(temp);
                }
                // i = i+1 the index of the second element to the next iteration
                i++;
                // reaching end of columns c => reinitialize variables for the next loop
                if (i == n - 1) {
                    // reset index
                    i = 0;
                    // resize the valid element in next column in the next iteration
                    n--;
                    // make the difference bigger between xi+j and xi
                    add++;
                    // delete the last element of this current columns ;
                    // so it do not interfere with the next column answer
                    q.poll();
                }
            }
            return res;
        }

        /**
         * Returns Interpolation Function As {@link Polynomial} using <b>Newton Backward Divided Subtractions</b>
         *
         * @param func   {@link PointsFunction} object representing the function to be interpolated
         * @param degree the degree of the required Polynomial
         * @return the result of Interpolation as {@link Polynomial}
         * @throws ArithmeticException if the given function is null <b>or</b> degree is smaller than zero
         */
        public static Polynomial getIFAP(PointsFunction func, int degree) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            else if (degree < 0)
                throw new ArithmeticException("invalid inputs : degree cannot be smaller or equal to zero");
            // get x points
            ArrayList<BigDecimal> xp = func.getXp();
            // get Newton Divides table values (lower diameter values)
            ArrayList<BigDecimal> fn = getLDV(func);
            // init result Polynomial (Interpolation answer by Newton Divides ) with the yn value;
            Polynomial res = new Polynomial(fn.get(0));
            for (int i = 1; i <= degree; i++) {
                // reaching zeros
                if (i >= fn.size())
                    break;
                // init a temporary Polynomial with the value 1
                // to multiply it by other Polynomials
                Polynomial poly = new Polynomial(new BigDecimal(1));
                for (int j = 0; j < i; j++) {
                    //init the current poly with the values
                    // a0 = -xn-j , a1 = 1 ; x - xn-j
                    Polynomial curr = new Polynomial(xp.get(degree - j).multiply(new BigDecimal(-1)), new BigDecimal(1));
                    // get the temp poly : ( x - xn ) * ( x - xn-1 ) * ... * ( x - xn-i-1 )
                    poly = poly.multiply(curr);
                }
                // multiply the temp poly by fni
                poly = poly.multiply(fn.get(i));
                // add the temp poly to answer
                res = res.add(poly);
            }
            return res;
        }

        /**
         * Returns Interpolation Function As <b>String</b> without shorthand using <b>Newton Backward Divided Subtractions</b>
         *
         * @param func   {@link PointsFunction} object representing the function to be interpolated
         * @param degree the degree of the required Polynomial
         * @return the result of Interpolation as {@link String}
         * @throws ArithmeticException if the given function is null <b>or</b> degree is smaller than zero
         */
        public static String getIFASNS(PointsFunction func, int degree) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            else if (degree < 0)
                throw new ArithmeticException("invalid inputs : degree cannot be smaller or equal to zero");
            // get x points
            ArrayList<BigDecimal> xp = func.getXp();
            // get Newton Divides table values (lower diameter values)
            ArrayList<BigDecimal> fn = getLDV(func);
            // init string builder to create string representing
            // the answer of interpolation by Newton Divides Forward
            StringBuilder sb = new StringBuilder();
            // append yn to answer
            sb.append(fn.get(0));
            for (int i = 1; i <= degree; i++) {
                // reaching zeros
                if (i >= fn.size())
                    break;
                // append ' + ' to make .. + ..
                sb.append(" + ");
                // append fni to answer
                sb.append(fn.get(i));
                sb.append(" ");
                for (int j = 0; j < i; j++) {
                    //init the current poly with the values
                    // a0 = -xn-j , a1 = 1 ; x - xn-j
                    Polynomial curr = new Polynomial(xp.get(degree - j).multiply(new BigDecimal(-1)), new BigDecimal(1));
                    // append the curr poly to answer with practices
                    sb.append('(');
                    sb.append(curr);
                    sb.append(')');
                }
            }
            return sb.toString();
        }
    }

    /**
     * Least-Squares class methods for getting the interpolation function using <b>Least-Squares</b> method
     */
    public static class LeastSquares {
        /**
         * Returns Interpolation Function As {@link Polynomial} using <b>Least-Squares</b>
         *
         * @param func   {@link PointsFunction} object representing the function to be interpolated
         * @param degree the degree of the required Polynomial
         * @return the result of Interpolation as {@link Polynomial}
         * @throws ArithmeticException if the given function is null <b>or</b> degree is smaller than zero
         */
        public static Polynomial getIFAP(PointsFunction func, int degree) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            else if (degree < 0)
                throw new ArithmeticException("invalid inputs : degree cannot be smaller or equal to zero");
            // get x points
            ArrayList<BigDecimal> xp = func.getXp();
            // get y point
            ArrayList<BigDecimal> yp = func.getYp();
            // init a new matrix to solve a system of equations
            // of m + 1 equation (row) and m + 2 column
            // where degree = m
            Matrix SE = new Matrix(degree + 1, degree + 2);
            // loop for equations coefficients
            for (int i = 0; i <= degree; i++) {
                ArrayList<BigDecimal> coeffs = new ArrayList<>();
                // loop for getting current equation coefficient
                for (int s = i; s <= i + degree; s++) {
                    // init xksum for ∑xk^s ; ∑xk^s , ∑xk^s+1 ∑xk^s+2 ...
                    BigDecimal xksum = new BigDecimal(0);
                    // loop for  ∑xk^s
                    for (int k = 0; k < xp.size(); k++) {
                        xksum = xksum.add(BigDecimalUtil.pow(xp.get(k), new BigDecimal(s)));
                    }
                    coeffs.add(xksum);
                }
                // init xk * f(xk) sum for ∑xk^s*f(xk)
                BigDecimal xk_fxk_sum = new BigDecimal(0);
                // loop for  ∑xk^s*f(xk)
                for (int k = 0; k < xp.size(); k++) {
                    xk_fxk_sum = xk_fxk_sum.add(BigDecimalUtil.pow(xp.get(k), new BigDecimal(i)).multiply(yp.get(k)));
                }
                coeffs.add(xk_fxk_sum);
                SE.setRow(i, coeffs);
            }
            //get solution Polynomial coefficient by solving the system of equations
            ArrayList<BigDecimal> solcoeefs = SE.solve();
            return new Polynomial(solcoeefs);
        }
    }

    /**
     * Spline class methods for getting the interpolation function using <b>Spline</b> method
     */
    public static class Spline {
        /**
         * Returns an ArrayList of {@link Polynomial} representing the Interpolation Function using <b>Spline</b>
         *
         * @param func {@link PointsFunction} object representing the function to be interpolated
         * @return the result of Interpolation an ArrayList of {@link Polynomial}
         * @throws ArithmeticException if the given function is null
         */
        public static ArrayList<Polynomial> getIFAPs(PointsFunction func) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Function cannot be null");
            // get x points
            ArrayList<BigDecimal> xp = func.getXp();
            // get y points
            ArrayList<BigDecimal> yp = func.getYp();
            // create ArrayList of Polynomials representing Spline Polynomials
            // where the number of Spline Polynomials = number of points -1
            ArrayList<Polynomial> S = new ArrayList<>(yp.size() - 1);
            for (int i = 0; i < yp.size() - 1; i++) {
                // init a scalar for the result of : (yi+1 - yi) / (xi+1 - xi)
                BigDecimal scalar = (yp.get(i + 1).subtract(yp.get(i))).divide(xp.get(i + 1).subtract(xp.get(i)), Accuracy.getValue(), RoundingMode.HALF_UP);
                // init curr Polynomial with a0 = -xi , a1 = 1 ; x - xi
                Polynomial curr = new Polynomial(xp.get(i).multiply(new BigDecimal(-1)), new BigDecimal(1));
                // multiply the curr Polynomial by scalar value
                // (yi+1 - yi) / (xi+1 - xi)   *  ( x - xi )
                curr = curr.multiply(scalar);
                // add yi to curr Polynomial
                // yi  +  (yi+1 - yi) / (xi+1 - xi)   *  ( x - xi )
                curr = curr.add(yp.get(i));
                // add the curr Polynomial to answer ; S(x)
                S.add(curr);
            }
            return S;
        }
    }


}
