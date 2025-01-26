import java.util.Random;

public class PerceptronTester {

    public static void main(String[] args) {
        int passageMax = 10000;
        double learningRate = 0.1;
        double errorThreshold = 0.01;

        double[][] inputs = {
                {0, 0}, {0, 1}, {1, 0}, {1, 1}
        };

        testWithTransferFunction(new Sigmoid(), "Sigmoid", inputs, passageMax, learningRate, errorThreshold);
        System.out.println("\n\n\n");
        testWithTransferFunction(new Tangente(), "Tangente", inputs, passageMax, learningRate, errorThreshold);
    }

    public static void testWithTransferFunction(TransferFunction transferFunction, String functionName, double[][] inputs,
                                                int passageMax, double learningRate, double errorThreshold) {
        double[][] andOutputs = {{0}, {0}, {0}, {1}};
        double[][] orOutputs = {{0}, {1}, {1}, {1}};
        double[][] xorOutputs = {{0}, {1}, {1}, {0}};

        double[][] andOutputs2 = {{0,0}, {0,0}, {0,0}, {1,1}};
        double[][] orOutputs2 = {{0,0}, {1,1}, {1,1}, {1,1}};
        double[][] xorOutputs2 = {{0,0}, {1,1}, {1,1}, {0,0}};

        int[] layers = {2, 2, 1};
        int[] layers2 = {2, 2, 2};
        System.out.println("Test avec " + functionName + " function" + " sortie dimension 1 \n");

        MLP mlp = new MLP(layers, learningRate, transferFunction);

        testLogicTable("AND", mlp, inputs, andOutputs, passageMax, errorThreshold);
        testLogicTable("OR", mlp, inputs, orOutputs, passageMax, errorThreshold);
        testLogicTable("XOR", mlp, inputs, xorOutputs, passageMax, errorThreshold);

        System.out.println("\n\nTest avec " + functionName + " function" + " sortie dimension 2\n");

        MLP mlp2 = new MLP(layers2, learningRate, transferFunction);

        testLogicTable("AND", mlp2, inputs, andOutputs2, passageMax, errorThreshold);
        testLogicTable("OR", mlp2, inputs, orOutputs2, passageMax, errorThreshold);
        testLogicTable("XOR", mlp2, inputs, xorOutputs2, passageMax, errorThreshold);

    }

    public static void testLogicTable(String tableName, MLP mlp, double[][] inputs, double[][] outputs, int passageMax, double errorThreshold) {
        System.out.println("Entrainement pour " + tableName + " table\n");

        Random random = new Random();
        int passages = 0;
        double erreur;

        do {
            erreur = 0.0;

            for (int i = 0; i < inputs.length; i++) {
                int swapIndex = random.nextInt(inputs.length);
                double[] tempInput = inputs[i];
                inputs[i] = inputs[swapIndex];
                inputs[swapIndex] = tempInput;

                double[] tempOutput = outputs[i];
                outputs[i] = outputs[swapIndex];
                outputs[swapIndex] = tempOutput;
            }

            for (int i = 0; i < inputs.length; i++) {
                erreur += mlp.backPropagate(inputs[i], outputs[i]);
            }

            passages++;

            if (passages % 1000 == 0) {
                System.out.printf("Passage %d, Erreur: %.6f%n", passages, erreur / inputs.length);
            }

        } while (erreur > errorThreshold && passages < passageMax);

        System.out.printf("Entrainement finie pour %s en %d passages, Erreur finale: %.6f%n", tableName, passages, erreur / inputs.length );

        System.out.println("Test resultat:");
        for (int i = 0; i < inputs.length; i++) {
            double[] result = mlp.execute(inputs[i]);
            System.out.printf("Entrée: %s, Attendu: %s, Prédit: %s%n", formatArray(inputs[i]), formatArray(outputs[i]), formatArray(result));
        }
        System.out.println();
    }

    public static String formatArray(double[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(String.format("%.6f", array[i]));
            if (i < array.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
