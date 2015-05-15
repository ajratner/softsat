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
public class BruteForceSat {
  private Config config;
  private int clusterId;
  private ArrayList<Clause> clauses;
  private ArrayList<Variable> vars;

  private void init() {
    for (Variable var : vars) {
      if (var.getClusterId() == clusterId) { var.randomFlip(); }
    }
  }

  private boolean checkSat() {
    for (Clause clause : clauses) {
      if (!clause.isSat()) { return false; }
    }
    return true;
  }

  private boolean explore(int varId) {
    if (varId < vars.size()) {
      if (explore(varId + 1)) { return true; }
      vars.get(varId).flipIsTrue();
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

  public BruteForceSat(int clusterId, ArrayList<Clause> clauses, Config config) {
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
