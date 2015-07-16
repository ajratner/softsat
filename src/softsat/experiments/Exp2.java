package softsat.experiments;

import softsat.generate.Data;
import softsat.config.Config;
import softsat.generate.SatInputDataGenerator;
import softsat.inference.DecompMCSat;
import softsat.objects.Clause;
import softsat.objects.Variable;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Simple MC-SAT example with outgoing network edges
 */
public class Exp2 {
  private Config config;
  private Data data;

  public void run() {
    config = new Config();

    String dataFilePath = "inputs/network2.sat";
    System.out.println("Loading SAT problem from " + dataFilePath);
    SatInputDataGenerator dataGen = new SatInputDataGenerator(dataFilePath);
    data = dataGen.generateData();

    System.out.println("Hard clauses:");
    for (ArrayList<Clause> cluster : data.clusters) {
      for (Clause clause : cluster) { System.out.println(clause); }
    }
    System.out.println("Soft clauses:");
    for (Clause clause : data.softClauses) { System.out.println(clause); }

    System.out.println("Running basic MC-SAT");
    DecompMCSat mcsat = new DecompMCSat(data, config, "basic");
    mcsat.sample();

    System.out.println("Computing marginals for all vars:");
    for (Variable var : data.vars) {
      System.out.println(var + " : " + mcsat.estimateMarginal(var, false));
    }
  }
}
