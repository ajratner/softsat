package softsat.generate;

import java.util.ArrayList;
import softsat.objects.Clause;

public class Data {
  public ArrayList<ArrayList<Clause>> clusters;
  public ArrayList<Clause> softClauses;

  public Data(ArrayList<ArrayList<Clause>> clusters, ArrayList<Clause> softClauses) {
    this.clusters = clusters;
    this.softClauses = softClauses;
  }
}
