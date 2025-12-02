package raytracer.raytracer;

import raytracer.geometry.Shape;
import raytracer.imaging.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Scene {
    // ... (attributs existants: width, height, camera, etc.) ...
    private int width;
    private int height;
    private Camera camera;
    private String output = "output.png";
    private Color ambient = new Color();
    private List<AbstractLight> lights = new ArrayList<>();
    private List<Shape> shapes = new ArrayList<>();

    // ... (Getters et Setters existants) ...
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
    public void addLight(AbstractLight light) { this.lights.add(light); }
    public void addShape(Shape shape) { this.shapes.add(shape); }

    /**
     * Trouve l'intersection la plus proche pour un rayon donné.
     * [cite: 172, 175, 179]
     */
    public Optional<Intersection> findClosestIntersection(Ray ray) {
        Optional<Intersection> closest = Optional.empty();

        for (Shape shape : shapes) {
            Optional<Intersection> intersection = shape.intersect(ray);

            if (intersection.isPresent()) {
                // Si on n'a pas encore de closest, ou si la nouvelle est plus proche
                if (closest.isEmpty() || intersection.get().getDistance() < closest.get().getDistance()) {
                    closest = intersection;
                }
            }
        }
        return closest;
    }
}