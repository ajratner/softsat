package softsat.main;
import softsat.objects.Variable;
import softsat.objects.Literal;
import softsat.generate.BGMCSatData;

/**
 * The Main.
 */
public class Main {
  
  private static void demoDan() {
    Variable var = new Variable(1,2);
    Literal lit = new Literal(var,true);
    System.out.println("Lit(Var(1,2),true)");
    System.out.println(lit);
  }

  private static void sanityBGMCSatData() {
    // BGMCSatData(int nClusters, int n, int k, double alpha, int numSoftClauses, int clusterNodesPerSoftClause, double softWeightMean, double softWeightStd) {
    BGMCSatData data = new BGMCSatData(3,4,2,2.0,2,2,10,1);
    data.print();
  }

  public static void main(String[] args) {
    demoDan();
    sanityBGMCSatData();
    
    //    HardClusters dataset = new HardClusters();
    //    dataset.generate(3, 3, 3, 4.0, 0.5, 1.0, 0.1, 1);
    //    dataset.print();
  }
}
