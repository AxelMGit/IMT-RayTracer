package imgcompare;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO; // [cite: 58]

/**
 * Programme principal pour comparer deux images PNG.
 * Prend deux fichiers image en argument et affiche le résultat de la comparaison.
 * Génère une image 'diff.png' si les images diffèrent.
 */
public class Main {

    public static void main(String[] args) {

        String pathImg1;
        String pathImg2;

        // 1. Vérifier les arguments
        if (args.length == 0) {
            // Cas "aucune image rentrée" : on définit des chemins par défaut
            System.out.println("Aucun argument fourni. Utilisation des images par défaut.");
            pathImg1 = "src/main/resources/test_compare_image1.png"; // Assure-toi que ce fichier existe
            pathImg2 = "src/main/resources/test_compare_image2.png"; // Assure-toi que ce fichier existe

        } else if (args.length == 2) {
            // Cas normal : on utilise les arguments fournis
            pathImg1 = args[0];
            pathImg2 = args[1];

        } else {
            // Cas invalide (1 seul argument, ou plus de 2)
            System.err.println("Erreur: Nombre d'arguments incorrect.");
            System.err.println("Usage: java imgcompare.Main <image1.png> <image2.png>");
            System.err.println("Ou ne fournissez aucun argument pour utiliser les images par défaut.");
            return;
        }


        try {
            // 2. Lire les images (on utilise maintenant les variables pathImg1 et pathImg2)
            File file1 = new File(pathImg1);
            File file2 = new File(pathImg2);

            BufferedImage img1 = ImageIO.read(file1);
            BufferedImage img2 = ImageIO.read(file2);

            if (img1 == null || img2 == null) {
                System.err.println("Erreur: Impossible de lire une ou les deux images.");
                return;
            }

            // 3. Initialiser le comparateur
            ImageComparator comparator = new ImageComparator(img1, img2);

            // 4. Vérifier les dimensions avant de comparer [cite: 65]
            if (!comparator.haveSameDimensions()) {
                System.err.println("Erreur: Les images n'ont pas les mêmes dimensions.");
                return;
            }

            // 5. Obtenir le nombre de pixels différents
            int diffPixels = comparator.countDifferentPixels(); // [cite: 45]

            // 6. Afficher le résultat (OK/KO) [cite: 48]
            // Moins de 1000 pixels différents = OK [cite: 14]
            if (diffPixels < 1000) {
                System.out.println("OK"); // [cite: 25]
            } else {
                System.out.println("KO"); // [cite: 25]
            }

            // 7. Afficher le nombre de pixels différents [cite: 48]
            System.out.println("Les deux images diffèrent de " + diffPixels + " pixels."); // [cite: 26]

            // 8. Générer et écrire l'image différentielle
            // On ne la génère que s'il y a au moins 1 pixel différent [cite: 16]
            if (diffPixels > 0) {
                BufferedImage diffImage = comparator.generateDiffImage(); // [cite: 46]

                File outputFile = new File("diff.png");
                ImageIO.write(diffImage, "png", outputFile); // [cite: 49, 60]
                System.out.println("Une image 'diff.png' a été générée.");
            }

        } catch (IOException e) { // [cite: 64]
            System.err.println("Erreur d'entrée/sortie : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Levée par ImageComparator si les dimensions ne correspondent pas
            System.err.println("Erreur de comparaison : " + e.getMessage());
        }
    }
}