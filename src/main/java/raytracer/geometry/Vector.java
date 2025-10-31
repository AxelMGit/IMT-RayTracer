package raytracer.geometry;

/**
 * Représente un vecteur en 3D (direction ou normale).
 * Hérite d'AbstractVec3 et implémente les opérations vectorielles.
 */
public class Vector extends AbstractVec3 {

    /**
     * Constructeur par défaut (0, 0, 0).
     */
    public Vector() {
        super();
    }

    /**
     * Constructeur à partir de 3 composantes.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Addition de deux vecteurs.
     * @param v Le vecteur à additionner.
     * @return Un nouveau vecteur résultat.
     */
    public Vector add(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    /**
     * Soustraction de deux vecteurs.
     * @param v Le vecteur à soustraire.
     * @return Un nouveau vecteur résultat.
     */
    public Vector subtract(Vector v) {
        return new Vector(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    /**
     * Multiplication par un scalaire.
     * @param d Le scalaire.
     * @return Un nouveau vecteur résultat.
     */
    public Vector multiply(double d) {
        return new Vector(this.x * d, this.y * d, this.z * d);
    }

    /**
     * Produit scalaire (dot product).
     * @param v L'autre vecteur.
     * @return Le résultat (un double).
     */
    public double dot(Vector v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    /**
     * Produit vectoriel (cross product).
     * @param v L'autre vecteur.
     * @return Le nouveau vecteur perpendiculaire.
     */
    public Vector cross(Vector v) {
        return new Vector(
                this.y * v.z - this.z * v.y,
                this.z * v.x - this.x * v.z,
                this.x * v.y - this.y * v.x
        );
    }

    /**
     * Produit de Schur (multiplication composante par composante).
     * @param v L'autre vecteur.
     * @return Un nouveau vecteur résultat.
     */
    public Vector schur(Vector v) {
        return new Vector(this.x * v.x, this.y * v.y, this.z * v.z);
    }

    /**
     * Calcule la longueur (magnitude) du vecteur.
     * @return La longueur (un double).
     */
    public double length() {
        // La longueur est la racine carrée du produit scalaire avec soi-même
        return Math.sqrt(this.dot(this));
    }

    /**
     * Normalise le vecteur (le ramène à une longueur de 1).
     * @return Un nouveau vecteur normalisé.
     */
    public Vector normalize() {
        double len = this.length();
        // Sécurité pour éviter la division par zéro
        if (areEqual(len, 0)) {
            return new Vector(0, 0, 0);
        }
        return this.multiply(1.0 / len);
    }
}