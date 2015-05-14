package softsat.main;
import java.util.ArrayList;

import softsat.objects.Variable;
import softsat.objects.Literal;
import softsat.objects.Clause;
import softsat.util.PrettyPrinter;
import softsat.generate.BGMCSatDataGenerator;

/**
 * The Main.
 */
public class Main {
  
  private static void sanityBGMCSatData() {
    // BGMCSatData(int nClusters, int n, int k, double alpha, int numSoftClauses, int clusterNodesPerSoftClause, double softWeightMean, double softWeightStd) {
    BGMCSatDataGenerator datagen = new BGMCSatDataGenerator();
    ArrayList<ArrayList<Clause> > clusters = datagen.generateData(3,4,2,2.0,2,2,10,1);
    PrettyPrinter pp = new PrettyPrinter();
    pp.printClusters(clusters);
  }

  public static void main(String[] args) {
    sanityBGMCSatData();
  }
}
