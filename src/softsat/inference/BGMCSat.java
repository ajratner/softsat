package softsat.inference;

import java.util.ArrayList;
import softsat.objects.Literal;
import softsat.objects.Clause;
import softsat.objects.SoftClause;
import softsat.generate.Data;

public class BGMCSat extends SampleCollector {

  @Override
  protected void sweep() {
    for (int clusterId = 0; clusterId < clusters.size(); clusterId++) {
      MCSat mcsat = new MCSat(clusterId,clusters.get(clusterId));
      mcsat.sample(0);
    }
  }

  public BGMCSat(Data data) {
    for (ArrayList<Clause> cluster : data.clusters) { clusters.add(cluster); }

    for (SoftClause softClause : data.softClauses) { 
      clusters.get(softClause.clusterId1).add(softClause.clause);
      clusters.get(softClause.clusterId2).add(softClause.clause);
    }
  }

}
