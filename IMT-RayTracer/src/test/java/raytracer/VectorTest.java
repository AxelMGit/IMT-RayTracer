package raytracer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    // On récupère l'epsilon défini dans la classe de base pour les comparaisons
    private static final double EPSILON = AbstractVec3.EPSILON;

    // Quelques vecteurs de base pour les tests
    private final Vector v1 = new Vector(1, 2, 3);
    private final Vector v2 = new Vector(4, 5, 6);
    private final Vector vX = new Vector(1, 0, 0);
    private final Vector vY = new Vector(0, 1, 0);
    private final Vector vZ = new Vector(0, 0, 1);

    @Test
    void testEquals() {
        // Teste la comparaison avec EPSILON
        assertTrue(new Vector(1, 1, 1).equals(new Vector(1, 1, 1 + EPSILON / 2)));
        assertFalse(new Vector(1, 1, 1).equals(new Vector(1, 1, 1 + EPSILON * 2)));
        assertTrue(v1.equals(new Vector(1, 2, 3)));
        assertFalse(v1.equals(v2));
    }

    @Test
    void testAdd() { // 
        Vector expected = new Vector(5, 7, 9);
        assertEquals(expected, v1.add(v2));
    }

    @Test
    void testSubtract() { // 
        Vector expected = new Vector(-3, -3, -3);
        assertEquals(expected, v1.subtract(v2));
    }

    @Test
    void testMultiplyScalar() { // 
        Vector expected = new Vector(3, 6, 9);
        assertEquals(expected, v1.multiply(3));
    }

    @Test
    void testDotProduct() { // 
        // 1*4 + 2*5 + 3*6 = 4 + 10 + 18 = 32
        double expected = 32.0;
        assertEquals(expected, v1.dot(v2), EPSILON);

        // Test d'orthogonalité
        assertEquals(0.0, vX.dot(vY), EPSILON);
    }

    @Test
    void testCrossProduct() { // 
        // vX (1,0,0) x vY (0,1,0) = vZ (0,0,1)
        assertEquals(vZ, vX.cross(vY));

        // Test non-commutatif: vY x vX = -vZ 
        assertEquals(vZ.multiply(-1), vY.cross(vX));
    }

    @Test
    void testSchurProduct() { // 
        // 1*4, 2*5, 3*6
        Vector expected = new Vector(4, 10, 18);
        assertEquals(expected, v1.schur(v2));
    }

    @Test
    void testLength() { // 
        // Vecteur 3-4-5
        Vector v = new Vector(3, 4, 0);
        assertEquals(5.0, v.length(), EPSILON);
        assertEquals(1.0, vX.length(), EPSILON);
    }

    @Test
    void testNormalize() { // 
        Vector v = new Vector(5, 0, 0);
        assertEquals(vX, v.normalize());

        // La longueur d'un vecteur normalisé doit être 1
        assertEquals(1.0, v1.normalize().length(), EPSILON);

        // La normalisation d'un vecteur nul doit donner un vecteur nul
        Vector zero = new Vector(0, 0, 0);
        assertEquals(zero, zero.normalize());
    }
}