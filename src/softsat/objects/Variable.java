package softsat.objects;

public class Variable {
  private int cluster_id;
  private int var_id;

  private boolean val = false;

  public String toString() {
    return "<" + cluster_id + ":" + var_id + ">";
  }

  public Variable(int cluster_id, int var_id) {
    this.cluster_id = cluster_id;
    this.var_id = var_id;
  }
}
