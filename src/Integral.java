import java.text.DecimalFormat;

public class Integral {

    private static double e;

    public static double getRect(Function func, double a, double b, double n) {
        if (func == null || a <= b || n <= 0)
            throw new ArithmeticException("invalid inputs");
        double h = (b - a) / n;
        double sum = 0;
        double value = a;
        for (int i = 0; i <= n - 1; i++) {
            sum += func.getValueAt(value);
            value += h;
            value = Math.round(value * 1e10) / 1e10;
        }

        e = ((b - a) / 2) * h * (Math.max(func.getDiffAt(a, 1), func.getDiffAt(b, 1)));

        return sum * h;
    }

    public static double getTraps(Function func, double a, double b, double n) {
        if (func == null || a <= b || n <= 0)
            throw new ArithmeticException("invalid inputs");
        double h = (b - a) / n;
        double sum = 0;
        sum += func.getValueAt(a) + func.getValueAt(b);
        double value = a + h;
        value = Math.round(value * 1e10) / 1e10;
        for (int i = 1; i <= n - 1; i++) {
            sum += 2 * func.getValueAt(value);
            value += h;
            value = Math.round(value * 1e10) / 1e10;
        }

        e = ((b - a) / 12) * Math.pow(h, 2) * (Math.max(func.getDiffAt(a, 2), func.getDiffAt(b, 2)));

        return sum * (h / 2);
    }

    public static double getSimpson3(Function func, double a, double b, double n) {
        if (func == null || a <= b || n <= 0 || n % 2 != 0)
            throw new ArithmeticException("invalid inputs");
        double h = (b - a) / n;
        double sum = 0;
        sum += func.getValueAt(a) + func.getValueAt(b);
        double value = a + h;
        value = Math.round(value * 1e10) / 1e10;
        for (int i = 1; i <= n - 1; i++) {
            if (i % 2 == 0)
                sum += 2 * func.getValueAt(value);
            else
                sum += 4 * func.getValueAt(value);
            value += h;
            value = Math.round(value * 1e10) / 1e10;
        }

        e = ((b - a) / 180) * Math.pow(h, 4) * (Math.max(func.getDiffAt(a, 4), func.getDiffAt(b, 4)));

        return sum * (h / 3);
    }

    public static double getSimpson8(Function func, double a, double b, double n) {
        if (func == null || a <= b || n <= 0 || n % 3 != 0)
            throw new ArithmeticException("invalid inputs");
        double h = (b - a) / n;
        double sum = 0;
        sum += func.getValueAt(a) + func.getValueAt(b);
        double value = a + h;
        value = Math.round(value * 1e10) / 1e10;
        for (int i = 1; i <= n - 1; i++) {
            if (i % 3 == 0)
                sum += 2 * func.getValueAt(value);
            else
                sum += 3 * func.getValueAt(value);
            value += h;
            value = Math.round(value * 1e10) / 1e10;
        }

        e = ((b - a) / 80) * Math.pow(h, 4) * (Math.max(func.getDiffAt(a, 4), func.getDiffAt(b, 4)));

        return sum * 3 * (h / 8);
    }

    public static double getPaul(Function func, double a, double b, double n) {
        if (func == null || a <= b || n <= 0 || n % 4 != 0)
            throw new ArithmeticException("invalid inputs");
        double h = (b - a) / n;
        double sum = 0;
        sum += 7 * func.getValueAt(a) + 7 * func.getValueAt(b);
        double value = a + h;
        value = Math.round(value * 1e10) / 1e10;
        for (int i = 1; i <= n - 1; i++) {
            if (i % 4 == 0)
                sum += 14 * func.getValueAt(value);
            else if (i % 2 == 0)
                sum += 12 * func.getValueAt(value);
            else
                sum += 32 * func.getValueAt(value);
            value += h;
            value = Math.round(value * 1e10) / 1e10;
        }

        e = (2 * (b - a) / 945) * Math.pow(h, 6) * (Math.max(func.getDiffAt(a, 6), func.getDiffAt(b, 6)));

        return sum * 2 * (h / 45);
    }

    public static double getError() {
        return e;
    }


}
