package raytracer.geometry;

import raytracer.imaging.Color;

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

    // --- Getters ---
    public Point getPoint() {
        return point;
    }

    public Vector getNormal() {
        return normal;
    }
}