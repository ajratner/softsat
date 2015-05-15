package softsat.objects;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;

/**
 * A boolean variable, which can belong to a specified cluster of variables
 */
public class VariableId {
  private int clusterId;
  private int index;

  public int getClusterId() { return clusterId; }
  public int getIndex() { return index; }

  public VariableId(int clusterId, int index) {
    this.clusterId = clusterId;
    this.index = index;
  }

  @Override
  public int hashCode() {
    return clusterId * 2 + index * 3;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof VariableId)) { return false; }
    if (obj == this) { return true; }

    VariableId rhs = (VariableId) obj;
    return clusterId == rhs.getClusterId() && index == rhs.getIndex();
  }

  public String toString() {
    return clusterId + ":" + index;
  }

}
