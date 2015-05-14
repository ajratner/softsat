package softsat.inference;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;
import softsat.objects.Literal;
import softsat.objects.Clause;


public class MCSat {

  private int clusterId;
  private ArrayList<Clause> clauses;

  /* [TODO] these don't exist yet! */
  private SATSolver solver = new SATSolver(clauses);
  private SATSampler sampler = new SATSampler(clauses);

  private Random rand = new Random();

  public MCSat(int clusterId,ArrayList<Clause> clauses) {
    this.clusterId = clusterId;
    this.clauses = clauses;
  }

  public void sample(int numIterations) {
    // Activate hard clauses
    for (Clause clause : clauses) { clause.setActive(clause.isHard() ? true : false); }
    solver.solveSAT();

    for (int iter = 0; iter < numIterations; iter++) {
      for (Clause clause : clauses) { clause.setActive(rand.nextFloat() < 1 - Math.exp(- clause.getLogWeight()) ? true : false); }
      sampler.sampleSAT();
    }
  }

}
