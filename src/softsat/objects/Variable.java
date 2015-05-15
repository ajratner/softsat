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
 
  private boolean isTrue = false;
  public boolean getIsTrue() { return isTrue; }

  /**
   * A fixed list of the clauses that this Variable is in, eg agnostic to active/inactive status
   */ 
  private ArrayList<Clause> clausesIn = new ArrayList<Clause>();
  public ArrayList<Clause> getClausesIn() { return clausesIn; }
  public void addToClausesIn(Clause clause) { this.clausesIn.add(clause); }

  /**
   * The make and break counts. These represent the number of clauses that would be 
   * satisfied or unsatisfied respectively by changing this variable's value
   */
  private int makeCount;
  private int breakCount;
  public int getBreakCount() { return breakCount; }
  public int getDelta() { return makeCount - breakCount; }

  /**
   * Set the initial make / break counts.  This will not change any other variables'
   * make/break counts
   */
  public void setMakeBreakCounts() {
    makeCount = 0;
    breakCount = 0;
    for (Clause clause : clausesIn) {
      int satCount = 0;
      boolean thisSat = false;
      for (Literal literal : clause.getLiterals()) {
        if (literal.isSat()) { 
          satCount += 1;
          if (literal.getVar() == this) { thisSat = true; } 
        }
      }
      if (thisSat && satCount == 1) { breakCount += 1; }
      if (!thisSat && satCount == 0) { makeCount += 1; }
    }
  }

  // TODO: update make break counts

  // TODO: flip + update?

  public void randomFlip() {
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
