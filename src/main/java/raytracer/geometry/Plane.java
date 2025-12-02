package raytracer.geometry;

import raytracer.imaging.Color;
import raytracer.raytracer.Intersection;
import raytracer.raytracer.Ray;
import java.util.Optional;

/**
 * Représente un objet Plan infini dans la scène.
 */
public class Plane extends Shape {

    private Point point;
    private Vector normal;

    // Mise à jour du constructeur : ajout de shininess
    public Plane(Point point, Vector normal, Color diffuse, Color specular, double shininess) {
        super(diffuse, specular, shininess); // On passe shininess au parent
        this.point = point;
        this.normal = normal;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        // Pour l'instant, on ignore toujours les plans (Bonus / Jalon 6)
        return Optional.empty();
    }

    public Point getPoint() { return point; }
    public Vector getNormal() { return normal; }
}