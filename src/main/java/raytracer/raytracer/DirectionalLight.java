package raytracer.raytracer;

import raytracer.geometry.Point; // Import
import raytracer.geometry.Vector;
import raytracer.imaging.Color;

public class DirectionalLight extends AbstractLight {

    private Vector direction;

    public DirectionalLight(Vector direction, Color color) {
        super(color);
        this.direction = direction;
    }

    public Vector getDirection() {
        return direction;
    }

    @Override
    public Vector getL(Point p) {
        // Pour une lumière directionnelle, le vecteur L est constant
        // On le normalise par sécurité
        return direction.normalize();
    }
}