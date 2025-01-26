import java.io.IOException;
import java.util.*;

public class MLPTester {
    public static void main(String[] args) throws IOException {
        // Chargement des données d'entraînement et de test
        //start timer
        long startTimeD = System.currentTimeMillis();
        Donnees donneesEntrainement = new Donnees(
                Exercice.getImagette(60000,
                        "Images/train-images-idx3-ubyte/train-images.idx3-ubyte",
                        "Images/train-labels-idx1-ubyte/train-labels.idx1-ubyte")
        );
        Donnees donneesTest = new Donnees(
                Exercice.getImagette(10000,
                        "Images/t10k-images-idx3-ubyte/t10k-images.idx3-ubyte",
                        "Images/t10k-labels-idx1-ubyte/t10k-labels.idx1-ubyte")
        );
        long endTimeD = System.currentTimeMillis();
        long durationD = (endTimeD - startTimeD);
        System.out.println("Temps de chargement des données : " + durationD / 1000 + " secondes");


        // Hyperparamètres à tester
        int[][] configurationsCouches = {
                {784, 10, 10},
                {784, 64, 10},
                {784, 128, 10},
                {784, 256, 128, 10},
                {784, 512,256 ,128,10},
                {784, 512,256 ,128, 64,32,16,10}
        };
        double[] learningRates = {0.01,0.1,0.4,0.9}; // Différents taux d'apprentissage
        int maxIterations = 30; // Nombre maximum d'itérations
        double seuilAmelioration = 0.000000001; // Critère d'arrêt basé sur la diminution de l'erreur

        // Résultats finaux
        List<Resultat> resultats = new ArrayList<>();

        // Boucle sur les configurations
        //set timer
        long startTime = System.currentTimeMillis();
        for (int[] configuration : configurationsCouches) {
            for (double learningRate : learningRates) {
                System.out.println("Configuration : " + Arrays.toString(configuration) + " | Learning Rate : " + learningRate);

                // Initialisation du perceptron
                MLP perceptron = new MLP(configuration, learningRate, new Sigmoid());

                double previousError = Double.MAX_VALUE;
                double error;
                boolean convergence = false;

                // Entraînement
                for (int iteration = 0; iteration < maxIterations; iteration++) {
                    error = 0;

                    for (Imagette imagette : donneesEntrainement.imagettes) {
                        double[] input = convertirEnVecteur(imagette.tableau); // Convertir tableau 2D en vecteur
                        double[] output = convertirEtiquetteEnOneHot(imagette.etiquette);
                        error += perceptron.backPropagate(input, output); // Rétropropagation
                    }

                    // Calcul de l'erreur moyenne
                    error /= donneesEntrainement.imagettes.length;


                    previousError = error;

                }

                // Évaluation sur les données de test
                int correct = 0;
                for (Imagette imagette : donneesTest.imagettes) {
                    double[] input = convertirEnVecteur(imagette.tableau); // Convertir tableau 2D en vecteur
                    double[] output = perceptron.execute(input);
                    int predictedLabel = obtenirLabelPredi(output);

                    if (predictedLabel == imagette.etiquette) {
                        correct++;
                    }
                }

                // Précision finale
                double accuracy = (double) correct / donneesTest.imagettes.length;
                System.out.println("Précision finale : " + (accuracy * 100) + "%");

                // Stocker les résultats
                resultats.add(new Resultat(configuration, learningRate, accuracy, previousError));
            }
        }
        //end timer
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("Temps d'exécution : " + duration / 1000 + " secondes");
        System.out.println("\n======== Résultats ========");
        for (Resultat resultat : resultats) {
            System.out.println(resultat);
        }
    }

    public static double[] convertirEnVecteur(int[][] tableau) {
        int nbLignes = tableau.length;
        int nbColonnes = tableau[0].length;
        double[] vecteur = new double[nbLignes * nbColonnes];
        int index = 0;

        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                vecteur[index++] = tableau[i][j];
            }
        }
        return vecteur;
    }

    public static double[] convertirEtiquetteEnOneHot(int etiquette) {
        double[] oneHot = new double[10];
        oneHot[etiquette] = 1.0;
        return oneHot;
    }
    public static int obtenirLabelPredi(double[] output) {
        int label = 0;
        double max = output[0];
        for (int i = 1; i < output.length; i++) {
            if (output[i] > max) {
                max = output[i];
                label = i;
            }
        }
        return label;
    }
}

// Classe pour représenter les résultats
class Resultat {
    int[] configuration;
    double learningRate;
    double accuracy;
    double finalError;
    boolean convergence;

    public Resultat(int[] configuration, double learningRate, double accuracy, double finalError) {
        this.configuration = configuration;
        this.learningRate = learningRate;
        this.accuracy = accuracy;
        this.finalError = finalError;
    }

    @Override
    public String toString() {
        return "Configuration : " + Arrays.toString(configuration) +
                " | Learning Rate : " + learningRate +
                " | Précision : " + (accuracy * 100) + "%" +
                " | Erreur finale : " + finalError;
    }
}
