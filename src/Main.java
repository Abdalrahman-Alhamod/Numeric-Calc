public class Main {
    public static void main(String[] args) {
        Function func = new Function(null);
        double a = 1, b = 2, n = 1e8;
        System.out.println("1/x Integral : ");
        System.out.println("a = " + a + "\nb = " + b + "\nn = " + n);
        System.out.println("Rect : " + Integral.getRect(func, a, b, n));
        System.out.println("Traps : " + Integral.getTraps(func, a, b, n));
        System.out.println("Simpson 1/3 : " + Integral.getSimpson3(func, a, b, n));
        System.out.println("Simpson 3/8 : " + Integral.getSimpson8(func, a, b, n));
        System.out.println("Paul : " + Integral.getPaul(func, a, b, n));
        System.out.println("Real Ans = " + (Math.log(2) - Math.log(1)));
    }
}