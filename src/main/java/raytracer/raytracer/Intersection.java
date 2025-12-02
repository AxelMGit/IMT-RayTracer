package raytracer.raytracer;

import raytracer.geometry.Shape;

public class Intersection {
    private final double distance;
    private final Shape shape;

    public Intersection(double distance, Shape shape) {
        this.distance = distance;
        this.shape = shape;
    }

    public double getDistance() { return distance; }
    public Shape getShape() { return shape; }
}