package softsat.generate;

import java.util.ArrayList;
import softsat.objects.Variable;
import softsat.objects.Clause;

/**
 * Utilities for dataset generation.  I.e. operations which are somewhat agnostic to 
 * the structure of the generated data
 */
public class DataGeneratorUtils {

  /**
   * Given a list of clauses, iterates through each referenced Variable and sets
   * the clausesIn array
   */
  public static void setClausesIn(ArrayList<ArrayList<Clause>> clusters) {
    for (ArrayList<Clause> clauses : clusters) {
      for (Clause clause : clauses) {
        for (Variable var : clause.getVars()) { var.addToClausesIn(clause); }
      }
    }
  }
}
