import java.util.*;

/**
 * Represents a polynomial with real coefficients.
 * The Polynomial class provides methods for evaluating the polynomial, computing its derivative and integral,
 * and performing basic arithmetic operations.
 */
public class Polynomial implements Function {
    /**
     * coefficients ArrayList of the current Polynomial
     */
    private ArrayList<Double> coeffs;

    /**
     * Constructs a Polynomial object with the given coefficients.
     *
     * @param coeffs the list of coefficients representing the polynomial terms, in descending order of degree.
     * @throws NullPointerException if the given list of coefficients is null.
     */
    public Polynomial(ArrayList<Double> coeffs) {
        this.coeffs = Objects.requireNonNull(coeffs, "coeffs cannot be null");
    }

    /**
     * Constructs a Polynomial object with the degree 0 (P0) with the given constant a0 value.
     *
     * @param a0 the constant value (first term of the Polynomial)
     */
    public Polynomial(double a0) {
        coeffs = new ArrayList<>();
        coeffs.add(a0);
    }

    /**
     * Construct a Polynomial with the degree 1 (P1) with the given constant a0 and a1
     *
     * @param a0 the constant value (first term of the Polynomial
     * @param a1 the value multiplied with x term (second term of the Polynomial)
     */
    public Polynomial(double a0, double a1) {
        coeffs = new ArrayList<>();
        coeffs.add(a0);
        coeffs.add(a1);
    }

    /**
     * Computes the value of the polynomial at the given x.
     *
     * @param x the value at which to evaluate the polynomial.
     * @return the computed value of the polynomial.
     */
    public double getValueAt(double x) {
        double res = 0.0;
        for (int i = 0; i < coeffs.size(); i++) {
            // Compute the contribution of the current term to the result and add it to the running sum
            // The contribution is the coefficient times x raised to the power of the degree of the current term
            // The degree of the current term is the number of terms remaining after the current term
            // minus one (since the degree of the highest-order term is zero)
            double ans = coeffs.get(i) * Math.pow(x, i);
            //Rounding value back to fix floating-point precision errors
            ans = Math.round(ans * 1e10) / 1e10;
            res += ans;
        }
        return res;
    }

    /**
     * Returns a string representation of the polynomial.
     *
     * @return a string representation of the polynomial in the form "a_n * x^n + a_(n-1) * x^(n-1) + ... + a_1 * x + a_0".
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Create a new StringBuilder to build the string representation
        int n = coeffs.size() - 1; // Get the degree of the polynomial (the size of the coefficient ArrayList minus one)
        for (int i = n; i >= 0; i--) { // Loop over each coefficient from the constant term to the highest-order term
            double coeff = coeffs.get(i); // Get the current coefficient
            //Rounding value back to fix floating-point precision errors
            coeff = Math.round(coeff * 1e10) / 1e10;
            if (coeff != 0) { // If the coefficient is nonzero
                if (i == 0) { // If the term is the constant term
                    sb.append(getFormattedCoefficient(coeff)); // Append the coefficient
                } else if (i == 1) { // If the term is the linear term
                    if (coeff == 1) { // If the coefficient is 1
                        sb.append("x"); // Append the variable symbol
                    } else if (coeff == -1) { // If the coefficient is -1
                        sb.append("-x"); // Append the negative variable symbol
                    } else { // If the coefficient is not 1 or -1
                        sb.append(getFormattedCoefficient(coeff)).append(" ").append("x"); // Append the coefficient and variable symbol
                    }
                } else { // If the term is a higher-order term
                    if (coeff == 1) { // If the coefficient is 1
                        sb.append("x^").append(i); // Append the variable symbol and exponent
                    } else if (coeff == -1) { // If the coefficient is -1
                        sb.append("-x^").append(i); // Append the negative variable symbol and exponent
                    } else { // If the coefficient is not 1 or -1
                        sb.append(getFormattedCoefficient(coeff)).append(" ").append("x^").append(i); // Append the coefficient, variable symbol, and exponent
                    }
                }
                double coeff1 = 1;
                if (i > 0)
                    coeff1 = coeffs.get(i - 1); // Get the prev coefficient
                //Rounding value back to fix floating-point precision errors
                coeff1 = Math.round(coeff1 * 1e10) / 1e10;
                //if (i > 0 && coeff1 != 0) {  //If there are more terms
                if (i > 0)
                    sb.append(" + ");  // Append the addition operator with spacing
                //}
            }
        }
        if (sb.charAt(sb.length() - 1) == ' ')
            sb.delete(sb.length()-3,sb.length()-1);
        return sb.toString(); // Return the final string representation
    }

    /**
     * Formats the given coefficient as a string.
     *
     * <p>If the coefficient is an integer, it is formatted as an integer with no decimal places. Otherwise, it is
     * formatted as a decimal with one decimal place, using Western numerals and a period as the decimal separator.</p>
     *
     * @param coeff the coefficient to format.
     * @return the formatted coefficient as a string.
     */
    private String getFormattedCoefficient(double coeff) {
        return (coeff == (int) coeff) ? String.valueOf((int) coeff) : String.valueOf(Math.round(coeff * 1e10) / 1e10);
    }

    /**
     * Computes the derivative of the polynomial.
     *
     * @return a new Polynomial object representing the derivative of the current polynomial.
     */
    public Polynomial getDerivative() {
        int n = coeffs.size(); // Get the size of the coefficient list
        ArrayList<Double> derivativeCoeffs = new ArrayList<>(n - 1); // Create a new ArrayList to store the derivative coefficients

        for (int i = 1; i < n; i++) { // Iterate through the coefficient list starting from the second term (index 1)
            double coeff = coeffs.get(i); // Get the coefficient at index i in the original polynomial
            derivativeCoeffs.add(coeff * i); // Multiply the coefficient by i (the exponent of the term) and add it to the derivative coefficient list
        }

        return new Polynomial(derivativeCoeffs); // Create a new Polynomial object using the derivative coefficient list and return it
    }

    /**
     * Computes the integral of the polynomial.
     *
     * @return a new Polynomial object representing the integral of the current polynomial.
     */
    public Polynomial getIntegral() {
        int n = coeffs.size(); // Get the size of the coefficient list
        ArrayList<Double> integralCoeffs = new ArrayList<>(n + 1); // Create a new ArrayList to store the integral coefficients

        integralCoeffs.add(0.0); // Add the constant term of the integral polynomial, which is always 0

        for (int i = 0; i < n; i++) { // Iterate through the coefficient list
            double coeff = coeffs.get(i); // Get the coefficient at index i in the original polynomial
            integralCoeffs.add(coeff / (i + 1)); // Divide the coefficient by (i + 1) and add it to the integral coefficient list
        }

        return new Polynomial(integralCoeffs); // Create a new Polynomial object using the integral coefficient list and return it
    }

    /**
     * Returns the list of coefficients of the polynomial.
     *
     * @return the list of coefficients representing the polynomial terms, in descending order of degree.
     */
    ArrayList<Double> getCoeffs() {
        return coeffs;
    }

    /**
     * Computes the product of two polynomials.
     *
     * @param other the other polynomial to multiply with this polynomial.
     * @return a new Polynomial object representing the product of the two polynomials.
     * @throws NullPointerException if the other polynomial is null.
     */
    public Polynomial multiply(Polynomial other) {
        Objects.requireNonNull(other, "other cannot be null");

        // Create a new ArrayList to store the coefficients of the product polynomial
        ArrayList<Double> newCoeffs = new ArrayList<>();

        // Compute the degree of the product polynomial
        int degree = this.coeffs.size() + other.coeffs.size() - 2;

        // Loop over each term in the product polynomial
        for (int i = 0; i <= degree; i++) {
            double coeff = 0.0;

            // Compute the coefficient of the current term as the sum of the products of the corresponding terms
            // in the two input polynomials
            for (int j = 0; j <= i; j++) {
                if (j < this.coeffs.size() && (i - j) < other.coeffs.size()) {
                    coeff += this.coeffs.get(j) * other.coeffs.get(i - j);
                }
            }

            // Add the computed coefficient to the list of coefficients of the product polynomial
            newCoeffs.add(coeff);
        }

        // Create a new Polynomial object using the computed coefficients and return it
        return new Polynomial(newCoeffs);
    }

    /**
     * Multiplies the polynomial by a scalar value.
     *
     * @param scalar the scalar value to multiply the polynomial by.
     * @return a new Polynomial object representing the product of the polynomial and the scalar value.
     */
    public Polynomial multiply(double scalar) {
        // Create a new ArrayList to store the scaled coefficients
        ArrayList<Double> scaledCoeffs = new ArrayList<>(coeffs.size());

        // Multiply each coefficient by the scalar value and add it to the scaled coefficient list
        for (double coeff : coeffs) {
            scaledCoeffs.add(coeff * scalar);
        }

        // Create a new Polynomial object using the scaled coefficient list and return it
        return new Polynomial(scaledCoeffs);
    }

    /**
     * Adds this polynomial to another polynomial.
     *
     * @param other the other polynomial to add to this polynomial.
     * @return a new Polynomial object representing the sum of the two polynomials.
     * @throws NullPointerException if the other polynomial is null.
     */
    public Polynomial add(Polynomial other) {
        Objects.requireNonNull(other, "other cannot be null");

        // Create a new ArrayList to store the coefficients of the sum polynomial
        ArrayList<Double> newCoeffs = new ArrayList<>();

        // Determine the larger of the two polynomials
        Polynomial larger = (this.coeffs.size() > other.coeffs.size()) ? this : other;

        // Determine the smaller of the two polynomials
        Polynomial smaller = (this.coeffs.size() > other.coeffs.size()) ? other : this;

        // Compute the sum of the corresponding terms in the two polynomials
        for (int i = 0; i < larger.coeffs.size(); i++) {
            double coeff = larger.coeffs.get(i);

            if (i < smaller.coeffs.size()) {
                coeff += smaller.coeffs.get(i);
            }

            newCoeffs.add(coeff);
        }

        // Create a new Polynomial object using the computed coefficients and return it
        return new Polynomial(newCoeffs);
    }

    /**
     * Adds to this polynomial the constant c.
     *
     * @param c the constant to add to this polynomial.
     * @return a new Polynomial object representing the sum of this polynomial with c value.
     */
    public Polynomial add(double c) {
        ArrayList<Double> coeffs = new ArrayList<>(this.getCoeffs());
        coeffs.set(0, this.getCoeffs().get(0) + c);
        return new Polynomial(coeffs);
    }

    /**
     * Returns Polynomial representing the result Polynomial
     * by replacing the Polynomial p instead of double x
     *
     * @param p the Polynomial to be Substituted
     * @return Polynomial representing the result of replacing the Polynomial p instead of double x
     * @throws ArithmeticException if the given p is null
     */
    public Polynomial getPolyOf(Polynomial p) {
        if (p == null)
            throw new ArithmeticException("invalid inputs");
        // Init res poly with a0 of coeff input
        Polynomial res = new Polynomial(coeffs.get(0));
        for (int i = 1; i < coeffs.size(); i++) {
            Polynomial multAns = new Polynomial(1);
            for (int j = 0; j < i; j++) {
                multAns = multAns.multiply(p);
            }
            multAns = multAns.multiply(coeffs.get(i));
            res = res.add(multAns);
        }
        return res;
    }

    /**
     * Return the value of the differentiated Polynomial at the given x point
     *
     * @param x    the value to evaluate the differentiated Polynomial with
     * @param rank the rank of differentiation
     * @return the value of the differentiated Polynomial in the given x point
     * @throws ArithmeticException if rank is smaller than zero
     */
    public double getDiffAt(double x, int rank) {
        if (rank < 0)
            throw new ArithmeticException("invalid rank");
        if (rank == 0)
            return this.getValueAt(x);
        Polynomial diffPoly = new Polynomial(this.getCoeffs());
        for (int i = 1; i <= rank; i++) {
            diffPoly = diffPoly.getDerivative();
        }
        return diffPoly.getValueAt(x);
    }

    /**
     * Return the value of the Integral Polynomial in the given x point
     *
     * @param x    the value to evaluate the Integral Polynomial with
     * @param rank the rank of Integration
     * @return the value of the Integral Polynomial in the given x point
     * @throws ArithmeticException if rank is smaller than zero
     */
    public double getIntegralAt(double x, int rank) {
        if (rank < 0)
            throw new ArithmeticException("invalid rank");
        if (rank == 0)
            return this.getValueAt(x);
        Polynomial integPoly = new Polynomial(this.getCoeffs());
        for (int i = 1; i <= rank; i++) {
            integPoly = integPoly.getIntegral();
        }
        return integPoly.getValueAt(x);
    }

    /**
     * Convert this Polynomial into Points Function
     *
     * @param a the lower limit of x point
     * @param b the upper limit of x point
     * @param n the number required points
     * @return {@link PointsFunction} object representing the converted Polynomial
     * @throws ArithmeticException if a>=b or n<=0
     */
    public PointsFunction toPointsFunction(double a, double b, double n) {
        if (a >= b || n <= 0)
            throw new ArithmeticException("invalid inputs");
        n--;
        //Rounding value back to fix floating-point precision errors
        a = Math.round(a * 1e10) / 1e10;
        //Rounding value back to fix floating-point precision errors
        b = Math.round(b * 1e10) / 1e10;
        double h = (b - a) / n;
        //Rounding value back to fix floating-point precision errors
        h = Math.round(h * 1e10) / 1e10;
        ArrayList<Double> xp = new ArrayList<>();
        ArrayList<Double> yp = new ArrayList<>();
        for (double i = a; i <= b; i += h) {
            //Rounding value back to fix floating-point precision errors
            i = Math.round(i * 1e10) / 1e10;
            xp.add(i);
            yp.add(getValueAt(i));
        }
        return new PointsFunction(xp, yp);
    }

    public static class Horner {

        private static ArrayList<Double> b;

        private static void calcB(Polynomial poly, double x) {
            ArrayList<Double> a = poly.getCoeffs();
            Collections.reverse(a);
            double bi = a.get(0);
            b = new ArrayList<>();
            b.add(bi);
            for (int i = 1; i < a.size(); i++) {
                bi = x * bi + a.get(i);
                b.add(bi);
            }
        }

        public static double getValueAt(Polynomial poly, double x) {
            calcB(poly, x);
            return b.get(b.size() - 1);
        }

        public static Polynomial getDivideOn(Polynomial poly, double x) {
            calcB(poly, x);
            ArrayList<Double> coeff = new ArrayList<>();
            for (int i = 0; i < b.size() - 1; i++)
                coeff.add(b.get(i));

            Collections.reverse(coeff);
            return new Polynomial(coeff);
        }

        public static double getDiffAt(Polynomial poly, double x, int rank) {
            b = new ArrayList<>();
            double bi = x;
            double factor = 1;
            for (int i = 0; i <= rank; i++) {
                if (i > 0 && b.size() == 0) {
                    bi = 0;
                    break;
                }
                calcB(poly, x);
                bi = b.get(b.size() - 1);
                b.remove(b.size() - 1);
                Collections.reverse(b);
                poly.coeffs = b;
                if (i > 0)
                    factor *= (i);
            }
            return bi * factor;
        }

    }

}
