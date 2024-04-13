package MinCut;

import org.jgrapht.Graph;
import org.jgrapht.alg.flow.DinicMFImpl;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.Set;

/**
 * This class is used to calculate the min-cut edges of the given graph
 * <pre>
 * Dinitz’ Algorithm: The Original Version and Even’s Version, Yefim Dinitz
 * </pre>
 */
public class MinCut {
    /**
     * This inner class is used to set the start and the end node of each edge
     */
    public class MCEdge {
        private final int start;
        private final int end;

        /**
         * This constructor is used to creat an object holds the start and the end nodes of an edge
         * @param start The start node of the given edge
         * @param end The end node of the given edge
         */
        public MCEdge(int start, int end) {
            this.start = start;
            this.end = end;
        }

        /**
         * This method is used to retrieve the start node of a given edge
         * @return The start node
         */
        public int GetStart() {
            return start;
        }

        /**
         * This method is used to retrieve the end node of a given edge
         * @return The end node
         */
        public int GetEnd() {
            return end;
        }
    }

    private final double[][] adjMat;

    /**
     * This constructer is used to creat an object to calculate the min-cut algorithm
     * @param AdjMatrix The adjacency matrix of the given graph problem.
     */
    public MinCut(double[][] AdjMatrix) {
        if (AdjMatrix == null) {
            throw new IllegalArgumentException("The matrix is null!");
        }
        this.adjMat = AdjMatrix;
    }

    /**
     * This method is used to calculate the min-cut edges
     * @param source The start node
     * @param asset The end node
     * @return The min-cut edges
     */
    public ArrayList<MCEdge> getMinCutEgdes(int source, int asset) {
        if (source < 1) {
            throw new IllegalArgumentException("The attackers' entrey node must be positive number starting from 1 S.t: 1, 2, 3, 4, ..., V");
        }
        if (asset < 1) {
            throw new IllegalArgumentException("The sink node or the asset node must be positive number starting from 1 S.t: 1, 2, 3, 4, ..., V");
        }
        if (source > adjMat.length) {
            throw new IllegalArgumentException("The attackers' entrey node must not be a bove the last node we have in our system");
        }
        if (asset > adjMat.length) {
            throw new IllegalArgumentException("The sink node or the asset node must not be a bove the last node we have in our system");
        }
        Graph<Integer, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        for (var i = 0; i < adjMat.length; i++) {
            graph.addVertex(i + 1);
        }
        for (var i = 0; i < adjMat.length; i++) {
            for (var j = 0; j < adjMat[0].length; j++) {
                if (adjMat[i][j] > 0 || adjMat[j][i] > 0)
                    graph.addEdge(i + 1, j + 1);
            }
        }
        DinicMFImpl<Integer, DefaultEdge> minCutAlg = new DinicMFImpl<>(graph);
        minCutAlg.calculateMinCut(source, asset);
        Set<DefaultEdge> minCutEdges = minCutAlg.getCutEdges();
        ArrayList<DefaultEdge> list = new ArrayList<>(minCutEdges);
        ArrayList<MCEdge> listEdges = new ArrayList<>();
        list.forEach(edge -> {
            String edgeString = edge.toString();
            String[] vertices = edgeString.substring(1, edgeString.length() - 1).split(" : ");
            listEdges.add(new MCEdge(Integer.parseInt(vertices[0]), Integer.parseInt(vertices[1])));
        });
        return listEdges;
    }
}