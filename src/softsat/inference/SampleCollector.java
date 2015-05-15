package softsat.inference;

import java.util.ArrayList;
import softsat.objects.Literal;
import softsat.objects.Clause;
import softsat.config.Config;

public abstract class SampleCollector {
  protected Config config;
  private int numSamples = 0;

  private ArrayList<ArrayList<Integer> > sampleCounts = new ArrayList<ArrayList<Integer> >();
  protected ArrayList<ArrayList<Clause> > clusters = new ArrayList<ArrayList<Clause> >();

  public ArrayList<ArrayList<Clause> > getClusters() { return clusters; };

  protected void sweep() { }

  private void collectSample() {
    for (int clusterId = 0; clusterId < clusters.size(); clusterId++) {
      for (Clause clause : clusters.get(clusterId)) {
        for (int litId = 0; litId < clause.getLiterals().size(); litId++) {
          Literal lit = clause.getLiterals().get(litId);
          // subtle, make sure not to double count
          if (lit.getVar().getIsTrue() && lit.getVar().getClusterId() == clusterId) {
            Integer priorCounts = sampleCounts.get(clusterId).get(litId);
            sampleCounts.get(clusterId).set(litId,priorCounts + 1);
          }
        }
      }
    }
    numSamples++;
  }

  public void run(int numSweeps,int collectSampleFrequency) {
    for (int sweep = 0; sweep < numSweeps; sweep++) {
      sweep();
      if (sweep % collectSampleFrequency == 0) { collectSample(); }
    }
  }

  public SampleCollector(Config config) { this.config = config; }

}
