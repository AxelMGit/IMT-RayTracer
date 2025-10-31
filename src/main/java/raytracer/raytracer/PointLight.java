package raytracer.raytracer;

import raytracer.geometry.Point;
import raytracer.imaging.Color;

/**
 * Représente une source de lumière ponctuelle.
 */
public class PointLight extends AbstractLight {

    private Point position;

    public PointLight(Point position, Color color) {
        super(color);
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }
}