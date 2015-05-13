import org.junit.* ;
import static org.junit.Assert.* ;
import softsat.objects.Variable;

public class VariableTest {

   @Test
   public void test_toString() {
     Variable var = new Variable(1,1);
     assertTrue(var.toString().equals("<1:1>"));
   }

}
