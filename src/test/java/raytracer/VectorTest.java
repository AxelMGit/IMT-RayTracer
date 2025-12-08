package raytracer;

import org.junit.jupiter.api.Test;
import raytracer.geometry.AbstractVec3;
import raytracer.geometry.Vector;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    private static final double EPSILON = AbstractVec3.EPSILON;

    private final Vector v1 = new Vector(1, 2, 3);
    private final Vector v2 = new Vector(4, 5, 6);
    private final Vector vX = new Vector(1, 0, 0);
    private final Vector vY = new Vector(0, 1, 0);
    private final Vector vZ = new Vector(0, 0, 1);

    @Test
    void testAdd() {
        Vector result = v1.add(v2);
        assertEquals(new Vector(5, 7, 9), result, "L'addition de vecteurs est incorrecte");
    }

    @Test
    void testSubtract() {
        Vector result = v1.subtract(v2);
        assertEquals(new Vector(-3, -3, -3), result, "La soustraction de vecteurs est incorrecte");
    }

    @Test
    void testMultiplyScalar() {
        Vector result = v1.multiply(2.0);
        assertEquals(new Vector(2, 4, 6), result, "La multiplication par un scalaire est incorrecte");
    }

    @Test
    void testDotProduct() {
        // v1 . v2 = 1*4 + 2*5 + 3*6 = 4 + 10 + 18 = 32
        double result = v1.dot(v2);
        assertEquals(32.0, result, EPSILON, "Le produit scalaire est incorrect");

        // Orthogonalité
        assertEquals(0.0, vX.dot(vY), EPSILON, "Le produit scalaire de vecteurs orthogonaux doit être 0");
    }

    @Test
    void testCrossProduct() {
        // X ^ Y = Z
        assertEquals(vZ, vX.cross(vY), "Le produit vectoriel X ^ Y devrait donner Z");
        // Y ^ X = -Z
        assertEquals(new Vector(0, 0, -1), vY.cross(vX), "Le produit vectoriel Y ^ X devrait donner -Z");
    }

    @Test
    void testSchurProduct() {
        Vector result = v1.schur(v2);
        assertEquals(new Vector(4, 10, 18), result, "Le produit de Schur est incorrect");
    }

    @Test
    void testLength() {
        assertEquals(Math.sqrt(14), v1.length(), EPSILON, "La longueur du vecteur est incorrecte");
        assertEquals(1.0, vX.length(), EPSILON, "La longueur d'un vecteur unitaire doit être 1");
    }

    @Test
    void testNormalize() {
        Vector norm = v1.normalize();
        assertEquals(1.0, norm.length(), EPSILON, "Un vecteur normalisé doit avoir une longueur de 1");

        // Vérification de la direction (colinéarité)
        // v1 et norm doivent être proportionnels. v1 = length * norm
        Vector check = norm.multiply(v1.length());
        assertEquals(v1, check, "La direction a changé après normalisation");
    }
}