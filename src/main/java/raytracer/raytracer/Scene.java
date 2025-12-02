package raytracer.raytracer;

import raytracer.geometry.Shape;
import raytracer.imaging.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Scene {

    // --- Attributs de la scène ---
    private int width;
    private int height;
    private Camera camera;
    private String output = "output.png"; // Valeur par défaut
    private Color ambient = new Color();  // Valeur par défaut (noir)
    private List<AbstractLight> lights = new ArrayList<>();
    private List<Shape> shapes = new ArrayList<>();

    // NOUVEAU (Jalon 6) : Profondeur de récursion pour les reflets
    private int maxDepth = 1; // Valeur par défaut (1 = pas de réflexion)

    // --- Méthodes ---

    /**
     * Trouve l'intersection la plus proche pour un rayon donné.
     * Parcourt tous les objets de la scène.
     */
    public Optional<Intersection> findClosestIntersection(Ray ray) {
        Optional<Intersection> closest = Optional.empty();

        for (Shape shape : shapes) {
            Optional<Intersection> intersection = shape.intersect(ray);

            if (intersection.isPresent()) {
                // Si on n'a pas encore de closest, ou si la nouvelle est plus proche (distance plus petite)
                if (closest.isEmpty() || intersection.get().getDistance() < closest.get().getDistance()) {
                    closest = intersection;
                }
            }
        }
        return closest;
    }

    // --- Getters et Setters ---

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public Camera getCamera() { return camera; }
    public void setCamera(Camera camera) { this.camera = camera; }

    public String getOutput() { return output; }
    public void setOutput(String output) { this.output = output; }

    public Color getAmbient() { return ambient; }
    public void setAmbient(Color ambient) { this.ambient = ambient; }

    public List<AbstractLight> getLights() { return lights; }
    public List<Shape> getShapes() { return shapes; }

    // Méthodes utilitaires pour l'ajout
    public void addLight(AbstractLight light) { this.lights.add(light); }
    public void addShape(Shape shape) { this.shapes.add(shape); }

    public int getMaxDepth() { return maxDepth; }
    public void setMaxDepth(int maxDepth) { this.maxDepth = maxDepth; }
}