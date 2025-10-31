package raytracer.raytracer;

import raytracer.imaging.Color;

/**
 * Classe abstraite de base pour toutes les sources de lumière.
 */
public abstract class AbstractLight {

    protected Color color;

    public AbstractLight(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}