package DijkestraAlgorithm.org;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is used to applay dijkestra algorithm an adjacency matrix which represents our attack defence graph
 */
public class DijkAlgorithm {
    private final double[][] adjMatrix;

    /**
     * This constructor is used to initialize the adjacency matrix that is going to be passed to dijkestra
     * @param adjMatrix The adjacency matrix which represents the attack-defence graph
     */
    public DijkAlgorithm(double[][] adjMatrix) {
        this.adjMatrix = adjMatrix;
    }

    /**
     * This method is used to call dijkestra algorithm
     * @param start The starting node of the path
     * @param end The distination node of the path
     * @return The path of the lowest cost (based on weight)
     */
    public List<Integer> DijkestraResult(int start, int end) {
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
