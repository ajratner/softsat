package softsat.util;

import java.util.ArrayList;
import softsat.objects.Clause;

public class PrettyPrinter {

  public static void printListOfClauses(ArrayList<Clause> clauses) {
    for (Clause clause : clauses) { System.out.println(clause.toString()); }
  }

  public static void printClusters(ArrayList<ArrayList<Clause> > clusters) {
    for (ArrayList<Clause> cluster : clusters) 
      { 
	printListOfClauses(cluster); 
	System.out.println("--");
      }
  }

}
