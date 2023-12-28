package CostFunction.org;

import java.util.List;

/**
 * This class is used to calculate the total cost that she loses if according to the given attck path
 */
public class CostFunction {
    private final double[][] adjMatrix;

    /**
     * This is the default constructor of the CostFunction class
     * @param adjMatrix The graph representation as adjacensy matrix
     */
    public CostFunction(double[][] adjMatrix) {
        if (adjMatrix == null) {
            throw new IllegalArgumentException("The matrix is null!");
        }
        this.adjMatrix = adjMatrix;
    }

    /**
     * This method is used to compute the cost
     * @param solution The attck path
     * @param lossVal The vector of the loss corresponding each asset
     * @return The total cost
     */
    public double computeCost(List<Integer> solution, double[] lossVal) {
        if (solution == null) {
            throw new IllegalArgumentException("The solution is null!");
        }
        if (lossVal == null) {
            throw new IllegalArgumentException("The loss vector is null!");
        }
        double cost = 0d;
        for (int i = 0; i < solution.size() - 1; i++) {
            int from = solution.get(i);
            int to = solution.get(i + 1);
            cost += Math.exp(-adjMatrix[from - 1][to - 1]) * lossVal[to - 1];
        }
        return cost;
    }
}
