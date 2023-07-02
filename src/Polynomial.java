import java.util.ArrayList;
import java.util.Objects;

public class Polynomial {
    private ArrayList<Double> coeffs = null;

    public Polynomial(ArrayList<Double> coeffs) {
        this.coeffs = Objects.requireNonNull(coeffs, "coeffs cannot be null");
    }

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

    public Polynomial getDerivative() {
        int n = coeffs.size(); // Get the size of the coefficient list
        ArrayList<Double> derivativeCoeffs = new ArrayList<>(n - 1); // Create a new ArrayList to store the derivative coefficients

        for (int i = 1; i < n; i++) { // Iterate through the coefficient list starting from the second term (index 1)
            double coeff = coeffs.get(i); // Get the coefficient at index i in the original polynomial
            derivativeCoeffs.add(coeff * i); // Multiply the coefficient by i (the exponent of the term) and add it to the derivative coefficient list
        }

        return new Polynomial(derivativeCoeffs); // Create a new Polynomial object using the derivative coefficient list and return it
    }

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


}
