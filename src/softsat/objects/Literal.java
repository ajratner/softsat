package softsat.objects;

import softsat.objects.Variable;

/**
 * A literal corresponding to either the negation or not of a variable
 */
public class Literal {
  private Variable var;
  public Variable getVar() { return var; }
  private boolean negated;

  public boolean isSat() { return var.getValue() != negated; }
  
  public String toString() { return (negated ? "-" : "") + var.toString(); }

  public Literal(Variable var, boolean negated) {
    this.var = var;
    this.negated = negated;
  }

}
