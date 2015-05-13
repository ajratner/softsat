package softsat.generate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import softsat.objects.Clause;
import softsat.objects.Atom;

/**
 * Generic template for a dataset of atoms and clauses
 */
public class Dataset {
  Random rand = new Random();
    
  public ArrayList<Atom> atoms = new ArrayList<Atom>();
  public ArrayList<Clause> clauses = new ArrayList<Clause>();

  public void print() {
    for (Clause c : clauses) {
      System.out.println(c.toString());
    }
  }
} 
