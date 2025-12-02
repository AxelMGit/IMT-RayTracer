package raytracer.raytracer;

import raytracer.geometry.Point;
import raytracer.geometry.Vector;

public class Ray {
    private final Point origin;
    private final Vector direction;

    public Ray(Point origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Point getOrigin() { return origin; }
    public Vector getDirection() { return direction; }
}