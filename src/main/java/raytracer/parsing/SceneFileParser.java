package raytracer.parsing;

// Imports de tous nos packages
import raytracer.geometry.*;
import raytracer.imaging.Color;
import raytracer.raytracer.*;

// Imports Java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe dédiée au parsing (à l'analyse) du fichier de description de scène.
 * 
 */
public class SceneFileParser {

    private Scene scene;

    // Variables d'état pour le parsing 
    private Color currentDiffuse = new Color(0, 0, 0);
    private Color currentSpecular = new Color(0, 0, 0);
    private int maxVerts = 0;
    private List<Point> vertices = new ArrayList<>();

    // Indicateurs pour les champs obligatoires
    private boolean sizeSet = false;
    private boolean cameraSet = false;

    /**
     * Analyse le fichier de scène et retourne un objet Scène complet.
     * @param filePath Chemin vers le fichier .txt de la scène.
     * @return L'objet Scene configuré.
     * @throws IOException Si le fichier ne peut être lu.
     */
    public Scene parse(String filePath) throws IOException {
        this.scene = new Scene();

        // On lit toutes les lignes du fichier 
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        for (String line : lines) {
            processLine(line);
        }

        // --- Validation finale ---
        if (!sizeSet) {
            throw new RuntimeException("Erreur: La commande 'size' (obligatoire) est manquante.");
        }
        if (!cameraSet) {
            throw new RuntimeException("Erreur: La commande 'camera' (obligatoire) est manquante.");
        }

        // Validation de la somme des lumières 
        validateLightColors();

        return scene;
    }

    /**
     * Traite une seule ligne du fichier de scène.
     * @param line La ligne à analyser.
     */
    private void processLine(String line) {
        line = line.trim(); // Nettoyer les espaces 
        if (line.isEmpty() || line.startsWith("#")) { // Ignorer commentaires et lignes vides 
            return;
        }

        // Sépare la commande (ex: "sphere") du reste (ex: "0 1 0 0.5")
        // On utilise split sur un ou plusieurs espaces "\\s+" 
        String[] parts = line.split("\\s+");
        String command = parts[0];

        try {
            // Utilise un switch pour gérer les commandes 
            switch (command) {
                // --- Paramètres de Scène ---
                case "size": // 
                    scene.setWidth(i(parts[1]));
                    scene.setHeight(i(parts[2]));
                    sizeSet = true;
                    break;
                case "output": // 
                    scene.setOutput(parts[1]);
                    break;
                case "camera": // 
                    Point lookFrom = new Point(d(parts[1]), d(parts[2]), d(parts[3]));
                    Point lookAt = new Point(d(parts[4]), d(parts[5]), d(parts[6]));
                    Vector up = new Vector(d(parts[7]), d(parts[8]), d(parts[9]));
                    double fov = d(parts[10]);
                    scene.setCamera(new Camera(lookFrom, lookAt, up, fov));
                    cameraSet = true;
                    break;

                // --- Matériaux et Lumières ---
                case "ambient": // 
                    scene.setAmbient(new Color(d(parts[1]), d(parts[2]), d(parts[3])));
                    break;
                case "diffuse": // 
                    currentDiffuse = new Color(d(parts[1]), d(parts[2]), d(parts[3]));
                    break;
                case "specular": // 
                    currentSpecular = new Color(d(parts[1]), d(parts[2]), d(parts[3]));
                    break;
                case "directional": // 
                    Vector dir = new Vector(d(parts[1]), d(parts[2]), d(parts[3]));
                    Color colDir = new Color(d(parts[4]), d(parts[5]), d(parts[6]));
                    scene.addLight(new DirectionalLight(dir, colDir));
                    break;
                case "point": // 
                    Point pos = new Point(d(parts[1]), d(parts[2]), d(parts[3]));
                    Color colPoint = new Color(d(parts[4]), d(parts[5]), d(parts[6]));
                    scene.addLight(new PointLight(pos, colPoint));
                    break;

                // --- Objets (Triangles) ---
                case "maxverts": // 
                    maxVerts = i(parts[1]);
                    vertices = new ArrayList<>(maxVerts);
                    break;
                case "vertex": // 
                    vertices.add(new Point(d(parts[1]), d(parts[2]), d(parts[3])));
                    break;

                // --- Objets (Formes) ---
                case "sphere": // 
                    validateAmbientDiffuse(scene.getAmbient(), currentDiffuse); // Validation 
                    Point center = new Point(d(parts[1]), d(parts[2]), d(parts[3]));
                    double radius = d(parts[4]);
                    scene.addShape(new Sphere(center, radius, currentDiffuse, currentSpecular));
                    break;
                case "plane": // 
                    validateAmbientDiffuse(scene.getAmbient(), currentDiffuse); // Validation 
                    Point p = new Point(d(parts[1]), d(parts[2]), d(parts[3]));
                    Vector n = new Vector(d(parts[4]), d(parts[5]), d(parts[6]));
                    scene.addShape(new Plane(p, n, currentDiffuse, currentSpecular));
                    break;
                case "tri": // 
                    validateAmbientDiffuse(scene.getAmbient(), currentDiffuse); // Validation 
                    int i1 = i(parts[1]);
                    int i2 = i(parts[2]);
                    int i3 = i(parts[3]);
                    // Validation des indices 
                    validateVertexIndex(i1);
                    validateVertexIndex(i2);
                    validateVertexIndex(i3);
                    scene.addShape(new Triangle(vertices.get(i1), vertices.get(i2), vertices.get(i3), currentDiffuse, currentSpecular));
                    break;

                default:
                    System.err.println("Commande inconnue : " + command);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'analyse de la ligne : '" + line + "'");
            e.printStackTrace();
        }
    }

    // --- Méthodes de validation ---

    /**
     * Vérifie que (ambient + diffuse) <= 1 pour chaque composante.
     * [cite: 170, 175]
     */
    private void validateAmbientDiffuse(Color ambient, Color diffuse) {
        if (ambient.getR() + diffuse.getR() > 1.0 ||
                ambient.getG() + diffuse.getG() > 1.0 ||
                ambient.getB() + diffuse.getB() > 1.0) {
            throw new RuntimeException("Erreur de validation: La somme (ambient + diffuse) dépasse 1.0");
        }
    }

    /**
     * Vérifie que la somme des couleurs des lumières <= 1.
     * [cite: 193-194]
     */
    private void validateLightColors() {
        Color totalLight = new Color(0, 0, 0);
        for (AbstractLight light : scene.getLights()) {
            totalLight = totalLight.add(light.getColor());
        }
        if (totalLight.getR() > 1.0 || totalLight.getG() > 1.0 || totalLight.getB() > 1.0) {
            throw new RuntimeException("Erreur de validation: La somme des couleurs des lumières dépasse 1.0");
        }
    }

    /**
     * Vérifie qu'un indice de vertex est valide.
     * 
     */
    private void validateVertexIndex(int index) {
        if (index < 0 || index >= maxVerts) {
            throw new IndexOutOfBoundsException("Erreur: Indice de vertex '" + index + "' invalide. Doit être < " + maxVerts);
        }
    }

    // --- Petits helpers pour convertir les String ---

    /** Convertit String en double  */
    private double d(String s) {
        return Double.parseDouble(s);
    }

    /** Convertit String en int  */
    private int i(String s) {
        return Integer.parseInt(s);
    }
}