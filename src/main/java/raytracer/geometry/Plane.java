package raytracer.geometry;

import raytracer.imaging.Color;
import raytracer.raytracer.Intersection; // Import nécessaire
import raytracer.raytracer.Ray;          // Import nécessaire
import java.util.Optional;               // Import nécessaire

/**
 * Représente un objet Plan infini dans la scène.
 */
public class Plane extends Shape {

    private Point point;
    private Vector normal;

    public Plane(Point point, Vector normal, Color diffuse, Color specular) {
        super(diffuse, specular);
        this.point = point;
        this.normal = normal;
    }

    // --- Méthode obligatoire ajoutée ---
    @Override
    public Optional<Intersection> intersect(Ray ray) {
        // Pour l'instant, on ignore les plans (Jalon 3 = Sphères uniquement)
        return Optional.empty();
    }

    // --- Getters ---
    public Point getPoint() { return point; }
    public Vector getNormal() { return normal; }
}