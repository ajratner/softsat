package softsat.sat;

import softsat.objects.Clause;
import softsat.objects.Literal;
import softsat.objects.Variable;
import softsat.config.Config;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

/**
 * Generic abstract class for a SAT solver/sampler.  Takes in a list of active variables and
 * clauses and a config object, implements a solve and sample method which return true iff
 * a satisfying assignment has been greedily found or (ideally) uniformly sampled resp.
 */
abstract class SatSolver {
  protected Random rand = new Random();
  protected Config config;

  /**
   * The set of active clauses, ie the clauses that must be satisfied.  Note that other
   * (inactive) clauses may be involved as parents of active vars.
   */
  private ArrayList<Clause> activeClauses;
  private HashSet<Clause> activeClausesSet;
  protected ArrayList<Clause> getActiveClauses() { return activeClauses; }
  protected boolean isActive(Clause clause) { return activeClausesSet.contains(clause); }

  /**
   * The set of active vars, ie the vars that can be altered in search for satisfying
   * assignment.  Not that other (inactive) vars can be involved as (effectively fixed)
   * members of active clauses.
   */
  private ArrayList<Variable> activeVars;
  private HashSet<Variable> activeVarsSet;
  protected ArrayList<Variable> getActiveVars() { return activeVars; }
  protected boolean isActive(Variable var) { return activeVarsSet.contains(var); }

  /**
   * Run SAT solver to greedily find a satisfying assignment, ie MAP inference.
   */
  abstract boolean runSolve();

  /**
   * Run SAT solver to sample from the space of satisfying assignments.  Ideally this
   * will sample uniformly in the limit of iterations.
   */
  abstract boolean runSample();

  public SatSolver(ArrayList<Clause> clauses, ArrayList<Variable> vars, Config config) {
    this.activeClausesSet = new HashSet<Clause>(clauses);
    this.activeClauses = new ArrayList<Clause>(activeClausesSet);
    this.activeVarsSet = new HashSet<Variable>(vars);
    this.activeVars = new ArrayList<Variable>(activeVarsSet);
    this.config = config;
  }
}
