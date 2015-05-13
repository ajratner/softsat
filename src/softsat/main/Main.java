package softsat.main;

import softsat.generate.HardClusters;

/**
 * The Main.
 */
public class Main {
  public static void main(String[] args) {
    HardClusters dataset = new HardClusters();
    dataset.generate(3, 3, 3, 4.0, 0.5, 1.0, 0.1, 1);
    dataset.print();
  }
}
