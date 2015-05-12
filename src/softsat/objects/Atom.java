package softsat.objects;

import java.util.ArrayList;

/**
 * An atom
 */
public class Atom {
  public String id = null;

  // The truth value
  public boolean value = false;

  // Returns the string representation of the Atom object
  public String toString() {
    return "<Atom" + id + ">";
  }

  // Create an Atom
  public Atom(String idString) {
    id = idString;
  }
}
