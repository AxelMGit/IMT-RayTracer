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
        // Formule du PDF : t = ((q - o) . n) / (d . n)
        double denom = ray.getDirection().dot(this.normal);

        // Si denom est nul (ou très proche), le rayon est parallèle au plan
        //
        if (Math.abs(denom) > 1e-6) {
            Vector rayToPlane = this.point.subtract(ray.getOrigin());
            double t = rayToPlane.dot(this.normal) / denom;

            // L'intersection doit être devant la caméra (t > epsilon)
            if (t > 1e-4) {
                return Optional.of(new Intersection(t, this, this.normal, this.diffuse, this.specular, this.shininess));
            }
        }
        return Optional.empty();
    }

    public Point getPoint() { return point; }
    public Vector getNormal() { return normal; }
}