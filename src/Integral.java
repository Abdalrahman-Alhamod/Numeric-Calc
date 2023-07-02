public class Integral {
    public static double getRect(Function func, double a, double b, double n) {
        double h = (b - a) / n;
        double sum = 0;
        double value = a;
        for (int i = 0; i <= n - 1; i++) {
            sum += func.getValueAt(value);
            value += h;
        }

        return sum * h;
    }

    public static double getTraps(Function func, double a, double b, double n) {
        double h = (b - a) / n;
        double sum = 0;
        sum += func.getValueAt(a) + func.getValueAt(b);
        double value = a + h;
        for (int i = 1; i <= n - 1; i++) {
            sum += 2 * func.getValueAt(value);
            value += h;
        }

        return sum * (h / 2);
    }

    public static double getSimpson3(Function func, double a, double b, double n) {
        double h = (b - a) / n;
        double sum = 0;
        sum += func.getValueAt(a) + func.getValueAt(b);
        double value = a + h;
        for (int i = 1; i <= n - 1; i++) {
            if (i % 2 == 0)
                sum += 2 * func.getValueAt(value);
            else
                sum += 4 * func.getValueAt(value);
            value += h;
        }

        return sum * (h / 3);
    }
}
