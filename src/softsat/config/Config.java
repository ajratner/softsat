package softsat.config;


/**
 * Specifies parameters for the various algorithms.
 */
public class Config {

  // DATASET CONFIG
  
  /**
   * Number of hard SAT clusters
   */
  public int nClusters = 3;

  /**
   * Number of variables per hard cluster
   */
  public int n = 4;

  /**
   * Number of literals per hard cluster clause
   */
  public int k = 2;

  /**
   * Ratio of clauses to variables in each hard SAT cluster
   */
  public double alpha = 2.0;

  /**
   * Number of inter-hard-cluster soft clauses
   */
  public int numSoftClauses = 2;
  public int clusterNodesPerSoftClause = 2;
  public double softWeightMean = 10;
  public double softWeightStd = 1;


  // SAMPLESAT CONFIG
  
  /**
   * Whether to randomly initialize the assignments to the active variables each iteration of
   * the SAT solver / sampler
   */
  public boolean satResetAssignments = true;

  /**
   * The probability of taking a 'simulated annealing' (SA) step in SampleSat
   */
  public double pSimAnnealStep = 0.5;  // following the original SampleSat paper

  /**
   * The temperature parameter for the SA step
   */
  public double simAnnealTemp = 0.1;  // following the original SampleSat paper

  /**
   * The probability of taking a random step in WalkSat
   */
  public double pRandomStep = 0.5;  // following Tuffy defaults

  /**
   * The total number of steps to take in SampleSat to get a sample
   */
  public long nSampleSatSteps = 10000;

  /**
   * Whether all the clusters should be active.  E.g. for 'vanilla' MC-SAT.
   */
  public boolean allVarsActive = false;

  public boolean sampleSatDebug = true;

}
