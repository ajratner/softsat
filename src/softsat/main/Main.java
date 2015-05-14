package softsat.main;
import softsat.objects.Variable;
import softsat.objects.Literal;
import softsat.generate.BGMCSatData;

/**
 * The Main.
 */
public class Main {
  
  private static void sanityBGMCSatData() {
    // BGMCSatData(int nClusters, int n, int k, double alpha, int numSoftClauses, int clusterNodesPerSoftClause, double softWeightMean, double softWeightStd) {
    BGMCSatData data = new BGMCSatData(3,4,2,2.0,2,2,10,1);
    data.print();
  }

  public static void main(String[] args) {
    sanityBGMCSatData();
  }
}
