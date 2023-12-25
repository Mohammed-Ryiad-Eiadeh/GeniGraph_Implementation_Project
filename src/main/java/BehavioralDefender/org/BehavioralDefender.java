package BehavioralDefender.org;

import java.util.Arrays;

/**
 * This class is used to apply the behavioral defender according to the prospect theory.
 * <pre>
 *     Behavioral and Game-Theoretic Security Investments in Interdependent Systems Modeled by Attack Graphs, Mustafa Abdallah et al, 2020
 * </pre>
 */
public class BehavioralDefender {
    private final double budget;
    private double[] edgeWeights;
    private final float alpha;

    /**
     * This constructer is used to creat an object of the behavioral defender
     * @param edgeWeights The initial weights of the edges among all attack paths
     * @param budget The spare budget that the defender would distribute on the edges
     * @param alpha The factor is nonlinear perception of probabilities in the probability weighting function
     */
    public BehavioralDefender(double[] edgeWeights, double budget, float alpha) {
        if (budget <= 0) {
            throw new IllegalStateException("The spare bidget must be positive integer");
        }
        if (alpha < 0 || alpha > 1) {
            throw new IllegalStateException("Alpha must be relied in 0 and 1");
        }
        this.edgeWeights = new double[edgeWeights.length];
        System.arraycopy(edgeWeights, 0, this.edgeWeights, 0, edgeWeights.length);
        this.budget = budget;
        this.alpha = alpha;
    }

    /**
     * This method is used to apply the resource allocation based on the prospect theory
     * @return The resource allocation according to the behavioral defenders
     */
    public double[] applyBehavioralDefendingForResourceAllocation() {
        double[] probabilityWeighting = new double[edgeWeights.length];
        Arrays.setAll(probabilityWeighting, i -> Math.exp(-Math.pow(-Math.log(Math.exp(-edgeWeights[i])), alpha)));  // Probability Weighting Function simulates the Prelec theory
        double sum  = Arrays.stream(probabilityWeighting).sum();
        Arrays.setAll(edgeWeights, i -> probabilityWeighting[i] / sum * budget);
        return edgeWeights;
    }
}

    