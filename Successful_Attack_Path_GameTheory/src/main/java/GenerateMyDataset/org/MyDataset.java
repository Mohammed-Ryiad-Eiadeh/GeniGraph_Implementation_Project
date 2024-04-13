package GenerateMyDataset.org;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * This class is used to generate a random dataset as attack graph
 */
public class MyDataset {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\dset4_rand.txt";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            int nodes = 300;
            int desiredEdgesNum = 600;
            int edgeCounter = 0;
            bufferedWriter.write(nodes + " <--# of nodes, " + desiredEdgesNum + " edges " + "\n");
            for (int i = 1; i <= nodes && edgeCounter <= desiredEdgesNum; i++) {
                int connectionCounter = 0;
                int connectionLimit = 2;
                for (int j = i + 1; j <= nodes && edgeCounter <= desiredEdgesNum; j++) {
                    if (connectionCounter == connectionLimit) {
                        break;
                    }
                    bufferedWriter.write(i + " " + j + " " + /*remove this term for weights of 1*/ new Random().nextDouble() + "\n");
                    edgeCounter++;
                    connectionCounter++;
                }
            }
            Random random = new Random();
            while (edgeCounter < desiredEdgesNum) {
                bufferedWriter.write((random.nextInt(nodes) + 1) + " " +
                        (random.nextInt(nodes) + 1) + " " +
                        new Random().nextDouble() + "\n");
                edgeCounter++;
            }
            double[] assetLossVec = new double[nodes];
            for (int i = 1; i < assetLossVec.length; i = i + 4) {
                assetLossVec[i] = new Random().nextDouble(100);
            }
            assetLossVec[nodes - 1] = new Random().nextDouble(100);
            bufferedWriter.write("\n" + "Remove this line and move it to the switch statement in GraphData.java" + "\n" +
                    Arrays.toString(assetLossVec));
            bufferedWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
