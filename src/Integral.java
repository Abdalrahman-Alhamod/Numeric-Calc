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
}
