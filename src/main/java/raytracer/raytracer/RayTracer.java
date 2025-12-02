package raytracer.raytracer;

import raytracer.geometry.Point; // Import
import raytracer.geometry.Vector;
import raytracer.imaging.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Optional;

public class RayTracer {
    // ... (Constructeur inchangé) ...
    private final Scene scene;
    public RayTracer(Scene scene) { this.scene = scene; }

    public void render() {
        // ... (Début inchangé : setup u, v, w, dimensions) ...
        int width = scene.getWidth();
        int height = scene.getHeight();
        Camera cam = scene.getCamera();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Vector w = cam.getLookFrom().subtract(cam.getLookAt()).normalize();
        Vector u = cam.getUp().cross(w).normalize();
        Vector v = w.cross(u).normalize();

        double fovRadians = Math.toRadians(cam.getFov());
        double halfHeight = Math.tan(fovRadians / 2);
        double halfWidth = halfHeight * ((double) width / height);

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                // ... (Calcul du rayon inchangé) ...
                double a = (halfWidth * (i - (width / 2.0) + 0.5)) / (width / 2.0);
                double b = (halfHeight * (j - (height / 2.0) + 0.5)) / (height / 2.0);
                Vector d = u.multiply(a).add(v.multiply(b)).subtract(w).normalize();
                Ray ray = new Ray(cam.getLookFrom(), d);

                // Recherche de l'intersection
                Optional<Intersection> closest = scene.findClosestIntersection(ray);
                Color pixelColor;

                if (closest.isPresent()) {
                    Intersection inter = closest.get();

                    // 1. Calcul de la position exacte du point P
                    Point p = ray.getOrigin().add(ray.getDirection().multiply(inter.getDistance()));

                    // 2. Base : Lumière Ambiante * Couleur Objet [cite: 341]
                    pixelColor = scene.getAmbient().schur(inter.getDiffuse());

                    // 3. Ajout des lumières (Lambert) [cite: 339, 350]
                    for (AbstractLight light : scene.getLights()) {
                        Color diffuseContrib = inter.calculateColor(light, p);
                        pixelColor = pixelColor.add(diffuseContrib);
                    }
                } else {
                    pixelColor = new Color(0, 0, 0);
                }

                image.setRGB(i, height - 1 - j, pixelColor.toRGB());
            }
        }
        // ... (Sauvegarde inchangée) ...
        try {
            File outputFile = new File(scene.getOutput());
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image générée : " + scene.getOutput());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}