package softsat.inference;

import java.util.ArrayList;
import java.util.HashSet;
import java.lang.Math;
import java.util.Random;
import softsat.objects.Clause;
import softsat.objects.Literal;
import softsat.objects.Variable;
import softsat.sat.SampleSat;
import softsat.config.Config;

public class MCSat {
  private Random rand = new Random();

  private ArrayList<Clause> activeClauses;
  private HashSet<Clause> activeClausesSet;
  private ArrayList<Variable> activeVars;
  private HashSet<Variable> activeVarsSet;
  private Config config;

  public void sample() {
    for (long iter = 0; iter < config.nMCSatSteps + 1; iter++) {

      // Add all clauses which were satisfied the previous iteration wp 1-e^{-w}
      // On iteration 0 initialize by running WalkSat over the hard clauses only
      ArrayList<Clause> Mc = new ArrayList<Clause>();
      ArrayList<Variable> Mv = new ArrayList<Variable>();
      for (Clause clause : activeClauses) {
        if (clause.isHard() || (iter > 0 && clause.isSat() && rand.nextFloat() < 1 - Math.exp(-clause.getLogWeight()))) {
          Mc.add(clause);
          for (Variable var : clause.getVars()) {
            if (activeVarsSet.contains(var)) { Mv.add(var); }
          }
        }
      }
      SampleSat satSolver = new SampleSat(Mc, Mv, config);
      if (iter > 0) {
        satSolver.runSample();
      } else {
        satSolver.runSolve();
      }

      // TODO: handle case where satSample returns false?  Should revert to the previous var assignment if so...
      // TODO: sample here!
    }
  }

  public MCSat(ArrayList<Clause> clauses, ArrayList<Variable> vars, Config config) {
    this.activeClausesSet = new HashSet<Clause>(clauses);
    this.activeClauses = new ArrayList<Clause>(activeClausesSet);
    this.activeVarsSet = new HashSet<Variable>(vars);
    this.activeVars = new ArrayList<Variable>(activeVarsSet);
    this.config = config;
  }
}
