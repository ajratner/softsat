package softsat.generate;

import java.util.ArrayList;
import softsat.objects.Clause;
import softsat.objects.SoftClause;

public class Data {
  public ArrayList<ArrayList<Clause> > clusters;
  public ArrayList<SoftClause> softClauses;

  public Data(ArrayList<ArrayList<Clause> > clusters,ArrayList<SoftClause> softClauses) {
    this.clusters = clusters;
    this.softClauses = softClauses;
  }
}
