package FitnessFunction.org;

import java.util.List;
import java.util.stream.IntStream;

/**
 * This class is used to calculate the fitness score of the solution(s)
 */
public class FitnessFunction {
    private final double[][] adjMatrix;

    /**
     * This constructor is used to initialize the adjacency matrix of the attack-defence graph
     * @param adjMatrix The adjacency matrix of the given graph
     */
    public FitnessFunction(double[][] adjMatrix) {
        if (adjMatrix == null) {
            throw new IllegalArgumentException("The matrix is null!");
        }
        this.adjMatrix = new double[adjMatrix.length][adjMatrix[0].length];
        IntStream.range(0, adjMatrix.length).forEach(i -> System.arraycopy(adjMatrix[i], 0, this.adjMatrix[i], 0, adjMatrix[i].length));
    }

    /**
     * This method is used to assest an individual solution at a time
     * @param solution The solution to be evaluated
     * @return The fitness score
     */
    public double evaluateSolution(List<Integer> solution) {
        if (solution == null) {
            throw new IllegalArgumentException("The solution is null");
        }
        double totalCcost = 0d;
        for (int i = 0; i < solution.size() - 1; i++) {
            int from = solution.get(i);
            int to = solution.get(i + 1);
            totalCcost += adjMatrix[from - 1][to - 1];
        }
        return Math.exp(-totalCcost);
    }

    /**
     * This method is used to assest a set of solutions at a time
     * @param setOfSolutions The set of paths to be evaluated
     * @return The average fitness score of all solutions in this set
     */
    public double evaluateAllSolution(List<List<Integer>> setOfSolutions) {
        if (setOfSolutions == null) {
            throw new IllegalArgumentException("The fitnes scores holder is empty");
        }
        double cost = setOfSolutions.stream().mapToDouble(this::evaluateSolution).sum();
        return cost / setOfSolutions.size();
    }
}
