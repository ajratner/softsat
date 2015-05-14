package softsat.objects;

import java.util.Random;

/**
 * A boolean variable, which can belong to a specified cluster of variables
 */
public class Variable {
  private int clusterId;
  private int varId;

  public int getClusterId() { return clusterId; }

  private boolean val = false;
  public boolean getValue() { return this.val; }

  public void randomFlip() {
    Random rand = new Random();
    val = rand.nextBoolean();
  }

  public String toString() {
    return "<" + clusterId + ":" + varId + ">";
  }

  public Variable(int clusterId, int varId) {
    this.clusterId = clusterId;
    this.varId = varId;
  }
}
