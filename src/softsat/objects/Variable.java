package softsat.objects;

import java.util.Random;
import java.util.HashSet;

/**
 * A boolean variable, which can belong to a specified cluster of variables
 */
public class Variable {
  private int clusterId;
  public int getClusterId() { return clusterId; }

  private int varId;
 
  private boolean isTrue = false;
  public boolean getIsTrue() { return isTrue; }

  /**
   * A fixed list of the clauses that this Variable is in, eg agnostic to active/inactive status
   */ 
  private ArrayList<Clause> clausesIn;
  public ArrayList<Clause> getClausesIn() { return clausesIn; }
  public void setClausesIn(ArrayList<Clause> clausesIn) { this.clausesIn = clausesIn; }

  public void randomFlip() {
    Random rand = new Random();
    isTrue = rand.nextBoolean();
  }

  public String toString() {
    return "<" + clusterId + ":" + varId + ">";
  }

  public Variable(int clusterId, int varId) {
    this.clusterId = clusterId;
    this.varId = varId;
  }
}
