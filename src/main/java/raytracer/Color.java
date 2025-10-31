package raytracer;

/**
 * Représente une couleur (R, G, B) avec des composantes flottantes.
 * Les composantes sont stockées sous forme de double (0-1).
 */
public class Color extends AbstractVec3 {

    /**
     * Constructeur par défaut (Noir).
     */
    public Color() {
        super(0, 0, 0);
    }

    /**
     * Constructeur à partir des composantes R, G, B.
     * Les valeurs sont "clampées" (limitées) entre 0 et 1.
     */
    public Color(double r, double g, double b) {
        super(clamp(r), clamp(g), clamp(b));
    }

    /**
     * Limite une valeur entre 0.0 et 1.0.
     * @param val La valeur à limiter.
     * @return La valeur limitée.
     */
    private static double clamp(double val) {
        return Math.max(0, Math.min(1, val));
    }

    // --- Getters sémantiques ---
    public double getR() { return this.x; } 
    public double getG() { return this.y; } 
    public double getB() { return this.z; } 

    // --- Opérations sur les couleurs

    /**
     * Addition de deux couleurs.
     */
    public Color add(Color c) {
        return new Color(this.x + c.x, this.y + c.y, this.z + c.z);
    }

    /**
     * Multiplication par un scalaire (augmente/diminue la luminosité).
     */
    public Color multiply(double d) {
        return new Color(this.x * d, this.y * d, this.z * d);
    }

    /**
     * Produit de Schur (mélange de couleurs, ex: filtres).
     */
    public Color schur(Color c) {
        return new Color(this.x * c.x, this.y * c.y, this.z * c.z);
    }

    /**
     * Convertit la couleur (flottante 0-1) en entier RGB (0-255).
     * [cite: 66, 67, 68, 69, 70, 71, 72, 73, 74]
     * C'est ce format que ImageIO utilisera pour écrire les pixels.
     * @return Un entier représentant la couleur ARGB (Alpha est à 255).
     */
    public int toRGB() {
        // Note: j'ai corrigé les typos du PDF
        int red   = (int) Math.round(this.x * 255); // 
        int green = (int) Math.round(this.y * 255); // 
        int blue  = (int) Math.round(this.z * 255); // 

        // On combine les 3 composantes en un seul entier
        // (Alpha est implicitement 255, opaque)
        return ((red   & 0xff) << 16) |  // 
                ((green & 0xff) << 8)  |  // 
                ((blue  & 0xff));         // 
    }
}