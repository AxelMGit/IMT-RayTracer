package raytracer.raytracer;

import raytracer.geometry.Shape;
import raytracer.imaging.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Stocke tous les éléments de la scène 3D.
 */
public class Scene {

    // Informations du fichier [cite: 253-260]
    private int width;
    private int height;
    private Camera camera;
    private String output = "output.png"; // Valeur par défaut 
    private Color ambient = new Color();  // Valeur par défaut (noir)
    private List<AbstractLight> lights = new ArrayList<>();
    private List<Shape> shapes = new ArrayList<>();

    // --- Getters et Setters ---
    // (Nous aurons besoin des "setters" pour le parseur)

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

    // Méthodes pratiques pour ajouter des éléments
    public void addLight(AbstractLight light) {
        this.lights.add(light);
    }

    public void addShape(Shape shape) {
        this.shapes.add(shape);
    }
}