package softsat.util;

import softsat.objects.Clause;
import java.util.ArrayList;

public class SatChecker {
  
  public boolean allClausesSat(ArrayList<Clause> clauses) {
    for (Clause clause : clauses) {
      if (!clause.isSat()) { return false; }
    }
    return true;
  }
}
