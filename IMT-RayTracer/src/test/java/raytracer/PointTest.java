package raytracer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    private final Point p1 = new Point(1, 2, 3);
    private final Point p2 = new Point(5, 7, 9);
    private final Vector v = new Vector(4, 5, 6);

    @Test
    void testSubtractPoint() { //
        // Point - Point = Vecteur
        Vector expected = new Vector(4, 5, 6);
        assertEquals(expected, p2.subtract(p1));
    }

    @Test
    void testAddVector() { //
        // Point + Vecteur = Point
        Point expected = new Point(5, 7, 9);
        assertEquals(expected, p1.add(v));
    }

    @Test
    void testMultiplyScalar() {
        Point expected = new Point(2, 4, 6);
        assertEquals(expected, p1.multiply(2));
    }
}