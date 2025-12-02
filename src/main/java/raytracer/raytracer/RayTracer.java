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

        // --- 1. Repère Caméra ---
        Vector w = cam.getLookFrom().subtract(cam.getLookAt()).normalize();
        Vector u = cam.getUp().cross(w).normalize();
        Vector v = w.cross(u).normalize();

        double fovRadians = Math.toRadians(cam.getFov());
        double halfHeight = Math.tan(fovRadians / 2);
        double halfWidth = halfHeight * ((double) width / height);

        // --- 2. Boucle Pixels ---
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                double a = (halfWidth * (i - (width / 2.0) + 0.5)) / (width / 2.0);
                double b = (halfHeight * (j - (height / 2.0) + 0.5)) / (height / 2.0);
                Vector d = u.multiply(a).add(v.multiply(b)).subtract(w).normalize();

                Ray ray = new Ray(cam.getLookFrom(), d);

                // Appel récursif avec la profondeur max définie dans la scène
                //
                Color pixelColor = computeColor(ray, scene.getMaxDepth());

                image.setRGB(i, height - 1 - j, pixelColor.toRGB());
            }
        }

        try {
            File outputFile = new File(scene.getOutput());
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image générée : " + scene.getOutput());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode récursive pour calculer la couleur d'un rayon (Direct + Réfléchi).
     */
    private Color computeColor(Ray ray, int depth) {
        // Arrêt de la récursion
        if (depth <= 0) {
            return new Color(0, 0, 0); //
        }

        Optional<Intersection> closest = scene.findClosestIntersection(ray);

        if (closest.isPresent()) {
            Intersection inter = closest.get();
            Point p = ray.getOrigin().add(ray.getDirection().multiply(inter.getDistance()));
            Vector viewDir = ray.getDirection().multiply(-1).normalize();

            // --- A. Couleur Directe (Locale) ---
            Color finalColor = scene.getAmbient().schur(inter.getDiffuse());

            for (AbstractLight light : scene.getLights()) {
                Vector l = light.getL(p);
                // Ombres portées (Shadow Rays)
                Point shadowOrigin = p.add(l.multiply(1e-4));
                Ray shadowRay = new Ray(shadowOrigin, l);
                Optional<Intersection> shadowInter = scene.findClosestIntersection(shadowRay);

                boolean inShadow = false;
                if (shadowInter.isPresent()) {
                    double distToObstacle = shadowInter.get().getDistance();
                    double distToLight = Double.MAX_VALUE;
                    if (light instanceof PointLight) {
                        Vector toLight = ((PointLight) light).getPosition().subtract(p);
                        distToLight = toLight.length();
                    }
                    if (distToObstacle < distToLight) {
                        inShadow = true;
                    }
                }

                if (!inShadow) {
                    finalColor = finalColor.add(inter.calculateColor(light, p, viewDir));
                }
            }

            // --- B. Couleur Réfléchie (Bonus Miroir) ---
            //
            Color specularMaterial = inter.getSpecular();
            // On ne lance un rayon réfléchi que si l'objet est brillant (specular > 0)
            if (specularMaterial.getR() > 0 || specularMaterial.getG() > 0 || specularMaterial.getB() > 0) {

                Vector n = inter.getNormal();
                Vector d = ray.getDirection();

                // Formule de réflexion : r = d + 2 * (n . (-d)) * n
                //
                // Note: d.dot(n) est négatif car ils sont opposés, donc on utilise -d.dot(n) pour avoir une valeur positive
                // Ou plus simplement : r = d - 2*(d.n)*n
                double dotDN = d.dot(n);
                Vector r = d.subtract(n.multiply(2 * dotDN)).normalize();

                // On lance le rayon réfléchi
                Point reflectedOrigin = p.add(r.multiply(1e-4));
                Ray reflectedRay = new Ray(reflectedOrigin, r);

                // Appel récursif (depth - 1)
                Color reflectedColor = computeColor(reflectedRay, depth - 1);

                // Ajout pondéré par la couleur spéculaire de l'objet
                // c = c + specular * c'
                finalColor = finalColor.add(specularMaterial.schur(reflectedColor));
            }

            return finalColor;

        } else {
            return new Color(0, 0, 0); // Fond noir
        }
    }
}