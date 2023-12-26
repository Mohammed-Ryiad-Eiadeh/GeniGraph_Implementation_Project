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
import MinCut.MinCut;

import java.util.List;

/**
 * This class is used as the main class for the first scenario_min-cut
 */
public class MainFirstMinCut {
    public static void main(String[] args) {
        var task = new GraphData(Graph.AWS03);
        var attackDefenceGraph = task.getAttackDefenceGraph();
        var AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);
        task.Display(AdjMat);
        var graph = new AttackDefenceGraph(AdjMat);
        var assetsLossValues = task.getNodeAssetsLossValues();

        var entryNode = 1;   // VS node
        var assetNode = 42;   // Targetd node by the attacker

        var population = graph.initialPopulation(entryNode, assetNode, 2000);
        System.out.print("The average fitness of all the initial solutions is : ");
        var FFun = new FitnessFunction(AdjMat, assetsLossValues);
        System.out.println(FFun.evaluateAllSolution(population));

        var engin = new GeneticAlgorithm(AdjMat,
                assetsLossValues,
                population,
                0.2,
                0.2,
                0.6,
                2000);

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
        System.out.println("Result of Dijkestra algorithm is " + dijSolution + "\t" + "with the score of : " + FFun.evaluateSolution(dijSolution) + "\t,And the value of loss if the attacker sucsses is : " + task.getValueOfLoss(dijSolution, assetsLossValues) + "\n");

        /*System.out.println("The total size of probable paths between the star and end node is : " + engin.getLastGeneration().stream().distinct().count());
        var path_Score = new ArrayList<Paths_Scores>();
        engin.getLastGeneration().stream().distinct().forEach(x -> path_Score.add(new Paths_Scores(x, FFun.evaluateSolution(x))));
        path_Score.sort(Comparator.comparing(Paths_Scores::score).reversed());
        System.out.println("Please type the number of paths");
        var k = new Scanner(System.in).nextInt();
        System.out.println("These are the top " + k + " most potential attack paths:");
        IntStream.range(0, Math.min(k, path_Score.size())).forEach(x -> System.out.println(path_Score.get(x)));*/

        // Set the spare budget of security resourcess for each defender
        Defenders.spareBudget_D1 = 1d;
        Defenders.spareBudget_D2 = 0.75d;
        Defenders.spareBudget_D3 = 0.5d;

        // This code segment is referred to allocate spare defenders investments on paths involoved in Markov Blanket
        var minCut = new MinCut(AdjMat);
        var minCutEdges_S_D = minCut.getMinCutEgdes(entryNode, assetNode);
        var minCutEdges_D_S = minCut.getMinCutEgdes(assetNode, entryNode);
        var minCutEdges = minCutEdges_S_D.size() < minCutEdges_D_S.size() ? minCutEdges_S_D : minCutEdges_D_S;
        System.out.println("The min-cut edges who disjoint the graph are : ");
        minCutEdges.forEach(x -> System.out.println(x.GetStart() + "\t" + x.GetEnd()));

        var B1 = Defenders.spareBudget_D1 / minCutEdges.size();
        var B2 = Defenders.spareBudget_D2 / minCutEdges.size();
        var B3 = Defenders.spareBudget_D3 / minCutEdges.size();
        for (MinCut.MCEdge mcedge : minCutEdges) {
            var edge = attackDefenceGraph[mcedge.GetStart() - 1][mcedge.GetEnd() - 1];
            edge.setInvest_D1(edge.getInvest_D1() + edge.addSpareInvestFor_D1(B1));
            edge.setInvest_D2(edge.getInvest_D2() + edge.addSpareInvestFor_D2(B2));
            edge.setInvest_D3(edge.getInvest_D3() + edge.addSpareInvestFor_D3(B3));
            attackDefenceGraph[mcedge.GetEnd() - 1][mcedge.GetStart() - 1] = attackDefenceGraph[mcedge.GetStart() - 1][mcedge.GetEnd() - 1];
        }
        AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);
        var FF = new FitnessFunction(AdjMat, assetsLossValues);
        System.out.println("\nThe fitness of the most potential path (was the most one)\t" + bestAttackPathByDE + "\tafter allocating the investments is : " + FF.evaluateSolution(bestAttackPathByDE));
        System.out.println("The fitness score of the most potential path (was the most one) for the attacker is minimized by : " + (FFun.evaluateSolution(bestAttackPathByDE) - FF.evaluateSolution(bestAttackPathByDE)));
        System.out.println("The fitness relative percentage after the resource allocation process is : " + (Math.abs(FFun.evaluateSolution(bestAttackPathByDE) - FF.evaluateSolution(bestAttackPathByDE)) / (FFun.evaluateSolution(bestAttackPathByDE))) * 100 + " %");

        var lossAfterResourseAllocation = new CostFunction(AdjMat);
        var costAfterRA = lossAfterResourseAllocation.computeCost(bestAttackPathByDE, assetsLossValues);
        System.out.println("\nThe cost function of GA after RA procedure is : " + costAfterRA);
        System.out.println("The cost relative reduction percentage is : " + (Math.abs(costBeforRA - costAfterRA) / costBeforRA * 100) + " %");
    }

    /**
     * This record is used as a container for an individual path with its fitness score
     */
    record Paths_Scores(List<Integer> path, double score) { }
}