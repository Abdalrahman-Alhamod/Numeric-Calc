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

    public static double getSimpson8(Function func, double a, double b, double n) {
        double h = (b - a) / n;
        double sum = 0;
        sum += func.getValueAt(a) + func.getValueAt(b);
        double value = a + h;
        for (int i = 1; i <= n - 1; i++) {
            if (i % 3 == 0)
                sum += 2 * func.getValueAt(value);
            else
                sum += 3 * func.getValueAt(value);
            value += h;
        }

        return sum * 3 * (h / 8);
    }

    public static double getPaul(Function func, double a, double b, double n) {
        double h = (b - a) / n;
        double sum = 0;
        sum += 7 * func.getValueAt(a) + 7 * func.getValueAt(b);
        double value = a + h;
        for (int i = 1; i <= n - 1; i++) {
            if (i % 4 == 0)
                sum += 14 * func.getValueAt(value);
            else if (i % 2 == 0)
                sum += 12 * func.getValueAt(value);
            else
                sum += 32 * func.getValueAt(value);
            value += h;
        }

        return sum * 2 * (h / 45);
    }
}
