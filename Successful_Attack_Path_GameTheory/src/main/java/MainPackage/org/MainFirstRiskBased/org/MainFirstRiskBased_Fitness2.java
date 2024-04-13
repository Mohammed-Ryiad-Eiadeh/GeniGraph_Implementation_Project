package MainPackage.org.MainFirstRiskBased.org;

import Attack_Defence_Graph.org.AttackDefenceGraph;
import Attack_Defence_Graph.org.Graph;
import Attack_Defence_Graph.org.GraphData;
import CostFunction.org.CostFunction;
import CurvesPlot.org.ConvergenceCurvePlot;
import Defender.org.Defenders;
import EvolutionaryOptimizers.org.GeneticAlgorithm;
import FitnessFunction.org.FitnessFunction_2;
import Risk_Based.org.RiskBased;

/**
 * This class is used as the main class for the first scenario upon the second fitness function
 */
public class MainFirstRiskBased_Fitness2 {
    public static void main(String[] args) {
        // Select the test case or the graph; construct the defenders; construct the adjacensy matrix; display the graph.
        var task = new GraphData(Graph.e_commerce);
        var attackDefenceGraph = task.getAttackDefenceGraph();
        var AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);
        task.Display(AdjMat);

        // Select the attacker entry node and the asset (sink) node
        var entryNode = 1;  // VS node
        var assetNode = 20;  // Targetd node by the attacker;

        // Costruct graph object to generate a set of paths from the start to the end node
        var graph = new AttackDefenceGraph(AdjMat);
        var population = graph.initialPopulation(entryNode, assetNode, 3000);

        // Compute and report the fitness scores of the intially generated paths
        var assetsLossValues = task.getNodeAssetsLossValues();
        System.out.print("The average fitness of all the initial solutions is : ");
        var FFun = new FitnessFunction_2(AdjMat, assetsLossValues);
        System.out.println(FFun.evaluateAllSolution(population));

        // Creat object from GA to optimize the intial paths
        var engin = new GeneticAlgorithm(AdjMat,
                population,
                0.2,
                0.2,
                0.2,
                500);

        var s = System.currentTimeMillis();
        engin.StartOptimization();
        var e = System.currentTimeMillis();
        System.out.println("\nDuration time of GA is : " + (e - s) + "\tms");

        // Store the fitness scores to display the convergence curve (indicates the performance of GA)
        new ConvergenceCurvePlot(engin.getConvergenceCurve()).ShowPlot();

        // Report the avg fitness scores of the last generation; get best (top-1) path
        // And compute its proparties like the loss and cost before the allocation process, ... etc
        System.out.print("\nThe average fitness of all the last generation is : ");
        System.out.print(FFun.evaluateAllSolution(engin.getLastGeneration()));
        var bestAttackPathByDE = engin.getBestCurrent();
        System.out.println("\nBest path is : " + bestAttackPathByDE +
                "\t" + "With the score of : " + FFun.evaluateSolution(engin.getBestCurrent()) +
                "\t,And the value of loss if the attacker sucsses is : " + task.getValueOfLoss(bestAttackPathByDE, assetsLossValues));
        var lossBeforeResourseAllocation = new CostFunction(AdjMat);
        var costBeforRA = lossBeforeResourseAllocation.computeCost(bestAttackPathByDE, assetsLossValues);
        System.out.println("\nThe cost function of GA before RA procedure is : " + costBeforRA);

        // Set the spare budget of security resourcess for each defender
        Defenders.spareBudget_D1 = 10d;
        Defenders.spareBudget_D2 = 7.5d;
        Defenders.spareBudget_D3 = 5d;

        var B1 = Defenders.spareBudget_D1;
        var B2 = Defenders.spareBudget_D2;
        var B3 = Defenders.spareBudget_D3;

        // apply the proactive defence
        RiskBased proactiveDefence = new RiskBased(AdjMat);
        var edgeCuts = proactiveDefence.DefenseTopPath(bestAttackPathByDE);

        for (var i = 0; i < bestAttackPathByDE.size() - 1; i++) {
            var edge = attackDefenceGraph[bestAttackPathByDE.get(i) - 1][bestAttackPathByDE.get(i + 1) - 1];
            var currentEdgeCut = edgeCuts.get(i);
            edge.setInvest_D1(edge.addSpareInvestFor_D1(B1 * currentEdgeCut));
            edge.setInvest_D2(edge.addSpareInvestFor_D2(B2 * currentEdgeCut));
            edge.setInvest_D3(edge.addSpareInvestFor_D3(B3 * currentEdgeCut));
            // The next statement won't make any difference
            // attackDefenceGraph[bestAttackPathByDE.get(i + 1) - 1][bestAttackPathByDE.get(i) - 1] = attackDefenceGraph[bestAttackPathByDE.get(i) - 1][bestAttackPathByDE.get(i + 1) - 1];
        }
        // Update the adjacensy matrix
        AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);

        // Compute and report the fitness and the reduction ratio of the path after the allocation
        var FF = new FitnessFunction_2(AdjMat, assetsLossValues);
        System.out.println("\nThe cost of the most potential path (was the most one)\t" + bestAttackPathByDE +
                "\tafter allocating the investments is : " + FF.evaluateSolution(bestAttackPathByDE));
        System.out.println("The fitness score of the most potential path (was the most one) for the attacker is minimized by : " +
                (FFun.evaluateSolution(bestAttackPathByDE) - FF.evaluateSolution(bestAttackPathByDE)));
        System.out.println("\nThe fitness relative percentage after the resource allocation process is : " +
                (Math.abs(FFun.evaluateSolution(bestAttackPathByDE) - FF.evaluateSolution(bestAttackPathByDE))
                        / (FFun.evaluateSolution(bestAttackPathByDE))) * 100 + " %");

        // Compute and report the cost and the reduction ratio after the allocation process
        var lossAfterResourseAllocation = new CostFunction(AdjMat);
        var costAfterRA = lossAfterResourseAllocation.computeCost(bestAttackPathByDE, assetsLossValues);
        System.out.println("\nThe cost function of GA after RA procedure is : " + costAfterRA);
        System.out.println("\nThe cost relative reduction percentage is : " +
                (Math.abs(costBeforRA - costAfterRA) / costBeforRA * 100) + " %");
    }
}
