package softsat.main;


/**
 * Specifies parameters for the various algorithms.
 */
public class Config {

  /**
   * Whether to randomly initialize the assignments to the active variables each iteration of
   * the SAT solver / sampler
   */
  public boolean satResetAssignments;

  /**
   * The probability of taking a 'simulated annealing' (SA) step in SampleSat
   */
  public double pSimAnnealStep;

  /**
   * The temperature parameter for the SA step
   */
  public double simAnnealTemp;

  /**
   * The probability of taking a random step in WalkSat
   */
  public double pRandomStep;

  /**
   * The total number of steps to take in SampleSat to get a sample
   */
  public long nSampleSatSteps;

  /**
   * Whether all the clusters should be active.  E.g. for 'vanilla' MC-SAT.
   */
  public boolean allVarsActive;

}
