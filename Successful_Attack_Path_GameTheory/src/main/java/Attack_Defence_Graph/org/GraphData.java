package Attack_Defence_Graph.org;

import Defender.org.Defenders;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * This class is used to read different samples attack-defence graph
 */
public class GraphData {
    private final Graph graphId;
    private Defenders[][] defenders;
    private int numOfEdges;

    /**
     * This constructor is used to construct a matrix of defenders according to the given data
     * @param graphId The id of the demanded graph
     */
    public GraphData(Graph graphId) {
        this.graphId = graphId;
        try (Scanner resource = new Scanner(new File(String.valueOf(graphId.getPath())))) {
            String[] lines = resource.nextLine().split("\s");
            int numOfNodes = Integer.parseInt(lines[0]);
            defenders = new Defenders[numOfNodes][numOfNodes];
            while (resource.hasNext()) {
                String edge = resource.nextLine().trim();
                String[] twoVertex = edge.split("\s");
                switch (twoVertex.length) {
                    case 2 -> {
                        defenders[Integer.parseInt(twoVertex[0]) - 1][Integer.parseInt(twoVertex[1]) - 1] = new Defenders(0, 1, 0);
                        defenders[Integer.parseInt(twoVertex[1]) - 1][Integer.parseInt(twoVertex[0]) - 1] = new Defenders(0, 1, 0);
                    }
                    case 3 -> {
                        defenders[Integer.parseInt(twoVertex[0]) - 1][Integer.parseInt(twoVertex[1]) - 1] = new Defenders(0, Double.parseDouble(twoVertex[2]), 0);
                        defenders[Integer.parseInt(twoVertex[1]) - 1][Integer.parseInt(twoVertex[0]) - 1] = new Defenders(0, Double.parseDouble(twoVertex[2]), 0);
                    }
                }
            }
            for (int r = 0; r < defenders.length; r++) {
                for (int c = 0; c < defenders[0].length; c++) {
                    if (defenders[r][c] == null) {
                        defenders[r][c] = new Defenders(0, 0, 0);
                    }
                }
            }
            Arrays.stream(defenders)
                    .forEach(defender -> IntStream.range(0, defenders[0].length).filter(col -> defender[col].totalInvest() > 0)
                            .forEach(col -> numOfEdges++));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is used to return the defenders' matrix corresponding to the given graph
     * @return The wanted graph as matrix of defenders
     */
    public Defenders[][] getAttackDefenceGraph() {
        return defenders;
    }

    /**
     * This method is used to retrieve the number of edges in the given graph
     * @return The number of edges
     */
    public int getNumberOfEdges() {
        return numOfEdges;
    }

    /**
     * This method is used to retrieve the losses of each asset of the desired graph problem
     * @return The vector of asset losses
     */
    public double[] getNodeAssetsLossValues() {
        return switch (graphId) {
            case HG1, HG1_rand ->                new double[] {0, 0, 0, 0, 25, 0, 30};  // vs = 1 and vm = 7
            case SCADA, SCADA_rand ->            new double[] {0, 63, 39, 0, 0, 0, 33, 0, 0, 22, 0, 90, 45};  // vs = 1 and vm = 13
            case HG2, HG2_rand ->                new double[] {0, 0, 0, 0, 25, 0, 30, 0, 55, 0, 0, 20, 0, 30, 0};  // vs = 1 and vm = 12
            case ABSNP, ABSNP_rand ->            new double[] {0, 33, 0, 35, 0, 0, 14, 0, 0, 0, 65, 0, 0, 0, 33, 0, 65};  // vs = 1 and vm = 17
            case e_commerce, e_commerce_rand ->  new double[] {0, 0, 45, 0, 0, 0, 65, 0, 0, 0, 0, 0, 44, 0, 0, 0, 0, 0, 0, 75};  // vs = 1 and vm = 20
            case DER, DER_rand ->                new double[] {0, 0, 0, 20, 0, 0, 62, 0, 0, 0, 0, 33, 0, 89, 0, 0, 0, 0, 0, 56, 0, 60};  // vs = 1 and vm = 22
            case VOIP, VOIP_rand ->              new double[] {0, 0, 38, 0, 0, 47, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 72, 0, 0, 0, 81};  // vs = 1 and vm = 22
            case ASFS3, ASFS3_rand ->            new double[] {0, 0, 47, 0, 0, 33, 0, 42, 0, 0, 98, 0, 47, 0, 0, 0, 0, 0, 74, 0, 97, 0, 0, 29, 0, 0, 20};  // vs = 1 and vm = 27
            case ASS2009, ASS2009_rand ->        new double[] {0, 91, 0, 0, 0, 25, 0, 0, 0, 0, 0, 0, 54, 0, 0, 0, 13, 0, 35, 0, 0, 9, 0, 0, 0, 15, 0, 0, 53, 0, 60};  // vs = 1 and vm = 31
            case AWS03, AWS03_rand ->            new double[] {0, 0, 0, 0, 0, 0, 65, 0, 0, 0, 56, 0, 0, 11, 97, 20, 0, 96, 0, 0, 0, 0, 0, 0, 59, 0, 81, 0, 0, 33, 0, 21, 76, 7, 0, 0, 21, 0, 15, 0, 0, 22};  // vs = 1 and vm = 42
            case ALSFSA2 ->                      new double[] {0, 68, 14, 0, 0, 28, 0, 84, 23, 73, 82, 0, 0, 58, 72, 0, 0, 86, 0, 0, 92, 39, 66, 38, 29, 24, 90, 66, 67, 0, 0, 69, 28, 12, 55, 0, 23, 91, 0, 0, 0, 0, 0, 80, 0, 33};  // vs = 1 and vm = 46
            case ASS ->                          new double[] {0, 0, 83, 0, 0, 0, 0, 0, 0, 0, 21, 0, 0, 0, 12, 95, 0, 9, 0, 24, 0, 0, 0, 42, 0, 21, 71, 61, 91, 0, 0, 41, 21, 98, 0, 99, 14, 0, 0, 0, 0, 0, 58, 0, 0, 76, 0, 0, 34, 0, 0, 42};  // vs = 1 and vm = 52
            case ATF ->                          new double[] {0, 0, 13, 0, 77, 0, 99, 0, 13, 0, 0, 0, 0, 0, 0, 0, 0, 63, 0, 0, 0, 0, 0, 0, 19, 49, 54, 0, 38, 0, 87, 0, 10, 78, 0, 23, 0, 0, 0, 0, 0, 0, 71, 93, 0, 0, 0, 2, 0, 0, 44, 59, 42, 0, 0, 0, 26, 0, 0, 0, 0, 55};  // vs = 1 and vm = 62
            case Figure3 ->                      new double[] {0, 0, 25, 10, 55, 10}; // vs = 1 and vm = 6 / This is the example we clarify in this research (refere to Figure 3)
            case dset1, dset1_rand ->            new double[] {0, 0.0, 0.0, 0.0, 24.49281696271145, 0.0, 0.0, 0.0, 37.97607381574649, 0.0, 0.0, 0.0, 4.169799619872993, 0.0, 0.0, 0.0, 11.712752453460828, 0.0, 0.0, 0.0, 45.17199719091114, 0.0, 0.0, 0.0, 90.26303455953007, 0.0, 0.0, 0.0, 68.20894757541247, 0.0, 0.0, 0.0, 73.46764240305264, 0.0, 0.0, 0.0, 58.49742594567626, 0.0, 0.0, 0.0, 28.82180713690621, 0.0, 0.0, 0.0, 53.05455653739678, 0.0, 0.0, 0.0, 10.076901115344839, 0.0, 0.0, 0.0, 45.063785428787625, 0.0, 0.0, 0.0, 68.52107335633426, 0.0, 0.0, 0.0, 64.64569961589122, 0.0, 0.0, 0.0, 26.52395286194933, 0.0, 0.0, 0.0, 65.6119925628609, 0.0, 0.0, 0.0, 52.0278883553298, 0.0, 0.0, 0.0, 65.69703048766972, 0.0, 0.0, 0.0, 77.27027056278486, 0.0, 0.0, 0.0, 27.206127713963426, 0.0, 0.0, 0.0, 66.77273333625305, 0.0, 0.0, 0.0, 88.77284635560989, 0.0, 0.0, 0.0, 44.77920636044892, 0.0, 0.0, 0.0, 47.002900962891204, 0.0, 0.0, 0.0, 76.05642523711467, 0.0, 0.0, 0.0, 20.365966216042064, 0.0, 0.0, 0.0, 59.460544248237625, 0.0, 0.0, 0.0, 65.24428781104447, 0.0, 0.0, 0.0, 18.992829456388126, 0.0, 0.0, 0.0, 47.25262089249609, 0.0, 0.0, 0.0, 93.03328919118738, 0.0, 0.0, 0.0, 17.532242398598008, 0.0, 0.0, 0.0, 18.10055481249733, 0.0, 0.0, 0.0, 93.92721773387989, 0.0, 0.0, 0.0, 99.32566742265612, 0.0, 0.0, 0.0, 59.39387874556409, 50.689408454107486};  // vs = 1 and vm = 150
            case dset2, dset2_rand ->            new double[] {0, 0.0, 0.0, 0.0, 56.86810774449875, 0.0, 0.0, 0.0, 20.024162004442726, 0.0, 0.0, 0.0, 97.38370364820447, 0.0, 0.0, 0.0, 26.120104438508807, 0.0, 0.0, 0.0, 53.968615674225774, 0.0, 0.0, 0.0, 23.813191587326532, 0.0, 0.0, 0.0, 90.68893941554245, 0.0, 0.0, 0.0, 36.73030485176144, 0.0, 0.0, 0.0, 42.13903758108304, 0.0, 0.0, 0.0, 75.68709223782463, 0.0, 0.0, 0.0, 41.702678459583645, 0.0, 0.0, 0.0, 72.52274650342, 0.0, 0.0, 0.0, 70.17244610338335, 0.0, 0.0, 0.0, 44.77476285015424, 0.0, 0.0, 0.0, 91.71301432305957, 0.0, 0.0, 0.0, 15.603558293494402, 0.0, 0.0, 0.0, 13.668860180382957, 0.0, 0.0, 0.0, 87.8438557425549, 0.0, 0.0, 0.0, 64.4549817337259, 0.0, 0.0, 0.0, 45.15635844452371, 0.0, 0.0, 0.0, 83.91535561536224, 0.0, 0.0, 0.0, 38.9592112990555, 0.0, 0.0, 0.0, 95.33308917415914, 0.0, 0.0, 0.0, 49.07493068363321, 0.0, 0.0, 0.0, 96.23416437271453, 0.0, 0.0, 0.0, 66.44687357516807, 0.0, 0.0, 0.0, 3.7463046130174815, 0.0, 0.0, 0.0, 68.88537568802099, 0.0, 0.0, 0.0, 50.5067602402006, 0.0, 0.0, 0.0, 65.36724801109537, 0.0, 0.0, 0.0, 2.7740158269750514, 0.0, 0.0, 0.0, 36.8877820524702, 0.0, 0.0, 0.0, 90.66971467383416, 0.0, 0.0, 0.0, 81.67603124493561, 0.0, 0.0, 0.0, 75.35608087497995, 0.0, 0.0, 0.0, 52.38227616417691, 0.0, 0.0, 0.0, 80.1076241173861, 0.0, 0.0, 0.0, 84.91045879868075, 0.0, 0.0, 0.0, 38.64849613617343, 0.0, 0.0, 0.0, 47.854635532301906, 0.0, 0.0, 0.0, 57.20500645353405, 0.0, 0.0, 0.0, 26.216446263777726, 0.0, 0.0, 0.0, 76.0802802864778, 0.0, 0.0, 0.0, 6.408431958392047, 0.0, 0.0, 0.0, 70.39070353127524, 0.0, 0.0, 0.0, 66.94271589817771, 0.0, 0.0, 0.0, 75.08255400430615, 0.0, 0.0, 0.0, 66.93858571817938, 0.0, 0.0, 0.0, 78.43605717608956, 0.0, 0.0, 33.11727487149728};  // vs = 1 and vm = 200
            case dset3, dset3_rand ->            new double[] {0, 0.0, 0.0, 0.0, 77.99805557235317, 0.0, 0.0, 0.0, 2.7331207935023083, 0.0, 0.0, 0.0, 20.47430441984802, 0.0, 0.0, 0.0, 50.356825575795106, 0.0, 0.0, 0.0, 54.552183988279204, 0.0, 0.0, 0.0, 60.52855769618962, 0.0, 0.0, 0.0, 3.4946929585947917, 0.0, 0.0, 0.0, 94.7081128766656, 0.0, 0.0, 0.0, 79.72416248080324, 0.0, 0.0, 0.0, 34.50259604796787, 0.0, 0.0, 0.0, 49.64075628560419, 0.0, 0.0, 0.0, 59.755663763508636, 0.0, 0.0, 0.0, 33.16510617261709, 0.0, 0.0, 0.0, 65.82200174008831, 0.0, 0.0, 0.0, 63.188690891314415, 0.0, 0.0, 0.0, 66.14447293790384, 0.0, 0.0, 0.0, 54.996952842317704, 0.0, 0.0, 0.0, 18.705584077809057, 0.0, 0.0, 0.0, 31.395704595060735, 0.0, 0.0, 0.0, 22.55168663817566, 0.0, 0.0, 0.0, 9.46841514864637, 0.0, 0.0, 0.0, 58.20596023076677, 0.0, 0.0, 0.0, 28.2164837786151, 0.0, 0.0, 0.0, 89.69579049918653, 0.0, 0.0, 0.0, 61.48729113974741, 0.0, 0.0, 0.0, 91.05974081365719, 0.0, 0.0, 0.0, 5.359429303115604, 0.0, 0.0, 0.0, 85.74019158186493, 0.0, 0.0, 0.0, 65.7201541293302, 0.0, 0.0, 0.0, 97.39646230217683, 0.0, 0.0, 0.0, 90.65760909264745, 0.0, 0.0, 0.0, 74.48523994598555, 0.0, 0.0, 0.0, 17.16243341648237, 0.0, 0.0, 0.0, 42.97288253944909, 0.0, 0.0, 0.0, 16.114788123594938, 0.0, 0.0, 0.0, 68.15322604131305, 0.0, 0.0, 0.0, 91.07368949017149, 0.0, 0.0, 0.0, 71.21359069336684, 0.0, 0.0, 0.0, 57.98714608436989, 0.0, 0.0, 0.0, 93.43119488500292, 0.0, 0.0, 0.0, 26.26083989885344, 0.0, 0.0, 0.0, 44.413881551721026, 0.0, 0.0, 0.0, 67.10760545471555, 0.0, 0.0, 0.0, 14.13957885487781, 0.0, 0.0, 0.0, 10.790364087316517, 0.0, 0.0, 0.0, 93.19052386882188, 0.0, 0.0, 0.0, 31.342613454589884, 0.0, 0.0, 0.0, 54.14329412392031, 0.0, 0.0, 0.0, 3.4758405927521774, 0.0, 0.0, 0.0, 16.426479285911554, 0.0, 0.0, 0.0, 5.31536887470706, 0.0, 0.0, 0.0, 38.559652517757556, 0.0, 0.0, 0.0, 13.518479194232992, 0.0, 0.0, 0.0, 42.62033851933204, 0.0, 0.0, 0.0, 19.70753707913656, 0.0, 0.0, 0.0, 69.59633702765498, 0.0, 0.0, 0.0, 81.75139209702607, 0.0, 0.0, 0.0, 84.00036234985842, 0.0, 0.0, 0.0, 53.71722720709818, 0.0, 0.0, 0.0, 41.733772010095564, 0.0, 0.0, 0.0, 45.501309777520916, 0.0, 0.0, 0.0, 53.91908947155238, 38.18114629739865};  // vs = 1 and vm = 250
            case dset4, dset4_rand ->            new double[] {0, 0.0, 0.0, 0.0, 26.364471173540814, 0.0, 0.0, 0.0, 79.18909682952878, 0.0, 0.0, 0.0, 7.118973058193356, 0.0, 0.0, 0.0, 55.16588762083543, 0.0, 0.0, 0.0, 57.23129216636848, 0.0, 0.0, 0.0, 56.60544541404327, 0.0, 0.0, 0.0, 38.528636753429645, 0.0, 0.0, 0.0, 34.29514761604505, 0.0, 0.0, 0.0, 73.00683186074717, 0.0, 0.0, 0.0, 72.51092126667959, 0.0, 0.0, 0.0, 36.36053965409093, 0.0, 0.0, 0.0, 37.125681176128886, 0.0, 0.0, 0.0, 44.68044214256503, 0.0, 0.0, 0.0, 85.89612407691696, 0.0, 0.0, 0.0, 13.063216854532122, 0.0, 0.0, 0.0, 55.25291925061029, 0.0, 0.0, 0.0, 44.352805170489475, 0.0, 0.0, 0.0, 39.5706528579512, 0.0, 0.0, 0.0, 56.09235030500474, 0.0, 0.0, 0.0, 23.40895976867263, 0.0, 0.0, 0.0, 14.702978869966742, 0.0, 0.0, 0.0, 41.82744904806201, 0.0, 0.0, 0.0, 55.84004112602824, 0.0, 0.0, 0.0, 90.87448994560071, 0.0, 0.0, 0.0, 39.943511814331835, 0.0, 0.0, 0.0, 4.054766801461119, 0.0, 0.0, 0.0, 4.95412596662792, 0.0, 0.0, 0.0, 6.140726958663967, 0.0, 0.0, 0.0, 78.47963876066447, 0.0, 0.0, 0.0, 50.83869813013021, 0.0, 0.0, 0.0, 78.48904633732762, 0.0, 0.0, 0.0, 77.34074200913746, 0.0, 0.0, 0.0, 45.54631817038977, 0.0, 0.0, 0.0, 27.327153167300455, 0.0, 0.0, 0.0, 44.203267296545945, 0.0, 0.0, 0.0, 91.71368777652003, 0.0, 0.0, 0.0, 44.23591563516799, 0.0, 0.0, 0.0, 37.398221880578774, 0.0, 0.0, 0.0, 75.07252798315167, 0.0, 0.0, 0.0, 19.247058831327625, 0.0, 0.0, 0.0, 69.91951994472049, 0.0, 0.0, 0.0, 87.69422313547352, 0.0, 0.0, 0.0, 38.02628721151432, 0.0, 0.0, 0.0, 97.14912649608152, 0.0, 0.0, 0.0, 82.30545939604687, 0.0, 0.0, 0.0, 1.7604030123677727, 0.0, 0.0, 0.0, 44.54189177096299, 0.0, 0.0, 0.0, 2.1281884861230504, 0.0, 0.0, 0.0, 41.46885567061997, 0.0, 0.0, 0.0, 78.10907355302048, 0.0, 0.0, 0.0, 32.397061753189526, 0.0, 0.0, 0.0, 43.96202180112982, 0.0, 0.0, 0.0, 99.27639354280873, 0.0, 0.0, 0.0, 65.46037022787445, 0.0, 0.0, 0.0, 82.2533704214105, 0.0, 0.0, 0.0, 41.73339677662834, 0.0, 0.0, 0.0, 94.60497876959141, 0.0, 0.0, 0.0, 61.62109959884224, 0.0, 0.0, 0.0, 76.213194948571, 0.0, 0.0, 0.0, 22.90910458767762, 0.0, 0.0, 0.0, 67.27469274912083, 0.0, 0.0, 0.0, 62.03382154991136, 0.0, 0.0, 0.0, 76.59265160185903, 0.0, 0.0, 0.0, 65.79396793483807, 0.0, 0.0, 0.0, 47.58001492315361, 0.0, 0.0, 0.0, 28.35479703293511, 0.0, 0.0, 0.0, 43.31622916201636, 0.0, 0.0, 0.0, 29.925901253502673, 0.0, 0.0, 0.0, 28.088877338742023, 0.0, 0.0, 0.0, 31.176577082782774, 0.0, 0.0, 0.0, 48.96636242690988, 0.0, 0.0, 0.0, 85.70319468291594, 0.0, 0.0, 0.0, 45.5211023915916, 0.0, 0.0, 0.0, 30.37080494378196, 0.0, 0.0, 38.08011660349937};  // vs = 1 and vm = 300
        };
    }

    /**
     * This method is used to return the adjacency matrix of the wanted graph problem
     * @param defenders The matrix of defenders and their initial investments
     * @return The adjacency matrix
     */
    public double[][] getAdjacencyMatrix(Defenders[][] defenders) {
        if (defenders == null) {
            throw new IllegalArgumentException("The defenders are null!");
        }
        var adjMat = new double[defenders.length][defenders[0].length];
        for (var row = 0; row < defenders.length; row++) {
            for (var col = 0; col < defenders[0].length; col++) {
                adjMat[row][col] = defenders[row][col].totalInvest();
            }
        }
        return adjMat;
    }

    /**
     * This method is used to retrieve the attack graph as defender object
     * @param matrix The adjacent matrix of the attack graph
     * @return The object from the defender class in which it represents the attack graph
     */
    public Defenders[][] fromMatrix(double[][] matrix) {
        Defenders[][] defenders = new Defenders[matrix.length][matrix.length];
        for (int i = 0; i < defenders.length; i++) {
            for (int j = 0; j < defenders[0].length; j++) {
                if (matrix[i][j] > 0) {
                    defenders[i][j] = new Defenders(0, matrix[i][j], 0);
                }
                else {
                    defenders[i][j] = new Defenders(0, 0, 0);
                }
            }
        }
        return defenders;
    }

    /**
     * This method is used to visualize the attack-defence graph problem
     * @param data The adjacency matrix of the graph
     */
    public void Display(double[][] data) {
        if (data == null) {
            throw new IllegalArgumentException("The matrix is null!");
        }
        SingleGraph visualizer = new SingleGraph("graph");
        visualizer.setStrict(false);
        visualizer.setAutoCreate(true);
        for (int node = 0; node < data.length; node++) {
            for (int nod = 0; nod < data[0].length; nod++) {
                if (data[node][nod] > 0) {
                    String nodeI = node + 1 + "";
                    String nodeJ = nod + 1 + "";
                    visualizer.addNode(nodeI);
                    visualizer.addNode(nodeJ);
                    visualizer.addEdge(nodeI.concat(nodeJ), nodeI, nodeJ, false);
                }
            }
        }
        visualizer.nodes().forEach(x -> x.setAttribute("label", x.getId()));
        List<Edge> listEdges = visualizer.edges().toList();
        for (Edge listEdge : listEdges) {
            int edgeId = Integer.parseInt(listEdge.getNode0().getId().split("->")[0]) - 1;
            int nodeId = Integer.parseInt(listEdge.getNode1().getId().split("->")[0]) - 1;
            listEdge.setAttribute("label", data[edgeId][nodeId]);
        }
        System.setProperty("org.graphstream.ui", "swing");
        visualizer.display();
    }

    /**
     * This method is used to retrieve the total summation of loss units based on the given attack path
     * @param AttackPathByDE The attack path by the attacker
     * @param assetsLossValues The vector of losses as each loss referred to a particular node
     * @return The summation of losses if the attack is occurred
     */
    public double getValueOfLoss(List<Integer> AttackPathByDE, double[] assetsLossValues) {
        if (AttackPathByDE == null) {
            throw new IllegalArgumentException("The solution is null!");
        }
        if (assetsLossValues == null) {
            throw new IllegalArgumentException("The loss vector is null!");
        }
        double sumOfLoss = 0.0d;
        for (Integer integer : AttackPathByDE) {
            sumOfLoss += assetsLossValues[integer - 1];
        }
        return sumOfLoss;
    }
}