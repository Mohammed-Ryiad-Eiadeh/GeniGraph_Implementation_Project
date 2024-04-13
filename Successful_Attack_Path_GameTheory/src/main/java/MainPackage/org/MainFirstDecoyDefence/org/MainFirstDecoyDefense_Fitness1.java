package MainPackage.org.MainFirstDecoyDefence.org;

import Attack_Defence_Graph.org.AttackDefenceGraph;
import Attack_Defence_Graph.org.Graph;
import Attack_Defence_Graph.org.GraphData;
import CostFunction.org.CostFunction;
import CurvesPlot.org.ConvergenceCurvePlot;
import Defender.org.Defenders;
import EvolutionaryOptimizers.org.GeneticAlgorithm;
import FitnessFunction.org.FitnessFunction_1;
import ProActiveDefense.org.ProactiveDefenseDecoy;

/**
 * This class is used as the main class for the first scenario of proactive decoy according to bahavioral defender upon the first fitness function
 */
public class MainFirstDecoyDefense_Fitness1 {
    public static void main(String[] args) {
        // Select the test case or the graph; construct the defenders; construct the adjacensy matrix; display the graph.
        var task = new GraphData(Graph.SCADA);
        var attackDefenceGraph = task.getAttackDefenceGraph();
        var AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);
        task.Display(AdjMat);

        // Select the attacker entry node and the asset (sink) node
        var entryNode = 1;  // VS node
        var assetNode = AdjMat.length;  // Targetd node by the attacker;

        // Set the spare budget of security resourcess for the first and the second defender
        Defenders.spareBudget_D1 = 1d;
        Defenders.spareBudget_D2 = 0.75d;

        // Apply decoy defense
        ProactiveDefenseDecoy proactiveDefenseDecoy = new ProactiveDefenseDecoy(AdjMat,
                Defenders.spareBudget_D1 + Defenders.spareBudget_D2);
        AdjMat = proactiveDefenseDecoy.generateDecoy(assetNode - 1);
        var numOfDecoys = proactiveDefenseDecoy.getNumberOfDecoyes();
        attackDefenceGraph = task.fromMatrix(AdjMat);

        // Costruct graph object to generate a set of paths from the start to the end node
        var graph = new AttackDefenceGraph(AdjMat);
        var population = graph.initialPopulation(entryNode, assetNode, 2000);

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
                100);

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
        var fitnessBeforeRA = FFun.evaluateSolution(engin.getBestCurrent());
        System.out.println("\nBest path is : " + bestAttackPathByDE +
                "\t" + "With the score of : " + fitnessBeforeRA +
                "\t,And the value of loss if the attacker sucsses is : " + task.getValueOfLoss(bestAttackPathByDE, assetsLossValues));
        var lossBeforeResourseAllocation = new CostFunction(AdjMat);
        var costBeforRA = lossBeforeResourseAllocation.computeCost(bestAttackPathByDE, assetsLossValues);
        System.out.println("\nThe cost function of GA before RA procedure is : " + costBeforRA);

        // Set the spare budget of security resourcess for the third defender
        Defenders.spareBudget_D3 = 0.5d;

        var B3 = Defenders.spareBudget_D3 / (bestAttackPathByDE.size() - 1);
        for (var i = 0; i < bestAttackPathByDE.size() - 1; i++) {
            var edge = attackDefenceGraph[bestAttackPathByDE.get(i) - 1][bestAttackPathByDE.get(i + 1) - 1];
            edge.setInvest_D3(edge.addSpareInvestFor_D3(B3));
            // The next statement won't make any difference
            // attackDefenceGraph[bestAttackPathByDE.get(i + 1) - 1][bestAttackPathByDE.get(i) - 1] = attackDefenceGraph[bestAttackPathByDE.get(i) - 1][bestAttackPathByDE.get(i + 1) - 1];
        }
        // Update the adjacensy matrix
        AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);

        // Compute and report the fitness and the reduction ratio of the path after the allocation
        var FF = new FitnessFunction_1(AdjMat);
        var fitnessAfterRA = FF.evaluateSolution(bestAttackPathByDE);
        System.out.println("\nThe cost of the most potential path (was the most one)\t" + bestAttackPathByDE +
                "\tafter allocating the investments is : " + fitnessAfterRA);
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

        System.out.println("\n\nCalculate totaly fitness and cost\n");

        var decoysId = proactiveDefenseDecoy.getDecoyesId();
        var totalFitness = 0d;

        var newAssetLossValues = new double[assetsLossValues.length + decoysId.size()];
        System.arraycopy(assetsLossValues, 0, newAssetLossValues, 0, assetsLossValues.length);
        for (int i = assetsLossValues.length; i < newAssetLossValues.length; i++) {
            newAssetLossValues[i] = assetsLossValues[assetNode - 1];
        }

        for (int decoyId = 0; decoyId < decoysId.size(); decoyId++) {
            // Costruct graph object to generate a set of paths from the start to the end node
            graph = new AttackDefenceGraph(AdjMat);
            population = graph.initialPopulation(entryNode, decoysId.get(decoyId), 2000);

            // Creat object from GA to optimize the intial paths
            engin = new GeneticAlgorithm(AdjMat,
                    population,
                    0.2,
                    0.2,
                    0.2,
                    100);
            engin.StartOptimization();

            bestAttackPathByDE = engin.getBestCurrent();

            var fitnessScore = FFun.evaluateSolution(engin.getBestCurrent());
            var cost = lossAfterResourseAllocation.computeCost(bestAttackPathByDE, newAssetLossValues);

            totalFitness += fitnessScore;
        }
        var fitnessAfter = (fitnessAfterRA + totalFitness) / (numOfDecoys + 1);
        System.out.println("Fitness Score Totaly is : " + Math.abs(fitnessBeforeRA - fitnessAfter) / fitnessBeforeRA * 100);
        System.out.println("Cost Totaly is : " + (Math.abs(costBeforRA - costAfterRA) / costBeforRA * 100));
    }
}
