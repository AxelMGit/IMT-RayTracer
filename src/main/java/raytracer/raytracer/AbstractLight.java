package raytracer.raytracer;

import raytracer.geometry.Point;
import raytracer.geometry.Vector; // Import
import raytracer.imaging.Color;

public abstract class AbstractLight {

    protected Color color;

    public AbstractLight(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    // Nouvelle méthode : vecteur vers la lumière depuis le point p
    public abstract Vector getL(Point p);
}