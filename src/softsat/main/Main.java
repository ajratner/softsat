package softsat.main;
import java.util.ArrayList;

import softsat.test.BasicTests;
import softsat.experiments.Exp1;
import softsat.config.Config;

/**
 * The Main.
 */
public class Main {
  public static void main(String[] args) {
    //BasicTests.runAll();
    Exp1 exp = new Exp1();
    exp.run();
  }
}
