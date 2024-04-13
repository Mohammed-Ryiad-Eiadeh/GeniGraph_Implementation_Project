package ProActiveDefense.org;

import java.util.ArrayList;

/**
 * This class is used to apply the proactive decoy defence algorithm.
 * <pre>
 *     Optimal Decoy Resource Allocation for Proactive Defense in Probabilistic Attack Graphs, Haoxiang et al, 2023
 * </pre>
 */
public class ProactiveDefenseDecoy {
    private final double[][] AdjMat;
    private final double firstSecondDefnderBudget;
    private int numOfDecoyes;
    private ArrayList<Integer> decoyesId;

    /**
     * This constructor is used to creat an object from the proactive decoy approach
     * @param adjMat The adjacency matrix of the given graph problem.
     * @param firstSecondDefnderBudget The total budget of the first and the second defender
     */
    public ProactiveDefenseDecoy(double[][] adjMat, double firstSecondDefnderBudget) {
        if (adjMat == null) {
            throw new IllegalArgumentException("The matrix is null!");
        }
        AdjMat = adjMat;
        this.firstSecondDefnderBudget = firstSecondDefnderBudget;
        decoyesId = new ArrayList<>();
    }

    /**
     * This method is used to creat the decoy nodes
     * @param assetId The node in which its gona be connected to the decoy nodes
     * @return The expanded adjacent matrix after adding the fake nodes
     */
    public double[][] generateDecoy(int assetId) {
        // Determine the in-degree nodes to the targetted asset
        ArrayList<Integer> in_degree_edges = new ArrayList<>();
        for (int i = 0; i < AdjMat.length; i++) {
            if (AdjMat[i][assetId] > 0d) {
                in_degree_edges.add(i);
            }
        }
        // We alwayes set the number of decoyes to 2 fake nodes
        int numOfDecoyesWithOneWeights = 2;
        double weightOfLastDecoy = firstSecondDefnderBudget - numOfDecoyesWithOneWeights;
        numOfDecoyes = numOfDecoyesWithOneWeights + (weightOfLastDecoy > 0 ? 1 : 0);
        // Creat the new adjacency matrix which has the fake assets to distruct the attackers
        int newMatSize = AdjMat.length + numOfDecoyesWithOneWeights;
        if (weightOfLastDecoy > 0) {
            newMatSize += 1;
        }
        double [][] expandedMatrix = new double[newMatSize][newMatSize];
        for (int i = 0; i < AdjMat.length; i++) {
            System.arraycopy(AdjMat[i], 0, expandedMatrix[i], 0, AdjMat.length);
        }
        int decoyNodeId = AdjMat.length;
        int inEdgeNode = in_degree_edges.remove(0);
        int j = 0;
        for (; j < numOfDecoyesWithOneWeights; j++) {
            expandedMatrix[inEdgeNode][decoyNodeId] = 1;
            if (weightOfLastDecoy > 0) {
                decoyNodeId++;
                expandedMatrix[inEdgeNode][decoyNodeId] = 0.75;
                weightOfLastDecoy = 0;
                decoyesId.add(decoyNodeId);
            }
            if (!in_degree_edges.isEmpty()) {
                //System.out.println(in_degree_edges);
                inEdgeNode = in_degree_edges.remove(j);
            }
            decoyNodeId++;
            decoyesId.add(decoyNodeId);
        }
        return expandedMatrix;
    }

    /**
     * This method is used to retrieve the number of decoyes we used as it is set to 2
     * @return The number of face nodes that are used to disturbe the attackers from the real assets
     */
    public int getNumberOfDecoyes() {
        return numOfDecoyes;
    }

    /**
     * This method is used to retrieve the id of the decoy nodes
     * @return The id of the fake nodes
     */
    public ArrayList<Integer> getDecoyesId() {
        return decoyesId;
    }
}
