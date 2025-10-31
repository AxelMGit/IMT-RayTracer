package raytracer.raytracer;

import raytracer.geometry.Vector;
import raytracer.imaging.Color;

/**
 * Représente une source de lumière directionnelle.
 */
public class DirectionalLight extends AbstractLight {

    private Vector direction;

    public DirectionalLight(Vector direction, Color color) {
        super(color);
        this.direction = direction;
    }

    public Vector getDirection() {
        return direction;
    }
}