package softsat.objects;

/**
 * A boolean variable, which can belong to a specified cluster of variables
 */
public class Variable {
  private int clusterId;
  private int varId;

  private boolean isTrue = false;

  public int getClusterId() { return clusterId; }
  public boolean getIsTrue() { return isTrue; }

  public String toString() {
    return "<" + clusterId + ":" + varId + ">";
  }

  public Variable(int clusterId, int varId) {
    this.clusterId = clusterId;
    this.varId = varId;
  }
}
