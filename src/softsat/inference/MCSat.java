package softsat.inference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
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

  public HashMap<Variable, int[]> sample() {

    // Initialize sample counts
    HashMap<Variable, int[]> sampleCounts = new HashMap<Variable, int[]>();
    for (Variable var : activeVars) { sampleCounts.put(var, new int[2]); }

    for (long iter = -1; iter < config.nMCSatSteps; iter++) {

      // Add all clauses which were satisfied the previous iteration wp 1-e^{-w}
      // On iteration -1 initialize by running WalkSat over the hard clauses only
      ArrayList<Clause> Mc = new ArrayList<Clause>();
      HashSet<Variable> Mv = new HashSet<Variable>();
      for (Clause clause : activeClauses) {
        if (clause.isHard() || (iter >= 0 && clause.isSat() && rand.nextFloat() < 1 - Math.exp(-clause.getLogWeight()))) {
          Mc.add(clause);
          for (Variable var : clause.getVars()) {
            if (activeVarsSet.contains(var)) { Mv.add(var); }
          }
        }
      }

      // Sample from the set of satisfying assignments using the SAT solver
      SampleSat satSolver = new SampleSat(Mc, new ArrayList<Variable>(Mv), config);
      if (iter >= 0) {
        assert satSolver.runSample();
      } else {
        assert satSolver.runSolve();
      }

      // Update sample counts
      if (iter > config.MCSatBurnIn) {
        for (Variable var : Mv) {
          sampleCounts.get(Mv)[var.getIsTrue() ? 1 : 0] += 1;
        }
      }
    }
    return sampleCounts;
  }

  public MCSat(ArrayList<Clause> clauses, ArrayList<Variable> vars, Config config) {
    this.activeClausesSet = new HashSet<Clause>(clauses);
    this.activeClauses = new ArrayList<Clause>(activeClausesSet);
    this.activeVarsSet = new HashSet<Variable>(vars);
    this.activeVars = new ArrayList<Variable>(activeVarsSet);
    this.config = config;
  }
}
