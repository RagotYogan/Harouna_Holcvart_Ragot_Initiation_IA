import java.io.*;
import java.util.*;

public class Statistiques {

    private AlgoClassification algo;
    private Donnees donneesTest;

    public Statistiques(AlgoClassification algo, Donnees donneesTest) {
        this.algo = algo;
        this.donneesTest = donneesTest;
    }

    // Question 2.3 : Calculer le pourcentage de réussite
    public double calculerPourcentageReussite() {
        int total = donneesTest.imagettes.length;
        int correct = 0;

        for (Imagette imagetteTest : donneesTest.imagettes) {
            int etiquettePredite = algo.predireEtiquette(imagetteTest);
            if (etiquettePredite == imagetteTest.etiquette) {
                correct++;
            }
        }

        return (correct / (double) total) * 100;
    }

    // Question 2.7 : Calculer les pourcentages par classe
    public Map<Integer, Double> calculerPourcentagesParClasse() {
        Map<Integer, Integer> totalParClasse = new HashMap<>();
        Map<Integer, Integer> correctParClasse = new HashMap<>();

        for (Imagette imagetteTest : donneesTest.imagettes) {
            int etiquetteReelle = imagetteTest.etiquette;
            int etiquettePredite = algo.predireEtiquette(imagetteTest);

            totalParClasse.put(etiquetteReelle, totalParClasse.getOrDefault(etiquetteReelle, 0) + 1);

            if (etiquetteReelle == etiquettePredite) {
                correctParClasse.put(etiquetteReelle, correctParClasse.getOrDefault(etiquetteReelle, 0) + 1);
            }
        }

        Map<Integer, Double> pourcentagesParClasse = new HashMap<>();
        for (int classe : totalParClasse.keySet()) {
            int total = totalParClasse.get(classe);
            int correct = correctParClasse.getOrDefault(classe, 0);
            pourcentagesParClasse.put(classe, (correct / (double) total) * 100);
        }

        return pourcentagesParClasse;
    }

    public static void main(String[] args) {
        try {
            // Charger les données d'entraînement et de test
            Donnees donneesEntrainement = new Donnees(Exercice.getImagette(60000, "Images/train-images-idx3-ubyte/train-images.idx3-ubyte", "Images/train-labels-idx1-ubyte/train-labels.idx1-ubyte"));
            Donnees donneesTest = new Donnees(Exercice.getImagette(10000, "Images/t10k-images-idx3-ubyte/t10k-images.idx3-ubyte", "Images/t10k-labels-idx1-ubyte/t10k-labels.idx1-ubyte"));

            // Question 2.4 : Utiliser l'algorithme PlusProche
            AlgoClassification algo = new PlusProche(donneesEntrainement);
            Statistiques stats = new Statistiques(algo, donneesTest);

            // Calculer le pourcentage de réussite global
            double pourcentageReussite = stats.calculerPourcentageReussite();
            System.out.println("Pourcentage de réussite : " + pourcentageReussite + "%");

            // Calculer les pourcentages par classe
            Map<Integer, Double> pourcentagesParClasse = stats.calculerPourcentagesParClasse();
            for (Map.Entry<Integer, Double> entry : pourcentagesParClasse.entrySet()) {
                System.out.println("Classe " + entry.getKey() + " : " + entry.getValue() + "%");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}