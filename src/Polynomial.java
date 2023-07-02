import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a polynomial with real coefficients.
 * The Polynomial class provides methods for evaluating the polynomial, computing its derivative and integral,
 * and performing basic arithmetic operations.
 */
public class Polynomial {
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
            res += coeffs.get(i) * Math.pow(x, i);
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
        for (int i = 0; i <= n; i++) { // Loop over each coefficient from the constant term to the highest-order term
            double coeff = coeffs.get(i); // Get the current coefficient
            if (coeff != 0) { // If the coefficient is nonzero
                if (i == 0) { // If the term is the constant term
                    sb.append(coeff); // Append the coefficient
                } else if (i == 1) { // If the term is the linear term
                    if (coeff == 1) { // If the coefficient is 1
                        sb.append("x"); // Append the variable symbol
                    } else if (coeff == -1) { // If the coefficient is -1
                        sb.append("-x"); // Append the negative variable symbol
                    } else { // If the coefficient is not 1 or -1
                        sb.append(coeff).append("x"); // Append the coefficient and variable symbol
                    }
                } else { // If the term is a higher-order term
                    if (coeff == 1) { // If the coefficient is 1
                        sb.append("x^").append(i); // Append the variable symbol and exponent
                    } else if (coeff == -1) { // If the coefficient is -1
                        sb.append("-x^").append(i); // Append the negative variable symbol and exponent
                    } else { // If the coefficient is not 1 or -1
                        sb.append(coeff).append("x^").append(i); // Append the coefficient, variable symbol, and exponent
                    }
                }
                if (i < n) {  //If there are more terms
                    sb.append(" + ");  // Append the addition operator with spacing
                }
            }
        }
        return sb.toString(); // Return the final string representation
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

}
