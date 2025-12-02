package raytracer.geometry;

import raytracer.imaging.Color;
import raytracer.raytracer.Intersection;
import raytracer.raytracer.Ray;
import java.util.Optional;

public class Plane extends Shape {

    private Point point;
    private Vector normal;

    public Plane(Point point, Vector normal, Color diffuse, Color specular, double shininess) {
        super(diffuse, specular, shininess);
        this.point = point;
        this.normal = normal;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        // Formule d'intersection Rayon-Plan
        double denom = ray.getDirection().dot(this.normal);

        // On vérifie que le rayon n'est pas parallèle au plan
        if (Math.abs(denom) > 1e-6) {
            Vector rayToPlane = this.point.subtract(ray.getOrigin());
            double t = rayToPlane.dot(this.normal) / denom;

            // L'intersection doit être devant la caméra
            if (t > 1e-4) {
                // Astuce : On oriente la normale face au rayon pour voir le plan des deux côtés
                Vector effectiveNormal = this.normal;
                if (denom > 0) {
                    effectiveNormal = effectiveNormal.multiply(-1);
                }
                return Optional.of(new Intersection(t, this, effectiveNormal, this.diffuse, this.specular, this.shininess));
            }
        }
        return Optional.empty();
    }

    public Point getPoint() { return point; }
    public Vector getNormal() { return normal; }
}