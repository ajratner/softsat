package softsat.generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import softsat.objects.Clause;
import softsat.objects.Literal;
import softsat.objects.Variable;
import softsat.objects.VariableId;

public class Data {
  public ArrayList<ArrayList<Clause>> clusters;
  public ArrayList<Clause> softClauses;
  public ArrayList<Variable> vars;

  private ArrayList<Variable> getAllVars() {
    HashSet<Variable> varSet = new HashSet<Variable>();
    for (ArrayList<Clause> clauses : clusters) {
      for (Clause clause : clauses) {
        for (Variable var : clause.getVars()) { varSet.add(var); }
      }
    }
    for (Clause clause : softClauses) {
      for (Variable var : clause.getVars()) { varSet.add(var); }
    }
    return new ArrayList<Variable>(varSet);
  }

  public Data deepCopy() {
    ArrayList<ArrayList<Clause>> clustersCopy = new ArrayList<ArrayList<Clause>>();
    ArrayList<Clause> softClausesCopy = new ArrayList<Clause>();

    HashMap<Variable, Variable> varMap = new HashMap<Variable, Variable>();

    // Copy the hard clusters
    for (ArrayList<Clause> cluster : clusters) {
      ArrayList<Clause> clusterCopy = new ArrayList<Clause>();
      for (Clause clause : cluster) {
        Clause clauseCopy = new Clause();
        clauseCopy.setLogWeight(clause.getLogWeight());
        for (Literal literal : clause.getLiterals()) {
          Variable oldVar = literal.getVar();
          if (varMap.containsKey(oldVar)) {
            clauseCopy.addLiteral(new Literal(varMap.get(oldVar), literal.getNegated()));
          } else {
            VariableId newVarId = new VariableId(oldVar.getClusterId(), oldVar.getIndex()); 
            Variable newVar = new Variable(newVarId);
            varMap.put(oldVar, newVar);
            clauseCopy.addLiteral(new Literal(newVar, literal.getNegated()));
          }
        }
        clusterCopy.add(clauseCopy);
      }
      for (Clause clauseCopy : clusterCopy) {
        for (Variable varCopy : clauseCopy.getVars()) { 
          varCopy.addToClausesIn(clauseCopy); 
        }
      }
      clustersCopy.add(clusterCopy);
    }

    // Copy the soft connectors
    for (Clause softClause : softClauses) {
      Clause softClauseCopy = new Clause();
      softClauseCopy.setLogWeight(softClause.getLogWeight());
      for (Literal literal : softClause.getLiterals()) {
        Variable oldVar = literal.getVar();
        softClauseCopy.addLiteral(new Literal(varMap.get(oldVar), literal.getNegated()));
      }
      for (Variable varCopy : softClauseCopy.getVars()) {
        varCopy.addToClausesIn(softClauseCopy);
      }
      softClausesCopy.add(softClauseCopy);
    }
    return new Data(clustersCopy, softClausesCopy);
  }

  public Data(ArrayList<ArrayList<Clause>> clusters, ArrayList<Clause> softClauses) {
    this.clusters = clusters;
    this.softClauses = softClauses;
    this.vars = getAllVars();
  }
}
