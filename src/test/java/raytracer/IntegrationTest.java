package raytracer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import raytracer.parsing.SceneFileParser;
import raytracer.raytracer.RayTracer;
import raytracer.raytracer.Scene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class IntegrationTest {

    @TempDir
    Path tempDir;

    @Test
    void testFullRenderPipeline() throws IOException {
        // 1. Création d'un fichier de scène simple
        Path scenePath = tempDir.resolve("test_scene.txt");
        String sceneContent =
                "size 200 200\n" + // J'ai augmenté un peu la taille pour que le temps soit mesurable
                        "output " + tempDir.resolve("output.png").toString() + "\n" +
                        "camera 0 0 5 0 0 0 0 1 0 45\n" +
                        "ambient 0.1 0.1 0.1\n" +
                        "directional 0 0 1 1 1 1\n" +
                        "diffuse 1 0 0\n" +
                        "sphere 0 0 0 1\n";

        Files.writeString(scenePath, sceneContent);

        // 2. Parsing
        SceneFileParser parser = new SceneFileParser();
        Scene scene = parser.parse(scenePath.toString());

        // 3. Rendu avec Chronomètre
        RayTracer rayTracer = new RayTracer(scene);

        System.out.println("Début du rendu...");
        long startTime = System.nanoTime(); // Démarrage du chrono

        rayTracer.render();

        long endTime = System.nanoTime(); // Arrêt du chrono

        // 4. Calcul et affichage
        long durationNs = endTime - startTime;
        double durationMs = durationNs / 1_000_000.0; // Conversion en millisecondes

        System.out.println(String.format("Rendu terminé en %.3f ms", durationMs));

        // 5. Vérification habituelle
        File outputFile = new File(scene.getOutput());
        assertTrue(outputFile.exists(), "Le fichier image de sortie devrait exister");
        assertTrue(outputFile.length() > 0, "Le fichier image ne devrait pas être vide");
    }
}