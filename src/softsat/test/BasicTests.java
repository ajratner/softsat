package softsat.test;
import java.util.ArrayList;

import softsat.objects.Variable;
import softsat.objects.Literal;
import softsat.objects.Clause;
import softsat.objects.SoftClause;
import softsat.util.PrettyPrinter;
import softsat.generate.BGMCSatDataGenerator;
import softsat.generate.Data;
import softsat.sat.SampleSat;
import softsat.sat.BruteForceSat;
import softsat.config.Config;

/**
 * Basic tests
 */
public class BasicTests {

  /**
   * Testing basic WalkSat
   */
  public static boolean testWalkSat() {
    System.out.println("---\n");
    System.out.println("Running basic test of WalkSat:");
    Config config = new Config();

    config.nClusters = 10;
    config.n = 1000;
    config.k = 3;
    config.alpha = 2.0;
    config.nSampleSatSteps = 10*config.n;
    System.out.println("Running "+config.nClusters+" iterations of (n,k,alpha) = ("+config.n+","+config.k+","+config.alpha+")");

    System.out.println("Generating dataset...");
    BGMCSatDataGenerator datagen = new BGMCSatDataGenerator(config);
    Data data = datagen.generateData();
    
    System.out.println("Testing on each hard SAT cluster:");
    for (int clusterId = 0 ; clusterId < data.clusters.size(); clusterId++) {
      SampleSat satSolver = new SampleSat(clusterId, data.clusters.get(clusterId), config);
      satSolver.runSolve();
      if (!checkSat(data.clusters.get(clusterId))) {
        System.out.println("TEST FAILED (clusterId="+clusterId+")");
        return false;
      }
    }
    System.out.println("TEST PASSED!");
    return true;
  }

  /**
   * Testing basic WalkSat on *SMALL* instances against a correct brute force solver
   */
  public static boolean testWalkSatBrute() {
    System.out.println("---\n");
    System.out.println("Running BRUTE FORCE test of WalkSat:");
    Config config = new Config();

    config.nClusters = 100;
    config.n = 15;
    config.k = 3;
    config.alpha = 3.5;
    System.out.println("Running "+config.nClusters+" iterations of (n,k,alpha) = ("+config.n+","+config.k+","+config.alpha+")");

    System.out.println("Generating dataset...");
    BGMCSatDataGenerator datagen = new BGMCSatDataGenerator(config);
    Data data = datagen.generateData();

    StringBuilder s = new StringBuilder();
    // for (int i=0; i < Math.pow(2, config.n); i++) { s.append("U"); }
    for (int i=0; i < config.n; i++) { s.append("U"); }
    System.out.println("Preparing to BR" + s.toString() + "TE!"); 

    System.out.println("Testing on each hard SAT cluster:");
    for (int clusterId = 0 ; clusterId < data.clusters.size(); clusterId++) {

      // Run BruteForce
      BruteForceSat bFSatSolver = new BruteForceSat(clusterId,data.clusters.get(clusterId),config);
      bFSatSolver.runSolve();
      boolean bFAns = checkSat(data.clusters.get(clusterId));

      // Run WalkSAT
      SampleSat satSolver = new SampleSat(clusterId, data.clusters.get(clusterId), config);
      satSolver.runSolve();
      boolean walkSatAns = checkSat(data.clusters.get(clusterId));

      if (bFAns != walkSatAns) {
        System.out.println("TEST FAILED (clusterId="+clusterId+"): WalkSAT=" + walkSatAns + ", BruteForce=" + !walkSatAns);
        return false;
      }
    }
    System.out.println("TEST PASSED!");
    //PrettyPrinter.printListOfClauses(data.clusters.get(0));
    return true;
  }

  private static boolean checkSat(ArrayList<Clause> clauses) {
    for (Clause clause : clauses) {
      if (!clause.isSat()) {
        //System.out.println("Clause " + clause + " is UNSAT.");
        return false;
      }
    }
    return true;
  }

  public static void runAll() {
    assert testWalkSatBrute();
    assert testWalkSat();
  }
}
