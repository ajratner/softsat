package softsat.generate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import softsat.objects.Variable;
import softsat.objects.VariableId;
import softsat.objects.Literal;
import softsat.objects.Clause;
import softsat.config.Config;
import softsat.util.Pair;

/**
 * Take in a text file describing a weighted SAT problem.
 */
public class SatInputDataGenerator {

  String inputFilePath;

  /**
   * Read in simple weighted Sat clause format.  Each non-blank line is of form:
   *  WEIGHT : LITERAL v LITERAL v ...
   * where . = inf. weight and ! is used for negation
  */
  public Data generateData() {
    ArrayList<Clause> clauses = new ArrayList<Clause>();
    HashMap<String, Variable> vars = new HashMap<String, Variable>();
    try {
      for (String line : Files.readAllLines(Paths.get(inputFilePath))) {
        String[] l = line.trim().split("\\s*:\\s*");
        if (l.length != 2) { continue; }
        Clause clause = new Clause();
        
        // set weight
        clause.setLogWeight(l[0].equals(".") ? Double.POSITIVE_INFINITY : Double.parseDouble(l[0]));

        // set vars / literals
        for (String literalString : l[1].split("\\s+v\\s+")) {
          boolean negated = false;
          Variable var = null;
          if (literalString.startsWith("!")) {
            literalString = literalString.substring(1);
            negated = true;
          }
          if (!vars.containsKey(literalString)) { vars.put(literalString, new Variable(literalString)); }
          var = vars.get(literalString);
          clause.addLiteral(new Literal(var, negated));
        }
        clauses.add(clause);
      }
    } catch (IOException ex) {
      System.out.println(ex.toString());
      return null;
    }

    // var -> clause pointers
    for (Clause clause : clauses) {
      for (Variable var : clause.getVars()) { var.addToClausesIn(clause); }
    }

    // put everything in a single cluster / soft clauses list
    ArrayList<Clause> hardClauses = new ArrayList<Clause>();
    ArrayList<Clause> softClauses = new ArrayList<Clause>();
    for (Clause clause : clauses) {
      if (clause.isHard()) {
        hardClauses.add(clause);
      } else {
        softClauses.add(clause);
      }
    }
    ArrayList<ArrayList<Clause>> clusters = new ArrayList<ArrayList<Clause>>();
    clusters.add(hardClauses);
    return new Data(clusters, softClauses);
  }

  public SatInputDataGenerator(String inputFilePath) {
    this.inputFilePath = inputFilePath;
  }
} 
