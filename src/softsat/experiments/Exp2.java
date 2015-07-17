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
    config.nSampleSatSteps = 1000000;
    config.nMCSatSteps = 100000;
    config.pSimAnnealStep = 0.5;
    config.pRandomStep = 0.5;
    // Interesting that here min steps post = 100 works the best (0.73), better than 1000... why?
    config.minStepsPostSatFound = 1000;

    String dataFilePath = "examples/network4.sat";
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
      System.out.println(var.getVariableIdString() + " : " + mcsat.estimateMarginal(var, false));
    }
  }
}
