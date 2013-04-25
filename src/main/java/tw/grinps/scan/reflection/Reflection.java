package tw.grinps.scan.reflection;

import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;

public class Reflection {

    private String namespace;

    public Reflection(String namespace) {
        this.namespace = namespace;
    }

    public List<Class<?>> allClasses() {
        ClassFileScanner classFileScanner = new ClassFileScanner(namespace);
        String[] classFullNames = classFileScanner.getAllClassFullNames();

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
