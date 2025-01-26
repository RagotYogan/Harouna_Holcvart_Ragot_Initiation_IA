public abstract class AlgoClassification {
    Donnees donnees;

    public AlgoClassification(Donnees d){
        donnees=d;
    }

    public abstract int predireEtiquette(Imagette testImagette);
}
