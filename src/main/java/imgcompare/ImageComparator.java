package imgcompare;

import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * Cette classe gère la logique de comparaison entre deux images.
 * Elle fournit des méthodes pour compter les pixels différents
 * et pour générer une image différentielle.
 */
public class ImageComparator {

    private BufferedImage img1;
    private BufferedImage img2;

    /**
     * Constructeur pour l'ImageComparator.
     * @param img1 La première image (par ex: image de référence)
     * @param img2 La seconde image (par ex: image générée)
     */
    public ImageComparator(BufferedImage img1, BufferedImage img2) {
        this.img1 = img1;
        this.img2 = img2;
    }

    /**
     * Vérifie si les deux images ont les mêmes dimensions.
     * @return true si elles ont la même largeur ET la même hauteur, false sinon.
     */
    public boolean haveSameDimensions() {
        return img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight();
    }

    /**
     * Compte le nombre de pixels qui sont différents entre les deux images.
     * La comparaison se fait pixel par pixel.
     *
     * @return Le nombre total de pixels différents.
     * @throws IllegalArgumentException si les images n'ont pas les mêmes dimensions.
     */
    public int countDifferentPixels() {
        if (!haveSameDimensions()) {
            throw new IllegalArgumentException("Les images n'ont pas les mêmes dimensions.");
        }

        int diffPixelCount = 0;
        int width = img1.getWidth();
        int height = img1.getHeight();

        // On parcourt chaque ligne (y)
        for (int y = 0; y < height; y++) {
            // Dans chaque ligne, on parcourt chaque pixel (x)
            for (int x = 0; x < width; x++) {

                // On récupère la couleur (entier RGB) de chaque pixel
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                // Si les valeurs RGB sont différentes, on incrémente le compteur
                if (rgb1 != rgb2) {
                    diffPixelCount++;
                }
            }
        }
        return diffPixelCount;
    }

    /**
     * Génère une image représentant la différence entre les two images.
     * - Pixel noir : Les pixels sont identiques.
     * - Autre couleur : La différence absolue des couleurs (R, G, B).
     *
     * @return Une nouvelle BufferedImage contenant l'image différentielle.
     * @throws IllegalArgumentException si les images n'ont pas les mêmes dimensions.
     */
    public BufferedImage generateDiffImage() {
        if (!haveSameDimensions()) {
            throw new IllegalArgumentException("Les images n'ont pas les mêmes dimensions.");
        }

        int width = img1.getWidth();
        int height = img1.getHeight();

        // On crée une nouvelle image en mémoire pour stocker le résultat
        BufferedImage diffImage = new BufferedImage(width, height, img1.getType());

        // On parcourt chaque pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                if (rgb1 == rgb2) {
                    // Pixels identiques : on met du noir
                    diffImage.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    // Pixels différents : on calcule la différence de couleur
                    Color c1 = new Color(rgb1);
                    Color c2 = new Color(rgb2);

                    // On calcule la différence absolue pour chaque composante (Rouge, Vert, Bleu)
                    int diffR = Math.abs(c1.getRed() - c2.getRed());
                    int diffG = Math.abs(c1.getGreen() - c2.getGreen());
                    int diffB = Math.abs(c1.getBlue() - c2.getBlue());

                    Color diffColor = new Color(diffR, diffG, diffB);
                    diffImage.setRGB(x, y, diffColor.getRGB());
                }
            }
        }
        return diffImage;
    }
}