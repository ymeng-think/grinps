package tw.grinps.scan.reflection;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClassFileScannerTest {

    private ClassFileScanner scanner;

    @Before
    public void setUp() throws Exception {
        scanner = new ClassFileScanner("tw.sample.soldier");
    }

    @Test
    public void should_get_all_files_in_namespace() {
        List<ClassInfo> classInfos = scanner.allClassFiles();

        assertEquals(2, classInfos.size());
    }
}
