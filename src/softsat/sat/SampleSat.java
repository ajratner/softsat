package softsat.sat;

import softsat.objects.Clause;
import softsat.objects.Literal;
import softsat.objects.Variable;
import softsat.main.Config;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * SampleSat attempts to sample uniformly from the space of satifying assignments for a given
 * set of clauses.  Note that clauses are assumed to be 'hard' i.e. weights are ignored /
 * assumed to be infinite.  SampleSat proceeds, roughly, by searching through assignment space
 * by stochastically switching between a simulated annealing (SA) and a WalkSat (WS) step.
 */
public class SampleSat {
  Random rand = new Random();

  private Config config;
  
  private ArrayList<Clause> clauses;
  private ArrayList<Variable> vars;

  private HashSet<Clause> unsatisfied;

  /**
   * The clusterId of the cluster we are currently in, which lets us filter out Variables
   * in other clusters (which we might want to consider as frozen)
   */
  private int clusterId;

  /**
   * Initializes the primary counts / data structures for SampleSat.  Optionally resets the
   * variable assignments, then populates the unsatisfied HashSet, generates initial make / 
   * break counts from scratch.
   */
  private void init(boolean resetAssignments) {
    
    // Optionally randomly reset the assignments of the active (ie in-cluster) vars
    if (resetAssignments) {
      for (Variable var : vars) {
        if (var.getClusterId() == clusterId) { var.randomFlip(); }
      }
    }

    // Set the make/break counts in the vars
    for (Variable var : vars) { var.setMakeBreakCounts(); }

    // Populate the HashSet of unsat clauses
    unsatisfied = new HashSet<Clause>();
    for (Clause clause : clauses) {
      if (!clause.isSat()) { unsatisfied.add(clause); }
    }
  }

  /**
   * Runs SampleSat, assuming all the clauses are 'hard' ie having weight infinity.
   * @return true iff a satisfying assignment is found 
   */
  public boolean run(boolean walkSatMode) {
    init(config.satResetAssignments);
    for (long step = 0; step < config.nSampleSatSteps; step++) {
      // TODO
    }
    return true;
  }

  public boolean runSolve() { return run(true); }

  public boolean runSample() { return run(false); }

  public SampleSat(int clusterId, ArrayList<Clause> clauses, Config config) {
    this.clusterId = clusterId;
    this.clauses = clauses;
    this.config = config;
    HashSet<Variable> uniqueVars = new HashSet<Variable>();
    for (Clause clause : clauses) {
      for (Variable var : clause.getVars()) { uniqueVars.add(var); }
    }
    this.vars = new ArrayList<Variable>(uniqueVars);
  }
}
