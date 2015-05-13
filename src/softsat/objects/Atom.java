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

  // Static method for creating atom id based on cluster id and atomid ints
  public static String idString(int clusterId, int atomId) {
    return Integer.toString(clusterId) + "_" + Integer.toString(atomId);
  }

  // Create an Atom
  public Atom(String idString) {
    id = idString;
  }
}
