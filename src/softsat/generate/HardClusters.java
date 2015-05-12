package softsat.generate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import softsat.objects.Clause;
import softsat.objects.Atom;

/**
 * Generate a dataset which is roughly a set of clusters of 'hard' clauses connected by 
 * 'soft' clauses.  More specifically, generates a set of random k-SAT clauses ('clusters') 
 * over atoms which are also members of soft (weight < inf) inter-cluster clauses
 */
public class HardClusters {
  Random rand = new Random();
    
  public ArrayList<Atom> atoms;
  public ArrayList<Clause> clauses;

  /**
   * Generate a set of atoms and clauses as random k-SAT instance
   */
  public void generateRandomKSAT(int n, int k, double alpha, String clustId) {

    // Create n atoms
    ArrayList<String> atomIds = new ArrayList<String>();
    for (int i = 0; i < n; i++) {
      String aid = clustId + "_" + Integer.toString(i);
      atoms.add(new Atom(aid));
      atomIds.add(aid);
    }

    // Create m = alpha*n random hard clauses each having k literals
    Double mm = Math.ceil(alpha*n);
    int m = mm.intValue();
    for (int i = 0; i < m; i++) {
      Collections.shuffle(atomIds);
      Clause c = new Clause(Integer.toString(i));
      for (int j = 0; j < k; j++) {
        c.atomIds.add(atomIds.get(j));
        c.negated.add(rand.nextBoolean());
      }
    clauses.add(c);
    }
  }

  /**
  * Main generation function.  Generates hard k-SAT clusters with soft clause inter-cluster
  * connectors.
  */
  public void generate(int nClusters, int n, int k, double alpha, double softConnectors, double softWeightMean, double softWeightStd, int clusterNodesPerSoftConnector) {
    atoms = new ArrayList<Atom>();
    clauses = new ArrayList<Clause>();

    // Generate the hard k-SAT clusters
    for (int i=0; i < nClusters; i++)
      generateRandomKSAT(n, k, alpha, Integer.toString(i));

    // Generate soft inter-cluster connector clauses
    //int N = nClusters * n;
    //for (int i=0; i < (softConnectors * N).intValue(); i++) {
    //  Clause sc = Clause("SC_" + i)
    //}
  }

  /**
   * Get all the clauses associated with a cluster, including connected soft clauses
   */
  public ArrayList<Clause> getCluster(String clustId) {
    // TODO
    return new ArrayList<Clause>();
  }

  public void print() {
    for (Clause c : clauses) {
      System.out.println(c.toString());
    }
  }
} 
