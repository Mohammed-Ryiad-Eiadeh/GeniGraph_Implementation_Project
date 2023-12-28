package MainPackage.org;

import Attack_Defence_Graph.org.AttackDefenceGraph;
import Attack_Defence_Graph.org.Graph;
import Attack_Defence_Graph.org.GraphData;
import CostFunction.org.CostFunction;
import CurvesPlot.org.ConvergenceCurvePlot;
import Defender.org.Defenders;
import DijkestraAlgorithm.org.DijkAlgorithm;
import EvolutionaryOptimizers.org.GeneticAlgorithm;
import FitnessFunction.org.FitnessFunction;

/**
 * This class is used as the main class for the first scenario_equally Distribution
 */
public class MainFirstEquallyDistributionOnEdges {
    public static void main(String[] args) {
        // Select the test case or the graph; construct the defenders; construct the adjacensy matrix; display the graph.
        var task = new GraphData(Graph.HG1);
        var attackDefenceGraph = task.getAttackDefenceGraph();
        var AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);
        task.Display(AdjMat);

        // Select the attacker entry node and the asset (sink) node
        var entryNode = 1;  // VS node
        var assetNode = 7;  // Targetd node by the attacker

        // Costruct graph object to generate a set of paths from the start to the end node
        var graph = new AttackDefenceGraph(AdjMat);
        var population = graph.initialPopulation(entryNode, assetNode, 2000);

        // Compute and report the fitness scores of the intially generated paths
        var assetsLossValues = task.getNodeAssetsLossValues();
        System.out.print("The average fitness of all the initial solutions is : ");
        var FFun = new FitnessFunction(AdjMat);
        System.out.println(FFun.evaluateAllSolution(population));

        // Creat object from GA to optimize the intial paths
        var engin = new GeneticAlgorithm(AdjMat,
                population,
                0.2,
                0.2,
                0.2,
                2000);

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

        // Compute the top-1 path according to Dijkstra algorithm
        s = System.currentTimeMillis();
        var dijSolution = new DijkAlgorithm(AdjMat).DijkestraResult(entryNode, assetNode);
        e = System.currentTimeMillis();

        // Compute and report the fitness and the loss of the generated path by dijkstra
        System.out.println("\nDuration time of Dijkstra is : " + (e - s) + "\tms");
        System.out.println("Result of Dijkestra algorithm is " + dijSolution +
                "\t" + "with the score of : " + FFun.evaluateSolution(dijSolution) +
                "\t,And the value of loss if the attacker sucsses is : " + task.getValueOfLoss(dijSolution, assetsLossValues));

        // Set the spare budget of security resourcess for each defender
        Defenders.spareBudget_D1 = 1d;
        Defenders.spareBudget_D2 = 0.75d;
        Defenders.spareBudget_D3 = 0.5d;

        // This code segment is referred to allocate spare defenders investments on all edges equally
        var numOfEdges = task.getNumberOfEdges();
        var B1 = Defenders.spareBudget_D1 / numOfEdges;
        var B2 = Defenders.spareBudget_D2 / numOfEdges;
        var B3 = Defenders.spareBudget_D3 / numOfEdges;

        for (var i = 0; i < AdjMat.length; i++) {
            for (var j = 0; j < AdjMat[0].length; j++) {
                if (attackDefenceGraph[i][j].totalInvest() > 0) {
                    var edge = attackDefenceGraph[i][j];
                    edge.setInvest_D1(edge.addSpareInvestFor_D1(B1));
                    edge.setInvest_D2(edge.addSpareInvestFor_D2(B2));
                    edge.setInvest_D3(edge.addSpareInvestFor_D3(B3));
                    // The next statement won't make any difference here
                    // attackDefenceGraph[j][i] = attackDefenceGraph[i][j];
                }
            }
        }
        // Update the adjacensy matrix
        AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);

        // Compute and report the fitness and the reduction ratio of the path after the allocation
        var FF = new FitnessFunction(AdjMat);
        System.out.println("\nThe cost of the most potential path (was the most one)\t" + bestAttackPathByDE +
                "\tafter allocating the investments is : " + FF.evaluateSolution(bestAttackPathByDE));
        System.out.println("The fitness score of the most potential path (was the most one) for the attacker is minimized by : " + (FFun.evaluateSolution(bestAttackPathByDE) - FF.evaluateSolution(bestAttackPathByDE)));
        System.out.println("\nThe fitness relative percentage after the resource allocation process is : " + (Math.abs(FFun.evaluateSolution(bestAttackPathByDE) - FF.evaluateSolution(bestAttackPathByDE)) / (FFun.evaluateSolution(bestAttackPathByDE))) * 100 + " %");

        // Compute and report the cost and the reduction ratio after the allocation process
        var lossAfterResourseAllocation = new CostFunction(AdjMat);
        var costAfterRA = lossAfterResourseAllocation.computeCost(bestAttackPathByDE, assetsLossValues);
        System.out.println("\nThe cost function of GA after RA procedure is : " + costAfterRA);
        System.out.println("\nThe cost relative reduction percentage is : " + (Math.abs(costBeforRA - costAfterRA) / costBeforRA * 100) + " %");
    }
}
