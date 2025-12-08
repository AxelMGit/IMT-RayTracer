package raytracer;

import raytracer.parsing.SceneFileParser;
import raytracer.raytracer.RayTracer;
import raytracer.raytracer.Scene;

public class RayTracerMain {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java raytracer.RayTracerMain <scene_file.txt>");
            System.err.println("Usage: RayTracer.exe <scene_file.txt>");
            return;
        }

        String sceneFilePath = args[0];
        SceneFileParser parser = new SceneFileParser();

        try {
            System.out.println("Lecture de la scène...");
            Scene scene = parser.parse(sceneFilePath);

            System.out.println("Rendu de l'image...");
            RayTracer rayTracer = new RayTracer(scene);
            rayTracer.render();

            System.out.println("Terminé !");

        } catch (Exception e) {
            System.err.println("Une erreur est survenue :");
            e.printStackTrace();
        }
    }
}