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
public class HardClusters extends Dataset {

  /**
   * Generate a set of atoms and clauses as random k-SAT instance
   */
  public void generateRandomKSAT(int n, int k, double alpha, int clusterId) {

    // Create n atoms
    ArrayList<String> atomIds = new ArrayList<String>();
    for (int i = 0; i < n; i++) {
      String atomId = Atom.idString(clusterId, i);
      atoms.add(new Atom(atomId));
      atomIds.add(atomId);
    }

    // Create m = alpha*n random hard clauses each having k literals
    int m = (new Double(Math.ceil(alpha*n))).intValue();
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

    // Generate the hard k-SAT clusters
    for (int i=0; i < nClusters; i++)
      generateRandomKSAT(n, k, alpha, i);

    // Generate soft inter-cluster connector clauses
    int N = nClusters * n;
    int c1, c2;
    for (int i=0; i < softConnectors * N; i++) {
      Clause sc = new Clause("SC_" + i);

      // Add nodes from two clusters to the soft connector clause
      c1 = rand.nextInt(nClusters);
      for (int j=0; j < clusterNodesPerSoftConnector; j++) {
        sc.atomIds.add(Atom.idString(c1, rand.nextInt(n)));
        sc.negated.add(rand.nextBoolean());
      }
      c2 = rand.nextInt(nClusters);
      while (c2 == c1)
        c2 = rand.nextInt(nClusters);
      for (int j=0; j < clusterNodesPerSoftConnector; j++) {
        sc.atomIds.add(Atom.idString(c2, rand.nextInt(n)));
        sc.negated.add(rand.nextBoolean());
      }

      // Sample the weight of the soft connector clause from Normal
      sc.weight = rand.nextGaussian()*softWeightStd + softWeightMean;
      clauses.add(sc);
    }
  }

  /**
   * Get all the clauses associated with a cluster, including connected soft clauses
   */
  public ArrayList<Clause> getCluster(String clustId) {
    // TODO
    return new ArrayList<Clause>();
  }
} 
