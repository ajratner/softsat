package softsat.inference;

import java.util.ArrayList;

import softsat.objects.Literal;
import softsat.objects.Clause;


public class BGMCSat {
  private int numSamples = 0;

  private ArrayList<ArrayList<Integer> > sampleCounts;
  private ArrayList<ArrayList<Clause> > clusters;

  private void sweep() {
    for (int clusterId = 0; clusterId < clusters.size(); clusterId++) {
      MCSat mcsat = new MCSat(clusterId,clusters.get(clusterId));
      mcsat.sample(); // [TODO] num iters?
    }
  }

  private void collectSample() {
    for (int clusterId = 0; clusterId < clusters.size(); clusterId++) {
      for (Clause clause : clusters.get(clusterId)) {
	for (int litId = 0; litId < clause.literals.size(); litId++) {
	  Literal lit = clause.literals.get(litId);
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

  public BGMCSat(ArrayList<ArrayList<Clause> > clusters) { this.clusters = clusters; }

}
