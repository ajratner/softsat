import org.junit.* ;
import static org.junit.Assert.* ;
import softsat.objects.Variable;

public class VariableTest {

   @Test
   public void test_toString() {
     Variable var = new Variable(1,1);
     assertEquals(var.toString(),"<1:1>");
   }

}
