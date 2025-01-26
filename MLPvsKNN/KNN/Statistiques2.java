import java.io.*;
import java.util.*;

public class Statistiques2 {

    private AlgoClassification algo;
    private Donnees donneesTest;

    public Statistiques2(AlgoClassification algo, Donnees donneesTest) {
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
            // Charger les données MNIST
            Donnees donneesEntrainementMNIST = new Donnees(Exercice.getImagette(1000, "Images/train-images-idx3-ubyte/train-images.idx3-ubyte", "Images/train-labels-idx1-ubyte/train-labels.idx1-ubyte"));
            Donnees donneesTestMNIST = new Donnees(Exercice.getImagette(1000, "Images/t10k-images-idx3-ubyte/t10k-images.idx3-ubyte", "Images/t10k-labels-idx1-ubyte/t10k-labels.idx1-ubyte"));

            // Charger les données Fashion MNIST
            Donnees donneesEntrainementFashion = new Donnees(Exercice.getImagette(1000, "Images/fashion/train-images-idx3-ubyte/train-images-idx3-ubyte", "Images/fashion/train-labels-idx1-ubyte/train-labels-idx1-ubyte"));
            Donnees donneesTestFashion = new Donnees(Exercice.getImagette(1000, "Images/fashion/t10k-images-idx3-ubyte/t10k-images-idx3-ubyte", "Images/fashion/t10k-labels-idx1-ubyte/t10k-labels-idx1-ubyte"));

            // Comparer les résultats entre MNIST et Fashion MNIST
            System.out.println("Résultats pour MNIST :");
            AlgoClassification algoMNIST = new PlusProche(donneesEntrainementMNIST);
            Statistiques statsMNIST = new Statistiques(algoMNIST, donneesTestMNIST);
            double pourcentageReussiteMNIST = statsMNIST.calculerPourcentageReussite();
            System.out.println("Pourcentage de réussite : " + pourcentageReussiteMNIST + "%");

            System.out.println("\nRésultats pour Fashion MNIST :");
            AlgoClassification algoFashion = new PlusProche(donneesEntrainementFashion);
            Statistiques statsFashion = new Statistiques(algoFashion, donneesTestFashion);
            double pourcentageReussiteFashion = statsFashion.calculerPourcentageReussite();
            System.out.println("Pourcentage de réussite : " + pourcentageReussiteFashion + "%");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}