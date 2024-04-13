package FitnessFunction.org;

import java.util.List;
import java.util.stream.IntStream;

public class FitnessFunction_2 extends FitnessFunction_1 {
    private final double[][] adjMatrix;
    private double[] assetLosses;

    /**
     * This constructor is used to initialize an object as an extention to the first fitness function since it considers further the loss of the asset
     * @param adjMatrix The adjacency matrix of the given graph
     */
    public FitnessFunction_2(double[][] adjMatrix, double[] assetLosses) {
        super(adjMatrix);
        if (assetLosses == null) {
            throw new IllegalArgumentException("The asset vector is null!");
        }
        this.adjMatrix = new double[adjMatrix.length][adjMatrix[0].length];
        IntStream
                .range(0, adjMatrix.length)
                .forEach(i -> System.arraycopy(adjMatrix[i], 0, this.adjMatrix[i], 0, adjMatrix[i].length));
        this.assetLosses = new double[assetLosses.length];
        System.arraycopy(assetLosses, 0, this.assetLosses, 0, this.assetLosses.length);
    }

    /**
     * This method is used to assest an individual solution at a time
     * @param solution The solution to be evaluated
     * @return The fitness score
     */
    @Override
    public double evaluateSolution(List<Integer> solution) {
        if (solution == null) {
            throw new IllegalArgumentException("The solution is null");
        }
        double totalCcost = 0d;
        double totalLoss = 0d;
        for (int i = 0; i < solution.size() - 1; i++) {
            int from = solution.get(i);
            int to = solution.get(i + 1);
            totalCcost += adjMatrix[from - 1][to - 1];
        }
        for (int node : solution) {
            totalLoss += assetLosses[node - 1];
        }
        return Math.exp(-totalCcost) + /*weight factor*/ 0.001 * totalLoss;
    }
}
