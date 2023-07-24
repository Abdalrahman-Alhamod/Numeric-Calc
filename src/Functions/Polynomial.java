package Functions;

import Util.Accuracy;
import Util.BigDecimalUtil;
import Util.EvaluateString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Represents a polynomial with real coefficients.
 * The Polynomial class provides methods for evaluating the polynomial, computing its derivative and integral,
 * and performing basic arithmetic operations.
 */
public class Polynomial implements Function {
    /**
     * coefficients ArrayList of the current Polynomial
     */
    private ArrayList<BigDecimal> coeffs;

    /**
     * Constructs a Polynomial object with the given coefficients.
     *
     * @param coeffs the list of coefficients representing the polynomial terms, in descending order of degree.
     * @throws NullPointerException if the given list of coefficients is null.
     */
    public Polynomial(ArrayList<BigDecimal> coeffs) {
        this.coeffs = Objects.requireNonNull(coeffs, "coeffs cannot be null");
    }

    /**
     * Constructs a Polynomial object with the degree 0 (P0) with the given constant a0 value.
     *
     * @param a0 the constant value (first term of the Polynomial)
     */
    public Polynomial(BigDecimal a0) {
        coeffs = new ArrayList<>();
        coeffs.add(a0);
    }

    /**
     * Construct a Polynomial with the degree 1 (P1) with the given constant a0 and a1
     *
     * @param a0 the constant value (first term of the Polynomial
     * @param a1 the value multiplied with x term (second term of the Polynomial)
     */
    public Polynomial(BigDecimal a0, BigDecimal a1) {
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
    public BigDecimal getValueAt(BigDecimal x) {
        BigDecimal res = new BigDecimal(0);
        for (int i = 0; i < coeffs.size(); i++) {
            // Compute the contribution of the current term to the result and add it to the running sum
            // The contribution is the coefficient times x raised to the power of the degree of the current term
            // The degree of the current term is the number of terms remaining after the current term
            // minus one (since the degree of the highest-order term is zero)
            BigDecimal ans = coeffs.get(i).multiply(BigDecimalUtil.pow(x, new BigDecimal(i)));
            res = res.add(ans);
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
            BigDecimal coeff = coeffs.get(i); // Get the current coefficient
            if (coeff.compareTo(new BigDecimal(0)) != 0) { // If the coefficient is nonzero
                if (i == 0) { // If the term is the constant term
                    if (sb.isEmpty()) {
                        sb.append(coeff); // Append the coefficient
                    } else {
                        sb.append(coeff.abs()); // Append the coefficient
                    }
                } else if (i == 1) { // If the term is the linear term
                    if (sb.isEmpty()) {
                        if (coeff.compareTo(new BigDecimal(1)) == 0) { // If the coefficient is 1 or -1
                            sb.append("x"); // Append the variable symbol and exponent
                        } else if (coeff.compareTo(new BigDecimal(-1)) == 0) {
                            sb.append("-x");
                        } else { // If the coefficient is not 1 or -1
                            sb.append(coeff).append(" ").append("x"); // Append the coefficient, variable symbol, and exponent
                        }
                    } else {
                        if (coeff.compareTo(new BigDecimal(1)) == 0 || coeff.compareTo(new BigDecimal(-1)) == 0) { // If the coefficient is 1 or -1
                            sb.append("x"); // Append the variable symbol
                        } else { // If the coefficient is not 1 or -1
                            sb.append(coeff.abs()).append(" ").append("x"); // Append the coefficient and variable symbol
                        }
                    }
                } else { // If the term is a higher-order term
                    if (sb.isEmpty()) {
                        if (coeff.compareTo(new BigDecimal(1)) == 0) { // If the coefficient is 1 or -1
                            sb.append("x^").append(i); // Append the variable symbol and exponent
                        } else if (coeff.compareTo(new BigDecimal(-1)) == 0) {
                            sb.append("-x^").append(i);
                        } else { // If the coefficient is not 1 or -1
                            sb.append(coeff).append(" ").append("x^").append(i); // Append the coefficient, variable symbol, and exponent
                        }
                    } else {
                        if (coeff.compareTo(new BigDecimal(1)) == 0) { // If the coefficient is 1 or -1
                            sb.append("x^").append(i); // Append the variable symbol and exponent
                        } else { // If the coefficient is not 1 or -1
                            sb.append(coeff.abs()).append(" ").append("x^").append(i); // Append the coefficient, variable symbol, and exponent
                        }
                    }
                }
            }
            BigDecimal coeff1 = new BigDecimal(1);
            if (i > 0)
                coeff1 = coeffs.get(i - 1); // Get the prev coefficient
            //if (i > 0 && coeff1 != 0) {  //If there are more terms
            if (i > 0)
                if (coeff1.compareTo(new BigDecimal(0)) > 0 && !sb.isEmpty())
                    sb.append(" + ");  // Append the addition operator with spacing
                else if (coeff1.compareTo(new BigDecimal(0)) < 0)
                    sb.append(" - ");
            //}
        }
        // if there is a ' + ' , delete it
        if (sb.length() > 3 && sb.charAt(sb.length() - 1) == ' ')
            sb.delete(sb.length() - 3, sb.length() - 1);
        return sb.toString(); // Return the final string representation
    }

    /**
     * Computes the derivative of the polynomial.
     *
     * @return a new Polynomial object representing the derivative of the current polynomial.
     */
    public Polynomial getDerivative() {
        int n = coeffs.size(); // Get the size of the coefficient list
        ArrayList<BigDecimal> derivativeCoeffs = new ArrayList<>(n - 1); // Create a new ArrayList to store the derivative coefficients

        for (int i = 1; i < n; i++) { // Iterate through the coefficient list starting from the second term (index 1)
            BigDecimal coeff = coeffs.get(i); // Get the coefficient at index i in the original polynomial
            derivativeCoeffs.add(coeff.multiply(new BigDecimal(i))); // Multiply the coefficient by i (the exponent of the term) and add it to the derivative coefficient list
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
        ArrayList<BigDecimal> integralCoeffs = new ArrayList<>(n + 1); // Create a new ArrayList to store the integral coefficients

        integralCoeffs.add(new BigDecimal(0)); // Add the constant term of the integral polynomial, which is always 0

        for (int i = 0; i < n; i++) { // Iterate through the coefficient list
            BigDecimal coeff = coeffs.get(i); // Get the coefficient at index i in the original polynomial
            integralCoeffs.add(coeff.divide(new BigDecimal(i + 1), Accuracy.getValue(), RoundingMode.HALF_UP)); // Divide the coefficient by (i + 1) and add it to the integral coefficient list
        }

        return new Polynomial(integralCoeffs); // Create a new Polynomial object using the integral coefficient list and return it
    }

    /**
     * Returns the list of coefficients of the polynomial.
     *
     * @return the list of coefficients representing the polynomial terms, in descending order of degree.
     */
    public ArrayList<BigDecimal> getCoeffs() {
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
        ArrayList<BigDecimal> newCoeffs = new ArrayList<>();

        // Compute the degree of the product polynomial
        int degree = this.coeffs.size() + other.coeffs.size() - 2;

        // Loop over each term in the product polynomial
        for (int i = 0; i <= degree; i++) {
            BigDecimal coeff = new BigDecimal(0);

            // Compute the coefficient of the current term as the sum of the products of the corresponding terms
            // in the two input polynomials
            for (int j = 0; j <= i; j++) {
                if (j < this.coeffs.size() && (i - j) < other.coeffs.size()) {
                    coeff = coeff.add(this.coeffs.get(j).multiply(other.coeffs.get(i - j)));
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
    public Polynomial multiply(BigDecimal scalar) {
        // Create a new ArrayList to store the scaled coefficients
        ArrayList<BigDecimal> scaledCoeffs = new ArrayList<>(coeffs.size());

        // Multiply each coefficient by the scalar value and add it to the scaled coefficient list
        for (BigDecimal coeff : coeffs) {
            scaledCoeffs.add(coeff.multiply(scalar));
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
        ArrayList<BigDecimal> newCoeffs = new ArrayList<>();

        // Determine the larger of the two polynomials
        Polynomial larger = (this.coeffs.size() > other.coeffs.size()) ? this : other;

        // Determine the smaller of the two polynomials
        Polynomial smaller = (this.coeffs.size() > other.coeffs.size()) ? other : this;

        // Compute the sum of the corresponding terms in the two polynomials
        for (int i = 0; i < larger.coeffs.size(); i++) {
            BigDecimal coeff = larger.coeffs.get(i);

            if (i < smaller.coeffs.size()) {
                coeff = coeff.add(smaller.coeffs.get(i));
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
    public Polynomial add(BigDecimal c) {
        ArrayList<BigDecimal> coeffs = new ArrayList<>(this.getCoeffs());
        coeffs.set(0, this.getCoeffs().get(0).add(c));
        return new Polynomial(coeffs);
    }

    /**
     * Returns Polynomial representing the result Polynomial
     * by replacing the Polynomial p instead of BigDecimal x
     *
     * @param p the Polynomial to be Substituted
     * @return Polynomial representing the result of replacing the Polynomial p instead of BigDecimal x
     * @throws ArithmeticException if the given p is null
     */
    public Polynomial getPolyOf(Polynomial p) {
        if (p == null)
            throw new ArithmeticException("invalid inputs : p cannot be null");
        // Init res poly with a0 of coeff input
        Polynomial res = new Polynomial(coeffs.get(0));
        for (int i = 1; i < coeffs.size(); i++) {
            Polynomial multAns = new Polynomial(new BigDecimal(1));
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
    public BigDecimal getDiffAt(BigDecimal x, int rank) {
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
    public BigDecimal getIntegralAt(BigDecimal x, int rank) {
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
    public PointsFunction toPointsFunction(BigDecimal a, BigDecimal b, int n) {
        if (a.compareTo(b) >= 0)
            throw new ArithmeticException("invalid inputs : a cannot be greater or equal to b");
        else if (n <= 0)
            throw new ArithmeticException("invalid inputs : n cannot be smaller or equal to zero");
        n--;
        BigDecimal h = (b.subtract(a)).divide(new BigDecimal(n), Accuracy.getValue(), RoundingMode.HALF_UP);
        ArrayList<BigDecimal> xp = new ArrayList<>();
        ArrayList<BigDecimal> yp = new ArrayList<>();
        BigDecimal curr = new BigDecimal(a.toString());
        for (int i = 0; i <= n; i++) {
            xp.add(curr);
            yp.add(getValueAt(curr));
            curr = curr.add(h);
        }
        return new PointsFunction(xp, yp);
    }

    /**
     * Converts the function into a PointsFunction.
     *
     * @param a The lower bound of the x-coordinate range as string
     * @param b The upper bound of the x-coordinate range as string
     * @param n The number of points to generate
     * @return The PointsFunction representing the function over the given range.
     * @throws ArithmeticException if any of the inputs is null
     */
    public PointsFunction toPointsFunction(String a, String b, int n) {
        if (a == null)
            throw new ArithmeticException("invalid inputs : a cannot be null");
        else if (b == null)
            throw new ArithmeticException("invalid inputs : b cannot be null");
        a = a.replaceAll("pi", Double.toString(Math.PI));
        BigDecimal ad = EvaluateString.evaluate(a);
        b = b.replaceAll("pi", Double.toString(Math.PI));
        BigDecimal bd = EvaluateString.evaluate(b);
        return toPointsFunction(ad, bd, n);
    }

    /**
     * The Horner class provides methods for polynomial evaluation, division, and differentiation using Horner's method.
     */
    public static class Horner {

        private static ArrayList<BigDecimal> b;

        /**
         * Calculates the intermediate coefficients of the polynomial using Horner's method.
         *
         * @param poly The polynomial to evaluate.
         * @param x    The value of x for evaluation.
         */
        private static void calcB(Polynomial poly, BigDecimal x) {
            ArrayList<BigDecimal> a = new ArrayList<>(poly.getCoeffs());
            Collections.reverse(a);
            BigDecimal bi = a.get(0);
            b = new ArrayList<>();
            b.add(bi);
            for (int i = 1; i < a.size(); i++) {
                bi = x.multiply(bi).add(a.get(i));
                b.add(bi);
            }
        }

        /**
         * Evaluates the polynomial at the specified value of x using Horner's method.
         *
         * @param poly The polynomial to evaluate.
         * @param x    The value of x for evaluation.
         * @return The result of evaluating the polynomial at x.
         */
        public static BigDecimal getValueAt(Polynomial poly, BigDecimal x) {
            calcB(poly, x);
            return b.get(b.size() - 1);
        }

        /**
         * Divides the polynomial by (x - c) using Horner's method and returns the resulting polynomial.
         *
         * @param poly The polynomial to divide.
         * @param x    The value of x in (x - c).
         * @return The resulting polynomial after division.
         */
        public static Polynomial getDivideOn(Polynomial poly, BigDecimal x) {
            calcB(poly, x);
            ArrayList<BigDecimal> coeff = new ArrayList<>();
            for (int i = 0; i < b.size() - 1; i++)
                coeff.add(b.get(i));

            Collections.reverse(coeff);
            return new Polynomial(coeff);
        }

        /**
         * Calculates the value of the derivative of the polynomial at the specified value of x and rank using Horner's method.
         *
         * @param poly The polynomial to differentiate.
         * @param x    The value of x for differentiation.
         * @param rank The rank of the derivative.
         * @return The value of the derivative at x.
         */
        public static BigDecimal getDiffAt(Polynomial poly, BigDecimal x, int rank) {
            b = new ArrayList<>();
            Polynomial tempPoly = new Polynomial(poly.getCoeffs());
            BigDecimal bi = x;
            BigDecimal factor = new BigDecimal(1);
            for (int i = 0; i <= rank; i++) {
                if (i > 0 && b.size() == 0) {
                    bi = new BigDecimal(0);
                    break;
                }
                calcB(tempPoly, x);
                bi = b.get(b.size() - 1);
                b.remove(b.size() - 1);
                Collections.reverse(b);
                tempPoly.coeffs = b;
                if (i > 0)
                    factor = factor.multiply(new BigDecimal(i));
            }
            return bi.multiply(factor);
        }

    }

}
