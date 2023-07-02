import java.util.ArrayList;
import java.util.Objects;

public class Polynomial {
    private static ArrayList<Double> coeffs = null;

    public Polynomial(ArrayList<Double> coeffs) {
        Polynomial.coeffs = Objects.requireNonNull(coeffs, "coeffs cannot be null");
    }

    public static double evaluate(double x) {
        double res = 0.0;
        for (int i = 0; i < coeffs.size(); i++) {
            // Compute the contribution of the current term to the result and add it to the running sum
            // The contribution is the coefficient times x raised to the power of the degree of the current term
            // The degree of the current term is the number of terms remaining after the current term
            // minus one (since the degree of the highest-order term is zero)
            res += coeffs.get(i) * Math.pow(x, coeffs.size() - i - 1);
        }
        return res;
    }

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
                if (i < n && coeffs.get(i + 1) > 0) { // If there are more terms and the next coefficient is positive
                    sb.append(" + "); // Append the addition operator with spacing
                } else if (i < n && coeffs.get(i + 1) < 0) { // If there are more terms and the next coefficient is negative
                    sb.append(" - "); // Append the subtraction operator with spacing
                }
            }
        }
        return sb.toString(); // Return the final string representation
    }
}
