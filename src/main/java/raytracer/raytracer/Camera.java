package raytracer.raytracer;

import raytracer.geometry.Point;
import raytracer.geometry.Vector;

/**
 * Stocke les informations de la caméra (point de vue, cible, etc.).
 */
public class Camera {

    private Point lookFrom;
    private Point lookAt;
    private Vector up;
    private double fov; // en degrés

    public Camera(Point lookFrom, Point lookAt, Vector up, double fov) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
    }

    // --- Getters ---
    public Point getLookFrom() { return lookFrom; }
    public Point getLookAt() { return lookAt; }
    public Vector getUp() { return up; }
    public double getFov() { return fov; }
}