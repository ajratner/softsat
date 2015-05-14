package softsat.main;
import softsat.objects.Variable;
import softsat.objects.Literal;

/**
 * The Main.
 */
public class Main {
  
  private static void demoDan() {
    Variable var = new Variable(1,2);
    Literal lit = new Literal(var,true);
    System.out.println("Lit(Var(1,2),true)");
    System.out.println(lit);
  }

  public static void main(String[] args) {
    demoDan();
    
    //    HardClusters dataset = new HardClusters();
    //    dataset.generate(3, 3, 3, 4.0, 0.5, 1.0, 0.1, 1);
    //    dataset.print();
  }
}
