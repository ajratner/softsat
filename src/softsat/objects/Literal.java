package softsat.objects;

/**
 * A literal corresponding to either the negation or not of a variable
 */
public class Literal {
  private Variable var;
  private boolean sign;
  
  public String toString() {
    return (sign ? "-" : "") + var.toString();
  }

  public Literal(Variable var, boolean sign) {
    this.var = var;
    this.sign = sign;
  }

}
