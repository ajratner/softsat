package softsat.generate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.HashMap;

import softsat.objects.Variable;
import softsat.objects.VariableId;
import softsat.objects.Literal;
import softsat.objects.Clause;
import softsat.config.Config;
import softsat.util.Pair;

/**
 * Take in an MLN in format similar to Alchemy/Tuffy.  This is VERY ROUGH and currently
 * supports a single test case only...
 */
public class MLNDataGenerator {

  /**
   * Read in an MLN input format.  Consists of typed atoms, objects, and weighted rules
   */
  public Data readMLN(String filepath) {
   HashMap<String, ArrayList<String>> atoms = new HashMap<String, ArrayList<String>>();
   HashMap<String, ArrayList<String>> objects = new HashMap<String, ArrayList<String>>();
   ArrayList<Variable> vars = new ArrayList<Variable>();
   ArrayList<Clause> clauses = new ArrayList<Clause>();

   int section = -1;
   for (String line : Files.readAllLines(Paths.get(filepath))) {
     if (line.trim().equals("Atoms:")) { section = 0;
     } else if (line.trim().equals("Objects:")) { section = 1;
     } else if (line.trim().equals("Rules:")) { section = 2;

     // Read in atoms
     } else if (section == 0) {
       String[] parts = line.trim().split(":\\s*");
       if (parts.length == 2) {
         atoms.put(parts[0], new ArrayList(parts[1].split(",\\s*")));
       }

     // Read in objects
     } else if (section == 1) {
       String[] parts = line.trim().split("\\(|\\)");
       if (parts.length == 3) {
         objects.put(parts[0], new ArrayList(parts[1].trim().split(",\\s*")));
       }

     // Read in weighted rules and assemble clauses
     } else if (section == 2) {
       String[] parts = line.trim().split("\\s*:\\s*");
       if (parts.length == 2) {

         // Handle EXISTS

         // Handle OR (" v ") and NOT ("!") for now
         String[] ored = parts[1].trim().split("\\sv\\s");


       



  private HashMap<VariableId,Variable> varMap;

  private Random rand = new Random();
  
  /**
   * Generate a set of atoms and clauses as random k-SAT instance
   */
  private ArrayList<Clause> generateRandomKSAT(int clusterId, int n, int k, double alpha) {

    ArrayList<Variable> variables = new ArrayList<Variable>();
    for (int index = 0; index < n; index++) {
      VariableId varId = new VariableId(clusterId,index);
      Variable var = new Variable(varId);
      varMap.put(varId,var);
      variables.add(var);
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

    // var -> [clause1,...] pointers
    for (Clause clause : clauses) {
      for (Variable var : clause.getVars()) { var.addToClausesIn(clause); }
    }
    return clauses;
  }

  public Data generateData() {
    varMap = new HashMap<VariableId,Variable>();

    ArrayList<ArrayList<Clause> > clusters = new ArrayList<ArrayList<Clause> >();
    ArrayList<Clause> softClauses = new ArrayList<Clause>();

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
            Variable var1 = varMap.get(new VariableId(clusterId1,varIds[litId]));
            Variable var2 = varMap.get(new VariableId(clusterId2,varIds[litId]));
            assert var1 != null;
            assert var2 != null;
            literals.add(new Literal(var1,negateds[litId]));
            literals.add(new Literal(var2,negateds[litId]));
          }
          /* [SERIAL] */
          Clause softClause = new Clause(literals,rand.nextGaussian()*softWeightStd + softWeightMean);
          for (Variable var : softClause.getVars()) { var.addToClausesIn(softClause); }
          softClauses.add(softClause);
        }
      }
    }
    return new Data(clusters, softClauses);
  }

  private int nClusters;
  private int n;
  private int k;
  private double alpha;
  private int numSoftClauses;
  private int clusterNodesPerSoftClause;
  private double softWeightMean;
  private double softWeightStd;

  public HardSoftDataGenerator(Config config) {
    this.nClusters = config.nClusters;
    this.n = config.n;
    this.k = config.k;
    this.alpha = config.alpha;
    this.numSoftClauses = config.numSoftClauses;
    this.clusterNodesPerSoftClause = config.clusterNodesPerSoftClause;
    this.softWeightMean = config.softWeightMean;
    this.softWeightStd = config.softWeightStd;
  }
} 
