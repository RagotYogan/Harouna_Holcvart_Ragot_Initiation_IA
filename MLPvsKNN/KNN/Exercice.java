import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class Exercice {
    public static void main(String[] args) throws IOException {
        Donnees d = new Donnees(getImagette(1000, "Images/train-images-idx3-ubyte/train-images.idx3-ubyte", "Images/train-labels-idx1-ubyte/train-labels.idx3-ubyte"));
        PlusProche pp = new PlusProche(d);
        Imagette testImage = creerUneImagette(1, "Images/t10k-images-idx3-ubyte/t10k-images-idx3-ubyte", "Images/t10k-labels-idx1-ubyte/t10k-labels-idx3-ubyte");
        pp.predireEtiquette(testImage);
        System.out.println("Etiquette de l'image test : " + testImage.etiquette);



    }

    public static Imagette[] getImagette(int nbImagesACreer, String cheminFichier, String cheminLabel) throws IOException {
        String path = cheminFichier;
        DataInputStream dis = new DataInputStream(new FileInputStream(path));

        int id = dis.readInt();
        int nombreImages = dis.readInt();
        int nombreLignes = dis.readInt();
        int nombreColonnes = dis.readInt();

        Imagette[] imagettes = new Imagette[nbImagesACreer];

        for (int i = 0; i < nbImagesACreer; i++) {
            Imagette imagette = new Imagette(nombreLignes, nombreColonnes, Etiquette.getEtiquette(i, cheminLabel));
            for (int j = 0; j < nombreLignes; j++) {
                for (int k = 0; k < nombreColonnes; k++) {
                    imagette.tableau[j][k] = dis.readUnsignedByte();
                }
            }
            imagettes[i] = imagette;
        }
        creerImage(imagettes, nbImagesACreer);
        return imagettes;
    }

    public static void creerImage(Imagette[] tabImagette, int nbImagesACreer) {
        String path = "Images/CreationUneImage/";
        for (int i = 0; i < nbImagesACreer; i++) {
            Imagette imagette = tabImagette[i];
            int[][] tableau = imagette.tableau;

            int rows = tableau.length;
            int cols = tableau[0].length;
            
            BufferedImage image = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
            for (int ligne = 0; ligne < rows; ligne++) {
                for (int colonne = 0; colonne < cols; colonne++) {
                    int niveauGris = tableau[ligne][colonne];
                    int couleurRGB = (niveauGris << 16) | (niveauGris << 8) | niveauGris;

                    image.setRGB(colonne, ligne, couleurRGB);
                }
            }

            try {
                String cheminFichier = path + "imagette_" + i + ".png";
                File fichier = new File(cheminFichier);
                ImageIO.write(image, "png", fichier);
                //System.out.println("Image sauvegardée avec succès dans : " + cheminFichier);
            } catch (IOException ex) {
                throw new RuntimeException("Erreur lors de la sauvegarde de l'image : " + ex.getMessage(), ex);
            }
        }
    }

    public static Imagette creerUneImagette(int index, String cheminFichier, String cheminLabel) throws IOException {
        String path = cheminFichier;
        DataInputStream dis = new DataInputStream(new FileInputStream(path));

        int id = dis.readInt();
        int nombreImages = dis.readInt();
        int nombreLignes = dis.readInt();
        int nombreColonnes = dis.readInt();
        Imagette[] imagettes = new Imagette[1];


        Imagette imagette = new Imagette(nombreLignes, nombreColonnes, Etiquette.getEtiquette(index-1, cheminLabel));
        dis.skipBytes((index-1) * nombreLignes * nombreColonnes);
        for (int j = 0; j < nombreLignes; j++) {
            for (int k = 0; k < nombreColonnes; k++) {
                imagette.tableau[j][k] = dis.readUnsignedByte();
            }
        }
        imagettes[0] = imagette;
        creerImage(imagettes, 1);
        return imagette;

    }
}
