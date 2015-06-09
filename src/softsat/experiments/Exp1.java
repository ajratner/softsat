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

  public void run() {

    // Set up the config
    config = new Config();

    config.nClusters = 3;
    config.k = 3;
    config.n = 10;
    config.alpha = 2.0;
    config.numSoftClauses = 2;
    config.nSampleSatSteps = 100 * config.n;

    // initialize the dataset
    System.out.println("[EXPERIMENT 1]: Initializing data & algorithms");
    HardSoftDataGenerator dataGen = new HardSoftDataGenerator(config);
    data = dataGen.generateData();


    // construct the inference algorithms & run CGs
    System.out.println("[EXPERIMENT 1]: Running CG eval for DECOMP vs. BASIC");
    ConditionalGames cgEval = new ConditionalGames(data, "decomp", "basic", config);
    //config.cgRunParallel = false;
    double score = cgEval.eval();

    // print results
    // Note: S_4(Q,R) = -S_4(R,Q)
    System.out.println("********************************************************");
    System.out.println("[EXPERIMENT 1]: SCORE for Decomp vs. Basic = " + score);
    //System.out.println("Avg. total marginals difference = " + cgEval.getAvgMarginalDiff());
    //System.out.println("Avg. abs. marginals difference = " + cgEval.getAvgAbsMarginalDiff());
    System.out.println("********************************************************");
  }
}
