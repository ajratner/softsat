package softsat.config;


/**
 * Specifies parameters for the various algorithms.
 */
public class Config {

  /**
   * Master debug level control
   */
  public int debug = 3;

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
  public int k = 3;

  /**
   * Ratio of clauses to variables in each hard SAT cluster
   */
  public double alpha = 2.0;

  /**
   * Number of inter-hard-cluster soft clauses
   */
  public int numSoftClauses = 2;
  public int clusterNodesPerSoftClause = 2;
  public double softWeightMean = 3;
  public double softWeightStd = 1;


  // SAMPLESAT CONFIG
  
  /**
   * The probability of taking a 'simulated annealing' (SA) step in SampleSat
   */
  public double pSimAnnealStep = 0.5;  // following the original SampleSat paper

  /**
   * The temperature parameter for the SA step
   */
  public double simAnnealTemp = 0.5;  // following the MC-SAT paper
  //public double simAnnealTemp = 0.1;  // following the original SampleSat paper

  /**
   * The probability of taking a random step in WalkSat
   */
  public double pRandomStep = 0.5;  // following Tuffy defaults

  /**
   * The total number of steps to take in SampleSat to get a sample
   */
  public long nSampleSatSteps = 20;

  /**
   * The total minimum number of SA steps to take in SampleSat mode after a solution
   * has been reached
   */
  //public int minStepsPostSatFound = 10;  // Following the MC-SAT paper
  public int minStepsPostSatFound = 0;

  /**
   * Number of random restarts to use (in WalkSat mode).  Empirically number of iterations
   * required often follows a power law, so randomly restarting is more effective than having
   * much larger number of steps.
   */
  public long nWalkSatRestarts = 3;

  // MC-SAT CONFIG

  public boolean MCSatRandInit = true;

  public long nMCSatSteps = 1000;

  public long MCSatBurnIn = 100;

  // CG CONFIG
  
  public boolean cgRunParallel = true;
}
