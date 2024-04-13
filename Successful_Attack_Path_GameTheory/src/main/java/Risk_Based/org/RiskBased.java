package Risk_Based.org;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to perform the Risk Based Allocation.
 * <pre>
 *     Risk-Based Resource Allocation
 * </pre>
 */
public class RiskBased {
    private final double[][] AdjMat;

    public RiskBased(double[][] adjMat) {
        if (adjMat == null) {
            throw new IllegalArgumentException("The matrix is null!");
        }
        AdjMat = adjMat;
    }

    public ArrayList<Double> DefenseTopPath(List<Integer> path) {
        double sumOfRisks;
        ArrayList<Double> arrayOfRisks = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            int source = path.get(i);
            int destination = path.get(i + 1);
            arrayOfRisks.add(Math.exp(-AdjMat[source - 1][destination - 1]));
        }
        sumOfRisks = arrayOfRisks.stream().mapToDouble(i -> i).sum();
        ArrayList<Double> NormalizedProbs = (ArrayList<Double>) arrayOfRisks.stream().
                map(item -> item / sumOfRisks).collect(Collectors.toList());
        return NormalizedProbs;
    }

    public ArrayList<Double> DefenseTopPaths(ArrayList<List<Integer>> paths) {
        double sumOfRisks;
        ArrayList<Double> arrayOfRisks = new ArrayList<>();
        for (List<Integer> integers : paths) {
            for (int i = 0; i < integers.size() - 1; i++) {
                int source = integers.get(i);
                int destination = integers.get(i + 1);
                arrayOfRisks.add(Math.exp(-AdjMat[source - 1][destination - 1]));
            }
        }
        sumOfRisks = arrayOfRisks.stream().mapToDouble(i -> i).sum();
        ArrayList<Double> NormalizedProbs = (ArrayList<Double>) arrayOfRisks.stream().
                map(item -> item / sumOfRisks).collect(Collectors.toList());
        return NormalizedProbs;
    }
}
