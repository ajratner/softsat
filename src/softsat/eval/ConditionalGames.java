package softsat.eval;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

import softsat.config.Config;
import softsat.objects.Clause;
import softsat.objects.Variable;
import softsat.inference.DecompMCSat;

public class ConditionalGames {
  private Random rand = new Random();
  private Config config;
  private DecompMCSat Q;
  private DecompMCSat R;
  private ArrayList<Variable> vars;
  private ArrayList<Clause> clauses;
  private double totalMarginalDiff;
  private double totalAbsMarginalDiff;

  /**
   * Main evaluation method following the Conditional Games paper.  Returns S_4(Q,R).
   */
  public double eval() {
    return play(Q,R,false) - play(R,Q,false) - play(Q,R,true) + play(R,Q,true);
  }

  /**
   * Get the marginal difference stats (wrt Q - R).
   * Must be called after playing at least one round.
   */
  public double getAvgMarginalDiff() { return totalMarginalDiff / vars.size(); }
  public double getAvgAbsMarginalDiff() { return totalAbsMarginalDiff / vars.size(); }
  
  /**
   * One round of the game.  With cpMaximizingV, this is V^+(Q,R).
   */
  private double play(DecompMCSat mp, DecompMCSat cp, boolean cpMaximizingV) {
    if (config.debug > 0) {
      String goal = cpMaximizingV ? "CP maximizing V" : "MP maximizing V";
      System.out.println("[CG]: Playing round: MP = " + mp + ", CP = " + cp + ", " + goal);
    }
    
    // unfix all the vars first
    for (Variable var : vars) { var.unfix(); }

    // Play the game by iterating through the vars and playing a 'round' for each one
    double logSumMarginals = 0.0;
    boolean fixedVal;
    int nVars = vars.size();
    int i = 0;
    totalMarginalDiff = 0.0;
    totalAbsMarginalDiff = 0.0;
    for (Variable var : vars) {
      double mpMarginal = mp.estimateMarginal(var, true);
      double cpMarginal = mp.estimateMarginal(var, true);
      if (config.debug > 1) {
        System.out.println("MP marginal Q(x=T|...) = " + mpMarginal);
        System.out.println("CP marginal R(x=T|...) = " + cpMarginal);
      }
      double marginalDiff = (mp == Q) ? mpMarginal - cpMarginal : cpMarginal - mpMarginal;
      totalMarginalDiff += marginalDiff;
      totalAbsMarginalDiff += Math.abs(marginalDiff);
      
      // Note: minor modification for hard constraints
      // Assume they agree in these cases!
      if (mpMarginal == 1 || mpMarginal == 0 || cpMarginal == 1 || cpMarginal == 0) {
        assert mpMarginal == cpMarginal;
        fixedVal = (mpMarginal == 1);
      } else {
        double qRT = mpMarginal / cpMarginal;
        double qRF = (1.0 - mpMarginal) / (1.0 - cpMarginal);

        if (qRT < qRF) {
          fixedVal = !cpMaximizingV;
        } else if (qRT > qRF) {
          fixedVal = cpMaximizingV;
        } else {
          fixedVal = rand.nextBoolean();
        }
      }
      var.setFixedAt(fixedVal);
      logSumMarginals += Math.log( fixedVal ? mpMarginal : (1.0 - mpMarginal) );
      i += 1;
      if (config.debug > 0) {
        System.out.println("[CG]: (" + i + "/" + nVars + ") Variable fixed @ " + var);
      }
    }

    // compute score V = \log(\prod_i q_i(x_i^*) / \prod_c \phi_c(x_c^*))
    //                 = \sum_i \log(q_i(x_i^*)) - \sum_c \log(\phi_c(x_c^*))
    return logSumMarginals - unNormLogProb();
  }

  /**
   * Computes the unnormalized joint probability given the current variable assignment.
   * TODO: move this to a util class?
   */
  private double unNormLogProb() {
    double logSum = 0.0;
    for (Clause clause : clauses) {
      if (clause.isHard() && !clause.isSat()) { return 0.0; }
      if (!clause.isHard() && clause.isSat()) { logSum += clause.getLogWeight(); }
    }
    return logSum;
  }

  /**
   * Default constructor accepts a list of clauses, vars and two conditional marginal estimation
   * objects with an estimateMarginal(Variable var, boolean resample) function
   */
  public ConditionalGames(ArrayList<Clause> clauses, ArrayList<Variable> vars, DecompMCSat Q, DecompMCSat R, Config config) {
    this.clauses = clauses;
    this.vars = vars;
    this.Q = Q;
    this.R = R;
    this.config = config;
  }
}
