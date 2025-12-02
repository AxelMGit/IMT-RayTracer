package raytracer.geometry;

import raytracer.imaging.Color;
import raytracer.raytracer.Intersection;
import raytracer.raytracer.Ray;
import java.util.Optional;

public class Triangle extends Shape {

    private Point p1, p2, p3;

    public Triangle(Point p1, Point p2, Point p3, Color diffuse, Color specular, double shininess) {
        super(diffuse, specular, shininess);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        // Algorithme de Möller–Trumbore
        Vector edge1 = p2.subtract(p1);
        Vector edge2 = p3.subtract(p1);
        Vector h = ray.getDirection().cross(edge2);
        double a = edge1.dot(h);

        if (a > -1e-9 && a < 1e-9) return Optional.empty();

        double f = 1.0 / a;
        Vector s = ray.getOrigin().subtract(p1);
        double u = f * s.dot(h);

        if (u < 0.0 || u > 1.0) return Optional.empty();

        Vector q = s.cross(edge1);
        double v = f * ray.getDirection().dot(q);

        if (v < 0.0 || u + v > 1.0) return Optional.empty();

        double t = f * edge2.dot(q);

        if (t > 1e-4) {
            Vector normal = edge1.cross(edge2).normalize();
            if (normal.dot(ray.getDirection()) > 0) normal = normal.multiply(-1);
            return Optional.of(new Intersection(t, this, normal, this.diffuse, this.specular, this.shininess));
        }
        return Optional.empty();
    }

    public Point getP1() { return p1; }
    public Point getP2() { return p2; }
    public Point getP3() { return p3; }
}