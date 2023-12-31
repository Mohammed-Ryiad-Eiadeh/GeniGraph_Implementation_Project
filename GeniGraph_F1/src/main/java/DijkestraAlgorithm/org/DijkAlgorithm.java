package DijkestraAlgorithm.org;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;

/**
 * This class is used to applay dijkestra algorithm an adjacency matrix which represents our attack defence graph
 * <pre>
 * A note on Two Problems in Connexion With Graphs, E.W.Dijkstra
 * </pre>
 */
public class DijkAlgorithm {
    private final double[][] adjMatrix;

    /**
     * This constructor is used to initialize the adjacency matrix that is going to be passed to dijkestra
     * @param adjMatrix The adjacency matrix which represents the attack-defence graph
     */
    public DijkAlgorithm(double[][] adjMatrix) {
        if (adjMatrix == null) {
            throw new IllegalArgumentException("The matrix is null!");
        }
        this.adjMatrix = adjMatrix;
    }

    /**
     * This method is used to call dijkestra algorithm
     * @param start The starting node of the path
     * @param end The distination node of the path
     * @return The path of the lowest cost (based on weight)
     */
    public List<Integer> DijkestraResult(int start, int end) {
        if (start < 1) {
            throw new IllegalArgumentException("The attackers' entrey node must be positive number starting from 1 S.t: 1, 2, 3, 4, ..., V");
        }
        if (end < 1) {
            throw new IllegalArgumentException("The sink node or the asset node must be positive number starting from 1 S.t: 1, 2, 3, 4, ..., V");
        }
        if (start > adjMatrix.length) {
            throw new IllegalArgumentException("The attackers' entrey node must not be a bove the last node we have in our system");
        }
        if (end > adjMatrix.length) {
            throw new IllegalArgumentException("The sink node or the asset node must not be a bove the last node we have in our system");
        }
        DefaultUndirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);
        double[][] Data = this.adjMatrix;
        for (int node = 0; node < Data[0].length; node++) {
            graph.addVertex(node);
        }
        for (int node = 0; node < Data.length; node++) {
            for (int nod = 0; nod < Data[0].length; nod++) {
                if (Data[node][nod] > 0) {
                    graph.addEdge(node, nod);
                    graph.setEdgeWeight(node, nod, Data[node][nod]);
                }
            }
        }
        DijkstraShortestPath<Integer, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        List<Integer> nodes = dijkstraShortestPath.getPath(start - 1, end - 1).getVertexList();
        return nodes.stream().map(i -> i + 1).toList();
    }
}
