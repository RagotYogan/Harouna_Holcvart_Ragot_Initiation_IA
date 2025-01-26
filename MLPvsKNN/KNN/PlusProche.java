import java.util.ArrayList;

public class PlusProche extends AlgoClassification {

    public PlusProche(Donnees donnees) {
        super(donnees);
    }

    @Override
    public int predireEtiquette(Imagette testImage) {
        ArrayList<Imagette> listeImagette = new ArrayList<>();
        ArrayList<Double> distances = new ArrayList<>();
        int label = -1;

        for (Imagette imagette : donnees.imagettes) {
            double distance = calculerDistance(testImage, imagette);

            if (listeImagette.size() < 10) {
                listeImagette.add(imagette);
                distances.add(distance);
            } else {
                int maxIndex = indiceDistanceMax(distances);
                if (distance < distances.get(maxIndex)) {
                    listeImagette.set(maxIndex, imagette);
                    distances.set(maxIndex, distance);
                }
            }
        }
        label = getMostLabel(listeImagette);
        return label;
    }

    private int indiceDistanceMax(ArrayList<Double> distance){
        int index = 0;
        double max = distance.get(0);

        for(int i = 1; i < distance.size(); i++){
            if(distance.get(i) > max){
                max = distance.get(i);
                index = i;
            }
        }
        return index;
    }

    private int getMostLabel(ArrayList<Imagette> imagette){
        int[] labels = new int[5];
        for(int i = 0; i < 5; i++){
            labels[i] = imagette.get(i).etiquette;
        }
        int maxOccurence = labels[0];
        int max = 0;
        for(int i = 0; i < labels.length; i++){
            int occurence = 0;
            for(int j = 0; j < labels.length; j++){
                if(labels[i] == labels[j]){
                    occurence++;
                }
            }
            if(occurence > max){
                max = occurence;
                maxOccurence = labels[i];
            }
        }
        return maxOccurence;
    }

    private double calculerDistance(Imagette img1, Imagette img2) {
        int[][] tableau1 = img1.tableau;
        int[][] tableau2 = img2.tableau;
        double distance = 0;

        for (int i = 0; i < tableau1.length; i++) {
            for (int j = 0; j < tableau1[i].length; j++) {
                distance += Math.pow(tableau1[i][j] - tableau2[i][j], 2);
            }
        }
        return Math.sqrt(distance);
    }
}
