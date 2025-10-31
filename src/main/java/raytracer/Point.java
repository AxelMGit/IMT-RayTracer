package raytracer;

/**
 * Représente un point en 3D.
 * Hérite d'AbstractVec3 et implémente les opérations spécifiques aux points.
 */
public class Point extends AbstractVec3 {

    /**
     * Constructeur par défaut (0, 0, 0).
     */
    public Point() {
        super();
    }

    /**
     * Constructeur à partir de 3 composantes.
     */
    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Addition d'un point et d'un vecteur.
     * @param v Le vecteur à ajouter (déplacement).
     * @return Le nouveau point résultant.
     */
    public Point add(Vector v) {
        return new Point(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    /**
     * Soustraction de deux points.
     * @param p Le point à soustraire.
     * @return Le vecteur allant de p à this.
     */
    public Vector subtract(Point p) {
        return new Vector(this.x - p.x, this.y - p.y, this.z - p.z);
    }

    /**
     * Multiplication par un scalaire (mise à l'échelle par rapport à l'origine).
     * @param d Le scalaire.
     * @return Un nouveau point résultat.
     */
    public Point multiply(double d) {
        return new Point(this.x * d, this.y * d, this.z * d);
    }
}