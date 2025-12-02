package raytracer.geometry;

import raytracer.imaging.Color;
import raytracer.raytracer.Intersection;
import raytracer.raytracer.Ray;
import java.util.Optional; // [cite: 203]

public abstract class Shape {
    protected Color diffuse;
    protected Color specular;

    public Shape(Color diffuse, Color specular) {
        this.diffuse = diffuse;
        this.specular = specular;
    }

    // Nouvelle méthode abstraite
    public abstract Optional<Intersection> intersect(Ray ray);

    public Color getDiffuse() { return diffuse; }
    public Color getSpecular() { return specular; }
}