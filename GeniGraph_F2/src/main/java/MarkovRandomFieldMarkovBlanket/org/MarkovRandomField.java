package MarkovRandomFieldMarkovBlanket.org;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to capture the edges of the Markove Random Field (MRF) of the node in interest.
 * <pre>
 *     Markov Random Field Optimisation, Peter Orchard
 * </pre>
 */
public class MarkovRandomField {
    private final double[][] AdjMat;
    private final int distinationNode_ID;
    private final List<Integer> markovBlankedNodes;

    /**
     * This constructer is used to initiate an object that is used to retrieve edges covered by MRF.
     * @param adjMat The adjacency matrix of the given graph problem.
     * @param distinationNode_id The node of the inerest.
     */
    public MarkovRandomField(double[][] adjMat, int distinationNode_id) {
        if (adjMat == null) {
            throw new IllegalArgumentException("The matrix is null!");
        }
        if (distinationNode_id < 1) {
            throw new IllegalArgumentException("The sink node or the asset node must be positive number starting from 1 S.t: 1, 2, 3, 4, ..., V");
        }
        if (distinationNode_id > adjMat.length) {
            throw new IllegalArgumentException("The sink node or the asset node must not be a bove the last node we have in our system");
        }
        AdjMat = adjMat;
        distinationNode_ID = distinationNode_id;
        markovBlankedNodes = new ArrayList<>();
    }

    /**
     * This method is used to compute the MRF and display the covered edges
     */
    public void showMarkovBlanket() {
        for (int i = 0 ; i < AdjMat[distinationNode_ID - 1].length; i++) {
            if (AdjMat[distinationNode_ID - 1][i] > 0) {
                markovBlankedNodes.add(i + 1);
            }
        }
    }

    /**
     * This method is used to retrieve the covered edges by MRF
     * @return
     */
    public List<Integer> retrieveNodeOfBlanket() {
        return markovBlankedNodes;
    }
}
