package raytracer.raytracer;

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

    /**
     * Méthode principale de rendu de l'image.
     * [cite: 85-95]
     */
    public void render() {
        int width = scene.getWidth();
        int height = scene.getHeight();
        Camera cam = scene.getCamera();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 1. Calcul du repère orthonormé (u, v, w) [cite: 96-106]
        Vector w = cam.getLookFrom().subtract(cam.getLookAt()).normalize(); // w = (lookFrom - lookAt) / norm
        Vector u = cam.getUp().cross(w).normalize(); // u = (up x w) / norm
        Vector v = w.cross(u).normalize(); // v = (w x u) / norm

        // 2. Calcul des dimensions du pixel dans la scène [cite: 108-118]
        double fovRadians = Math.toRadians(cam.getFov()); // Conversion en radians [cite: 112]
        // Note: Le PDF dit "hauteur d'un pixel" mais la formule tan(fov/2) correspond à la demi-hauteur du plan image.
        double halfHeight = Math.tan(fovRadians / 2);
        double halfWidth = halfHeight * ((double) width / height);

        // 3. Boucle sur chaque pixel
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                // Calcul des coefficients a et b pour la direction du rayon [cite: 129]
                // Attention à la conversion en double pour la division
                double a = (halfWidth * (i - (width / 2.0) + 0.5)) / (width / 2.0);
                double b = (halfHeight * (j - (height / 2.0) + 0.5)) / (height / 2.0);

                // Calcul du vecteur direction d [cite: 131]
                // d = u*a + v*b - w
                Vector d = u.multiply(a).add(v.multiply(b)).subtract(w).normalize();

                // Création du rayon
                Ray ray = new Ray(cam.getLookFrom(), d);

                // Recherche de l'intersection [cite: 89, 90]
                Optional<Intersection> closest = scene.findClosestIntersection(ray);

                Color pixelColor;
                if (closest.isPresent()) {
                    // Si intersection, on prend la couleur ambiante définie dans la scène [cite: 161]
                    // (Pour ce jalon, c'est ce qui est demandé)
                    pixelColor = scene.getAmbient();
                } else {
                    // Sinon noir [cite: 93]
                    pixelColor = new Color(0, 0, 0);
                }

                // Colorier le pixel (attention à l'inversion Y mentionnée dans le PDF) [cite: 122]
                // L'axe Y de l'image (j) va vers le bas, mais l'axe Y mathématique va souvent vers le haut.
                // Selon le résultat, il faudra peut-être faire image.setRGB(i, height - 1 - j, ...)
                // Pour l'instant on suit le standard (0,0 en haut à gauche).
                image.setRGB(i, j, pixelColor.toRGB());
            }
        }

        // Sauvegarde de l'image
        try {
            File outputFile = new File(scene.getOutput());
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image générée : " + scene.getOutput());
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture de l'image : " + e.getMessage());
        }
    }
}