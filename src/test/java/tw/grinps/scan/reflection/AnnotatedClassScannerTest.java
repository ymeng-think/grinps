package tw.grinps.scan.reflection;

import org.junit.Before;
import org.junit.Test;
import tw.grinps.annotation.Bean;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AnnotatedClassScannerTest {

    private AnnotatedClassScanner scanner;

    @Before
    public void setUp() throws Exception {
        scanner = new AnnotatedClassScanner(new ClassScanner("tw.sample.soldier"));
    }

    @Test
    public void should_only_filter_class_with_bean_annotation() {
        scanner.annotatedBy(Bean.class);

        List<Class<?>> classes = scanner.allClasses();

        assertEquals(1, classes.size());
    }
}
