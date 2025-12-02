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
        // Implémentation des formules du PDF (Page 3)

        // p1=a, p2=b, p3=c
        Vector edge1 = p2.subtract(p1); // (b - a)
        Vector edge2 = p3.subtract(p1); // (c - a)

        // p = d ^ (c - a)
        Vector pvec = ray.getDirection().cross(edge2);

        // det = (b - a) . p
        double det = edge1.dot(pvec);

        // Si det est proche de 0, rayon parallèle
        if (Math.abs(det) < 1e-9) return Optional.empty();

        double invDet = 1.0 / det;
        Vector tvec = ray.getOrigin().subtract(p1); // t = (lookFrom - a)

        // beta = (t . p) / det
        double u = tvec.dot(pvec) * invDet;
        if (u < 0.0 || u > 1.0) return Optional.empty(); //

        // q = t ^ (b - a)
        Vector qvec = tvec.cross(edge1);

        // gamma = (d . q) / det
        double v = ray.getDirection().dot(qvec) * invDet;

        // Si gamma < 0 ou beta + gamma > 1
        if (v < 0.0 || u + v > 1.0) return Optional.empty();

        // t = ((c - a) . q) / det
        double t = edge2.dot(qvec) * invDet;

        if (t > 1e-4) { //
            // Calcul de la normale (produit vectoriel des arêtes)
            Vector normal = edge1.cross(edge2).normalize();

            // Correction de la normale si elle est opposée au rayon (pour voir le triangle des deux côtés)
            if (normal.dot(ray.getDirection()) > 0) normal = normal.multiply(-1);

            return Optional.of(new Intersection(t, this, normal, this.diffuse, this.specular, this.shininess));
        }

        return Optional.empty();
    }

    public Point getP1() { return p1; }
    public Point getP2() { return p2; }
    public Point getP3() { return p3; }
}