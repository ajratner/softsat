package softsat.inference;

import java.util.ArrayList;
import softsat.objects.Literal;
import softsat.objects.Clause;
import softsat.objects.SoftClause;
import softsat.generate.Data;
import softsat.config.Config;

public class BGMCSat extends SampleCollector {

  
  @Override
  protected void sweep() {
    for (int clusterId = 0; clusterId < clusters.size(); clusterId++) {
      MCSat mcsat = new MCSat(clusterId,false,clusters.get(clusterId),config);
      mcsat.sample(0);
    }
  }

  public BGMCSat(Data data,Config config) {
    super(config);
    for (ArrayList<Clause> cluster : data.clusters) { clusters.add(new ArrayList<Clause>(cluster)); }

    for (SoftClause softClause : data.softClauses) { 
      clusters.get(softClause.clusterId1).add(softClause.clause);
      clusters.get(softClause.clusterId2).add(softClause.clause);
    }
  }

}
