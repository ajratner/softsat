package softsat.sat;

import softsat.config.Config;
import softsat.objects.Clause;
import softsat.objects.Literal;
import softsat.objects.Variable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Brute force!  For use in sanity check-level testing.
 */
public class BruteForceSat extends SatSolver {

  private void init() {
    for (Variable var : getActiveVars()) { var.randomFlip(); }
  }

  private boolean checkSat() {
    for (Clause clause : getActiveClauses()) {
      if (!clause.isSat()) { return false; }
    }
    return true;
  }

  private boolean explore(int varId) {
    if (varId < getActiveVars().size()) {
      if (explore(varId + 1)) { return true; }
      getActiveVars().get(varId).flipIsTrue();
      if (explore(varId + 1)) { return true; }
      return false;
    } else {
      return checkSat();
    }
  }

  public boolean runSolve() {
    init();
    return explore(0);
  }

  public boolean runSample() { return runSolve(); }

  public BruteForceSat(ArrayList<Clause> clauses, ArrayList<Variable> vars, Config config) {
    super(clauses, vars, config);
  }
}
