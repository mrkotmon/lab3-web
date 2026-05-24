package utils;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class HitCheckerTest {

    private static final double R = 2.0;

    @Test
    public void originIsHit() {
        assertTrue(HitChecker.hit(0, 0, R));
    }

    @Test
    public void rectangleAreaHit() {
        // Прямоугольник во 2-й четверти: x<=0, x>=-r/2, 0<=y<=r
        assertTrue(HitChecker.hit(-1, 1, R));
    }

    @Test
    public void triangleAreaHit() {
        // Треугольник в 1-й четверти: x>=0, y>=0, x<=r/2, y<=r-2x
        assertTrue(HitChecker.hit(0.5, 0.5, R));
    }

    @Test
    public void quarterCircleAreaHit() {
        // Четверть круга в 4-й четверти: x>=0, y<=0, x^2+y^2 <= (r/2)^2
        assertTrue(HitChecker.hit(0.5, -0.5, R));
    }

    @Test
    public void thirdQuadrantIsMiss() {
        // 3-я четверть (x<0, y<0) полностью вне области
        assertFalse(HitChecker.hit(-1, -1, R));
    }

    @Test
    public void outsideRectangleIsMiss() {
        // За границей прямоугольника по x
        assertFalse(HitChecker.hit(-2, 1, R));
    }

    @Test
    public void outsideTriangleIsMiss() {
        assertFalse(HitChecker.hit(2, 2, R));
    }
}
