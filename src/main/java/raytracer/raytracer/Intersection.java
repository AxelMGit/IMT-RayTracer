package raytracer.raytracer;

import raytracer.geometry.Point;
import raytracer.geometry.Shape;
import raytracer.geometry.Vector;
import raytracer.imaging.Color;

public class Intersection {
    private final double distance;
    private final Shape shape;
    private final Vector normal;
    private final Color diffuse;
    private final Color specular; // Nouveau (Jalon 5)
    private final double shininess; // Nouveau (Jalon 5)

    // Constructeur complet avec les propriétés de matériau
    public Intersection(double distance, Shape shape, Vector normal, Color diffuse, Color specular, double shininess) {
        this.distance = distance;
        this.shape = shape;
        this.normal = normal;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    // --- Getters ---
    public double getDistance() { return distance; }
    public Shape getShape() { return shape; }
    public Vector getNormal() { return normal; }
    public Color getDiffuse() { return diffuse; }
    public Color getSpecular() { return specular; }
    public double getShininess() { return shininess; }

    /**
     * Calcule la couleur (Lambert + Phong) pour une lumière donnée.
     * @param light La source de lumière
     * @param p Le point d'intersection exact dans la scène
     * @param viewDir Le vecteur direction vers l'œil (nécessaire pour le reflet spéculaire)
     * @return La couleur résultante pour cette lumière
     */
    public Color calculateColor(AbstractLight light, Point p, Vector viewDir) {
        // 1. Calcul du vecteur Lumière (L)
        Vector l = light.getL(p);
        Color lightColor = light.getColor();

        // 2. Diffuse (Lambert) : max(N . L, 0)
        //
        double nDotL = normal.dot(l);
        double diffuseIntensity = Math.max(nDotL, 0.0);
        Color diffuseTerm = lightColor.multiply(diffuseIntensity).schur(this.diffuse);

        // 3. Spéculaire (Blinn-Phong)
        //
        Color specularTerm = new Color(0, 0, 0);

        // On ne calcule le reflet que si la surface est éclairée (Lambert > 0)
        if (diffuseIntensity > 0) {
            // Vecteur H (Halfway) = (L + V) normalisé
            // Formule : h = (lightdir + eyedir) / |lightdir + eyedir|
            Vector h = l.add(viewDir).normalize();

            double nDotH = normal.dot(h);

            // Intensité = (N . H) ^ shininess
            double specularIntensity = Math.pow(Math.max(nDotH, 0.0), this.shininess);

            // Résultat = Intensité * CouleurLumière * CouleurSpéculaire
            specularTerm = lightColor.multiply(specularIntensity).schur(this.specular);
        }

        // 4. On combine les deux (Diffuse + Spéculaire)
        //
        return diffuseTerm.add(specularTerm);
    }
}