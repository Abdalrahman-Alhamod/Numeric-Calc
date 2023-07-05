import java.util.ArrayList;

public class Differentiation {
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
         * @param func {@link Function} object representing the function to be differentiated
         * @return the result of Differentiation as {@link Polynomial}
         * @throws ArithmeticException if the given function is null
         */
        public static Polynomial getDFAP(Function func) {
            if (func == null)
                throw new ArithmeticException("invalid inputs");
            // get x points
            ArrayList<Double> xp = func.getXp();
            // get y point
            ArrayList<Double> yp = func.getYp();
            // initialize result Polynomial (Interpolation answer by Lagrange )
            // with value 0 to add it to other Polynomial
            Polynomial res = new Polynomial(0);
            for (int i = 0; i < xp.size(); i++) {
                //get Lagrange Polynomial at index = i (L'i)
                Polynomial lag = getLagrangePolyAt(i, xp);
                //multiply Lagrange Polynomial with yi ( yi * L'i)
                lag = lag.multiply(yp.get(i));
                // add the multiply result to result Polynomial (Interpolation answer by Lagrange )
                res = res.add(lag);
            }
            return res;
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
