package softsat.inference;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;
import softsat.objects.Literal;
import softsat.objects.Clause;
import softsat.sat.SampleSat;
import softsat.config.Config;


public class MCSat {

  private int clusterId;
  private ArrayList<Clause> clauses;
  private Config config;

  private SampleSat satSolver = new SampleSat(clusterId, clauses, config);

  private Random rand = new Random();

  private boolean allClustersActive;

  public MCSat(int clusterId,boolean allClustersActive, ArrayList<Clause> clauses, Config config) {
    this.clusterId = clusterId;
    this.allClustersActive = allClustersActive;
    this.clauses = clauses;
    this.config = config;
  }

  // [TODO] have this as part of the config
  public void sample(int numIterations) {

    // Activate hard clauses
    for (Clause clause : clauses) { clause.setActive(clause.isHard() ? true : false); }

    // Run SampleSat in WalkSat mode (take no SA steps) to initialize
    satSolver.runSolve();

    for (int iter = 0; iter < numIterations; iter++) {
      for (Clause clause : clauses) { clause.setActive(rand.nextFloat() < 1 - Math.exp(- clause.getLogWeight()) ? true : false); }
      satSolver.runSample();
    }
  }

}
