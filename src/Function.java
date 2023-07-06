import java.util.ArrayList;
import java.util.Objects;

public abstract class Function {

    abstract public double getValueAt(double x);

    abstract public double getDiffAt(double x, int rank);

    abstract public double getIntegralAt(double x, int rank);

    abstract public String toString();

}
