package raytracer.geometry;

import raytracer.imaging.Color;
import raytracer.raytracer.Intersection;
import raytracer.raytracer.Ray;
import java.util.Optional;

/**
 * Représente un objet Triangle dans la scène.
 */
public class Triangle extends Shape {

    private Point p1;
    private Point p2;
    private Point p3;

    // Mise à jour du constructeur : ajout de shininess
    public Triangle(Point p1, Point p2, Point p3, Color diffuse, Color specular, double shininess) {
        super(diffuse, specular, shininess); // On passe shininess au parent
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        // Pour l'instant, on ignore toujours les triangles (Bonus / Jalon 6)
        return Optional.empty();
    }

    public Point getP1() { return p1; }
    public Point getP2() { return p2; }
    public Point getP3() { return p3; }
}