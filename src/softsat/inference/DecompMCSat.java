package softsat.inference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.lang.Math;
import java.util.Random;
import java.util.Collections;

import softsat.objects.Clause;
import softsat.objects.Literal;
import softsat.objects.Variable;
import softsat.config.Config;
import softsat.sat.SampleSat;
import softsat.generate.Data;

public class DecompMCSat {
  private Random rand = new Random();

  private ArrayList<HashSet<Clause>> clauseBlocks;
  private ArrayList<HashSet<Variable>> varBlocks;
  private int nBlocks;
  private String mode;
  private Config config;

  HashMap<Variable, int[]> sampleCounts;

  public void sample() {

    // Initialize sample counts & opt. random init the vars
    sampleCounts = new HashMap<Variable, int[]>();
    for (HashSet<Variable> varBlock : varBlocks) {
      for (Variable var : varBlock) {
        sampleCounts.put(var, new int[2]);
        if (config.MCSatRandInit) { var.randomFlip(); }
      }
    }

    for (long iter = -1; iter < config.nMCSatSteps; iter++) {
      for (int block = 0; block < nBlocks; block++) {

        // Add all clauses which were satisfied the previous iteration wp 1-e^{-w}
        // On iteration -1 initialize by running WalkSat over the hard clauses only
        ArrayList<Clause> Mc = new ArrayList<Clause>();
        HashSet<Variable> Mv = new HashSet<Variable>();
        for (Clause clause : clauseBlocks.get(block)) {
          if (clause.isHard() || (iter >= 0 && clause.isSat() && rand.nextFloat() < 1 - Math.exp(-clause.getLogWeight()))) {
            Mc.add(clause);
            for (Variable var : clause.getVars()) {
              if (varBlocks.get(block).contains(var)) { Mv.add(var); }
            }
          }
        }

        // Sample from the set of satisfying assignments using the SAT solver
        SampleSat satSolver = new SampleSat(Mc, new ArrayList<Variable>(Mv), config);
        if (iter >= 0) {
          assert satSolver.runSample();
        } else {
          assert satSolver.runSolve();
        }

        // Update sample counts
        if (iter > config.MCSatBurnIn) {
          for (Variable var : Mv) {
            sampleCounts.get(var)[var.getIsTrue() ? 1 : 0] += 1;
          }
        }
      }
    }
  }

  /**
   * Estimate the marginal for a specific variable based on sample function.  Optionally
   * resample.  Assume binary variables so return the probability P(x=true)
   */
  public double estimateMarginal(Variable var, boolean resample) {
    if (resample) { sample(); }
    int[] counts = sampleCounts.get(var);
    if (config.debug > 2 && !config.cgRunParallel) {
      System.out.println("Counts = (" + counts[1] + "," + counts[0] + ")");
    }
    return ((double) counts[1]) / (counts[0] + counts[1]);
  }

  public String toString() {
    return "<DecompMCSat(Mode=" + this.mode + ")>";
  }

  /**
   * Default constructor for Data object consisting of hard clusters connected by soft clauses.
   */
  public DecompMCSat(Data data, Config config, String mode) {
    this.mode = mode;
    this.config = config;
    this.clauseBlocks = new ArrayList<HashSet<Clause>>();
    this.varBlocks = new ArrayList<HashSet<Variable>>();
    
    // Uses the following blocking scheme as default: given a random ordering of hard clusters,
    // blocks over vars in each cluster, all hard cluster clauses, and all attached soft connectors
    if (mode == "decomp") {
      this.nBlocks = data.clusters.size();
      HashSet<Clause> added = new HashSet<Clause>();
      Collections.shuffle(data.clusters);

      // Add hard cluster clauses, associated vars, and all connected soft clauses not already
      // in a block to a block
      for (ArrayList<Clause> cluster : data.clusters) {
        HashSet<Variable> varBlock = new HashSet<Variable>();
        for (Clause clause : cluster) { varBlock.addAll(clause.getVars()); }
        HashSet<Clause> clauseBlock = new HashSet<Clause>(cluster);
        for (Clause softClause : data.softClauses) {
          if (added.contains(softClause)) { continue; }
          boolean connected = false;
          for (Variable var : softClause.getVars()) {
            if (varBlock.contains(var)) { connected = true; }
          }
          if (connected) {
            clauseBlock.add(softClause);
            added.add(softClause);
          }
        }
        this.varBlocks.add(varBlock);
        this.clauseBlocks.add(clauseBlock);
      }

    } else if (mode == "basic") {
      this.nBlocks = 1;
      HashSet<Clause> clauseBlock = new HashSet<Clause>();
      HashSet<Variable> varBlock = new HashSet<Variable>();
      for (ArrayList<Clause> cluster : data.clusters) {
        clauseBlock.addAll(cluster);
        for (Clause clause : cluster) { varBlock.addAll(clause.getVars()); }
      }
      clauseBlock.addAll(data.softClauses);
      this.varBlocks.add(varBlock);
      this.clauseBlocks.add(clauseBlock);

    } else {
      System.out.println("ERROR: DecompMCSat mode not recognized.");
      assert false;
    }
  }

  /**
   * Basic- most general- constructor.  Accepts a list of clause sets and var sets, which
   * represent the blocking order.
   */
  public DecompMCSat(ArrayList<ArrayList<Clause>> clauseBlocks, ArrayList<ArrayList<Variable>> varBlocks, Config config) {
    this.mode = "custom";
    this.config = config;
    assert clauseBlocks.size() == varBlocks.size();
    this.nBlocks = clauseBlocks.size();
    this.clauseBlocks = new ArrayList<HashSet<Clause>>();
    for (ArrayList<Clause> clauseBlock : clauseBlocks) {
      this.clauseBlocks.add(new HashSet<Clause>(clauseBlock));
    }
    this.varBlocks = new ArrayList<HashSet<Variable>>();
    for (ArrayList<Variable> varBlock : varBlocks) {
      this.varBlocks.add(new HashSet<Variable>(varBlock));
    }
  }
}
