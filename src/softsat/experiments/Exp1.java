package softsat.experiments;

import softsat.generate.Data;
import softsat.config.Config;
import softsat.generate.HardSoftDataGenerator;
import softsat.inference.DecompMCSat;
import softsat.eval.ConditionalGames;
import softsat.objects.Clause;
import softsat.objects.Variable;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Testing blocking for MC-SAT.  Over different hard cluster / soft connector datasets
 */
public class Exp1 {
  private Config config;
  private Data data;
  private double avgScore;
  private double avgTotalMarginalDiff;
  private double avgAbsMarginalDiff;

  public void run() {

    // Set up the config
    config = new Config();

    config.nClusters = 5;
    config.k = 3;
    config.n = 100;
    config.alpha = 2.5;
    config.numSoftClauses = 3;
    config.nSampleSatSteps = 100 * config.n;
    
    int nRepeats = 20;
    for (int i=0; i < nRepeats; i++) {

      // initialize the dataset
      HardSoftDataGenerator dataGen = new HardSoftDataGenerator(config);
      data = dataGen.generateData();

      // construct the inference algorithms & run CGs
      System.out.println("[EXPERIMENT "+i+"]: Running CG eval for DECOMP vs. BASIC");
      ConditionalGames cg = new ConditionalGames(data, "decomp", "basic", config);
      //config.cgRunParallel = false;
      double score = cg.eval();

      // print results
      // Note: S_4(Q,R) = -S_4(R,Q)
      System.out.println("********************************************************");
      System.out.println("[EXPERIMENT 1]: SCORE for Decomp vs. Basic = " + score);
      System.out.println("Avg. total marg. diff = " + cg.getAvgTotalMarginalDiff());
      System.out.println("Avg. abs marg. diff = " + cg.getAvgAbsMarginalDiff());
      System.out.println("********************************************************");
      avgScore += score;
      avgTotalMarginalDiff += cg.getAvgTotalMarginalDiff();
      avgAbsMarginalDiff += cg.getAvgAbsMarginalDiff();
    }
    System.out.println("AVG SCORE = " + (avgScore / nRepeats));
    System.out.println("AVG TMD = " + (avgTotalMarginalDiff / nRepeats));
    System.out.println("AVG AMD = " + (avgAbsMarginalDiff / nRepeats));
  }
}
