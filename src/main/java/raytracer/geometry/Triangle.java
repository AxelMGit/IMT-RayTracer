package raytracer.geometry;

import raytracer.imaging.Color;

/**
 * Représente un objet Triangle dans la scène.
 */
public class Triangle extends Shape {

    private Point p1;
    private Point p2;
    private Point p3;

    public Triangle(Point p1, Point p2, Point p3, Color diffuse, Color specular) {
        super(diffuse, specular);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    // --- Getters ---
    public Point getP1() { return p1; }
    public Point getP2() { return p2; }
    public Point getP3() { return p3; }
}