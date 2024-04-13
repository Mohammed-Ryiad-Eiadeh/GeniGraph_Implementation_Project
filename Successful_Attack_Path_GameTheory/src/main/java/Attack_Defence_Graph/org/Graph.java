package Attack_Defence_Graph.org;

import java.nio.file.Path;

/**
 * Thi enumeration includes the value id for each graph problem we have
 */
public enum Graph {
    SCADA, DER, e_commerce, VOIP, ABSNP, ASFS3, ASS2009, HG1, HG2, AWS03, ALSFSA2, ASS, ATF,
    Figure3, dset1, dset2, dset3, dset4, dset1_rand, dset2_rand, dset3_rand, dset4_rand, SCADA_rand,
    DER_rand, e_commerce_rand, VOIP_rand, ABSNP_rand, ASFS3_rand, ASS2009_rand, HG1_rand, HG2_rand, AWS03_rand;

    /**
     * This method is used to retrieve the path of the graph problem
     * @return The path of the graph problem
     */
    public Path getPath() {
        return switch (this) {
            case SCADA -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\SCADA.txt");
            case DER -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\DER.txt");
            case e_commerce -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\e_commerce.txt");
            case VOIP -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\VOiP.txt");
            case ABSNP -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\ABSNP.txt");
            case ASFS3 -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\ASFS3.txt");
            case ASS2009 -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\ASS2009.txt");
            case HG1 -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\HG1.txt");
            case HG2 -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\HG2.txt");
            case AWS03 -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\AWS03.txt");
            case ALSFSA2 -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\ALSFSA2.txt");
            case ASS -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\ASS.txt");
            case ATF -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\ATF.txt");
            case SCADA_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\SCADA_rand.txt");
            case DER_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\DER_rand.txt");
            case e_commerce_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\e_commerce_rand.txt");
            case VOIP_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\VOiP_rand.txt");
            case ABSNP_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\ABSNP_rand.txt");
            case ASFS3_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\ASFS3_rand.txt");
            case ASS2009_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\ASS2009_rand.txt");
            case HG1_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\HG1_rand.txt");
            case HG2_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\HG2_rand.txt");
            case AWS03_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\AWS03_rand.txt");
            case Figure3 -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\Figure3.txt");
            case dset1 -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\dset1.txt");
            case dset2 -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\dset2.txt");
            case dset3 -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\dset3.txt");
            case dset4 -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\dset4.txt");
            case dset1_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\dset1_rand.txt");
            case dset2_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\dset2_rand.txt");
            case dset3_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\dset3_rand.txt");
            case dset4_rand -> Path.of(System.getProperty("user.dir"), "\\DataGraph\\dset4_rand.txt");
        };
    }
}
