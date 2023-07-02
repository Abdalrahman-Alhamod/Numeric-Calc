import java.util.ArrayList;
import java.util.Objects;

public class Polynomial {
    private static ArrayList<Double> coeffs = null;

    public Polynomial(ArrayList<Double> coeffs) {
        Polynomial.coeffs = Objects.requireNonNull(coeffs, "coeffs cannot be null");
    }

}
