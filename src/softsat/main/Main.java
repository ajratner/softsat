package softsat.main;
import java.util.ArrayList;

import softsat.test.BasicTests;
import softsat.experiments.Exp1;
import softsat.experiments.Exp2;
import softsat.config.Config;

/**
 * The Main.
 */
public class Main {
  public static void main(String[] args) {
    //BasicTests.runAll();
    //Exp1 exp = new Exp1();
    Exp2 exp = new Exp2();
    exp.run();
  }
}
