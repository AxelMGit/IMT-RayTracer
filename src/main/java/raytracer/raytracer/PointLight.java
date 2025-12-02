package raytracer.raytracer;

import raytracer.geometry.Point;
import raytracer.geometry.Vector; // Import
import raytracer.imaging.Color;

public class PointLight extends AbstractLight {

    private Point position;

    public PointLight(Point position, Color color) {
        super(color);
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    @Override
    public Vector getL(Point p) {
        // Vecteur du point p VERS la lumière (position - p)
        return position.subtract(p).normalize();
    }
}