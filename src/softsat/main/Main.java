package softsat.main;
import java.util.ArrayList;

import softsat.objects.Variable;
import softsat.objects.Literal;
import softsat.objects.Clause;
import softsat.util.PrettyPrinter;
import softsat.generate.BGMCSatDataGenerator;
import softsat.generate.Data;
import softsat.objects.SoftClause;
import softsat.inference.BGMCSat;
import softsat.inference.VanillaMCSat;
import softsat.util.SatChecker;
import softsat.sat.SampleSat;
/**
 * The Main.
 */
public class Main {
  
  private static void sanityBGMCSatData() {

    BGMCSatDataGenerator datagen = new BGMCSatDataGenerator();
    Data data = datagen.generateData(3,4,2,2.0,2,2,10,1);
    PrettyPrinter pp = new PrettyPrinter();
    Config config = new Config();
    System.out.println("--BGMCSat--\n");
    BGMCSat bgmcsat = new BGMCSat(data,config);
    pp.printClusters(bgmcsat.getClusters());

    System.out.println("\n\n--Vanilla--\n");
    VanillaMCSat vmcsat = new VanillaMCSat(data,config);
    pp.printClusters(vmcsat.getClusters());

  }

  private static void sanityTestWalkSAT() {
    //  public Data generateData(int nClusters, int n, int k, double alpha, int numSoftClauses, int clusterNodesPerSoftClause, double softWeightMean, double softWeightStd) {
    BGMCSatDataGenerator datagen = new BGMCSatDataGenerator();
    Data data = datagen.generateData(3,4,2,2.0,2,2,10,1);
    Config config = new Config();
    SatChecker satChecker = new SatChecker();

    for (int clusterId = 0 ; clusterId < data.clusters.size(); clusterId++) {
      SampleSat satSolver = new SampleSat(clusterId, data.clusters.get(clusterId), config);
      satSolver.runSolve();
      assert satChecker.allClausesSat(data.clusters.get(clusterId));
    }
    
  }

  public static void main(String[] args) {
    //sanityBGMCSatData();
    sanityTestWalkSAT();
  }
}
