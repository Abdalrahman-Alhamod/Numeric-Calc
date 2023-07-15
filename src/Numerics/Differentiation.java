package Numerics;

import Functions.PointsFunction;
import Functions.Polynomial;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Differentiation class provides inner classes to get the differential function
 * with multiple methods such as <b>Lagrange</b>, <b>Newton_Gregory Forward Subtractions</b>,
 * <b>Newton_Gregory Backward Subtractions</b> and <b>Central,Forward,Backward Subtractions</b>
 */
public abstract class Differentiation {
    /**
     * The Lagrange class provides methods to get the Differential Function using <b>Lagrange</b> method
     */
    public static class Lagrange {
        /**
         * Returns <b>Lagrange Differential Polynomial</b> at the given index
         *
         * @param index the index of the required Lagrange Polynomial (Lj)
         * @param xp    an ArrayList of x points
         * @return the result Lagrange Polynomial as {@link Polynomial}
         */
        private static Polynomial getLagrangePolyAt(int index, ArrayList<Double> xp) {
            // initialize scalar to 1 to be multiplied with a number
            double scalar = 1;
            // initialize a Polynomial with the value a0 = 1 to be multiplied with other polynomials ; the Lagrange Polynomial
            Polynomial lag = new Polynomial(1);
            for (int i = 0; i < xp.size(); i++) {
                // xi != xj ; where j is Lagrange Polynomial is created for (Lj) ; other words j = index
                if (i != index) {
                    // make scalar = (xj - xj) => (xj - x0 ) * (xj - x1 ) ... (xj - xj-1) * (xj - xj+1)
                    scalar *= (xp.get(index) - xp.get(i));
                    //Rounding value back to fix floating-point precision errors
                    scalar = Math.round(scalar * 1e10) / 1e10;
                    // create a Polynomial with a0 = -xi and a1 = 1 (a1*x + a0) ; (x - xi ) => (x - x0 ) * (x - x1)
                    Polynomial poly = new Polynomial(-1 * xp.get(i), 1);
                    // create Lagrange Polynomial by : (x - x0 ) * (x - x1) .. (x - xj) * (x - xj+1) ..
                    lag = lag.multiply(poly);
                }
            }
            //Rounding value back to fix floating-point precision errors
            scalar = Math.round(scalar * 1e10) / 1e10;
            // Divide the multiplied polynomials ( (x - x0 ) * (x - x1) .. (x - xj) * (x - xj+1) ..)
            // on scalar ( (xj - x0 ) * (xj - x1 ) ... (xj - xj-1) * (xj - xj+1)..)
            // which is Lagrange Polynomial
            lag = lag.multiply(1 / scalar);
            return lag.getDerivative();
        }

        /**
         * Returns Differential Function As {@link Polynomial} using <b>Lagrange</b>
         *
         * @param func {@link PointsFunction} object representing the function to be differentiated
         * @return the result of Differentiation as {@link Polynomial}
         * @throws ArithmeticException if the given function is null
         */
        public static Polynomial getDFAP(PointsFunction func) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Functions cannot be null");
            // get x points
            ArrayList<Double> xp = func.getXp();
            // get y point
            ArrayList<Double> yp = func.getYp();
            // initialize result Polynomial (Differentiation answer by Lagrange )
            // with value 0 to add it to other Polynomial
            Polynomial res = new Polynomial(0);
            for (int i = 0; i < xp.size(); i++) {
                //get Lagrange Polynomial at index = i (L'i)
                Polynomial lag = getLagrangePolyAt(i, xp);
                //multiply Lagrange Polynomial with yi ( yi * L'i)
                lag = lag.multiply(yp.get(i));
                // add the multiply result to result Polynomial (Differentiation answer by Lagrange )
                res = res.add(lag);
            }
            return res;
        }
    }

    /**
     * Newton-Gregory Forward Subtractions class provides methods for getting the Differential function
     * using <b>Newton-Gregory Forward Subtractions</b> method
     */
    public static class Newton_GregoryForwardSubtractions {
        /**
         * Returns the values of the upper diameter of the Newton-Gregory Forward Subtractions Table
         *
         * @param func {@link PointsFunction} object representing a function to get its x and y point
         * @return the values of the upper diameter of the <b>Newton-Gregory Forward Subtractions Table</b>
         * @throws ArithmeticException if the given function is null
         */
        private static ArrayList<Double> getUDV(PointsFunction func) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Functions cannot be null");
            //get y points
            ArrayList<Double> yp = func.getYp();
            // initialize result ArrayList (Upper diameter values)
            ArrayList<Double> res = new ArrayList<>();
            // add y0
            res.add(yp.get(0));
            // initialize queue with the first column values ( yi )
            Queue<Double> q = new LinkedList<>(yp);
            // n : the number of the valid element to the current columns
            // i = the index of the current element
            int n = yp.size(), i = 0;
            // yi : current y value
            // yi1 : forward y value ( yi+1 )
            // temp : temporary result of yi+1 - yi
            double yi, yi1, temp;
            while (q.size() > 1) {
                // get yi and delete it from queue
                yi = q.poll();
                // get yi+1 without deleting ; to use it in the next iteration
                yi1 = q.element();
                // get current element result
                temp = yi1 - yi;
                //Rounding value back to fix floating-point precision errors
                temp = Math.round(temp * 1e10) / 1e10;
                // add the current element to the queue
                q.add(temp);
                // reaching first element of the current columns ( upper diameter element)
                if (i == 0) {
                    // reaching zeros ; no more terms
                    if (temp == 0)
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
         * Returns Differential Function As {@link Polynomial} using <b>Newton-Gregory Forward Subtractions</b>
         *
         * @param func   {@link PointsFunction} object representing the function to be differentiated
         * @param degree the degree of the required Polynomial
         * @param rank   the rank of the required Differentiation
         * @return the result of Differentiation as {@link Polynomial}
         * @throws ArithmeticException if the given function is null <b>or</b> degree is smaller than zero
         */
        public static Polynomial getIFAP(PointsFunction func, int degree, int rank) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Functions cannot be null");
            else if (degree < 0)
                throw new ArithmeticException("invalid inputs : degree cannot be smaller than zero");
            else if (rank < 1)
                throw new ArithmeticException("invalid inputs : rank cannot be smaller than one");
            //get x point
            ArrayList<Double> xp = func.getXp();
            // initialize and calculate h ; h = xi+1 - xi;
            double h = xp.get(1) - xp.get(0);
            //Rounding value back to fix floating-point precision errors
            h = Math.round(h * 1e10) / 1e10;
            for (int i = 1; i < xp.size() - 1; i++) {
                // get the temporary value of xi+1 - xi
                double temp = (xp.get(i + 1) - xp.get(i));
                //Rounding value back to fix floating-point precision errors
                temp = Math.round(temp * 1e10) / 1e10;
                // if the temporary value do not equal h => the step is invalid
                if (temp != h) {
                    throw new ArithmeticException("step h is not static");
                }
            }
            // Creating P Polynomial with a0 = -x0 and a1 = 1 ; x - x0
            Polynomial P = new Polynomial(-1 * xp.get(0), 1);
            // Dividing P on h
            P = P.multiply(1 / h);
            // Initialize the result Polynomial (Differentiation Polynomial by Newton-Gregory Forward)
            // with 0 to add it to other Polynomials
            Polynomial res = new Polynomial(0);
            //Get Newton Gregory Forward Table values (Upper diameter values)
            ArrayList<Double> df0 = getUDV(func);
            for (int i = 1; i <= degree; i++) {
                // Initialize Newton-Gregory Forward Polynomial
                // with value 1 to multiply it with other Polynomials
                Polynomial NGFPoly = new Polynomial(1);
                // Init P variable ( to get df/dp )
                Polynomial PV = new Polynomial(0, 1);
                //Multiply by P ( x-x0/h)
                NGFPoly = NGFPoly.multiply(PV);
                for (int j = 1; j < i; j++) {
                    // Multiply Newton-Gregory Forward Polynomial by (P-1)(P-2)..
                    NGFPoly = NGFPoly.multiply(PV.add(-1 * j));
                }
                // Differentiate rank times ; df/dp ; d2p/dp^2....
                for (int j = 1; j <= rank; j++) {
                    // if current poly do not equal zero
                    if (NGFPoly.getCoeffs().size() > 0)
                        // Derivative the current Poly by rank
                        NGFPoly = NGFPoly.getDerivative();
                    else
                        break; // current poly zero
                }
                //current poly do not equal zero
                if (NGFPoly.getCoeffs().size() >= 1)
                    // get the value of Substituting p in the differentiated poly
                    NGFPoly = NGFPoly.getPolyOf(P);
                //if the current iteration is valid according to the
                // upper diameter values (not reaching zeros)
                if (i < df0.size())
                    // Multiply by df0
                    NGFPoly = NGFPoly.multiply(df0.get(i));
                else
                    // otherwise exit the loop
                    break;
                // init factorial with value 1 to multiply it by other numbers
                double factorial = 1;
                for (int j = 2; j <= i; j++) {
                    // Get n! : 1 * 2 * 3 * .. * n
                    factorial *= j;
                }
                // Dividing Newton-Gregory Forward Polynomial by n!
                NGFPoly = NGFPoly.multiply(1 / factorial);
                // add this Newton-Gregory Forward Polynomial to result
                res = res.add(NGFPoly);
            }
            res = res.multiply(1 / (Math.pow(h, rank)));
            return res;
        }
    }

    /**
     * Newton-Gregory Backward Subtractions class provides methods for getting the Differentiation function
     * using <b>Newton-Gregory Backward Subtractions</b> method
     */
    public static class Newton_GregoryBackwardSubtractions {
        /**
         * Returns the values of the lower diameter of the Newton-Gregory Backward Subtractions Table
         *
         * @param func {@link PointsFunction} object representing a function to get its x and y point
         * @return the values of the upper diameter of the <b>Newton-Gregory Backward Subtractions Table</b>
         * @throws ArithmeticException if the given function is null
         */
        public static ArrayList<Double> getLDV(PointsFunction func) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Functions cannot be null");
            //get y points
            ArrayList<Double> yp = func.getYp();
            // initialize result ArrayList (Lower diameter values)
            ArrayList<Double> res = new ArrayList<>();
            // add yn
            res.add(yp.get(yp.size() - 1));
            // initialize queue with the first column values ( yi )
            Queue<Double> q = new LinkedList<>(yp);
            // n : the number of the valid element to the current columns
            // i = the index of the current element
            int n = yp.size(), i = 0;
            // yi : current y value ( yi-1 )
            // yi1 : forward y value ( yi )
            // temp : temporary result of yi - yi-1
            double yi, yi1, temp;
            while (q.size() > 1) {
                // get yi-1 and delete it from queue
                yi = q.poll();
                // get yi without deleting ; to use it in the next iteration
                yi1 = q.element();
                // get current element result
                temp = yi1 - yi;
                //Rounding value back to fix floating-point precision errors
                temp = Math.round(temp * 1e10) / 1e10;
                // add the current element to the queue
                q.add(temp);
                // reaching last element of the current columns ( Lower diameter element)
                if (i == n - 2) {
                    // reaching zeros ; no more terms
                    if (temp == 0)
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
         * Returns Differential Function As {@link Polynomial} using <b>Newton-Gregory Backward Subtractions</b>
         *
         * @param func   {@link PointsFunction} object representing the function to be differentiated
         * @param degree the degree of the required Polynomial
         * @param rank   the rank of the required Differentiation
         * @return the result of Differentiation as {@link Polynomial}
         * @throws ArithmeticException if the given function is null <br>
         *                             <b>or</b> degree is smaller than zero <br>
         *                             <b>or</b> rank is smaller than one
         */
        public static Polynomial getIFAP(PointsFunction func, int degree, int rank) {
            if (func == null)
                throw new ArithmeticException("invalid inputs : Functions cannot be null");
            else if (degree < 0)
                throw new ArithmeticException("invalid inputs : degree cannot be smaller than zero");
            else if (rank < 1)
                throw new ArithmeticException("invalid inputs : rank cannot be smaller than one");
            //get x point
            ArrayList<Double> xp = func.getXp();
            // initialize and calculate h ; h = xi+1 - xi;
            double h = xp.get(1) - xp.get(0);
            //Rounding value back to fix floating-point precision errors
            h = Math.round(h * 1e10) / 1e10;
            for (int i = 1; i < xp.size() - 1; i++) {
                // get the temporary value of xi+1 - xi
                double temp = (xp.get(i + 1) - xp.get(i));
                //Rounding value back to fix floating-point precision errors
                temp = Math.round(temp * 1e10) / 1e10;
                // if the temporary value do not equal h => the step is invalid
                if (temp != h) {
                    throw new ArithmeticException("step h is not static");
                }
            }
            // Creating S Polynomial with a0 = -xn and a1 = 1 ; x - xn
            Polynomial S = new Polynomial(-1 * xp.get(xp.size() - 1), 1);
            // Dividing S on h
            S = S.multiply(1 / h);
            // Initialize the result Polynomial (Differentiation Polynomial by Newton-Gregory Backward)
            // with 0 to add it to other Polynomials
            Polynomial res = new Polynomial(0);
            //Get Newton Gregory Backward Table values (lower diameter values)
            ArrayList<Double> dfn = getLDV(func);
            for (int i = 1; i <= degree; i++) {
                // Initialize Newton-Gregory Backward Polynomial
                // with value 1 to multiply it with other Polynomials
                Polynomial NGBPoly = new Polynomial(1);
                // Init S variable ( to get df/ds )
                Polynomial SV = new Polynomial(0, 1);
                //Multiply by S ( x-xn/h)
                NGBPoly = NGBPoly.multiply(SV);
                for (int j = 1; j < i; j++) {
                    // Multiply Newton-Gregory Backward Polynomial by (S+1)(S+2)..
                    NGBPoly = NGBPoly.multiply(SV.add(j));
                }
                // Differentiate rank times ; df/ds ; d2s/ds^2....
                for (int j = 1; j <= rank; j++) {
                    // if current poly do not equal zero
                    if (NGBPoly.getCoeffs().size() > 0)
                        // Derivative the current Poly by rank
                        NGBPoly = NGBPoly.getDerivative();
                    else
                        break; // current poly zero
                }
                //current poly do not equal zero
                if (NGBPoly.getCoeffs().size() >= 1)
                    // get the value of Substituting S in the differentiated poly
                    NGBPoly = NGBPoly.getPolyOf(S);
                //if the current iteration is valid according to the
                // lower diameter values (not reaching zeros)
                if (i < dfn.size())
                    // Multiply by dfn
                    NGBPoly = NGBPoly.multiply(dfn.get(i));
                else
                    // otherwise exit the loop
                    break;
                // init factorial with value 1 to multiply it by other numbers
                double factorial = 1;
                for (int j = 2; j <= i; j++) {
                    // Get n! : 1 * 2 * 3 * .. * n
                    factorial *= j;
                }
                // Dividing Newton-Gregory Backward Polynomial by n!
                NGBPoly = NGBPoly.multiply(1 / factorial);
                // add this Newton-Gregory Backward Polynomial to result
                res = res.add(NGBPoly);
            }
            res = res.multiply(1 / (Math.pow(h, rank)));
            return res;
        }
    }

    /**
     * Subtractions class provides inner classes to calculate
     * the differential function of a specified value
     */
    public static class Subtractions {
        /**
         * Central class provide a method to calculate the differential
         * function of a specified value using <b>Central Subtractions</b>
         */
        public static class Central {
            /**
             * Returns the differentiation answer of the given index using <b>Central Subtractions</b>
             *
             * @param func {@link PointsFunction} object representing the function to get its x and y points
             * @param x    the value of the required element to get the differentiation at
             * @return the result of the differentiation
             * @throws ArithmeticException if func is null <br>
             *                             <b>OR</b> index of x = 0 <br>
             *                             <b>OR</b> index of x = n <br>
             *                             <b>OR</b> x do not exist <br>
             *                             <b>OR</b>  step h is not static
             */
            public static double getValueAt(PointsFunction func, double x) {
                if (func == null)
                    throw new ArithmeticException("invalid inputs : Functions cannot be null");
                //get x points
                ArrayList<Double> xp = func.getXp();
                // get y point
                ArrayList<Double> yp = func.getYp();
                // initialize and calculate h ; h = xi+1 - xi;
                double h = xp.get(1) - xp.get(0);
                //Rounding value back to fix floating-point precision errors
                h = Math.round(h * 1e10) / 1e10;
                for (int i = 1; i < xp.size() - 1; i++) {
                    // get the temporary value of xi+1 - xi
                    double temp = (xp.get(i + 1) - xp.get(i));
                    //Rounding value back to fix floating-point precision errors
                    temp = Math.round(temp * 1e10) / 1e10;
                    // if the temporary value do not equal h => the step is invalid
                    if (temp != h) {
                        throw new ArithmeticException("step h is not static");
                    }
                }
                //get index
                int index = xp.indexOf(x);
                if (index == (xp.size() - 1))
                    throw new ArithmeticException("invalid inputs : cannot apply to xn");
                else if (index == 0)
                    throw new ArithmeticException("invalid inputs : cannot apply to x0");
                else if (index == -1)
                    throw new ArithmeticException("invalid inputs : element do not exist");
                // apply the law : (yi+1 - yi-1) / 2*h
                double res = (yp.get(index + 1) - yp.get(index - 1)) / (2 * h);
                //Rounding value back to fix floating-point precision errors
                res = Math.round(res * 1e10) / 1e10;
                return res;
            }
        }

        /**
         * Forward class provide a method to calculate the differential
         * function of a specified value using <b>Forward Subtractions</b>
         */
        public static class Forward {
            /**
             * Returns the differentiation answer of the given index using <b>Forward Subtractions</b>
             *
             * @param func {@link PointsFunction} object representing the function to get its x and y points
             * @param x    the value of the required element to get the differentiation at
             * @return the result of the differentiation
             * @throws ArithmeticException if func is null <br>
             *                             <b>OR</b> index of x = 0 <br>
             *                             <b>OR</b> x do not exist <br>
             *                             <b>OR</b>  step h is not static
             */
            public static double getValueAt(PointsFunction func, double x) {
                if (func == null)
                    throw new ArithmeticException("invalid inputs : Functions cannot be null");
                //get x points
                ArrayList<Double> xp = func.getXp();
                // get y point
                ArrayList<Double> yp = func.getYp();
                // initialize and calculate h ; h = xi+1 - xi;
                double h = xp.get(1) - xp.get(0);
                //Rounding value back to fix floating-point precision errors
                h = Math.round(h * 1e10) / 1e10;
                for (int i = 1; i < xp.size() - 1; i++) {
                    // get the temporary value of xi+1 - xi
                    double temp = (xp.get(i + 1) - xp.get(i));
                    //Rounding value back to fix floating-point precision errors
                    temp = Math.round(temp * 1e10) / 1e10;
                    // if the temporary value do not equal h => the step is invalid
                    if (temp != h) {
                        throw new ArithmeticException("step h is not static");
                    }
                }
                //get index
                int index = xp.indexOf(x);
                if (index == 0)
                    throw new ArithmeticException("invalid inputs : cannot apply to x0");
                else if (index == -1)
                    throw new ArithmeticException("invalid inputs : element do not exist");
                // apply the law : (yi+1 - yi) / h
                double res = (yp.get(index + 1) - yp.get(index)) / h;
                //Rounding value back to fix floating-point precision errors
                res = Math.round(res * 1e10) / 1e10;
                return res;
            }
        }

        /**
         * Backward class provide a method to calculate the differential
         * function of a specified value using <b>Backward Subtractions</b>
         */
        public static class Backward {
            /**
             * Returns the differentiation answer of the given index using <b>Backward Subtractions</b>
             *
             * @param func {@link PointsFunction} object representing the function to get its x and y points
             * @param x    the value of the required element to get the differentiation at
             * @return the result of the differentiation
             * @throws ArithmeticException if func is null <br>
             *                             <b>OR</b> index of x = n <br>
             *                             <b>OR</b> x do not exist <br>
             *                             <b>OR</b>  step h is not static
             */
            public static double getValueAt(PointsFunction func, double x) {
                if (func == null)
                    throw new ArithmeticException("invalid inputs");
                //get x points
                ArrayList<Double> xp = func.getXp();
                // get y point
                ArrayList<Double> yp = func.getYp();
                // initialize and calculate h ; h = xi+1 - xi;
                double h = xp.get(1) - xp.get(0);
                //Rounding value back to fix floating-point precision errors
                h = Math.round(h * 1e10) / 1e10;
                for (int i = 1; i < xp.size() - 1; i++) {
                    // get the temporary value of xi+1 - xi
                    double temp = (xp.get(i + 1) - xp.get(i));
                    //Rounding value back to fix floating-point precision errors
                    temp = Math.round(temp * 1e10) / 1e10;
                    // if the temporary value do not equal h => the step is invalid
                    if (temp != h) {
                        throw new ArithmeticException("step h is not static");
                    }
                }
                //get index
                int index = xp.indexOf(x);
                if (index == (xp.size() - 1))
                    throw new ArithmeticException("invalid inputs : cannot apply to xn");
                else if (index == -1)
                    throw new ArithmeticException("invalid inputs : element do not exist");
                // apply the law : (yi - yi-1) / h
                double res = (yp.get(index) - yp.get(index - 1)) / h;
                //Rounding value back to fix floating-point precision errors
                res = Math.round(res * 1e10) / 1e10;
                return res;
            }
        }
    }

    /**
     * Formats the given Double as a string.
     *
     * <p>If the Double is an integer, it is formatted as an integer with no decimal places. Otherwise, it is
     * formatted as a decimal with one decimal place, using Western numerals and a period as the decimal separator.</p>
     *
     * @param num the Double to format.
     * @return the formatted Double as a string.
     */
    private static String getFormattedDouble(double num) {
        return (num == (int) num) ? String.valueOf((int) num) : String.valueOf(Math.round(num * 1e10) / 1e10);
    }
}
