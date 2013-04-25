package tw.grinps.scan.reflection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassFileScannerTest {

    private ClassFileScanner scanner;

    @Before
    public void setUp() throws Exception {
        scanner = new ClassFileScanner("tw.sample.soldier");
    }

    @Test
    public void should_get_all_files_in_namespace() {
        String[] classFullNames = scanner.allClassFullNames();

        assertEquals(2, classFullNames.length);
        assertEquals("tw.sample.soldier.Cavalry", classFullNames[0]);
        assertEquals("tw.sample.soldier.Infantry", classFullNames[1]);
    }
}
