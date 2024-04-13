package MainPackage.org.MainSecond.org;

import Attack_Defence_Graph.org.AttackDefenceGraph;
import Attack_Defence_Graph.org.Graph;
import Attack_Defence_Graph.org.GraphData;
import CostFunction.org.CostFunction;
import CurvesPlot.org.ConvergenceCurvePlot;
import Defender.org.Defenders;
import EvolutionaryOptimizers.org.GeneticAlgorithm;
import FitnessFunction.org.FitnessFunction_1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class is used as the main class for the second scenario upon the first fitness function
 */
public class MainSecond_Fitness1 {
    public static void main(String[] args) {
        // Select the test case or the graph; construct the defenders; construct the adjacensy matrix; display the graph.
        var task = new GraphData(Graph.AWS03_rand);
        var attackDefenceGraph = task.getAttackDefenceGraph();
        var AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);
        task.Display(AdjMat);

        // Select the attacker entry node and the asset (sink) node
        var entryNode = 1;  // VS node
        var assetNode = 42;  // Targetd node by the attacker

        // Costruct graph object to generate a set of paths from the start to the end node
        var graph = new AttackDefenceGraph(AdjMat);
        var population = graph.initialPopulation(entryNode, assetNode, 3000);

        // Compute and report the fitness scores of the intially generated paths
        var assetsLossValues = task.getNodeAssetsLossValues();
        System.out.print("The average fitness of all the initial solutions is : ");
        var FFun = new FitnessFunction_1(AdjMat);
        System.out.println(FFun.evaluateAllSolution(population));

        // Creat object from GA to optimize the intial paths
        var engin = new GeneticAlgorithm(AdjMat,
                population,
                0.2,
                0.2,
                0.2,
                50);

        var s = System.currentTimeMillis();
        engin.StartOptimization();
        var e = System.currentTimeMillis();
        System.out.println("\nDuration time of GA is : " + (e - s) + "\tms");

        // Store the fitness scores to display the convergence curve (indicates the performance of GA)
        new ConvergenceCurvePlot(engin.getConvergenceCurve()).ShowPlot();

        // Compute and display the avg fitness score of all population
        System.out.print("\nThe average fitness of all the last generation is : ");
        System.out.print(FFun.evaluateAllSolution(engin.getLastGeneration()));
        var bestAttackPathByDE = engin.getBestCurrent();
        System.out.println("\nBest path is : " + bestAttackPathByDE +
                "\t" + "With the score of : " + FFun.evaluateSolution(engin.getBestCurrent()) +
                "\t, And the value of loss if the attacker sucsses is : " + task.getValueOfLoss(bestAttackPathByDE, assetsLossValues));

        // Report the size of all possible paths between the start and the end node
        System.out.println("The total size of probable paths between the star and end node is : " +
                engin.getLastGeneration().stream().distinct().count());
        var path_Score = new ArrayList<Paths_Scores>();
        engin.getLastGeneration().stream().distinct().forEach(x -> path_Score.add(new Paths_Scores(x, FFun.evaluateSolution(x))));
        path_Score.sort(Comparator.comparing(Paths_Scores::score).reversed());

        // Type the number of paths (top-k) that you want to allocate the resources over, and display them
        System.out.println("\nPlease type the number of paths");
        var k = new Scanner(System.in).nextInt();
        System.out.println("These are the top " + k + " most potential attack paths:");
        IntStream.range(0, Math.min(k, path_Score.size())).forEach(x -> System.out.println(path_Score.get(x)));

        // Compute and display each solution of the number of paths with their avg fitness score (sorted)
        var costFunctionBeforeRA = new CostFunction(AdjMat);
        var sumFitnessBeforeRA = 0d;
        var sumCostBeforeRA = 0d;
        var sumOfLoss = 0d;
        for (var i = 0; i < Math.min(k, path_Score.size()); i++) {
            sumFitnessBeforeRA += path_Score.get(i).score;
            sumCostBeforeRA += costFunctionBeforeRA.computeCost(path_Score.get(i).path, assetsLossValues);
            sumOfLoss += task.getValueOfLoss(path_Score.get(i).path, assetsLossValues);
        }
        var avgFitnessBeforeRA = sumFitnessBeforeRA / Math.min(k, path_Score.size());
        var avgCostBeforeRA = sumCostBeforeRA / Math.min(k, path_Score.size());
        System.out.println("\nThe average fitness of top " + k + " before RA procedure is : " + avgFitnessBeforeRA);
        System.out.println("\nThe average cost of top " + k + " before RA procedure is : " + avgCostBeforeRA);
        System.out.println("\nThe average of the loss is : " + sumOfLoss / Math.min(k, path_Score.size()));

        // Type the number of solutions top-k that you want to make your allocation decision based
        System.out.println("\nPlease type the number of paths");
        var numOfAttackers = Math.min(new Scanner(System.in).nextInt(), k);
        var listOfAttackers = IntStream.range(0, numOfAttackers).
                mapToObj(i -> path_Score.get(i).path).
                collect(Collectors.toCollection(ArrayList::new));

        // Set the spare budget of security resourcess for each defender
        Defenders.spareBudget_D1 = 4.5d;
        Defenders.spareBudget_D2 = 3.375d;
        Defenders.spareBudget_D3 = 2.25d;

        // This code segment is referred to allocate spare defenders investments (equally) on the top-k paths (same time attacks)
        var B1 = Defenders.spareBudget_D1 / listOfAttackers.size();
        var B2 = Defenders.spareBudget_D2 / listOfAttackers.size();
        var B3 = Defenders.spareBudget_D3 / listOfAttackers.size();
        for (var listOfAttacker : listOfAttackers) {
            for (var i = 0; i < listOfAttacker.size() - 1; i++) {
                var edge = attackDefenceGraph[listOfAttacker.get(i) - 1][listOfAttacker.get(i + 1) - 1];
                edge.setInvest_D1(edge.addSpareInvestFor_D1(B1 / (listOfAttacker.size() - 1)));
                edge.setInvest_D2(edge.addSpareInvestFor_D2(B2 / (listOfAttacker.size() - 1)));
                edge.setInvest_D3(edge.addSpareInvestFor_D3(B3 / (listOfAttacker.size() - 1)));
                // The next statement won't make any difference
                // attackDefenceGraph[listOfAttacker.get(i + 1) - 1][listOfAttacker.get(i) - 1] = attackDefenceGraph[listOfAttacker.get(i) - 1][listOfAttacker.get(i + 1) - 1];
            }
        }
        // Update the adjacensy matrix
        AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);

        // Compute the avg fitness and the avg cost and the reduction ratios of these paths after the allocation
        var FF = new FitnessFunction_1(AdjMat);
        var CC = new CostFunction(AdjMat);
        var sumOfFitnessAfterRA = 0d;
        var sumOfRelativeReductions = 0d;
        var sumOfCostAfterRA = 0d;
        var sumOfRelativeCostReduction = 0d;
        for (List<Integer> listOfAttacker : listOfAttackers) {
            System.out.println("For this path : " + listOfAttacker +
                    "\nPath score before resource allocation is : " + FFun.evaluateSolution(listOfAttacker) +
                    "\nand after allocation is : " + FF.evaluateSolution(listOfAttacker) +
                    "\nand the different between before and after allocation is : " +
                    (FFun.evaluateSolution(listOfAttacker) - FF.evaluateSolution(listOfAttacker)) +
                    "\nThe improvement percentage after the resource allocation process is : " +
                    (Math.abs(FFun.evaluateSolution(listOfAttacker) - FF.evaluateSolution(listOfAttacker)) /
                            (FFun.evaluateSolution(listOfAttacker))) * 100 +
                    "\nAnd the value of loss if the attacker sucsses is : " +
                    task.getValueOfLoss(listOfAttacker, assetsLossValues) + "\n");
            sumOfFitnessAfterRA += FF.evaluateSolution(listOfAttacker);
            sumOfRelativeReductions += (Math.abs(FFun.evaluateSolution(listOfAttacker) -
                    FF.evaluateSolution(listOfAttacker)) / (FFun.evaluateSolution(listOfAttacker))) * 100;
            sumOfCostAfterRA += CC.computeCost(listOfAttacker, assetsLossValues);
            sumOfRelativeCostReduction += (Math.abs(costFunctionBeforeRA.computeCost(listOfAttacker, assetsLossValues) -
                    CC.computeCost(listOfAttacker, assetsLossValues)) /
                    costFunctionBeforeRA.computeCost(listOfAttacker, assetsLossValues) * 100);
        }

        // Report the avg fitness and the avg cost and the reduction ratios of these paths after the allocation
        System.out.println("The average fitness of top " + k + " after resource allocation is : " +
                sumOfFitnessAfterRA / listOfAttackers.size());
        System.out.println("\nThe fitness relative reduction percentage is : " +
                sumOfRelativeReductions / listOfAttackers.size() + " %");
        System.out.println("\nThe average cost of top " + k + " after RA procedure is : " +
                sumOfCostAfterRA / listOfAttackers.size());
        System.out.println("\nThe cost relative reduction percentage is : " +
                sumOfRelativeCostReduction / listOfAttackers.size() + " %");
    }

    /**
     * This record is used as a container for an individual path with its fitness score
     */
    record Paths_Scores(List<Integer> path, double score) { }
}

