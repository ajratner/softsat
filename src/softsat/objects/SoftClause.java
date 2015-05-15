package softsat.objects;

import java.util.ArrayList;
import softsat.objects.Clause;

public class SoftClause {
  public Clause clause;
  public int clusterId1;
  public int clusterId2;

  public SoftClause(Clause clause,int clusterId1, int clusterId2) {
    this.clause = clause;
    this.clusterId1 = clusterId1;
    this.clusterId2 = clusterId2;
  }
}
