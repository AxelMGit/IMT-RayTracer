package raytracer.geometry;

import raytracer.imaging.Color;

/**
 * Représente un objet Sphère dans la scène.
 */
public class Sphere extends Shape {

    private Point center;
    private double radius;

    public Sphere(Point center, double radius, Color diffuse, Color specular) {
        super(diffuse, specular); // Appelle le constructeur de Shape
        this.center = center;
        this.radius = radius;
    }

    // --- Getters ---
    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }
}