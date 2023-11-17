package DijkestraAlgorithm.org;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        this.adjMatrix = adjMatrix;
    }

    /**
     * This method is used to call dijkestra algorithm
     * @param start The starting node of the path
     * @param end The distination node of the path
     * @return The path of the lowest cost (based on weight)
     */
    public List<Integer> DijkestraResult(int start, int end) {
        if (adjMatrix[0].length < end) {
            throw new IllegalStateException("No node matchs the end node");
        }
        SingleGraph graph = new SingleGraph("graph");
        double[][] Data = this.adjMatrix;
        for (int node = 0; node < Data[0].length; node++) {
            graph.addNode(node + "");
        }
        for (int node = 0; node < Data.length; node++) {
            for (int nod = 0; nod < Data[0].length; nod++) {
                if (Data[node][nod] > 0) {
                    graph.addEdge(node + "" + new Random().nextDouble(Integer.MAX_VALUE) + nod, node, nod, true).setAttribute("cost", Data[node][nod]);
                }
            }
        }
        Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "cost");
        dijkstra.init(graph);
        dijkstra.setSource(graph.getNode(start - 1 + ""));
        dijkstra.compute();
        return new ArrayList<>(dijkstra.getPath(graph.getNode(end - 1 + "")).nodes().toList().stream().map(x -> Integer.parseInt(x.toString()) + 1).toList());
    }
}
