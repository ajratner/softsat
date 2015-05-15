import org.junit.* ;
import static org.junit.Assert.* ;
import softsat.objects.Variable;
import softsat.objects.VariableId;

public class VariableTest {

   @Test
   public void test_toString() {
     Variable var = new Variable(new VariableId(1,1));
     assertEquals(var.toString(),"<1:1>");
   }

}
