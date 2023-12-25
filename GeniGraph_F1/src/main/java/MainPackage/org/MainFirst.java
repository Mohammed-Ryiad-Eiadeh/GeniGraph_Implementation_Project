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

import java.util.List;

/**
 * This class is used as the main class for the first scenario
 */
public class MainFirst {
    public static void main(String[] args) {
        var task = new GraphData(Graph.HG1);
        var attackDefenceGraph = task.getAttackDefenceGraph();
        var AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);
        task.Display(AdjMat);
        var graph = new AttackDefenceGraph(AdjMat);
        var assetsLossValues = task.getNodeAssetsLossValues();

        var entryNode = 1;   // VS node
        var assetNode = 7;   // Targetd node by the attacker

        var population = graph.initialPopulation(entryNode, assetNode, 100);
        System.out.print("The average fitness of all the initial solutions is : ");
        var FFun = new FitnessFunction(AdjMat);
        System.out.println(FFun.evaluateAllSolution(population));

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

        new ConvergenceCurvePlot(engin.getConvergenceCurve()).ShowPlot();

        System.out.print("\nThe average fitness of all the last generation is : ");
        System.out.print(FFun.evaluateAllSolution(engin.getLastGeneration()));
        var bestAttackPathByDE = engin.getBestCurrent();
        System.out.println("\nBest path is : " + bestAttackPathByDE + "\t" + "With the score of : " + FFun.evaluateSolution(engin.getBestCurrent()) + "\t,And the value of loss if the attacker sucsses is : " + task.getValueOfLoss(bestAttackPathByDE, assetsLossValues));

        var lossBeforeResourseAllocation = new CostFunction(AdjMat);
        var costBeforRA = lossBeforeResourseAllocation.computeCost(bestAttackPathByDE, assetsLossValues);
        System.out.println("\nThe cost function of GA before RA procedure is : " + costBeforRA);

        s = System.currentTimeMillis();
        var dijSolution = new DijkAlgorithm(AdjMat).DijkestraResult(entryNode, assetNode);
        e = System.currentTimeMillis();

        System.out.println("\nDuration time of Dijkstra is : " + (e - s) + "\tms");
        System.out.println("Result of Dijkestra algorithm is " + dijSolution + "\t" + "with the score of : " + FFun.evaluateSolution(dijSolution) + "\t,And the value of loss if the attacker sucsses is : " + task.getValueOfLoss(dijSolution, assetsLossValues));

        /*System.out.println("\nThe total size of probable paths between the star and end node is : " + engin.getLastGeneration().stream().distinct().count());
        var path_Score = new ArrayList<Paths_Scores>();
        engin.getLastGeneration().stream().distinct().forEach(x -> path_Score.add(new Paths_Scores(x, FFun.evaluateSolution(x))));
        path_Score.sort(Comparator.comparing(Paths_Scores::score).reversed());
        System.out.println("Please type the number of paths");
        var k = new Scanner(System.in).nextInt();
        System.out.println("These are the top " + k + " most potential attack paths:");
        IntStream.range(0, Math.min(k, path_Score.size())).forEach(x -> System.out.println(path_Score.get(x)));*/

        // This code segment is referred to allocate spare defenders investments on the most potential path (top-1)
        var B1 = Defenders.spareBudget_D1 / (bestAttackPathByDE.size() - 1);
        var B2 = Defenders.spareBudget_D2 / (bestAttackPathByDE.size() - 1);
        var B3 = Defenders.spareBudget_D3 / (bestAttackPathByDE.size() - 1);
        for (var i = 0; i < bestAttackPathByDE.size() - 1; i++) {
            var edge = attackDefenceGraph[bestAttackPathByDE.get(i) - 1][bestAttackPathByDE.get(i + 1) - 1];
            edge.setInvest_D1(edge.getInvest_D1() + edge.addSpareInvestFor_D1(B1));
            edge.setInvest_D2(edge.getInvest_D2() + edge.addSpareInvestFor_D2(B2));
            edge.setInvest_D3(edge.getInvest_D3() + edge.addSpareInvestFor_D3(B3));
            // The next statement won't make any difference
            // attackDefenceGraph[bestAttackPathByDE.get(i + 1) - 1][bestAttackPathByDE.get(i) - 1] = attackDefenceGraph[bestAttackPathByDE.get(i) - 1][bestAttackPathByDE.get(i + 1) - 1];
        }
        AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);
        var FF = new FitnessFunction(AdjMat);
        System.out.println("\nThe cost of the most potential path (was the most one)\t" + bestAttackPathByDE + "\tafter allocating the investments is : " + FF.evaluateSolution(bestAttackPathByDE));
        System.out.println("The fitness score of the most potential path (was the most one) for the attacker is minimized by : " + (FFun.evaluateSolution(bestAttackPathByDE) - FF.evaluateSolution(bestAttackPathByDE)));
        System.out.println("\nThe fitness relative percentage after the resource allocation process is : " + (Math.abs(FFun.evaluateSolution(bestAttackPathByDE) - FF.evaluateSolution(bestAttackPathByDE)) / (FFun.evaluateSolution(bestAttackPathByDE))) * 100 + " %");

        var lossAfterResourseAllocation = new CostFunction(AdjMat);
        var costAfterRA = lossAfterResourseAllocation.computeCost(bestAttackPathByDE, assetsLossValues);
        System.out.println("\nThe cost function of GA after RA procedure is : " + costAfterRA);
        System.out.println("\nThe cost relative reduction percentage is : " + (Math.abs(costBeforRA - costAfterRA) / costBeforRA * 100) + " %");
    }

    /**
     * This record is used as a container for an individual path with its fitness score
     */
    record Paths_Scores(List<Integer> path, double score) { }
}