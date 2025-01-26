import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Etiquette {

    public static List<Integer> getAllEtiquette(int nbEtiquette, String cheminLabel) {
        try {
            String path = cheminLabel;
            DataInputStream dis = new DataInputStream(new FileInputStream(path));

            int id = dis.readInt();

            int nombreImages = dis.readInt();

            List<Integer> listeChiffre = new ArrayList<Integer>(nbEtiquette);
            for (int i = 0; i < nbEtiquette; i++) {
                listeChiffre.add(dis.readUnsignedByte());
            }

            return listeChiffre;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static int getEtiquette(int index, String cheminLabel) {
        try {
            String path = cheminLabel;
            DataInputStream dis = new DataInputStream(new FileInputStream(path));

            int id = dis.readInt();

            int nombreImages = dis.readInt();

            dis.skipBytes(index);
            int numeroEtiquette = dis.readUnsignedByte();

            return numeroEtiquette;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
