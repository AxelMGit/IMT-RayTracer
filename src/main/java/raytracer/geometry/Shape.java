package raytracer.geometry;

import raytracer.imaging.Color;
import raytracer.raytracer.Intersection;
import raytracer.raytracer.Ray;
import java.util.Optional;

public abstract class Shape {
    protected Color diffuse;
    protected Color specular;
    protected double shininess;

    public Shape(Color diffuse, Color specular, double shininess) {
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    public abstract Optional<Intersection> intersect(Ray ray);

    public Color getDiffuse() { return diffuse; }
    public Color getSpecular() { return specular; }
    public double getShininess() { return shininess; }
}