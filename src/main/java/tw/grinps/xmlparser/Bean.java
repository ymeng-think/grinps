package tw.grinps.xmlparser;

public class Bean {
    private final String className;
    private String id;

    public Bean(String className, String id) {
        this.className = className;
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public String getId() {
        return id;
    }
}
