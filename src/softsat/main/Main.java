package softsat.main;
import java.util.ArrayList;

import softsat.objects.Variable;
import softsat.objects.Literal;
import softsat.objects.Clause;
import softsat.util.PrettyPrinter;
import softsat.generate.BGMCSatDataGenerator;
import softsat.generate.Data;
import softsat.objects.SoftClause;
import softsat.inference.BGMCSat;
import softsat.inference.VanillaMCSat;
import softsat.sat.SampleSat;
import softsat.test.BasicTests;
import softsat.config.Config;
/**
 * The Main.
 */
public class Main {
  
  private static void sanityBGMCSatData() {
    Config config = new Config();
    BGMCSatDataGenerator datagen = new BGMCSatDataGenerator(config);
    Data data = datagen.generateData();
    PrettyPrinter pp = new PrettyPrinter();
    System.out.println("--BGMCSat--\n");
    BGMCSat bgmcsat = new BGMCSat(data,config);
    pp.printClusters(bgmcsat.getClusters());

    System.out.println("\n\n--Vanilla--\n");
    VanillaMCSat vmcsat = new VanillaMCSat(data,config);
    pp.printClusters(vmcsat.getClusters());

  }

  public static void main(String[] args) {
    BasicTests.runAll();
  }
}
