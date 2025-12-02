package raytracer.raytracer;

import raytracer.geometry.Point;
import raytracer.geometry.Vector;
import raytracer.imaging.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Optional;

public class RayTracer {

    private final Scene scene;

    public RayTracer(Scene scene) {
        this.scene = scene;
    }

    public void render() {
        int width = scene.getWidth();
        int height = scene.getHeight();
        Camera cam = scene.getCamera();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // --- 1. Calcul du repère caméra (inchangé) ---
        Vector w = cam.getLookFrom().subtract(cam.getLookAt()).normalize();
        Vector u = cam.getUp().cross(w).normalize();
        Vector v = w.cross(u).normalize();

        double fovRadians = Math.toRadians(cam.getFov());
        double halfHeight = Math.tan(fovRadians / 2);
        double halfWidth = halfHeight * ((double) width / height);

        // --- 2. Boucle de rendu ---
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                // Calcul de la direction du rayon primaire
                double a = (halfWidth * (i - (width / 2.0) + 0.5)) / (width / 2.0);
                double b = (halfHeight * (j - (height / 2.0) + 0.5)) / (height / 2.0);
                Vector d = u.multiply(a).add(v.multiply(b)).subtract(w).normalize();
                Ray ray = new Ray(cam.getLookFrom(), d);

                // Recherche de l'intersection la plus proche
                Optional<Intersection> closest = scene.findClosestIntersection(ray);
                Color pixelColor;

                if (closest.isPresent()) {
                    Intersection inter = closest.get();
                    // Point d'intersection P
                    Point p = ray.getOrigin().add(ray.getDirection().multiply(inter.getDistance()));

                    // --- Base : Lumière Ambiante (toujours présente) ---
                    pixelColor = scene.getAmbient().schur(inter.getDiffuse());

                    // Vecteur vue (nécessaire pour Phong) : de P vers l'œil (inverse du rayon)
                    Vector viewDir = ray.getDirection().multiply(-1).normalize();

                    // --- Boucle sur les lumières ---
                    for (AbstractLight light : scene.getLights()) {
                        Vector l = light.getL(p); // Direction vers la lumière

                        // === GESTION DES OMBRES (Shadow Rays) ===
                        // On décale légèrement le point d'origine pour éviter l'auto-intersection (acné)
                        Point shadowOrigin = p.add(l.multiply(1e-4));
                        Ray shadowRay = new Ray(shadowOrigin, l);

                        // On cherche si un objet coupe le chemin vers la lumière
                        Optional<Intersection> shadowInter = scene.findClosestIntersection(shadowRay);

                        boolean inShadow = false;
                        if (shadowInter.isPresent()) {
                            // Distance vers l'obstacle
                            double distToObstacle = shadowInter.get().getDistance();
                            double distToLight = Double.MAX_VALUE;

                            // Si c'est une lumière ponctuelle, on vérifie la distance
                            if (light instanceof PointLight) {
                                Vector toLight = ((PointLight) light).getPosition().subtract(p);
                                distToLight = toLight.length();
                            }

                            // Si l'obstacle est plus proche que la lumière, on est à l'ombre
                            if (distToObstacle < distToLight) {
                                inShadow = true;
                            }
                        }

                        // Si le point n'est pas à l'ombre, on ajoute l'éclairage (Lambert + Phong)
                        if (!inShadow) {
                            // Note: Assure-toi que Intersection.calculateColor prend bien viewDir maintenant !
                            Color lightContrib = inter.calculateColor(light, p, viewDir);
                            pixelColor = pixelColor.add(lightContrib);
                        }
                    }
                } else {
                    pixelColor = new Color(0, 0, 0);
                }

                // Inversion de l'axe Y pour l'image
                image.setRGB(i, height - 1 - j, pixelColor.toRGB());
            }
        }

        // Sauvegarde
        try {
            File outputFile = new File(scene.getOutput());
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image générée : " + scene.getOutput());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}