package softsat.sat;

import softsat.objects.Clause;
import softsat.objects.Literal;
import softsat.objects.Variable;
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
  private ArrayList<Clause> clauses;
  private ArrayList<Variable> vars;

  /**
   * The clusterId of the cluster we are currently in, which lets us filter out Variables
   * in other clusters (which we might want to consider as frozen)
   */
  private int clusterId;

  private HashSet<Clause> unsatisfied;

  /**
   * Reset the current assignments of all variables in the current cluster
   */
  private void resetAssignments() {
    for (Variable var : vars) {
      if (var.getClusterId() == clusterId) { var.randomFlip(); }
    }
  }

  /**
   * 
   * TODO: we can make this incremental eg the outer MC-SAT loop can keep the make / break
   * counts up to date so that we don't need to re-do completely each time
   */
  //private void generateMakeBreakCounts() {

  /**
   * Runs SampleSat, assuming all the clauses are 'hard' ie having weight infinity.
   * @param nSteps
   * @return true iff a satisfying assignment is found 
   */
  public boolean run(long nSteps) {
    unsatisfied = new HashSet<Clause>();
    //for (long step = 0; step < nSteps; step++) {
  }

  // TODO: implement WalkSat as special case of SampleSat

  public SampleSat(int clusterId, ArrayList<Clause> clauses) {
    this.clusterId = clusterId;
    this.clauses = clauses;
    HashSet<Variable> uniqueVars = new HashSet<Variable>();
    for (Clause clause : clauses) {
      for (Literal literal : clause.literals) {
        uniqueVars.add(literal.getVar());
      }
    }
    this.vars = new ArrayList<Variable>(uniqueVars);
  }
}
