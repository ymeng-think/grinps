package tw.grinps.scan.reflection;

import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;

public class ClassScanner implements TypeScanner {

    private String namespace;

    public ClassScanner(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public List<Class<?>> allClasses() {
        ClassFileScanner classFileScanner = new ClassFileScanner(namespace);
        String[] classFullNames = classFileScanner.allClassFullNames();

        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (String fullName : classFullNames) {
            classes.add(parseClass(fullName));
        }

        return classes;
    }

    private Class<?> parseClass(String classFullName) {
        try {
            return Class.forName(classFullName);
        } catch (ClassNotFoundException e) {
            throw new InvalidClassFullNameException(format("Class is {0}", classFullName));
        }
    }

}
