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
}
