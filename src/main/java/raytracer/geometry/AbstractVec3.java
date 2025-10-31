package raytracer.geometry;

/**
 * Classe abstraite de base pour les éléments à 3 composantes (double).
 * [cite: 47, 49]
 * Gère le stockage des composantes et la logique de comparaison (equals).
 */
public abstract class AbstractVec3 {

    // Epsilon pour la comparaison des doubles [cite: 58, 59]
    public static final double EPSILON = 1e-9;

    protected double x, y, z;

    /**
     * Constructeur par défaut (0, 0, 0).
     */
    public AbstractVec3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Constructeur à partir de 3 composantes.
     * @param x composante x
     * @param y composante y
     * @param z composante z
     */
    public AbstractVec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // --- Getters ---
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    /**
     * Compare deux valeurs double avec une tolérance (EPSILON).
     * @param a première valeur
     * @param b seconde valeur
     * @return true si les valeurs sont considérées comme égales
     */
    public static boolean areEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    /**
     * Méthode Equals pour comparer deux objets Vec3.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof AbstractVec3)) return false;

        AbstractVec3 other = (AbstractVec3) obj;

        // Comparaison composante par composante avec tolérance
        return areEqual(this.x, other.x) &&
                areEqual(this.y, other.y) &&
                areEqual(this.z, other.z);
    }

    /**
     * Représentation textuelle (utile pour le débogage).
     */
    @Override
    public String toString() {
        return String.format("(%f, %f, %f)", x, y, z);
    }
}