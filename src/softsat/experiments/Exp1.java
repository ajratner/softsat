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

    // initialize the dataset
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
    ConditionalGames cgEval = new ConditionalGames(clauses, vars, alg1, alg2, config);
    double score12 = cgEval.eval();
    cgEval = new ConditionalGames(clauses, vars, alg2, alg1, config);
    double score21 = cgEval.eval();

    // print results
    System.out.println("SCORE: Basic vs. Decomp: " + score12);
    System.out.println("SCORE: Decomp vs. Basic: " + score21);
  }
}
