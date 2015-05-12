package softsat.objects;

import java.util.ArrayList;
import java.util.HashMap;

/**
  * A weighted clause- a disjunction of literals corresponding to Atoms
  */
public class Clause {
  public String id = null;

  // Weight of clause- by default is set as a hard clause
  public double weight = Double.POSITIVE_INFINITY;

  // Get whether this is a 'hard' or 'soft' clause
  public boolean isHard() {
    return Math.abs(weight) == Double.POSITIVE_INFINITY;
  }

  // The list of Atom ids
  public ArrayList<String> atomIds;

  // Whether the literals corresponding to atoms are negated (true) or not (false)
  public ArrayList<Boolean> negated;

  // Whether the clause is satisfied by the current assignment
  public boolean sat = false;

  public boolean isSat() {
    return sat;
  }

  // Returns string representation of Clause object
  public String toString() {
    ArrayList<String> literals = new ArrayList<String>();
    for (int i = 0; i < atomIds.size(); i++)
      literals.add( negated.get(i) ? "-" + atomIds.get(i) : atomIds.get(i) );
    return "( " + String.join("  ", literals) + " )  w=" + String.valueOf(weight);
  }

  // Create a Clause object from list of atom ids and negated bools
  public Clause(String cid, ArrayList<String> ids, ArrayList<Boolean> negs) {
    id = cid;
    atomIds = ids;
    negated = negs;
  }

  public Clause(String cid) {
    id = cid;
    atomIds = new ArrayList<String>();
    negated = new ArrayList<Boolean>();
  }
}
