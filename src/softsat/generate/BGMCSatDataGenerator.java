package softsat.generate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import softsat.objects.Variable;
import softsat.objects.Literal;
import softsat.objects.Clause;
import softsat.objects.SoftClause;

/**
 * Generate a dataset which is roughly a set of clusters of 'hard' clauses connected by 
 * 'soft' clauses.  More specifically, generates a set of random k-SAT clauses ('clusters') 
 * over atoms which are also members of soft (weight < inf) inter-cluster clauses
 */
public class BGMCSatDataGenerator {

  private Random rand = new Random();
  
  /**
   * Generate a set of atoms and clauses as random k-SAT instance
   */
  private ArrayList<Clause> generateRandomKSAT(int clusterId, int n, int k, double alpha) {

    ArrayList<Variable> variables = new ArrayList<Variable>();
    for (int varId = 0; varId < n; varId++) {
      variables.add(new Variable(clusterId,varId));
    }

    ArrayList<Clause> clauses = new ArrayList<Clause>();

    // Create m = alpha*n random hard clauses each having k literals
    int m = (new Double(Math.ceil(alpha*n))).intValue();

    for (int clauseId = 0; clauseId < m; clauseId++) {
      ArrayList<Literal> literals = new ArrayList<Literal>();

      Collections.shuffle(variables);
      for (int litId = 0; litId < k; litId++) {
        literals.add(new Literal(variables.get(litId),rand.nextBoolean()));
      }

      clauses.add(new Clause(literals));
    }
    return clauses;
  }

  public Data generateData(int nClusters, int n, int k, double alpha, int numSoftClauses, int clusterNodesPerSoftClause, double softWeightMean, double softWeightStd) {

    ArrayList<ArrayList<Clause> > clusters = new ArrayList<ArrayList<Clause> >();
    ArrayList<SoftClause> softClauses = new ArrayList<SoftClause>();

    // Generate the hard k-SAT clusters
    for (int clusterId=0; clusterId < nClusters; clusterId++) {
      clusters.add(generateRandomKSAT(clusterId,n,k,alpha));
    }

    // Generate the soft 'connector' inter-cluster clauses
    for (int softClauseId=0; softClauseId < numSoftClauses; softClauseId++) {
      int[] varIds = new int[clusterNodesPerSoftClause];
      boolean[] negateds = new boolean[clusterNodesPerSoftClause];

      for (int clusterNodeId = 0; clusterNodeId < clusterNodesPerSoftClause; clusterNodeId++) {
        varIds[clusterNodeId] = rand.nextInt(n);
        negateds[clusterNodeId] = rand.nextBoolean();
      }

      for (int clusterId1 = 0; clusterId1 < nClusters - 1; clusterId1++) {
        for (int clusterId2 = clusterId1 + 1; clusterId2 < nClusters; clusterId2++) {
          ArrayList<Literal> literals = new ArrayList<Literal>();
          for (int litId = 0; litId < varIds.length; litId++) {
            literals.add(new Literal(new Variable(clusterId1,varIds[litId]),negateds[litId]));
            literals.add(new Literal(new Variable(clusterId2,varIds[litId]),negateds[litId]));
          }
          /* [SERIAL] */
          Clause softClause = new Clause(literals,rand.nextGaussian()*softWeightStd + softWeightMean);
	  softClauses.add(new SoftClause(softClause,clusterId1,clusterId2));
        }
      }
    }

    // Set the pointers from Variable to Cluster for the set then return
    // [TODO URGENT] this needs to be after we add the soft clauses to the clusters
    DataGeneratorUtils.setClausesIn(clusters);
    return new Data(clusters,softClauses);
  }
} 
