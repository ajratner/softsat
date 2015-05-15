package softsat.inference;

import java.util.ArrayList;
import softsat.objects.Literal;
import softsat.objects.Clause;
import softsat.objects.SoftClause;
import softsat.generate.Data;
import softsat.main.Config;

public class VanillaMCSat extends SampleCollector {

  @Override
  protected void sweep() {
    MCSat mcsat = new MCSat(0,true,clusters.get(0),config);
    mcsat.sample(0);
  }

  public VanillaMCSat(Data data,Config config) {
    super(config);
    ArrayList<Clause> singletonCluster = new ArrayList<Clause>();
    for (ArrayList<Clause> cluster : data.clusters) { singletonCluster.addAll(cluster); }
    for (SoftClause softClause : data.softClauses) { singletonCluster.add(softClause.clause); }
    ArrayList<ArrayList<Clause> > degenerateClusters = new ArrayList<ArrayList<Clause> >();
    clusters.add(singletonCluster);
  }

}
