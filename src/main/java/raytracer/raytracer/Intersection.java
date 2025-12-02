package raytracer.raytracer;

import raytracer.geometry.Point;  // Import
import raytracer.geometry.Shape;
import raytracer.geometry.Vector; // Import
import raytracer.imaging.Color;   // Import

public class Intersection {
    private final double distance;
    private final Shape shape;
    private final Vector normal;   // [cite: 363]
    private final Color diffuse;   // [cite: 363]

    public Intersection(double distance, Shape shape, Vector normal, Color diffuse) {
        this.distance = distance;
        this.shape = shape;
        this.normal = normal;
        this.diffuse = diffuse;
    }

    public double getDistance() { return distance; }
    public Shape getShape() { return shape; }
    public Vector getNormal() { return normal; }
    public Color getDiffuse() { return diffuse; }

    /**
     * Calcule la couleur diffuse (Lambert) pour une lumière donnée.
     * Formule : ld = max(n . l, 0) * lightColor * diffuseColor
     * [cite: 351, 364]
     */
    public Color calculateColor(AbstractLight light, Point p) {
        Vector l = light.getL(p); // Direction vers la lumière

        // Produit scalaire (cosinus de l'angle)
        double nDotL = normal.dot(l);

        // On garde uniquement les valeurs positives (lumière face à la surface)
        double intensity = Math.max(nDotL, 0.0);

        // lightColor * intensity * materialDiffuse
        return light.getColor().multiply(intensity).schur(this.diffuse);
    }
}