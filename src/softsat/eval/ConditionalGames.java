package softsat.eval;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
import java.lang.Math;

import softsat.config.Config;
import softsat.objects.Clause;
import softsat.objects.Literal;
import softsat.objects.Variable;
import softsat.inference.DecompMCSat;
import softsat.generate.Data;

public class ConditionalGames {
  private Random rand = new Random();
  private Config config;
  private Data data;
  private String mode1;
  private String mode2;

  /**
   * Main evaluation method following the Conditional Games paper.  Returns S_4(Q,R).
   */
  public double eval() {
    ConditionalGamesS game1 = createGame(mode1, mode2, false);
    ConditionalGamesS game2 = createGame(mode2, mode1, false);
    ConditionalGamesS game3 = createGame(mode1, mode2, true);
    ConditionalGamesS game4 = createGame(mode2, mode1, true);

    // parallel execution
    if (config.cgRunParallel) {
      Thread thread1 = new Thread(game1);
      thread1.setDaemon(true);
      thread1.start();

      Thread thread2 = new Thread(game2);
      thread2.setDaemon(true);
      thread2.start();

      Thread thread3 = new Thread(game3);
      thread3.setDaemon(true);
      thread3.start();

      Thread thread4 = new Thread(game4);
      thread4.setDaemon(true);
      thread4.start();
      
      try {
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
      } catch (InterruptedException e) {
        assert false;
      }
    } else {
      game1.run();
      game2.run();
      game3.run();
      game4.run();
    }
    return game1.getScore() - game2.getScore() - game3.getScore() + game4.getScore();
  }

  private ConditionalGamesS createGame(String mpMode, String cpMode, boolean polarity) {
    Data dataCopy = data.deepCopy();
    DecompMCSat mp = new DecompMCSat(dataCopy, config, mpMode);
    DecompMCSat cp = new DecompMCSat(dataCopy, config, cpMode);
    ArrayList<Clause> clauses = unpackClauses(dataCopy);
    ArrayList<Variable> vars = unpackVars(dataCopy);
    return new ConditionalGamesS(clauses, vars, mp, cp, polarity, config);
  }

  // Get the clause and var sets
  // TODO: these should probably be moved to a utility class
  private ArrayList<Clause> unpackClauses(Data data) {
    HashSet<Clause> clauseSet = new HashSet<Clause>();
    for (ArrayList<Clause> cluster : data.clusters) { clauseSet.addAll(cluster); }
    return new ArrayList<Clause>(clauseSet);
  }
  private ArrayList<Variable> unpackVars(Data data) {
    HashSet<Variable> varSet = new HashSet<Variable>();
    for (ArrayList<Clause> cluster : data.clusters) {
      for (Clause clause : cluster) {
        for (Variable var : clause.getVars()) { varSet.add(var); }
      }
    }
    return new ArrayList<Variable>(varSet);
  }

  /**
   * Default constructor accepts a list of clauses, vars and two conditional marginal estimation
   * objects with an estimateMarginal(Variable var, boolean resample) function
   */
  public ConditionalGames(Data data, String mode1, String mode2, Config config) {
    this.config = config;
    this.data = data;
    this.mode1 = mode1;
    this.mode2 = mode2;
  }
}
