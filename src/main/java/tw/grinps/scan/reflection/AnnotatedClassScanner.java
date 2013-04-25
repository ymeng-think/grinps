package tw.grinps.scan.reflection;

import tw.grinps.annotation.Bean;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AnnotatedClassScanner implements TypeScanner {

    private TypeScanner scanner;
    private Class<?> annotationType;

    public AnnotatedClassScanner(TypeScanner scanner) {
        this.scanner = scanner;
    }

    public AnnotatedClassScanner annotatedBy(Class<?> annotationType) {
        this.annotationType = annotationType;
        return this;
    }

    @Override
    public List<Class<?>> allClasses() {
        List<Class<?>> classes = scanner.allClasses();
        List<Class<?>> filteredClasses = new ArrayList<Class<?>>();

        for (Class<?> cls : classes) {
            if (hasAnnotatedBy(cls, annotationType)) {
                filteredClasses.add(cls);
            }
        }

        return filteredClasses;
    }

    private boolean hasAnnotatedBy(Class<?> cls, Class<?> annotationType) {
        Annotation[] annotations = cls.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == annotationType) {
                return true;
            }
        }
        return false;
    }
}
