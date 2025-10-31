package raytracer.geometry;

import raytracer.imaging.Color;

/**
 * Classe abstraite de base pour toutes les formes géométriques de la scène.
 * Stocke les propriétés de matériaux communes (couleurs).
 */
public abstract class Shape {

    protected Color diffuse;
    protected Color specular;

    public Shape(Color diffuse, Color specular) {
        this.diffuse = diffuse;
        this.specular = specular;
    }

    // --- Getters ---
    public Color getDiffuse() {
        return diffuse;
    }

    public Color getSpecular() {
        return specular;
    }
}