package softsat.sat;

import softsat.objects.Clause;
import softsat.objects.Literal;
import softsat.objects.Variable;
import softsat.main.Config;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import softsat.util.HashArray;

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

  private HashArray<Clause> unsatisfied;

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
    unsatisfied = new HashArray<Clause>();
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

      // SA step: pick a random variable to flip and accept wp e^(-deltaCost)
      if (rand.nextDouble() < config.pSimAnnealStep) {
        Variable var = vars.get(rand.nextInt(vars.size()));
        if (rand.nextDouble() < Math.exp(-var.getCost() / config.simAnnealTemp)) { 
          flipAndUpdate(var);
        }
      
      // WS step: pick a random unsat clause- pick var to flip randomly or by breakCount
      } else {
        Clause clause = unsatisfied.getRandomElement();
        if (rand.nextDouble() < config.pRandomStep) {
          flipAndUpdate(clause.getVars().get(rand.nextInt(clause.getVars().size())));
        } else {
          flipAndUpdate(clause.getMinBreakVar());
        }
      }

      // WalkSat terminates here
      if (walkSatMode && unsatisfied.isEmpty()) { return true; }
    }
    return unsatisfied.isEmpty();
  }

  private void flipAndUpdate(Variable var) {
    var.flipIsTrue();
    var.resetCounts();
    for (Clause clause : var.getClausesIn()) {

      // Find out if the input var is sat in the clause, get total sat count
      int satCount = 0;
      boolean thisSat = false;
      for (Literal literal : clause.getLiterals()) {
        if (literal.isSat()) {
          satCount += 1;
          if (literal.getVar() == var) { thisSat = true; }
        }
      }

      // update the unsat list if necessary
      boolean inUnsat = unsatisfied.contains(clause);
      if (inUnsat && satCount > 0) {
        unsatisfied.removeObj(clause);
      } else if (!inUnsat && satCount == 0) {
        unsatisfied.add(clause);
      }

      // update this var's counts
      if (thisSat && satCount == 1) {
        var.incBreakCount();
      } else if (!thisSat && satCount == 0) {
        var.incMakeCount();
      }

      // Update neighboring vars' counts
      for (Literal neighbor : clause.getLiterals()) {
        if (neighbor.getVar() == var) { continue; }
        if (thisSat && neighbor.isSat() && satCount == 2) {
          neighbor.getVar().decBreakCount();
        } else if (thisSat && !neighbor.isSat() && satCount == 1) {
          neighbor.getVar().decMakeCount();
        } else if (!thisSat && neighbor.isSat() && satCount == 1) {
          neighbor.getVar().incBreakCount();
        } else if (!thisSat && !neighbor.isSat() && satCount == 0) {
          neighbor.getVar().incMakeCount();
        }
      }
    }
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
