package softsat.objects;

import java.util.ArrayList;
import java.util.HashMap;

/**
  * A weighted clause, which is a disjunction of literals
  */
public class Clause {

  /**
   * The log weight of the clause.  In each loop of MC-SAT, the clause will be satisfied
   * w.p. 1 - exp(-logWeight)
   */
  public double logWeight = Double.POSITIVE_INFINITY;

  public boolean isHard() {
    return logWeight == Double.POSITIVE_INFINITY;
  }

  /**
   * The literals comprising the clause.  An ArrayList of Literal objects, each of which
   * represents either the negation or not of a single Variable which it references.
   */
  public ArrayList<Literal> literals;

  /**
   * Whether the clause is active.  In the context of MC-SAT for example: each iteration MC-SAT
   * will use a SAT solver (e.g. SampleSAT) to sample from the satisfying assignments of all
   * active clauses (as hard clauses).
   * [SERIAL]
   */
  public boolean active = false;

  public String toString() {
    StringBuilder s = new StringBuilder();
    for (Literal l : literals)
      s.append("  " + l.toString());
    return "( " + s.toString() + " )  w = " + String.valueOf(weight);
  }

  public Clause(ArrayList<Literal> literals) {
    this.literals = literals;
  }

  public Clause() {
    this.literals = new ArrayList<Literal>();
  }
}
