package softsat.sat;

import softsat.objects.Clause;
import softsat.objects.Literal;
import softsat.objects.Variable;
import softsat.config.Config;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import softsat.util.HashArray;

/**
 * SampleSat attempts to sample uniformly from the space of satifying assignments for a given
 * set of clauses.  Note that clauses are assumed to be 'hard' i.e. weights are ignored /
 * assumed to be infinite.  SampleSat proceeds, roughly, by searching through assignment space
 * by stochastically switching between a simulated annealing (SA) and a WalkSat (WS) step.
 */
public class SampleSat extends SatSolver {
  private HashArray<Clause> unsat;

  /**
   * Initializes the primary counts / data structures for SampleSat.  Optionally resets the
   * variable assignments, then populates the unsatisfied HashSet, generates initial make / 
   * break counts from scratch.
   */
  private void init(boolean resetAssignments) {
    
    // Optionally randomly reset the assignments of the active (ie in-cluster) vars
    if (resetAssignments) {
      for (Variable var : getActiveVars()) { var.randomFlip(); }
    }

    // Set the make/break counts in the vars
    for (Variable var : getActiveVars()) { setVarCounts(var); }

    // Populate the HashSet of unsat clauses
    unsat = new HashArray<Clause>();
    for (Clause clause : getActiveClauses()) {
      if (!clause.isSat()) { unsat.add(clause); }
    }
  }

  /**
   * Runs SampleSat, assuming all the clauses are 'hard' ie having weight infinity.
   * @return true iff a satisfying assignment is found 
   */
  public boolean run(boolean walkSatMode, boolean randomReset) {
    init(randomReset);
    boolean satFound = false;
    int stepsPostSatFound = 0;
    for (long step = 0; step < config.nSampleSatSteps; step++) {
      
      // Per the MC-SAT implementation, in SampleSat mode we continue after finding a soln
      // Note: temporary assert to see if this works okay (not in tuffy)
      if (satFound) { stepsPostSatFound += 1; }
      assert stepsPostSatFound < 10*config.minStepsPostSatFound;

      // Termination conditions
      if (unsat.isEmpty()) {
        satFound = true;
        if (walkSatMode || stepsPostSatFound >= config.minStepsPostSatFound) { return true; }
      }

      // SA step: pick a random variable to flip and accept wp e^(-deltaCost)
      // Note: We only take SampleSat steps if at a SAT assignment, as per tuffy impl.
      // The informal justification is that we use WS to find assignment cluster,
      // then use SA steps to explore locally in more uniform fashion
      if (!walkSatMode && (unsat.isEmpty() || rand.nextDouble() < config.pSimAnnealStep)) {
        Variable var = getActiveVars().get(rand.nextInt(getActiveVars().size()));

        // Following general Metropolis acceptance prob.
        // e.g. see http://en.wikipedia.org/wiki/Simulated_annealing#Acceptance_probabilities
        int cost = var.getCost();
        if (cost <= 0 || rand.nextDouble() < Math.exp(-cost / config.simAnnealTemp)) { 
          flipAndUpdate(var);
        }

      // WS step: pick a random unsat clause- pick var to flip randomly or by heuristic
      } else {
        Clause clause = unsat.getRandomElement();
        ArrayList<Variable> clauseVars = clause.getVars();
        Collections.shuffle(clauseVars);
        
        // pick randomly from active vars
        if (rand.nextDouble() < config.pRandomStep) {
          for (Variable var : clauseVars) {
            if (isActive(var)) {
              flipAndUpdate(var);
              break;
            }
          }

        // pick according to heuristic
        // Note: here we use cost = breakCount - makeCount; some sources just use breakCount
        } else {
          int minCost = Integer.MAX_VALUE;
          Variable minCostVar = clauseVars.get(0);
          for (Variable var : clauseVars) {
            if (isActive(var) && (var.getCost() < minCost)) {
              minCost = var.getCost();
              minCostVar = var;
            }
          }
          flipAndUpdate(minCostVar);
        }
      }
    }
    return unsat.isEmpty();
  }

  /**
   * Run SampleSat in WalkSat mode, ie doing MAP inference.  Search greedily for a satisfying
   * assignment using WalkSat steps only.  Optionally utilize random restarts
   */
  public boolean runSolve() { 
    for (int r = 0; r < config.nWalkSatRestarts; r++) {
      if (run(true, true)) { return true; }
    }
    return false;
  }

  /**
   * Run SampleSat for fixed number of steps. Attempt to sample uniformly from the space of SAT
   * assignments by alternating WalkSat steps with simulated annealing steps
   */
  public boolean runSample() { 
    if (run(false, true)) { return true; }

    // Run in WalkSat mode as failure-case backup
    System.out.println("WARNING: SampleSat failed, running in WalkSat mode!");
    return run(true, false);
  }

  private void updateVarCounts(Variable var, boolean initial) {
    var.resetCounts();
    for (Clause clause : var.getClausesIn()) {

      // Don't take inactive clauses into consideration
      if (!isActive(clause)) { continue; }

      // Find out if the input var is sat in the clause, get total sat count
      int satCount = 0;
      boolean thisSat = false;
      for (Literal literal : clause.getLiterals()) {
        if (literal.isSat()) {
          satCount += 1;
          if (literal.getVar() == var) { thisSat = true; }
        }
      }

      // update this var's counts & the unsat set
      if (thisSat && satCount == 1) {
        var.incBreakCount();
        if (!initial) {
          var.decMakeCount();
          unsat.removeObj(clause);
        }
      } else if (!thisSat && satCount == 0) {
        var.incMakeCount();
        if (!initial) {
          var.decBreakCount();
          unsat.add(clause);
        }
      }

      // Update neighboring vars' counts
      if (!initial) {
        for (Literal neighbor : clause.getLiterals()) {
          if (neighbor.getVar() == var || !isActive(neighbor.getVar())) { continue; }
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
  }
  
  private void flipAndUpdate(Variable var) { 
    var.flipIsTrue();
    updateVarCounts(var, false); 
  }

  private void setVarCounts(Variable var) { updateVarCounts(var, true); }

  public SampleSat(ArrayList<Clause> clauses, ArrayList<Variable> vars, Config config) {
    super(clauses, vars, config);
  }
}
