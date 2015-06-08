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

    // Get the clause and var sets
    // TODO: move this to general util class?
    HashSet<Clause> clauseSet = new HashSet<Clause>();
    HashSet<Variable> varSet = new HashSet<Variable>();
    for (ArrayList<Clause> cluster : data.clusters) {
      for (Clause clause : cluster) {
        clauseSet.add(clause);
        for (Variable var : clause.getVars()) { varSet.add(var); }
      }
    }
    ArrayList<Clause> clauses = new ArrayList<Clause>(clauseSet);
    ArrayList<Variable> vars = new ArrayList<Variable>(varSet);

    // construct the inference algorithms
    DecompMCSat alg1 = new DecompMCSat(data, config, "basic");
    DecompMCSat alg2 = new DecompMCSat(data, config, "decomp");

    // run the CG eval
    System.out.println("[EXPERIMENT 1]: Running CG eval for BASIC vs. DECOMP");
    ConditionalGames cgEval12 = new ConditionalGames(clauses, vars, alg1, alg2, config);
    double score12 = cgEval12.eval();

    System.out.println("[EXPERIMENT 1]: Running CG eval for DECOMP vs. BASIC");
    ConditionalGames cgEval21 = new ConditionalGames(clauses, vars, alg2, alg1, config);
    double score21 = cgEval21.eval();

    // print results
    System.out.println("********************************************************");
    System.out.println("[EXPERIMENT 1]: SOCRE for Basic vs. Decomp = " + score12);
    System.out.println("[EXPERIMENT 1]: SCORE for Decomp vs. Basic = " + score21);
    System.out.println("Avg. total marginals difference = " + cgEval21.getAvgMarginalDiff());
    System.out.println("Avg. abs. marginals difference = " + cgEval21.getAvgAbsMarginalDiff());
    System.out.println("********************************************************");
  }
}
