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
        // t = (PointPlan - OrigineRayon) . Normale / (DirectionRayon . Normale)

        double denom = ray.getDirection().dot(this.normal);

        // On vérifie que le rayon n'est pas parallèle au plan (denom != 0)
        if (Math.abs(denom) > 1e-6) {

            Vector rayToPlane = this.point.subtract(ray.getOrigin());
            double t = rayToPlane.dot(this.normal) / denom;

            // L'intersection doit être devant la caméra (t > epsilon)
            if (t > 1e-4) {
                // Astuce : Pour que le plan soit visible des deux côtés et éclairé correctement,
                // on oriente la normale face au rayon arrivant.
                Vector effectiveNormal = this.normal;
                if (denom > 0) {
                    effectiveNormal = effectiveNormal.multiply(-1);
                }

                return Optional.of(new Intersection(t, this, effectiveNormal, this.diffuse, this.specular, this.shininess));
            }
        }
        return Optional.empty();
    }

    // Getters
    public Point getPoint() { return point; }
    public Vector getNormal() { return normal; }
}