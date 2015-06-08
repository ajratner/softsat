package softsat.objects;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;

/**
 * A boolean variable, which can belong to a specified cluster of variables
 */
public class Variable {

  private VariableId varId;
  public VariableId getVariableId() { return varId; }

  public int getClusterId() { return varId.getClusterId(); }
 
  /**
   * Whether the variable's value is fixed.  Eg for conditional probabilities
   */
  private boolean isFixed = false;
  public boolean getIsFixed() { return isFixed; }
  public void setFixedAt(boolean fixedVal) {
    isTrue = fixedVal;
    isFixed = false;
  }
  public void unfix() { isFixed = false; }

  private boolean isTrue = false;
  public boolean getIsTrue() { return isTrue; }
  public void flipIsTrue() { 
    assert !isFixed;
    isTrue = !isTrue;
  }

  /**
   * A fixed list of the clauses that this Variable is in, eg agnostic to active/inactive status
   */ 
  private HashSet<Clause> clausesIn = new HashSet<Clause>();
  public ArrayList<Clause> getClausesIn() { return new ArrayList<Clause>(clausesIn); }
  public void addToClausesIn(Clause clause) { this.clausesIn.add(clause); }

  /**
   * The make and break counts. These represent the number of clauses that would be 
   * satisfied or unsatisfied respectively by changing this variable's value
   */
  private int makeCount;
  private int breakCount;
  public void incBreakCount() { breakCount += 1; }
  public void decBreakCount() { breakCount -= 1; }
  public void incMakeCount() { makeCount += 1; }
  public void decMakeCount() { makeCount -= 1; }
  public void resetCounts() {
    makeCount = 0;
    breakCount = 0;
  }
  public int getBreakCount() { return breakCount; }
  public int getCost() { return breakCount - makeCount; }

  public void randomFlip() {
    assert !isFixed;
    Random rand = new Random();
    isTrue = rand.nextBoolean();
  }

  public String toString() {
    return "<" + varId.toString() + ":" + isTrue + ">";
  }

  public Variable(VariableId varId) {
    this.varId = varId;
  }
}
