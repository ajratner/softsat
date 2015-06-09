package softsat.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
  * A weighted clause, which is a disjunction of literals
  */
public class Clause {
  Random rand = new Random();

  /**
   * The log weight of the clause.  In each loop of MC-SAT, the clause will be satisfied
   * w.p. 1 - exp(-logWeight)
   */
  private double logWeight = Double.POSITIVE_INFINITY;
  public double getLogWeight() { return logWeight; }
  public void setLogWeight(double lw) { logWeight = lw; }
  public boolean isHard() { return logWeight == Double.POSITIVE_INFINITY; }

  /**
   * The literals comprising the clause.  An ArrayList of Literal objects, each of which
   * represents either the negation or not of a single Variable which it references.
   */
  private ArrayList<Literal> literals;
  public ArrayList<Literal> getLiterals() { return literals; }
  public void addLiteral(Literal literal) { literals.add(literal); }
  
  public ArrayList<Variable> getVars() {
    ArrayList<Variable> vars = new ArrayList<Variable>();
    for (Literal literal : literals) { vars.add(literal.getVar()); }
    return vars;
  }

  /**
   * Tests whether the clause is satisfied given the Variable's assignments.
   */
  public boolean isSat() {
    for (Literal literal : literals) {
      if (literal.isSat()) { return true; }
    }
    return false;
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    for (Literal literal : literals)
      s.append("  " + literal.toString());
    return "( " + s.toString() + " )  logWeight = " + String.valueOf(logWeight);
  }

  public Clause() { this.literals = new ArrayList<Literal>(); }

  public Clause(ArrayList<Literal> literals) { this.literals = literals; }

  public Clause(ArrayList<Literal> literals, double logWeight) {
    this.literals = literals;
    this.logWeight = logWeight;
  }
}
