package raytracer.geometry;

import raytracer.imaging.Color;
import raytracer.raytracer.Intersection;
import raytracer.raytracer.Ray;
import java.util.Optional;

public class Sphere extends Shape {

    private Point center;
    private double radius;

    public Sphere(Point center, double radius, Color diffuse, Color specular) {
        super(diffuse, specular);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        Point o = ray.getOrigin();
        Vector d = ray.getDirection();
        Point c = this.center;

        // --- Résolution quadratique (inchangée) ---
        double a = d.dot(d);
        Vector oc = o.subtract(c);
        double b = 2 * oc.dot(d);
        double cVal = oc.dot(oc) - (radius * radius);

        double delta = b * b - 4 * a * cVal;

        if (delta < 0) return Optional.empty();

        double t;
        if (delta == 0) {
            t = -b / (2 * a);
        } else {
            double t1 = (-b + Math.sqrt(delta)) / (2 * a);
            double t2 = (-b - Math.sqrt(delta)) / (2 * a);
            if (t2 > 0) t = t2;
            else if (t1 > 0) t = t1;
            else return Optional.empty();
        }

        // --- NOUVEAU : Calcul de la normale ---
        // Point d'intersection P = O + t*d
        Point p = o.add(d.multiply(t));

        // Normale n = (P - Centre) normalisé
        Vector normal = p.subtract(center).normalize();

        // On retourne l'intersection avec la normale et la couleur diffuse
        return Optional.of(new Intersection(t, this, normal, this.diffuse));
    }

    public Point getCenter() { return center; }
    public double getRadius() { return radius; }
}