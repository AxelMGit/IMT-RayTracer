package raytracer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    private static final double EPSILON = AbstractVec3.EPSILON;

    @Test
    void testDefaultConstructor() {
        // Le constructeur par défaut doit être noir
        Color black = new Color(0, 0, 0);
        assertEquals(black, new Color());
    }

    @Test
    void testClampingConstructor() {
        // Teste que les valeurs sont limitées entre 0 et 1
        Color c = new Color(1.5, -0.5, 0.5);
        Color expected = new Color(1.0, 0.0, 0.5);
        assertEquals(expected, c);
    }

    @Test
    void testAdd() {
        Color c1 = new Color(0.1, 0.2, 0.8);
        Color c2 = new Color(0.5, 0.5, 0.3);
        // Doit aussi clamper le résultat si > 1
        Color expected = new Color(0.6, 0.7, 1.0);
        assertEquals(expected, c1.add(c2));
    }

    @Test
    void testMultiplyScalar() {
        Color c = new Color(0.2, 0.3, 0.4);
        Color expected = new Color(0.4, 0.6, 0.8);
        assertEquals(expected, c.multiply(2));

        // Teste le clamping
        Color bright = new Color(0.5, 0.8, 1.0);
        assertEquals(new Color(1.0, 1.0, 1.0), bright.multiply(3));
    }

    @Test
    void testSchurProduct() {
        Color c1 = new Color(0.5, 1.0, 0.2);
        Color c2 = new Color(0.5, 0.8, 1.0);
        Color expected = new Color(0.25, 0.8, 0.2);
        assertEquals(expected, c1.schur(c2));
    }

    @Test
    void testToRGB() { //
        // Test des couleurs primaires et du blanc/noir
        assertEquals(0x000000, new Color(0, 0, 0).toRGB()); // Noir
        assertEquals(0xFFFFFF, new Color(1, 1, 1).toRGB()); // Blanc
        assertEquals(0xFF0000, new Color(1, 0, 0).toRGB()); // Rouge
        assertEquals(0x00FF00, new Color(0, 1, 0).toRGB()); // Vert
        assertEquals(0x0000FF, new Color(0, 0, 1).toRGB()); // Bleu

        // Test de l'arrondi (0.5 * 255 = 127.5, arrondi à 128)
        Color grey = new Color(0.5, 0.5, 0.5);
        assertEquals(0x808080, grey.toRGB()); // 128, 128, 128
    }
}