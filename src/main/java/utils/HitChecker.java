package utils;

public class HitChecker {

    public static boolean hit(double x, double y, double r) {
        // 1 прямоугольник в 2 четверти
        if (x <= 0 && x >= -r / 2 && y >= 0 && y <= r) {
            return true;
        }

        // 2 треугольник в 1 четверти
        if (x >= 0 && y >= 0 && x <= r / 2 && y <= (r - 2 * x)) {
            return true;
        }

        // 3 четверть круга в четверти
        if (x >= 0 && y <= 0 && (x * x + y * y) <= (r * r) / 4.0) {
            return true;
        }

        return false;
    }
}
