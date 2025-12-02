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

        // Calcul des coefficients [cite: 148, 149]
        // a = d . d
        double a = d.dot(d);
        // b = 2 * (o - c) . d
        Vector oc = o.subtract(c); // (o - c)
        double b = 2 * oc.dot(d);
        // c = (o - c) . (o - c) - r^2
        double cVal = oc.dot(oc) - (radius * radius);

        // Discriminant [cite: 150]
        double delta = b * b - 4 * a * cVal;

        if (delta < 0) {
            return Optional.empty(); // Pas d'intersection [cite: 151]
        }

        double t;
        if (delta == 0) {
            t = -b / (2 * a); // Une seule intersection [cite: 152]
        } else {
            // Deux intersections, on cherche la plus proche positive [cite: 153-157]
            double t1 = (-b + Math.sqrt(delta)) / (2 * a);
            double t2 = (-b - Math.sqrt(delta)) / (2 * a);

            // t2 est toujours plus petit que t1, on teste t2 d'abord
            if (t2 > 0) {
                t = t2;
            } else if (t1 > 0) {
                t = t1;
            } else {
                return Optional.empty(); // Sphère derrière la caméra
            }
        }

        return Optional.of(new Intersection(t, this));
    }

    // Getters existants...
    public Point getCenter() { return center; }
    public double getRadius() { return radius; }
}