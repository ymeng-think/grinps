package tw.grinps.scan.reflection;

import org.junit.Before;
import org.junit.Test;
import tw.sample.soldier.Cavalry;
import tw.sample.soldier.Infantry;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReflectionTest {

    private Reflection reflection;

    @Before
    public void setUp() throws Exception {
        reflection = new Reflection("tw.sample.soldier");
    }

    @Test
    public void should_get_all_classes_in_namespace() {
        List<Class<?>> classes = reflection.allClasses();

        assertEquals(2, classes.size());
        assertEquals(Cavalry.class, classes.get(0));
        assertEquals(Infantry.class, classes.get(1));
    }
}
