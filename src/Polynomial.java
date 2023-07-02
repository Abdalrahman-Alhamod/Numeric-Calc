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
            res += coeffs.get(i) * Math.pow(x, coeffs.size() - i - 1);
        }
        return res;
    }
}
